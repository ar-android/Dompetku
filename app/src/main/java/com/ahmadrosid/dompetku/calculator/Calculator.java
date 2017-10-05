package com.ahmadrosid.dompetku.calculator;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;

import com.ahmadrosid.dompetku.R;

/**
 * Created by yogja on 10/4/17.
 */

public class Calculator extends ConstraintLayout {


    public Calculator(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calculator, this, true);

        Button btn1 = (Button) findViewById(R.id.button4);

        btn1.setText("1");
    }

}
