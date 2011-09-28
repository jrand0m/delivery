package delivery.restaurant.web;

import delivery.restaurant.web.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class main extends Activity {
    /** Called when the activity is first created. */ 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        WebView webView = (WebView)findViewById(R.id.webview_main);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("http://192.168.1.6:9000/client/restaurant/web/main.html");
        //webView.set
    }
}