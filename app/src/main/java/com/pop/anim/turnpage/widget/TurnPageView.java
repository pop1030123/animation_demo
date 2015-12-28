package com.pop.anim.turnpage.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

import com.pop.anim.SlideShowActivity;
import com.pop.anim.turnpage.ITurnPage;

/**
 * 
 * @author yanglonghui
 *
 */
public class TurnPageView extends SurfaceView implements Runnable {

    private static final String TAG = SlideShowActivity.TAG;
//    private GestureDetector mGestureDetector; // 手势
//	private IFillingEvent mFillingListener;
	private ITurnPage mTrunPageAnimation;
	private boolean isPause=true;
	private SurfaceHolder holder;
    private TurnPageViewCallback mCallback ;
	private Object mObject=new Object();
	private Bitmap[]mBitmaps;
    boolean isRunning =true;

    public TurnPageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public TurnPageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TurnPageView(Context context) {
		super(context);
		init();
	}

	private void init()
	{
		try {  
            if(android.os.Build.VERSION.SDK_INT>=11)  
            {  
                setLayerType(LAYER_TYPE_SOFTWARE, null);  
            }  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
		
		holder=this.getHolder();
		setZOrderOnTop(true);
		holder.setFormat(PixelFormat.TRANSPARENT);
		setFocusableInTouchMode(true);
//		mGestureDetector=new GestureDetector(mSimpleOnGestureListener);
	}
	
//	public void setOnFillingListener(IFillingEvent mFillingListener)
//	{
//		this.mFillingListener=mFillingListener;
//	}
	
	
	public void setTurnPageStyle(ITurnPage mTrunPageAnimation)
	{
		this.mTrunPageAnimation=mTrunPageAnimation;
	}
	
	public void setBitmaps(Bitmap[]mBitmaps)
	{
		this.mBitmaps=mBitmaps;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		mGestureDetector.onTouchEvent(event); //通知手势识别方法
		return false;
	}


//	private SimpleOnGestureListener mSimpleOnGestureListener=new SimpleOnGestureListener()
//	{
//		final int MIN_DISTANCE = 100;
//		final int MIN_VELOCITY = 200;
//
//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY)
//		{
//
//			 	boolean isHor=(Math.abs(e1.getX() - e2.getX())>Math.abs(e1.getY()-e2.getY()));
//			 	if(isHor)
//			 	{
//			        if (e1.getX() - e2.getX() > MIN_DISTANCE&& Math.abs(velocityX) > MIN_VELOCITY)
//			        {
//			            Log.v("SimpleOnGestureListener", "Fling left");
//
//			            if(null!=mFillingListener)
//			            {
//			            	mFillingListener.onFlingLeft();
//			            }
//			            notifyTurnPage();
//			        }
//			        else if (e2.getX() - e1.getX() > MIN_DISTANCE&& Math.abs(velocityX) > MIN_VELOCITY)
//			        {
//			            Log.v("SimpleOnGestureListener", "Fling right");
//
//			            if(null!=mFillingListener)
//			            {
//			            	mFillingListener.onFlingRight();
//			            }
//			            notifyTurnPage();
//			        }
//			 	}
//			 	else
//			 	{
//			        if (e1.getY() - e2.getY() > MIN_DISTANCE&& Math.abs(velocityX) > MIN_VELOCITY)
//			        {
//			            Log.v("SimpleOnGestureListener", "Fling up");
//			            if(null!=mFillingListener)
//			            {
//			            	mFillingListener.onFlingUp();
//			            }
//			            notifyTurnPage();
//			        }
//			        else if (e2.getY() - e1.getY() > MIN_DISTANCE&& Math.abs(velocityX) > MIN_VELOCITY)
//			        {
//			            Log.v("SimpleOnGestureListener", "Fling down");
//			            if(null!=mFillingListener)
//			            {
//			            	mFillingListener.onFlingDown();
//			            }
//			            notifyTurnPage();
//			        }
//			 	}
//		        return true;
//		}
//	};
	
	// let's be nice with the cpu
	@Override
	public void setVisibility(int visibility) {
        if (getVisibility() != visibility) 
        {
            super.setVisibility(visibility);
            isPause=(visibility == GONE || visibility == INVISIBLE);
        }
	}

	// let's be nice with the cpu
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		isPause=(visibility == GONE || visibility == INVISIBLE);
	}

	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		isPause=(visibility == GONE || visibility == INVISIBLE);
	}
	
	public void notifyTurnPage()
	{
		synchronized (mObject) 
		{
			mObject.notifyAll();
		}
	}
    public void stop(){
        isRunning = false ;
    }
    public void start(){
        Thread thread = new Thread(this) ;
        thread.start();
    }
	
	private void startPlay()
	{
        try {
            Log.d(TAG ,"drawThread:--isRunning:"+isRunning+"--isPause:"+isPause+"--anim:"+mTrunPageAnimation+"--mBitmaps:"+mBitmaps);
                while(isRunning)
                {
                    if(!isPause)
                    {
                        if(null!=mTrunPageAnimation&&null!=mBitmaps)
                        {
                            clearDraw();

                            mTrunPageAnimation.onCreate();
                            mTrunPageAnimation.onTurnPageDraw(holder,mBitmaps,getWidth(),getHeight());
                            mTrunPageAnimation.onDestory();
                            mTrunPageAnimation=null;

                            if(mCallback!=null){
                                mCallback.animFinished();
                            }
                            synchronized (mObject) {
                                mObject.wait();
                            }
                        }
                        else
                        {
                            Thread.sleep(50);
                            break;
                        }
                    }
                    else
                    {
                        Thread.sleep(500);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG ,"Exception:"+e.toString()) ;
            }
    }

	public void clearDraw() {  
		 Canvas canvas = holder.lockCanvas(null);
		 canvas.drawColor(Color.BLACK);// 清除画布
		 holder.unlockCanvasAndPost(canvas);
   }

    @Override
    public void run() {
        startPlay();
    }


    public interface TurnPageViewCallback {
        void animFinished();
    }

    public void setCallback(TurnPageViewCallback callback){
        mCallback = callback ;
    }
}
