package com.example.g7190305.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.g7190305.gridimagesearch.R;
import com.example.g7190305.gridimagesearch.models.imageResult;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by g7190305 on 2015/8/1.
 */
public class imageResultsAdapter extends ArrayAdapter<imageResult> {
    public imageResultsAdapter(Context context, List<imageResult> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // return super.getView(position, convertView, parent);
        imageResult result = getItem(position);
        String title = result.getTitle();
        String url = result.getFullUrl();

        if ( convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvItemTitle);
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivItemImage);

        tvTitle.setText(Html.fromHtml(result.getTitle()));
        ivImage.setImageResource(0);
        Picasso.with(getContext()).load(result.getThumbUrl()).resize(100,100).into(ivImage);

        return convertView;
    }
}
