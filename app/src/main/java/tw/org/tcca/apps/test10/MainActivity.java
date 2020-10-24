package tw.org.tcca.apps.test10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        initWebView();
    }

    public class MyBrad {
        @JavascriptInterface
        public void test2(){
            Log.v("bradlog", "OK");
        }
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyBrad(), "brad");

        webView.loadUrl("file:///android_asset/brad.html");
    }
}