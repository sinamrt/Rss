package au.edu.myscribble.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import au.edu.myscribble.rssreader.Model.RSSObject;

public class web_explorer extends AppCompatActivity
{

WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        webView  = (WebView)findViewById(R.id.webview_layout);
      // RSSObject rss = (RSSObject) getIntent().getSerializableExtra("");


        String url = getIntent().getStringExtra("rss_object");


        Toast.makeText(getApplicationContext(),"hello" + url,Toast.LENGTH_LONG).show();
       // String url = getIntent().getExtras().getString("rss_object");
        webView.loadUrl(url);


    }
}
