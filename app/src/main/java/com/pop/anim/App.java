package com.pop.anim;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.pop.anim.turnpage.util.BitmapUtil;

/**
 * Created by pengfu on 15/12/12.
 */

public class App extends Application {

    private static final String TAG = "SlideShow:";
    /**
     * width of screen in pixels
     */
    public static int SCREEN_WIDTH = 0;
    /**
     * height of screen in pixels
     */
    public static int SCREEN_HEIGHT = 0;
    /**
     * this value used to transform pix to dip unit, pix = dp * SCREEN_DENSITY
     * screen density if screen size is 320*480 in pixels than SCREEN_DENSITY is 1.0f,
     * wile if screen size is 480*800 than SCREEN_DENSITY is 1.5f
     */
    public static float SCREEN_DENSITY = 0F;

    private static App instance ;

    public static Bitmap[] mBitmaps;
    @Override
    public void onCreate() {
        super.onCreate();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        SCREEN_DENSITY = dm.density;

        instance = this ;
        loadMyPics();
    }

    public static App getInstance(){
        return instance ;
    }

    public static void showToast(int res){
        showToast(getInstance().getString(res));
    }
    public static void showToast(String msg){
        Toast.makeText(getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    private void loadMyPics() {
//        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI ,new String[]{MediaStore.Images.Media.DATA} ,null,null,null) ;
        mBitmaps = new Bitmap[3] ;
        mBitmaps[0] = BitmapUtil.getFitBitmapFromResource(getResources(), R.drawable.img1, App.SCREEN_WIDTH, App.SCREEN_HEIGHT)  ;
        mBitmaps[1] = BitmapUtil.getFitBitmapFromResource(getResources() ,R.drawable.img2, App.SCREEN_WIDTH, App.SCREEN_HEIGHT)  ;
        mBitmaps[2] = BitmapUtil.getFitBitmapFromResource(getResources() ,R.drawable.img3, App.SCREEN_WIDTH, App.SCREEN_HEIGHT)  ;
        Log.d(TAG, "mBitmaps length--" + mBitmaps.length);
    }

}
