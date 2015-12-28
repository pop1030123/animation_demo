package com.pop.anim;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;


import com.pop.anim.turnpage.BlackSquareFadeAway;
import com.pop.anim.turnpage.BlackSquareZoomIn;
import com.pop.anim.turnpage.ITurnPage;
import com.pop.anim.turnpage.ShutterDown2Up;
import com.pop.anim.turnpage.ShutterLeft2Right;
import com.pop.anim.turnpage.ShutterRight2Left;
import com.pop.anim.turnpage.ShutterUp2Down;
import com.pop.anim.turnpage.TranslateLeft;
import com.pop.anim.turnpage.TranslateRight;
import com.pop.anim.turnpage.util.BitmapUtil;
import com.pop.anim.turnpage.widget.TurnPageView;

import java.util.Random;

/**
 * Created by mips on 8/2/14.
 */

public class SlideShowActivity extends Activity {

    public static final String TAG = "SlideShow:";
    private TurnPageView mTurnPageView=null;
    private static final int MSG_NEXT = 1 ;

    int type ;

    private ITurnPage mBlackSquareFadeAway ,mBlackSquareZoomIn ,mShutterDown2Up ,mShutterLeft2Right,mShutterRight2Left,mShutterUp2Down,mTranslateLeft,mTranslateRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBlackSquareFadeAway = new BlackSquareFadeAway() ;
        mBlackSquareZoomIn = new BlackSquareZoomIn() ;
        mShutterDown2Up = new ShutterDown2Up() ;
        mShutterLeft2Right = new ShutterLeft2Right() ;
        mShutterRight2Left = new ShutterRight2Left() ;
        mShutterUp2Down = new ShutterUp2Down() ;
        mTranslateLeft = new TranslateLeft() ;
        mTranslateRight = new TranslateRight() ;


        mTurnPageView=new TurnPageView(this);
        mTurnPageView.getHolder().addCallback(callBack);
        mTurnPageView.setCallback(new TurnPageView.TurnPageViewCallback() {
            @Override
            public void animFinished() {
                mHandler.sendEmptyMessageDelayed(MSG_NEXT, 2000);
            }
        });

        setContentView(mTurnPageView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        type = getIntent().getIntExtra("type" ,0) ;
        Log.d(TAG, "onResume:type:" + type) ;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeMessages(MSG_NEXT);
    }

    private int curBitmapIndex=0;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_NEXT:
                    Log.d(TAG, "msg_next---curBitmapIndex:" + curBitmapIndex);
                    prepare();
                    mTurnPageView.notifyTurnPage();
                    break ;
            }
        }
    } ;
    void prepare(){
        Log.d(TAG ,"prepare....") ;
        int cur = curBitmapIndex%App.mBitmaps.length ;
        curBitmapIndex++ ;
        int next = curBitmapIndex%App.mBitmaps.length ;
        if(curBitmapIndex>=App.mBitmaps.length){
            curBitmapIndex = next ;
        }
        // set animation
        mTurnPageView.setTurnPageStyle(getTurnPageAnimation());
        // set 2 bitmaps
        mTurnPageView.setBitmaps(new Bitmap[]{App.mBitmaps[next],App.mBitmaps[cur]});
    }


    private SurfaceHolder.Callback callBack=new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG, "surfaceCreated ------");
            prepare();
            mTurnPageView.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d(TAG ,"surfaceDestroyed ------------");
            mTurnPageView.stop();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.d(TAG, "surfaceChanged: format:" + format + "----width：" + width + "height: " + height);
        }
    };


    @Override
    protected void onDestroy() {
//        for(int i=0;i<mBitmaps.length;i++)
//        {
//            if(null!=mBitmaps[i]&&!mBitmaps[i].isRecycled())
//            {
//                mBitmaps[i].recycle();
//            }
//        }
        super.onDestroy();
    }

    private ITurnPage getTurnPageAnimation(){
       switch (type){
           default:
               return mBlackSquareFadeAway ;
           case 1:
               return mBlackSquareZoomIn ;
           case 2:
               return mShutterDown2Up ;
           case 3:
               return mShutterLeft2Right ;
           case 4:
               return mShutterRight2Left ;
           case 5:
               return mShutterUp2Down ;
           case 6:
               return mTranslateLeft ;
           case 7:
               return mTranslateRight ;
       }
    }
}
