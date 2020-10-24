package tw.org.tcca.apps.test10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private TextView name;
    private boolean isCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 123);
        } else {
            isCamera = true;
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            isCamera = true;
        }else{
            isCamera = false;
        }
        init();
    }

    private void init(){
        webView = findViewById(R.id.webview);
        name = findViewById(R.id.name);
        initWebView();
    }

    public class MyBrad {
        @JavascriptInterface
        public void startScan(){
            if (isCamera){
                gotoScan();
            }
        }
    }

    private void gotoScan(){
        Intent intent = new Intent(this, ScanActivity.class);
        startActivityForResult(intent, 111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK){
            String code = data.getStringExtra("code");
            webView.loadUrl("javascript:showCode('" + code + "')");
        }
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyBrad(), "brad");

        webView.loadUrl("file:///android_asset/brad.html");
    }
}