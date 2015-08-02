package com.example.g7190305.gridimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.g7190305.gridimagesearch.R;
import com.example.g7190305.gridimagesearch.adapters.imageResultsAdapter;
import com.example.g7190305.gridimagesearch.models.imageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity {
    private EditText etSeatch;
    private GridView gvResults;
    private ArrayList<imageResult> imageResults;
    private imageResultsAdapter aImageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // display logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // setup Views
        setupViews();
    }

    public void setupViews() {
        etSeatch = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResult);
        imageResults = new ArrayList<imageResult>();
        aImageResult = new imageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResult);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // create an intent
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                // get image result to display
                imageResult result = imageResults.get(position);
                //pass image result to imtent
                i.putExtra("result", result);
                //  launch activity
                startActivity(i);
            }
        });
    }
    public void onImageSearch(View v) {
        String query = etSeatch.getText().toString();
        // Toast.makeText(this, "search for " + query , Toast.LENGTH_SHORT).show();
        // Log.i("DEBUG", "onImageSearch");

        // fetch google image info
        fetchGoogleSearchImage(query);
    }

    public void fetchGoogleSearchImage(String query) {
        String url = "https://ajax.googleapis.com/ajax/services/search/images";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", query);
        params.put("v", "1.0");
        params.put("rsz", 8);
        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // called when response HTTP status is "200 OK"
                        // Log.d("DEBUG", response.toString());
                        JSONArray imageResultJSON = null;
                        try {
                            imageResultJSON = response.getJSONObject("responseData").getJSONArray("results");
                            aImageResult.clear();
                            aImageResult.addAll(imageResult.fromJSONArray(imageResultJSON));
                        } catch( JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("INFO", imageResults.toString());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable thowable) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.d("DEBUG", "statusCode:" + statusCode + " msg:" + responseString);
                    }
                }
        );
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
