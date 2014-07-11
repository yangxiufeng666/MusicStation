package com.devilyang.musicstation.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

public class RoundNetWorkImageView extends NetworkImageView{

	public RoundNetWorkImageView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public RoundNetWorkImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RoundNetWorkImageView(Context context) {
		super(context);
	}
	@Override
	public void setImageResource(int resId) {
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resId);
		setImageBitmap(bmp);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		if (bm != null) {
			Bitmap output = initBoundBmp(bm);
			super.setImageBitmap(output);
		}
	}
	
	/**
	 * 
	 * @param bm 剪切圆形图片
	 * @return
	 */
	private Bitmap initBoundBmp(Bitmap bm) {
		int w = bm.getWidth();
		int h = bm.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);//
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		float roundPx = w > h ? w / 2 : h / 2;
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bm, rect, rect, paint);
		return output;
	}
}
