package delivery.restaurant.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import delivery.restaurant.web.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	WebView view;
	String url;
	SharedPreferences settings;
	ProgressBar pd;
	View pbc;

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
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);
		pd = (ProgressBar) findViewById(R.id.web_view_progress_bar);
		pbc = (RelativeLayout) findViewById(R.id.web_view_progress_bar_container);
		view = (WebView) findViewById(R.id.webview_main);
		view.getSettings().setJavaScriptEnabled(true);
		view.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if(progress < 100 && pd.getVisibility() == ProgressBar.GONE){
					pbc.setVisibility(View.VISIBLE);
                }
                pd.setProgress(progress);
                if(progress == 100) {
                	pbc.setVisibility(View.GONE);
                }
			}
		});
		
		view.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                findViewById(R.id.web_view_err_dialog).setVisibility(View.VISIBLE);
            }
 
     
        });
		
		view.addJavascriptInterface(new JavaScriptInterface(this), "External");
		showResults();
	}

	public void showResults() {
        String htmlContent = "";
        try {
            
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response;
            HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            DefaultHttpClient client = new DefaultHttpClient(httpParameters);
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = entity.getContent();
                    htmlContent = convertToString(inputStream);
                }
            } else throw new Exception();
         } catch (Exception e) {
        	 findViewById(R.id.web_view_err_dialog).setVisibility(View.VISIBLE);
         }

         view.loadDataWithBaseURL(url, htmlContent, "text/html", "utf-8", null);
	}
	
	public String convertToString(InputStream inputStream){
        StringBuffer string = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                string.append(line + "\n");
            }
        } catch (IOException e) {}
        return string.toString();
    }

	protected void settingsBtnClicked() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.auth);
		final Date date = new Date();
		final String key = MainActivity.getKey(date);
		((TextView) dialog.findViewById(R.id.auth_label))
				.setText("Please input password. Current date is: "
						+ DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
								DateFormat.SHORT).format(date)/*
															 * + "" + key
															 */);
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
							refresh();
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
			refresh();
			return true;
		}
		return false;
	}

	private void refresh() {
		findViewById(R.id.web_view_err_dialog).setVisibility(View.GONE);
        pbc.setVisibility(View.VISIBLE);
		showResults();
	}
	
	@Override
	public void onBackPressed() {
		refresh();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}