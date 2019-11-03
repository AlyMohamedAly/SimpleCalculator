package com.example.mycalc;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView Results, Results2;

    Button[] Numbers = new Button[10];
    Button[] Operations = new Button[5];

    Button Clear, Equal, Backspace, Negative, Dot;

    HashMap<Integer, Character> OperationLocation = new HashMap<>();

    public static double CalcMe(double Num1, double Num2, char OP){
        switch (OP){
            case '+':
                return Num1 + Num2;
            case '-':
                return Num1 - Num2;
            case '÷':
                return Num1 / Num2;
            case 'x':
                return Num1 * Num2;
            case '%':
                return Num1 % Num2;
        }
        throw new RuntimeException("The program should not be here");
    }

    public void ClearAll(){
        Results.setText("");
        Results2.setText("");
        OperationLocation.clear();
    }


    View.OnClickListener NumListen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button TempBTN = (Button)v;
            String TempStr = TempBTN.getText().toString();
            String ResStr = Results.getText().toString();
            int sz = ResStr.length();
            int OpLoc = -1;

            if(OperationLocation.size() == 0){
                if (ResStr.equals("0"))
                    Results.setText(TempStr);
                else
                    Results.append(TempStr);
            }else{
                Log.d("msgs", "onClick: Here");
                for (int i = sz-1; i > 0 ; i--) {
                    if (OperationLocation.get(i) != null){
                        OpLoc = i;
                        break;
                    }
                }
                Log.d("msgs", "OPLOC: " + OpLoc);

                if(OperationLocation.get(sz-1) == null) {
                    if (ResStr.substring(OpLoc+1).equals("0")){
                        Results.setText(ResStr.substring(0,sz - 1) + TempStr);
                    }else{
                        Results.append(TempStr);
                    }
                }else{
                    Results.append(TempStr);
                }
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

        final DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);

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
                ClearAll();
            }
        });

        Equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TempStr = Results.getText().toString();
                if (TempStr.length() == 0)
                    Results.setText("");
                else {
                    if (!OperationLocation.isEmpty()){

                        int[] Locations = new int[OperationLocation.size()];
                        int index = 0;
                        for (Integer i : OperationLocation.keySet()) {
                            Locations[index] = i;
                            index++;
                        }

                        if (Locations.length == 1) {
                            Log.d("msg", "onClick: Len1");
                            if (TempStr.length() == Locations[0] + 1) {

                                Results.setText(df.format(Double.parseDouble(TempStr.substring(0, Locations[0]))));
                            }
                            else {
                                if (TempStr.substring(Locations[0] + 1).equals("0") && OperationLocation.get(Locations[0]) == '÷') {
                                    Toast.makeText(getBaseContext(), getString(R.string.DivideZero), Toast.LENGTH_SHORT).show();
                                    ClearAll();
                                }else {

                                    Results.setText(df.format(CalcMe(Double.parseDouble(TempStr.substring(0, Locations[0])),
                                            Double.parseDouble(TempStr.substring(Locations[0] + 1)),
                                            OperationLocation.get(Locations[0]))));
                                }
                            }
                        } else {
                            boolean Mistake = false;
                            double ANS = Double.parseDouble(TempStr.substring(0, Locations[0]));
                            Log.d("msg", "Before: " + ANS);
                            for (int i = 1; i < Locations.length; i++) {
                                Log.d("msg", "In: " + ANS);
                                if (Double.parseDouble(TempStr.substring(Locations[i-1] + 1, Locations[i])) == 0 && OperationLocation.get(Locations[i-1])== '÷'){
                                    Log.d("msg", "Im hereeee: ");
                                    Toast.makeText(getBaseContext(), getString(R.string.DivideZero), Toast.LENGTH_SHORT).show();
                                    ClearAll();
                                    Mistake = true;
                                    break;
                                }
                                ANS = Double.parseDouble(df.format(CalcMe(ANS,
                                        Double.parseDouble(TempStr.substring(Locations[i-1] + 1,
                                                Locations[i])),
                                        OperationLocation.get(Locations[i-1]))));

                            }
                            if (!Mistake){
                                Log.d("msg", "Out: " + ANS);
                                if (TempStr.substring(Locations[Locations.length-1] + 1).length() == 0)
                                    Results.setText(ANS+"");
                                else {
                                    if (TempStr.substring(Locations[Locations.length - 1] + 1).equals("0") && OperationLocation.get(Locations[Locations.length - 1]) == '÷') {
                                        Toast.makeText(getBaseContext(), getString(R.string.DivideZero), Toast.LENGTH_SHORT).show();
                                        ClearAll();
                                    }
                                    else {
                                        Results.setText(df.format(CalcMe(ANS,
                                                Double.parseDouble(TempStr.substring(Locations[Locations.length - 1] + 1)),
                                                OperationLocation.get(Locations[Locations.length - 1]))));
                                        Log.d("msg", "After: " + ANS);
                                    }
                                }
                            }
                        }
                    }
                }
                OperationLocation.clear();
                //handle Infinity
                //handle empty
            }
        });

        Backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ResStr = Results.getText().toString();
                if (ResStr.length() > 0) {
                    if (OperationLocation.get(ResStr.length() - 1) != null)
                        OperationLocation.remove(ResStr.length() - 1);
                    Results.setText(ResStr.substring(0, ResStr.length() - 1));
                }
            }
        });
    }
}
