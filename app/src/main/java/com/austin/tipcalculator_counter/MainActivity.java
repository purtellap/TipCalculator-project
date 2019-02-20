package com.austin.tipcalculator_counter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.spec.ECField;
import java.util.Locale;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    // Please ignore the incoherent naming scheme

    float amount;
    int numPeople;
    int tipPercent;

    float totalAmt;
    float totalTip;
    float totalPP;

    Button calc;
    Button reset;

    TextView finalAmt;
    TextView finalTip;
    TextView finalPP;

    String finalTipString;
    String finalAmtString;
    String finalPPString;

    boolean amtValid = false;
    boolean pplValid = false;
    boolean percentValid = false;

    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finalAmt = findViewById(R.id.totalAmt);
        finalAmt.setVisibility(View.GONE);
        finalTip = findViewById(R.id.totalTip);
        finalTip.setVisibility(View.GONE);
        finalPP = findViewById(R.id.totalPP);
        finalPP.setVisibility(View.GONE);

        final EditText amtID = (EditText) findViewById(R.id.amtIn);
        final EditText peopleID = (EditText) findViewById(R.id.pplIn);
        final EditText percentID = (EditText) findViewById(R.id.percentIn);
        percentID.setEnabled(false);

        radioGroup = findViewById(R.id.RGroup);

        calc = (Button) findViewById(R.id.calc);
        calc.setEnabled(false);
        reset = (Button) findViewById(R.id.reset);


        // Radio group
        final RadioButton custom = findViewById(R.id.custom);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // If custom, checks value so that if there's something already entered you don't have to type in keylistener to update
                if(custom.isChecked()) {
                    percentID.setEnabled(true);
                    percentValid = false;
                    String s = percentID.getText().toString();
                    if (s.length() > 0) {
                        int i = Integer.parseInt(s);
                        if (i >= 1) {
                            percentValid = true;
                            tipPercent = i;
                        } else {
                            percentValid = false;
                        }
                    } else {
                        percentValid = false;
                    }
                }
                else{
                    percentID.setEnabled(false);
                    percentValid = true;
                }
                if(amtValid && percentValid && pplValid){
                    calc.setEnabled(true);
                }
                else{
                    calc.setEnabled(false);
                }
            }
        });

        //Keylistener
        final View.OnKeyListener mKeyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(KeyEvent.ACTION_UP == event.getAction()) {

                    // Checking to make sure each field is evaluated on a per-input basis. Wow factor.
                    String s = "";
                    switch (v.getId()) {
                        case R.id.amtIn: {
                            s = amtID.getText().toString();
                            if (s.length() > 0) {
                                Float i = Float.parseFloat(s);
                                if (i >= 1) {
                                    amtValid = true;
                                    amount = i;

                                } else {
                                    amtValid = false;
                                    showErrorAlert("Please make your amount at least 1.", R.id.amtIn);
                                }
                            } else {
                                amtValid = false;
                                showErrorAlert("Please enter an amount.", R.id.amtIn);
                            }
                        }

                        case R.id.pplIn: {
                            s = peopleID.getText().toString();
                            if (s.length() > 0) {
                                int i = Integer.parseInt(s);
                                if (i >= 1) {
                                    pplValid = true;
                                    numPeople = i;
                                } else {
                                    pplValid = false;
                                    showErrorAlert("Please make your party at least 1.", R.id.pplIn);
                                }
                            } else {
                                pplValid = false;
                                showErrorAlert("Please enter a number of people.", R.id.pplIn);
                            }
                        }

                        case R.id.percentIn: {
                            if (custom.isChecked()) {
                                s = percentID.getText().toString();
                                if (s.length() > 0) {
                                    int i = Integer.parseInt(s);
                                    if (i >= 1) {
                                        percentValid = true;
                                        tipPercent = i;
                                    } else {
                                        percentValid = false;
                                        showErrorAlert("Please make your tip at least 1%. You cheapskate.", R.id.percentIn);
                                    }
                                } else {
                                    percentValid = false;
                                    showErrorAlert("Please enter a tip. This is the U.S.", R.id.percentIn);
                                }
                            }
                        }


                    }

                }

                // You can't calculate until your input is valid
                if(amtValid && percentValid && pplValid){
                    calc.setEnabled(true);
                }
                else{
                    calc.setEnabled(false);
                }
                return false;
            }

        };

        amtID.setOnKeyListener(mKeyListener);
        peopleID.setOnKeyListener(mKeyListener);
        percentID.setOnKeyListener(mKeyListener);

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("myTag", "Amount: " + amount);
                Log.d("myTag", "People: " + numPeople);

                // Sets value of percent to whatever is selected at the time of hitting the button, rather than updating it beforehand.
                if(radioGroup.getCheckedRadioButtonId() == R.id.tenPercent){
                    tipPercent = 10;
                }
                else if (radioGroup.getCheckedRadioButtonId() == R.id.fifteenPercent){
                    tipPercent = 15;
                }
                else if (radioGroup.getCheckedRadioButtonId() == R.id.twentyPercent){
                    tipPercent = 20;
                }
                Log.d("myTag", "Tip: " + tipPercent);

                // Actually calculates the stuff
                totalTip = amount * tipPercent/100;
                totalAmt = amount + totalTip;
                totalPP = totalAmt / numPeople;

                // Display text
                finalTipString = "Total Tip:\n" + String.format(Locale.US,"$%.2f", totalTip);
                finalAmtString = "Total Amount:\n" + String.format(Locale.US,"$%.2f", totalAmt);
                finalPPString = "Total amount per person:\n" + String.format(Locale.US,"$%.2f", totalPP);

                finalTip.setText(finalTipString);
                finalAmt.setText(finalAmtString);
                finalPP.setText(finalPPString);

                finalTip.setVisibility(View.VISIBLE);
                finalAmt.setVisibility(View.VISIBLE);
                finalPP.setVisibility(View.VISIBLE);

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Resets all values
                radioGroup.clearCheck();

                amtID.getText().clear();
                peopleID.getText().clear();
                percentID.getText().clear();

                amount = 0f;
                numPeople = 0;
                tipPercent = 0;

                finalAmt.setVisibility(View.GONE);
                finalTip.setVisibility(View.GONE);
                finalPP.setVisibility(View.GONE);

                amtValid = false;
                pplValid = false;
                percentValid = false;

                calc.setEnabled(false);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putFloat("amtKey", amount);
        outState.putInt("pplKey", numPeople);
        outState.putInt("percentKey", tipPercent);

        outState.putString("finalAmtKey", finalAmtString);
        outState.putString("finalPPKey", finalPPString);
        outState.putString("finalTipKey", finalTipString);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);

        amount = bundle.getFloat("amtKey");
        numPeople = bundle.getInt("pplKey");
        tipPercent = bundle.getInt("percentKey");

        finalTip.setText(bundle.getString("finalTipKey"));
        finalAmt.setText(bundle.getString("finalAmtKey"));
        finalPP.setText(bundle.getString("finalPPKey"));
        finalTip.setVisibility(View.VISIBLE);
        finalAmt.setVisibility(View.VISIBLE);
        finalPP.setVisibility(View.VISIBLE);

    }

    private void showErrorAlert(String errorMessage, final int fieldId) {

        // I replaced your error message because I did the wow factor of having it not let you enter incorrect information,
        // and having the error message pop up after every input was really spammy.
        // Also for some reason it's popping the message even when the keylistener is on a different case
        /*new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();*/
        Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
        toast.show();
    }

}

