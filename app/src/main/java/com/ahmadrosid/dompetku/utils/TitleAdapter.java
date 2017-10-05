package com.ahmadrosid.dompetku.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by staf on 05-Oct-17.
 */

public class TitleAdapter extends ArrayAdapter<String> {

    private List<String> titles;
    private TitlePickerListener titlePickerListener;

    public TitleAdapter(@NonNull Context context, @NonNull List<String> data, TitlePickerListener listener) {
        super(context, 0, data);
        titles = data;
        titlePickerListener = listener;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return titles.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);

        final String title = getItem(position);

        textView.setText(title);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titlePickerListener.onClickListener(title);
            }
        });

        return convertView;
    }
}
