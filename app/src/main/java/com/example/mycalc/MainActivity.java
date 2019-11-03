package com.example.mycalc;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView Results, Results2;

    Button[] Numbers = new Button[10];
    Button[] Operations = new Button[5];

    Button Clear, Equal, Backspace, Negative, Dot;

    HashMap<Integer, Character> OperationLocation = new HashMap<>();

    View.OnClickListener NumListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button TempBTN = (Button)v;
            String TempStr = TempBTN.getText().toString();
            if (Results.getText().toString().equals("0")) {
                Results.setText(TempStr);
            }else{
                Results.append(TempStr);
            }
        }
    };

    View.OnClickListener OpListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button TempBTN = (Button)v;
            String ResStr = Results.getText().toString();
            int sz = ResStr.length();
            if (sz != 0){
                if(OperationLocation.get(sz-1) == null) {
                    OperationLocation.put(sz, TempBTN.getText().charAt(0));
                    Results.append(TempBTN.getText().toString());
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Results = findViewById(R.id.Results);
        Results2 = findViewById(R.id.Results2);

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
        Dot = findViewById(R.id.BtnDot);

        Dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = Results.getText().toString();
                boolean Flag = false;
                int StopHere = temp.length();
                for (int i = StopHere - 1; (i > 0) && (OperationLocation.get(i) == null) ; i--) {
                    if(temp.charAt(i) == '.')
                        Flag = true;
                }
                if (!Flag)
                    Results.append(".");
            }
        });

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Results.setText("");
                OperationLocation.clear();
            }
        });

        Equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement
            }
        });

        Backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String ResStr = Results.getText().toString();
//                if (ResStr.length() > 0)
//                    Results.setText(ResStr.substring(0,ResStr.length()-1));

                // Implement
            }
        });
    }
}
