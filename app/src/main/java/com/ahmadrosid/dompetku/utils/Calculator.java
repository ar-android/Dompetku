package com.ahmadrosid.dompetku.utils;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.dompetku.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yogja on 10/4/17.
 */

public class Calculator extends ConstraintLayout {


    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button_clear)
    Button buttonClear;
    @BindView(R.id.button_multiply)
    Button buttonMultiply;
    @BindView(R.id.button_devide)
    Button buttonDevide;
    @BindView(R.id.button_substract)
    Button buttonSubstract;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    @BindView(R.id.button_plus)
    Button buttonPlus;
    @BindView(R.id.button000)
    Button button000;
    @BindView(R.id.button_enter)
    Button buttonEnter;
    @BindView(R.id.button_titik)
    Button buttonTitik;
    @BindView(R.id.button0)
    Button button0;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.textView)
    TextView textView;

    private CalculatorListener calculatorListener;

    public Calculator(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calculator, this, true);

        ButterKnife.bind(this);

        textView.setText("");

    }

    public void setListener(CalculatorListener listener) {
        calculatorListener = listener;
    }

    @OnClick({R.id.button7, R.id.button_clear, R.id.button_multiply, R.id.button_devide, R.id.button_substract, R.id.button8, R.id.button9, R.id.button_plus, R.id.center, R.id.right, R.id.left, R.id.button000, R.id.button_enter, R.id.button_titik, R.id.button0, R.id.button2, R.id.button3, R.id.button1, R.id.button4, R.id.button5, R.id.button6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button000:
                textView.append("000");
                break;
            case R.id.button0:
                textView.append("0");
                break;
            case R.id.button1:
                textView.append("1");
                break;
            case R.id.button2:
                textView.append("2");
                break;
            case R.id.button3:
                textView.append("3");
                break;
            case R.id.button4:
                textView.append("4");
                break;
            case R.id.button5:
                textView.append("5");
                break;
            case R.id.button6:
                textView.append("6");
                break;
            case R.id.button7:
                textView.append("7");
                break;
            case R.id.button8:
                textView.append("8");
                break;
            case R.id.button9:
                textView.append("9");
                break;
            case R.id.button_clear:
                textView.setText("");
                break;
            case R.id.button_multiply:
                Toast.makeText(getContext(), "Not Implemented yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_devide:
                Toast.makeText(getContext(), "Not Implemented yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_substract:
                Toast.makeText(getContext(), "Not Implemented yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_plus:
                Toast.makeText(getContext(), "Not Implemented yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_titik:
                Toast.makeText(getContext(), "Not Implemented yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_enter:
                String result = textView.getText().toString();
                int r = Integer.parseInt(result);
                calculatorListener.result(r);
                break;
        }
    }
}
