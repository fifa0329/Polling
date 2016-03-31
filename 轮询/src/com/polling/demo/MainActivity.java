package com.polling.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.polling.R;
import com.polling.util.NotificationUtil;
import com.polling.util.PollingUtil;

public class MainActivity extends Activity {

	private TextView mTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) { // ���ô˷����Ͳ������onNewIntent
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.textView);
		test();
	}

	// MainActivity��launchMode����Ϊstandard�����򲻻����onNewIntent����
	@Override
	protected void onNewIntent(Intent intent) { // ���ô˷����Ͳ������onCreate
		Log.i("My", "onNewIntent");
		super.onNewIntent(intent);
		setIntent(intent); // ��ֵ��Activity��Intent�����������getIntent()���ǵõ��ϵ�Intent
		test();
	}

	private void test() {
		Intent intent = getIntent();
		mTextView.setText("" + intent.getIntExtra("count", -1));
	}

	public void startPolling(View v) {
		Bundle bundle = new Bundle();
		bundle.putInt("count", 0);
		PollingUtil.start(new PollingManager(), getApplicationContext(),
				bundle, 5 * 1000, 10 * 1000);
	}

	public void cancelPolling(View v) {
		PollingUtil.cancel(MainActivity.this);
	}

	public void cancelAllNotifies(View v) {
		NotificationUtil.cancelAllNotifies(this);
	}
}
