package com.ahmadrosid.dompetku.calculator;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.ahmadrosid.dompetku.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by staf on 05-Oct-17.
 */

public class TitlePicker extends ConstraintLayout {

    @BindView(R.id.listview)
    ListView listview;

    private TitlePickerListener listener;

    public TitlePicker(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.title_picker, this, true);

        ButterKnife.bind(this);

    }

    public void setListener(TitlePickerListener listener) {
        this.listener = listener;
    }

    public void setTextList(List<String> data) {
        TitleAdapter titleAdapter = new TitleAdapter(getContext(), data, listener);
        listview.setAdapter(titleAdapter);
    }

}
