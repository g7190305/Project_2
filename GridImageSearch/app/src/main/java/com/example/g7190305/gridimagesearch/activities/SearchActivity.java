package com.example.g7190305.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.support.v7.widget.SearchView;

import com.example.g7190305.gridimagesearch.R;
import com.example.g7190305.gridimagesearch.adapters.imageResultsAdapter;
import com.example.g7190305.gridimagesearch.models.EndlessScrollListener;
import com.example.g7190305.gridimagesearch.models.SetupInfo;
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
    private SetupInfo setupInfo;

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
        setupInfo = new SetupInfo(null,null,null,null);

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

        gvResults.setOnScrollListener(new EndlessScrollListener(8, 0) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
            }
        });
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        String query = etSeatch.getText().toString();
        fetchGoogleSearchImage(query, offset*8);
    }


    public void onImageSearch(View v) {
        String query = etSeatch.getText().toString();
        aImageResult.clear();
        // Toast.makeText(this, "search for " + query , Toast.LENGTH_SHORT).show();
        // Log.i("DEBUG", "onImageSearch");

        // fetch google image info
        fetchGoogleSearchImage(query, 0);
    }

    public void fetchGoogleSearchImage(String query, int page) {
        String url = "https://ajax.googleapis.com/ajax/services/search/images";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", query);
        params.put("v", "1.0");
        params.put("rsz", 8);

        if (page > 0 ) {
            params.put("start", page);
        }

        if (setupInfo.getImageType() != null) {
            params.put("imgtype", setupInfo.getImageType());
        }
        if (setupInfo.getColorFilter() != null) {
            params.put("imgcolor", setupInfo.getColorFilter());
        }
        if (setupInfo.getImageSize() != null) {
            params.put("imgsz", setupInfo.getImageSize());
        }
        if (setupInfo.getSiteFilter() != null) {
            params.put("as_sitesearch", setupInfo.getSiteFilter());
        }

        client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // called when response HTTP status is "200 OK"
                        // Log.d("DEBUG", response.toString());
                        JSONArray imageResultJSON = null;
                        try {
                            imageResultJSON = response.getJSONObject("responseData").getJSONArray("results");
                            // aImageResult.clear();
                            aImageResult.addAll(imageResult.fromJSONArray(imageResultJSON));
                        } catch (JSONException e) {
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

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                aImageResult.clear();
                // fetch google image info
                fetchGoogleSearchImage(query, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    public void onSetupActivity() {
        Intent i = new Intent(SearchActivity.this, SetupActivity.class);
        i.putExtra("setupInfo", setupInfo);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if ( resultCode == RESULT_OK && requestCode == 1 ) {
            setupInfo = (SetupInfo) data.getSerializableExtra("setupInfo");
            Log.i("DEBUG", setupInfo.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                onSetupActivity();
                return true;
        }
        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //     return true;
        // }

        return super.onOptionsItemSelected(item);
    }
}
