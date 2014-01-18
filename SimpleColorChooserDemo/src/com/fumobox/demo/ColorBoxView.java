package com.fumobox.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class ColorBoxView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder _sh;
	
	public static final int PADDING_DEFAULT = 4;
	private int _padding = PADDING_DEFAULT;

	public static final int COLOR_DEFAULT = 0xffffffff;
	private int _color = COLOR_DEFAULT;	
	
	public ColorBoxView(Context context) {
		super(context);
		init();
	}

	public ColorBoxView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ColorBoxView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		_sh = getHolder();
		_sh.addCallback(this);
		_sh.setFormat(PixelFormat.TRANSLUCENT);
		
		setFocusable(true);
		setZOrderOnTop(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		draw();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
	}
	
	public void draw() {
		Canvas canvas = _sh.lockCanvas();
		if(canvas == null) {
			return;
		}
		
		float w = this.getWidth();
		float h = this.getHeight();
		float bw = w - _padding * 2f;
		float bh = h - _padding * 2f;
		RectF rect = new RectF(_padding, _padding, _padding + bw, _padding + bh);
		
		Paint p = new Paint();
		p.setAntiAlias(true);
		
		canvas.drawColor(Color.TRANSPARENT, android.graphics.PorterDuff.Mode.CLEAR);

		p.setStyle(Style.FILL);
		p.setColor(_color);
		canvas.drawRoundRect(rect, 4, 4, p);

		p.setStyle(Style.STROKE);
		p.setStrokeWidth(2);
		p.setColor(0xff666666);
		canvas.drawRoundRect(rect, 4, 4, p);
		
		_sh.unlockCanvasAndPost(canvas);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return true;
	}
	
	public int getColor() {
		return _color;
	}
	
	public void setColor(int color) {
		_color = color | 0xff000000;
		draw();
	}
	
}
