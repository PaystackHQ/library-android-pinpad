package com.segunfamisa.demo.pinpad;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.paystack.android.design.widget.PinPadView;

public class MainActivity extends AppCompatActivity {

    private PinPadView pinpadView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pinpadView = (PinPadView) findViewById(R.id.pinpadView);
        textView = (TextView) findViewById(R.id.textView);

        pinpadView.setOnPinChangedListener(new PinPadView.OnPinChangedListener() {
            @Override
            public void onPinChanged(String oldPin, String newPin) {
                textView.setText(textView.getText() + "\nChanged from: " + oldPin + " to " + newPin);
            }

        });

        pinpadView.setOnCompletedListener(new PinPadView.OnCompletedListener() {
            @Override
            public void onCompleted(String pin) {
                textView.setText(textView.getText() + "\nSubmitted: " + pin);

            }
        });
    }


}
