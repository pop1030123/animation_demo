package com.pop.anim.turnpage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.pop.anim.SlideShowActivity;

/**
 * 
 * @author yanglonghui
 *
 */
public class TranslateRight implements ITurnPage {

    private int duration=3000;//动画持续时间
	private PaintFlagsDrawFilter pdf=new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG| Paint.FILTER_BITMAP_FLAG);
	
	public TranslateRight() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTurnPageDraw(SurfaceHolder holder, Bitmap[] bitmap,
			int maxWidth, int maxHeight) {
        int b0w = bitmap[0].getWidth() ;
        int b0h = bitmap[0].getHeight() ;
        Log.d(SlideShowActivity.TAG, "maxWidth:" + maxWidth + "--maxHeight:" + maxHeight + "----b0:" + b0w + "X" + b0h);
        int dx=(maxWidth-b0w)/2;
		int dy=(maxHeight-b0h)/2;
		
		int dx2=(maxWidth-bitmap[1].getWidth())/2;
		int dy2=(maxHeight-bitmap[1].getHeight())/2;
		
		long start= System.currentTimeMillis();
		long runMills=0;
		
		Rect src=new Rect();
		Canvas canvas=null;
		boolean isRunning=true;
		while(isRunning)
		{
			isRunning=((runMills=(System.currentTimeMillis()-start))<duration);
			if(!isRunning)
			{
				runMills=duration;
			}
			
			try {
					canvas=holder.lockCanvas(null);
					canvas.setDrawFilter(pdf);
					canvas.drawColor(Color.BLACK);// 清除画布
					canvas.drawBitmap(bitmap[0], dx, dy, null);
					
					canvas.save();
					canvas.translate(dx2, dy2);
					canvas.translate((int)(maxWidth*((float)runMills/(float)duration)), 0);
					src.set(0, 0, maxWidth, maxHeight);
					canvas.drawBitmap(bitmap[1], src, src, null);
					canvas.restore();
					
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(null!=canvas)
				{
					holder.unlockCanvasAndPost(canvas);
				}
				else
				{
					break;
				}
				
				if(!isRunning)
				{
					break;
				}
			}
		}
	}

	@Override
	public void onDestory() {
		// TODO Auto-generated method stub

	}

}
