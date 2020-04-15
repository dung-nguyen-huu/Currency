package com.example.currency;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

import static java.lang.StrictMath.pow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView1, textView2;
    TextView txtSymbol1, txtSymbol2;
    Spinner spinner1, spinner2;

    int state; //state = 1 dang nhap o edittext 1, state = 2 dang nhap o edittext 2
    int checkDot, dots; //checkDot = 1 da bam phim dot , dots la so lan an phim dot
    double op1, op2; // toan hang o edittext 1 va 2
    DecimalFormat dcf = new DecimalFormat("###,###,###.##");

    String items[] = {"Vietnam - Dong", "United States - Dollar", "United Kingdom - Pound", "Thailand - Baht", "Singapare - Dallar"};
    double rate[] = {1, 23310, 29594, 761, 17230}; //so voi vietnam dong
    String symbol[] = {"₫", "$", "£", "฿", "$"};
    int index1, index2; // chi so trong mang items va rate


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetListener();
        SetSpinner();

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 1;
                checkDot = dots = 0;
                op1 = op2 = 0;
                textView1.setTypeface(Typeface.DEFAULT_BOLD);
                textView2.setTypeface(Typeface.DEFAULT);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state = 2;
                checkDot = dots = 0;
                op1 = op2 = 0;
                textView2.setTypeface(Typeface.DEFAULT_BOLD);
                textView1.setTypeface(Typeface.DEFAULT);
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn0:
                addDigit(0);
                break;
            case R.id.btn1:
                addDigit(1);
                break;
            case R.id.btn2:
                addDigit(2);
                break;
            case R.id.btn3:
                addDigit(3);
                break;
            case R.id.btn4:
                addDigit(4);
                break;
            case R.id.btn5:
                addDigit(5);
                break;
            case R.id.btn6:
                addDigit(6);
                break;
            case R.id.btn7:
                addDigit(7);
                break;
            case R.id.btn8:
                addDigit(8);
                break;
            case R.id.btn9:
                addDigit(9);
                break;
            case R.id.btnBS:
                removeDigit();
                break;
            case R.id.btnCE:
                textView1.setText("0");
                textView2.setText("0");
                op1 = op2 = checkDot = dots = 0;
                break;
            case R.id.btnDot:
                if (checkDot == 0) {
                    if (state == 1)
                        textView1.append(",0");
                    else
                        textView2.append(",0");
                }
                checkDot = 1;
                break;

        }
    }

    private void removeDigit() {
        String t;
        if (state == 1) {
            t = textView1.getText().toString();
        } else {
            t = textView2.getText().toString();
        }
        double digit;
        digit = Double.parseDouble(String.valueOf(t.charAt(t.length() - 1)));
        if (checkDot == 0) {
            if (state == 1) {
                op1 = (op1 - digit) / 10;
                op2 = op1 * rate[index1] / rate[index2];

                SetFormat();
            } else {
                op2 = (op2 - digit) / 10;
                op1 = op2 * rate[index2] / rate[index1];

                SetFormat();
            }
        } else {
            if (state == 1) {
                op1 = op1 - digit * pow(10, -dots);
                op2 = op1 * rate[index1] / rate[index2];

                SetFormat();

                if (dots != 0) dots--;
                if(dots == 0) checkDot = 0;

            } else {
                op2 = op2 - digit * pow(10, -dots);
                op1 = op2 * rate[index2] / rate[index1];

                SetFormat();

                if (dots != 0) dots--;
                if(dots == 0) checkDot = 0;
            }
        }
    }

    private void addDigit(double digit) {
        if (checkDot == 0) {
            if (state == 1) {
                op1 = op1 * 10 + digit;
                op2 = op1 * rate[index1] / rate[index2];

                SetFormat();
            } else {
                op2 = op2 * 10 + digit;
                op1 = op2 * rate[index2] / rate[index1];

                SetFormat();
            }
        } else {
            if (dots < 3) {
                dots++;
                if (state == 1) {
                    op1 = op1 + digit * pow(10, -dots);
                    op2 = op1 * rate[index1] / rate[index2];

                    SetFormat();
                } else {
                    op2 = op2 + digit * pow(10, -dots);
                    op1 = op2 * rate[index2] / rate[index1];

                    SetFormat();
                }
            }
        }
    }

    private void SetFormat() {
        String k1 = dcf.format(op1);
        String k2 = dcf.format(op2);

        textView1.setText(k1);
        textView2.setText(k2);

    }

    public void SetSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index1 = position;
                txtSymbol1.setText(symbol[index1]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index2 = position;
                txtSymbol2.setText(symbol[index2]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void SetListener() {
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        txtSymbol1 = findViewById(R.id.txtSymbol1);
        txtSymbol2 = findViewById(R.id.txtSymbol2);

        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);

        findViewById(R.id.btnDot).setOnClickListener(this);
        findViewById(R.id.btnBS).setOnClickListener(this);
        findViewById(R.id.btnCE).setOnClickListener(this);

        op1 = op2 = 0;
        index1 = index2 = 0;
        checkDot = dots = 0;
        state = 1;
        textView1.setTypeface(Typeface.DEFAULT_BOLD);
        textView2.setTypeface(Typeface.DEFAULT);
    }
}
