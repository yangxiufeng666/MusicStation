package com.devilyang.musicstation;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnInfoListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.utils.StringUtils;
import io.vov.vitamio.widget.VideoView;

import java.util.ArrayList;

import com.devilyang.musicstation.bean.PlayInfoBean;
import com.devilyang.musicstation.net.LogUtil;
import com.devilyang.musicstation.util.Util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class VideoViewPlayerActivity extends Activity implements
		OnBufferingUpdateListener, OnPreparedListener, OnInfoListener,
		OnCompletionListener,OnTouchListener{
	private static final String TAG = "VideoViewPlayerActivity";
	private static final int sDefaultTimeout = 5000;
	private static final int FADE_OUT = 1;
	private static final int SHOW_PROGRESS = 2;
	private boolean mShowing;
	private boolean mDragging;
	private long mDuration;
	private int mVideoLayout = 1;
	private VideoView mVideoView;
	private ProgressBar progressBar;
	private TextView downloadRate;
	private TextView loadRate;
	private LinearLayout bufferLayout;
	private String path;
	private long currentPosition = 0;
	private String playMode;
	
	private RelativeLayout topLayout;
	private TextView movieName;
	private TextView systemTimes;
	private ImageView batteryImg;
	
	private View playProgressView;
	private ImageButton playBtn;
	private TextView modeBtn;
	private TextView mCurrentTime;
	private TextView endTime;
	private SeekBar seekBar;
	private Button displayModebtn;
	
	private boolean isOnPause = false;
	private boolean isComPlete = false;
	
	PlayInfoBean playInfoBean;
	
	Runnable delayRunnable = new Runnable() {

		@Override
		public void run() {
			topLayout.setVisibility(View.GONE);
			playProgressView.setVisibility(View.GONE);
			displayModebtn.setVisibility(View.GONE);
			mShowing = false;
			dismissPopWindow();
		}
	};
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			LogUtil.i("duration", "msg.what = " + msg.what);
			switch (msg.what) {
			case FADE_OUT:
				// hide();
				break;
			case SHOW_PROGRESS:
				setProgress();
				LogUtil.i("duration", "mDragging = " + mDragging);
				LogUtil.i("duration", "mShowing = " + mShowing);
				if (!mDragging && mShowing) {
					msg = obtainMessage(SHOW_PROGRESS);
					sendMessageDelayed(msg, 1000);// - (pos % 1000)
					updatePausePlay();
				}
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!LibsChecker.checkVitamioLibs(this))
			return;
		setContentView(R.layout.video_view);
		playInfoBean = getIntent().getParcelableExtra("playInfo");
		findView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isOnPause){
			startVideoPlayback();
			isOnPause = false;
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(broadcastReceiver, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		isOnPause = true;
		Log.v(TAG, "onPause()....");
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	long currentTimes;

	@Override
	public void onBackPressed() {
		long temCurTimes = System.currentTimeMillis();
		if (temCurTimes - currentTimes > 2000) {
			currentTimes = temCurTimes;
			Toast.makeText(this, "在按一次推出播放", Toast.LENGTH_SHORT).show();
			return;
		}
		super.onBackPressed();

	}

	private void findView() {
		mVideoView = (VideoView) findViewById(R.id.videoView);
		bufferLayout = (LinearLayout) findViewById(R.id.buffering_layout);
		progressBar = (ProgressBar) findViewById(R.id.probar);
		downloadRate = (TextView) findViewById(R.id.download_rate);
		loadRate = (TextView) findViewById(R.id.load_rate);
		topLayout = (RelativeLayout)findViewById(R.id.top);
		movieName = (TextView)findViewById(R.id.play_title);
		systemTimes = (TextView)findViewById(R.id.system_time);
		batteryImg = (ImageView)findViewById(R.id.battery_icon);
		
		movieName.setText(playInfoBean.getTitle());
		
		playProgressView = (View) findViewById(R.id.media_progress_layout);
		playBtn = (ImageButton) findViewById(R.id.mediacontroller_play_pause);
		modeBtn = (TextView) findViewById(R.id.current_fenbianlv);
		mCurrentTime = (TextView) findViewById(R.id.mediacontroller_time_current);
		endTime = (TextView) findViewById(R.id.mediacontroller_time_total);
		seekBar = (SeekBar) findViewById(R.id.mediacontroller_seekbar);
		displayModebtn = (Button)findViewById(R.id.display_mode_btn);
		playBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				doPauseResume();
			}
		});
		seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
		modeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
		playVideo();
	}

	private void playVideo() {
		
		try {
			path = playInfoBean.getHurl();
			Uri uri = Uri.parse(path);
			mVideoView.setVideoURI(uri);
			mVideoView.requestFocus();
			mVideoView.setOnBufferingUpdateListener(this);
			mVideoView.setOnCompletionListener(this);
			mVideoView.setOnInfoListener(this);
			mVideoView.setOnPreparedListener(this);
			mVideoView.setOnTouchListener(this);
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		
		mVideoView.start();
		
	}

	private int mCurrentBufferPercentage;
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		mCurrentBufferPercentage = percent;
		loadRate.setText(percent + "%");
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		startVideoPlayback();
	}

	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:
			if (mVideoView.isPlaying()) {
				mVideoView.pause();
				bufferLayout.setVisibility(View.VISIBLE);
				downloadRate.setText("");
				loadRate.setText("");
			}
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			mVideoView.start();
			bufferLayout.setVisibility(View.GONE);
			break;
		case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
			downloadRate.setText("" + extra + "kb/s" + "  ");
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		Log.v(TAG, "onCompletion....");
		isComPlete = true;
		this.finish();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		DisplayFloatView();
		return false;
	}
	private void showPopWindow(){
		LayoutInflater mLayoutInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		
	}
	private void dismissPopWindow(){
		
	}
	private void DisplayFloatView() {
		mHandler.removeCallbacks(delayRunnable);
		if (topLayout.isShown() || playProgressView.isShown()) {
			topLayout.setVisibility(View.GONE);
			playProgressView.setVisibility(View.GONE);
			displayModebtn.setVisibility(View.GONE);
			dismissPopWindow();
			mShowing = false;
		} else {
			systemTimes.setText(Util.getCurrentSystemTimes());
			topLayout.setVisibility(View.VISIBLE);
			playProgressView.setVisibility(View.VISIBLE);
			displayModebtn.setVisibility(View.VISIBLE);
			modeBtn.setText(playMode);
			mShowing = true;
			Message msg = Message.obtain();
			msg.what = SHOW_PROGRESS;
			mHandler.sendMessage(msg);
			mHandler.postDelayed(delayRunnable, sDefaultTimeout);
		}
	}

	private void doPauseResume() {
		if (mVideoView.isPlaying())
			mVideoView.pause();
		else{
			mVideoView.start();
		}
		updatePausePlay();
	}

	private void updatePausePlay() {
		if (playBtn == null)
			return;
		if (mVideoView.isPlaying())
			playBtn.setImageResource(R.drawable.mediacontroller_pause);
		else {
			playBtn.setImageResource(R.drawable.mediacontroller_play);
		}
	}

	private long setProgress() {
		if (mVideoView == null || mDragging)
			return 0;

		long position = mVideoView.getCurrentPosition();
		long duration = mVideoView.getDuration();
		mDuration = duration;
		if (seekBar != null) {
			if (duration > 0) {
				long pos = 1000L * position / duration;
				seekBar.setProgress((int) pos);
			}
			int percent = mCurrentBufferPercentage;
			seekBar.setSecondaryProgress(percent * 10);
		}
		if (endTime != null)
			endTime.setText(StringUtils.generateTime(duration));
		if (mCurrentTime != null)
			mCurrentTime.setText(StringUtils.generateTime(position));

		return position;
	}
	public void changeLayout(View view) {
		mVideoLayout++;
		if (mVideoLayout == 4) {
			mVideoLayout = 0;
		}
		switch (mVideoLayout) {
		case 0:
			mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
			view.setBackgroundResource(R.drawable.mediacontroller_sreen_size_100);
			break;
		case 1:
			mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
			view.setBackgroundResource(R.drawable.mediacontroller_screen_fit);
			break;
		case 2:
			mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
			view.setBackgroundResource(R.drawable.mediacontroller_screen_size);
			break;
		case 3:
			mVideoLayout = VideoView.VIDEO_LAYOUT_ZOOM;
			view.setBackgroundResource(R.drawable.mediacontroller_sreen_size_crop);

			break;
		}
		mVideoView.setVideoLayout(mVideoLayout, 0);
	}
	private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			LogUtil.d(TAG, "onStopTrackingTouch.....seekBar.getProgress() = "+seekBar.getProgress());
			mDragging = false;
			mHandler.postDelayed(delayRunnable, sDefaultTimeout);
			long position = (mDuration * seekBar.getProgress())/1000;
			LogUtil.d(TAG, "onStopTrackingTouch.....position = "+position);
			LogUtil.d(TAG, "onStopTrackingTouch.....mDuration = "+mDuration);
			mVideoView.seekTo(position);
			mHandler.sendEmptyMessageDelayed(SHOW_PROGRESS, 1000);
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			LogUtil.d(TAG, "onStartTrackingTouch.....seekBar.getProgress() = "+seekBar.getProgress());
			mDragging = true;
			mHandler.removeMessages(SHOW_PROGRESS);
			mHandler.removeCallbacks(delayRunnable);
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			LogUtil.d(TAG, "onProgressChanged.....fromUser = "+fromUser+" progress = "+progress);
			if (!fromUser)
		        return;
		}
	};
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Intent.ACTION_BATTERY_CHANGED)){
				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
				int max = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
				int state = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
				switch (state) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					batteryImg.setImageResource(R.drawable.battery_charging);
					return;
				case BatteryManager.BATTERY_STATUS_FULL:
					batteryImg.setImageResource(R.drawable.battery_full);
					return;
				default:
					break;
				}
				if(0<=level && level<=20){
					batteryImg.setImageResource(R.drawable.battery_10);
					return;
				}
				if(20<level && level<=50){
					batteryImg.setImageResource(R.drawable.battery_20);
					return;
				}
				if(50<level && level<=70){
					batteryImg.setImageResource(R.drawable.battery_50);
					return;
				}
				if(70<level && level<=90){
					batteryImg.setImageResource(R.drawable.battery_80);
					return;
				}
				if(90<level && level<=190){
					batteryImg.setImageResource(R.drawable.battery_100);
				}
			}
		}
	};
}
