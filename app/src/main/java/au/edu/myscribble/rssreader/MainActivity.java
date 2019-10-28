package au.edu.myscribble.rssreader;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

  import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;


import au.edu.myscribble.rssreader.Adapter.FeedAdapter;
import au.edu.myscribble.rssreader.Common.HTTPDataHandler;
import au.edu.myscribble.rssreader.Model.RSSObject;



public class MainActivity extends AppCompatActivity
{

    Toolbar toolbar;
    RecyclerView recyclerView;
    RSSObject rssObject;

    //RSS link
   // private final String RSS_ny="http://rss.nytimes.com/services/xml/rss/nyt/Science.xml";


    private final String RSS_deepmind = "https://deepmind.com/blog/feed/basic/";

    private final String json_link = " https://api.rss2json.com/v1/api.json?rss_url=";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Today's News");
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar( toolbar);

        try
        {

            recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

            LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(linearLayoutManager);

            loadRSS();
        }
        catch (Exception exception)
        {
            Log.e("failure of action",Log.getStackTraceString(exception));
        }

    }



    private void loadRSS()
    {

        AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>()
        {



            ProgressDialog mDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute()
            {
                mDialog.setMessage("Please wait...");
                mDialog.show();
            }

            @Override
            protected String doInBackground(String... params)
            {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(params[0]);
                return  result;
            }

            @Override
            protected void onPostExecute(String s)
            {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s,RSSObject.class);
                FeedAdapter adapter = new FeedAdapter(rssObject,getBaseContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        };



        //adding json link to string builder
        StringBuilder url_get_data_h1 = new StringBuilder(json_link);


       url_get_data_h1.append(RSS_deepmind);

  loadRSSAsync.execute(url_get_data_h1.toString());



//adding our RSS link through Json link which was received via RSS2Json.com


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.menu_refresh)
            loadRSS();

        return true;
    }
}