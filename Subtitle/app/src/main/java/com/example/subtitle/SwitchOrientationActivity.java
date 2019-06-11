package com.example.subtitle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.VideoView;

import viewlp.DensityUtil;
import viewlp.ScreenOrientationUtil;

/**
 * 根据感应器判断横竖屏来实现VideoView的大小屏切换，即使横竖屏切换被禁止也能用
 * Created by gaolei on 17/3/13.
 */
public class SwitchOrientationActivity extends Activity implements OnClickListener {

	private int screenWidth;
	private int screenHeight;
	private VideoView videoView;
	private LinearLayout ll_playcontroller;
	private ImageView iv_stretch;
	private ImageView iv_recordcourse_start;
	
	//private ScreenSwitchUtils instance;
	private ScreenOrientationUtil instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		screenWidth = display.getWidth();
		screenHeight = display.getHeight();
		setContentView(R.layout.activity_main);

		initView();
		Uri rawUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
		//设置视频路径
		videoView.setVideoURI(rawUri);
		//开始播放视频
		videoView.start();
		//instance = ScreenSwitchUtils.init(this.getApplicationContext());
		instance = ScreenOrientationUtil.getInstance();
	}
	
	
	@Override
	protected void onStart() {
		super.onStart();
		instance.start(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		instance.stop();
	}


	@SuppressLint("NewApi")
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.e("test", "onConfigurationChanged");
		if (instance.isPortrait()) {
			// 切换成竖屏
			LayoutParams params1 = new RelativeLayout.LayoutParams(screenWidth, DensityUtil.dip2px(this, 250));
			videoView.setLayoutParams(params1);
			Log.e("test", "onConfigurationChanged,竖屏");
		} else {
			// 切换成横屏
			LayoutParams params1 = new RelativeLayout.LayoutParams(screenHeight, screenWidth);
			videoView.setLayoutParams(params1);
			Log.e("test", "onConfigurationChanged,横屏");
		}
	}

	private void initView() {
		videoView = findViewById(R.id.sf_play);

		ll_playcontroller = (LinearLayout) findViewById(R.id.ll_playcontroller);
		ll_playcontroller.getBackground().setAlpha(150);

		iv_stretch = (ImageView) findViewById(R.id.iv_stretch);
		iv_recordcourse_start = (ImageView) findViewById(R.id.iv_recordcourse_start);
		iv_stretch.setOnClickListener(this);
		iv_recordcourse_start.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.iv_stretch:
			instance.toggleScreen();
			break;
		}
	}
}
