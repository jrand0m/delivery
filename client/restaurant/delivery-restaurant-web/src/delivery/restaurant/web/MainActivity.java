package delivery.restaurant.web;

import java.text.DateFormat;
import java.util.Date;

import delivery.restaurant.web.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	WebView view;
	String url;
	SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		settings = PreferenceManager.getDefaultSharedPreferences(this);
		String value = settings.getString("server_url", "");
		if (value != null && value.length() > 0) {
			url = value;
		} else {
			url = "http://10.0.2.2:9000/client/restaurant/web/main.html";
			settings.edit().putString("server_url", url).commit();
		}
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		view = (WebView) findViewById(R.id.webview_main);
		view.getSettings().setJavaScriptEnabled(true);
		showResults();
	}

	public void showResults() {
		view.loadUrl(url);
	}

	protected void settingsBtnClicked() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.auth);
		final Date date = new Date();
		final String key = MainActivity.getKey(date);
		((TextView) dialog.findViewById(R.id.auth_label))
				.setText("Please input password. Current date is: "
						+ DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
								DateFormat.SHORT).format(date)/* + ""
				 + key */);
		dialog.findViewById(R.id.auth_ok).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (((EditText) dialog.findViewById(R.id.pin))
								.getText().toString().equals(key)) {
							openSettingsDialog();
							dialog.dismiss();
						}
					}
				});
		dialog.findViewById(R.id.auth_cancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

		dialog.show();
	}

	public static String getKey(Date date) {
		String todaysKey = date.getDay() + "" + date.getDate() + ""
				+ date.getHours() + "" + date.getMonth() + "" + date.getYear();

		return (Math.abs(todaysKey.hashCode()) + "").substring(0, 5);
	}

	public void openSettingsDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.settings);
		dialog.findViewById(R.id.change_settings).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						MainActivity.this.startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
						dialog.dismiss();
					}
				});

		dialog.findViewById(R.id.set_url).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						openSeturlDialog();
						dialog.dismiss();
					}
				});
		dialog.show();
	}

	public void openSeturlDialog() {
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.set_url);
		final EditText setUrlET = (EditText) dialog
				.findViewById(R.id.set_url_input);
		setUrlET.setText(url);
		((Button) dialog.findViewById(R.id.set_url_ok))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String newUrl = setUrlET.getText().toString();
						if (newUrl != "") {
							url = newUrl;
							settings.edit().putString("server_url", url)
									.commit();
							MainActivity.this.showResults();
						}
						dialog.dismiss();
					}
				});
		((Button) dialog.findViewById(R.id.set_url_cancel))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		dialog.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings:
			settingsBtnClicked();
			return true;
		case R.id.menu_refresh:
			showResults();
			return true;
		}
		return false;
	}

	@Override
	public void onBackPressed() {
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}