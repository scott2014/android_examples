package com.tencent.news.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ResizeHeightAnimation extends Animation {

	final int startHeight;
	final int targetHeight;
	View view;
 
	public ResizeHeightAnimation(View view, int startHeight, int targetHeight) {
		this.view = view;
		this.targetHeight = targetHeight;
		this.startHeight  = startHeight;
	}
 
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		int newHeight = (int) (startHeight + (targetHeight - startHeight) * interpolatedTime);
		view.getLayoutParams().height = newHeight;
		view.requestLayout();
	}
 
	@Override
	public void initialize(int width, int height, int parentWidth, int parentHeight) {
		super.initialize(width, height, parentWidth, parentHeight);
	}
 
	@Override
	public boolean willChangeBounds() {
		return true;
	}
}
