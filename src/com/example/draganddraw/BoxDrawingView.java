package com.example.draganddraw;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoxDrawingView extends View {
	public static final String TAG = "BoxDrawingView";
	private static final String KEY = "key";
	
	private Box mCurrentBox;
	private ArrayList<Box> mBoxs = new ArrayList<Box>();
	
	private Paint mBoxPaint;
	private Paint mBackgroundPaint;
	
	public BoxDrawingView(Context context) {
		this(context,null);
	}
	
	public BoxDrawingView(Context context,AttributeSet attrs){
		super(context,attrs);
		setId(655);
		mBoxPaint = new Paint();
		mBoxPaint.setColor(0x22ff0000);
		
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setColor(0xfff8efe0);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		PointF curr = new PointF(event.getX(),event.getY());
		Log.i(TAG,"Received event at x=" + curr.x + ", y=" +curr.y);
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG," ACTION_DOWN");
			mCurrentBox = new Box(curr);
			mBoxs.add(mCurrentBox);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG," ACTION_MOVE");
			if(mCurrentBox != null) {
				mCurrentBox.setCurrent(curr);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i(TAG," ACTION_UP");
			mCurrentBox = null;
			break;
		case MotionEvent.ACTION_CANCEL:
			Log.i(TAG," ACTION_CANCEL");
			mCurrentBox = null;
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawPaint(mBackgroundPaint);
		
		for(Box box : mBoxs){
			float left = Math.min(box.getOrigin().x, box.getCurrent().x);
			float right = Math.max(box.getOrigin().x, box.getCurrent().x);
			float top = Math.min(box.getOrigin().y, box.getCurrent().y);
			float bottom = Math.max(box.getOrigin().y,box.getCurrent().y);
			
			canvas.drawRect(left, top, right, bottom, mBoxPaint);
		}
	}
	
	@Override
	protected Parcelable onSaveInstanceState() {
		// TODO Auto-generated method stub
		Bundle onSaveInstanceState = new Bundle();
		onSaveInstanceState.putParcelable(KEY, super.onSaveInstanceState());
		onSaveInstanceState.putSerializable(KEY, mBoxs);
		return onSaveInstanceState;
	}
	
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		// TODO Auto-generated method stub
		Bundle onSaveInstanceState = (Bundle)state;
		mBoxs = (ArrayList<Box>)onSaveInstanceState.getSerializable(KEY);
		super.onRestoreInstanceState(onSaveInstanceState.getParcelable(KEY));
		invalidate();
	}
}
