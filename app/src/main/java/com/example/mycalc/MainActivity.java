package com.example.mycalc;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView Results;

    Button[] Numbers;
    Button[] Operations;

    Button Clear, Equal, Backspace, Negative;

    View.OnClickListener NumListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button TempBTN = (Button)v;
            Results.append(TempBTN.getText().toString());
        }
    };

    View.OnClickListener OpListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button TempBTN = (Button)v;
            Results.append(TempBTN.getText().toString());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Results = findViewById(R.id.Results);

        Numbers[0] = findViewById(R.id.Btn0);
        Numbers[1] = findViewById(R.id.Btn1);
        Numbers[2] = findViewById(R.id.Btn2);
        Numbers[3] = findViewById(R.id.Btn3);
        Numbers[4] = findViewById(R.id.Btn4);
        Numbers[5] = findViewById(R.id.Btn5);
        Numbers[6] = findViewById(R.id.Btn6);
        Numbers[7] = findViewById(R.id.Btn7);
        Numbers[8] = findViewById(R.id.Btn8);
        Numbers[9] = findViewById(R.id.Btn9);

        for (int i = 0; i < 10; i++) {
            Numbers[i].setOnClickListener(NumListen);
        }

        Operations[0] = findViewById(R.id.BtnPlus);
        Operations[1] = findViewById(R.id.BtnMinus);
        Operations[2] = findViewById(R.id.BtnTimes);
        Operations[3] = findViewById(R.id.BtnDivide);
        Operations[4] = findViewById(R.id.BtnRaminder);

        for (int i = 0; i < 5; i++) {
            Operations[i].setOnClickListener(OpListen);
        }

        Clear = findViewById(R.id.BtnClear);
        Equal = findViewById(R.id.BtnEqual);
        Backspace = findViewById(R.id.BtnDelete);
        Negative = findViewById(R.id.BtnNegative);

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Results.setText("");
            }
        });

        Equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ResStr = Results.getText().toString();
                for (int i = 0; i < ResStr.length(); i++) {
                    //Equal here
                }
            }
        });

        Backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ResStr = Results.getText().toString();
                Results.setText(ResStr.substring(0,ResStr.length()-1));
            }
        });
    }
}
