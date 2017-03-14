package com.example.subtitle;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class APPApplication extends Application {
	
	public static int screenWidth,screenHeight;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		getScreenDimension();
	}
	
	public void getScreenDimension(){
		WindowManager mWM=((WindowManager) getSystemService(Context.WINDOW_SERVICE));
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		mWM.getDefaultDisplay().getMetrics(mDisplayMetrics);
		screenWidth = mDisplayMetrics.widthPixels;
		screenHeight = mDisplayMetrics.heightPixels;
	}
}
