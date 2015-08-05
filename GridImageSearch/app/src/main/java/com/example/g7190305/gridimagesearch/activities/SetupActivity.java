package com.example.g7190305.gridimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.g7190305.gridimagesearch.R;
import com.example.g7190305.gridimagesearch.models.SetupInfo;

public class SetupActivity extends AppCompatActivity {
    private SetupInfo setupInfo;
    private int position;
    Spinner spImageSize;
    Spinner spColorFilter;
    Spinner spImageType;
    EditText etSiteFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        setupInfo = (SetupInfo) getIntent().getSerializableExtra("setupInfo");
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);


        addItemOnImageSize();
        addItemOnColorFilter();
        addItemOnImageType();
        addItemOnSiteFilter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return true;
    }

    public void addItemOnSiteFilter() {

        if (setupInfo.getSiteFilter() != null ) {

            etSiteFilter.setText(setupInfo.getSiteFilter());
        }
    }
    public void addItemOnImageSize() {

        // TextView tvSpItem = (TextView) findViewById(R.id.spItem);

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_size, R.layout.spinner_item );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spImageSize.setAdapter(dataAdapter);

        if ( setupInfo.getImageSize() != null) {
            switch (setupInfo.getImageSize()) {
                case "icon":
                    position = 1;
                    break;
                case "small":
                    position = 2;
                    break;
                case "medium":
                    position = 3;
                    break;
                case "large":
                    position = 4;
                    break;
                case "xlarge":
                    position = 5;
                    break;
                case "xxlarge":
                    position = 6;
                    break;
                case "huge":
                    position = 7;
                    break;
                default:
                    position = 0;
            }

            spImageSize.setSelection(position);
        }
    }

    public void addItemOnColorFilter() {

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_color, R.layout.spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColorFilter.setAdapter(dataAdapter);

        if ( setupInfo.getColorFilter() != null) {
            switch (setupInfo.getColorFilter()) {
                case "black":
                    position = 1;
                    break;
                case "blue":
                    position = 2;
                    break;
                case "brown":
                    position = 3;
                    break;
                case "gray":
                    position = 4;
                    break;
                case "green":
                    position = 5;
                    break;
                case "orange":
                    position = 6;
                    break;
                case "pink":
                    position = 7;
                    break;
                case "purple":
                    position = 8;
                    break;
                case "red":
                    position = 9;
                    break;
                case "teal":
                    position = 10;
                    break;
                case "white":
                    position = 11;
                    break;
                case "yellow":
                    position = 12;
                    break;
                default:
                    position = 0;
            }

            spColorFilter.setSelection(position);
        }

    }

    public void addItemOnImageType() {

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_type, R.layout.spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageType.setAdapter(dataAdapter);

        if (setupInfo.getImageType() != null) {
            switch (setupInfo.getImageType()) {
                case "face":
                    position = 1;
                    break;
                case "photo":
                    position = 2;
                    break;
                case "clipart":
                    position = 3;
                    break;
                case "lineart":
                    position = 4;
                    break;
                default:
                    position = 0;
            }

            spImageType.setSelection(position);
        }
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

    public void saveSetup(View v){

        String imageSize = spImageSize.getSelectedItem().toString();
        String colorFilter = spColorFilter.getSelectedItem().toString();
        String imageType = spImageType.getSelectedItem().toString();
        String siteFilter = etSiteFilter.getText().toString();

        if (imageSize.equalsIgnoreCase("please select")) imageSize = null;
        if (colorFilter.equalsIgnoreCase("please select")) colorFilter = null;
        if (imageType.equalsIgnoreCase("please select")) imageType = null;
        if (siteFilter.length() == 0 ) siteFilter = null;

        SetupInfo setupInfo = new SetupInfo(imageSize, colorFilter, imageType, siteFilter);
        Intent data = new Intent();
        data.putExtra("setupInfo", setupInfo);

        setResult(RESULT_OK, data);
        finish();

    }
    public  void cancelSetup(View v){
        setResult(RESULT_CANCELED, null);
        finish();
    }
}

