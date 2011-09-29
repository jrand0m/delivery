package delivery.restaurant.web;

import delivery.restaurant.web.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class main extends Activity {
	WebView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button settingsBtn = (Button) findViewById(R.id.button1);
		settingsBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				main.this.settingsBtnClicked();

			}
		});
		view = (WebView) findViewById(R.id.webview_main);
		view.getSettings().setJavaScriptEnabled(true);

		view.loadUrl("http://192.168.1.6:9000/client/restaurant/web/main.html");
	}

	protected void settingsBtnClicked() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.auth);
		dialog.findViewById(R.id.auth_ok).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						//if(((EditText)dialog.findViewById(R.id.pin)).getText().equals("111")) {
							openSettingsDialog();
							dialog.dismiss();
						//}
					}
				});
		
		dialog.show();
	}

	public void openSettingsDialog() {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.settings);
		dialog.findViewById(R.id.change_settings).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						main.this.startActivity(new Intent(
								Settings.ACTION_WIRELESS_SETTINGS));
						dialog.dismiss();
					}
				});

		dialog.findViewById(R.id.set_url).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

					}
				});
		dialog.show();
	}

	@Override
	public void onBackPressed() {
	}
}