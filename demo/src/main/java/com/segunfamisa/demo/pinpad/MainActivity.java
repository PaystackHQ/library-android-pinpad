package com.segunfamisa.demo.pinpad;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        textView.setMovementMethod(new ScrollingMovementMethod());

        pinpadView.setOnPinChangedListener(new PinPadView.OnPinChangedListener() {
            @Override
            public void onPinChanged(String oldPin, String newPin) {
                appendText("Changed from: '" + oldPin + "' to '" + newPin + "'");
            }

        });

        pinpadView.setOnSubmitListener(new PinPadView.OnSubmitListener() {
            @Override
            public void onCompleted(String pin) {
                appendText("Submitted: " + pin);
                pinpadView.setAutoSubmit(false);
                pinpadView.clear();
            }

            @Override
            public void onIncompleteSubmit(String pin) {
                appendText("Submitted Incomplete PIN: " + pin);
            }
        });
    }

    private void appendText(String text) {
        String nl = (textView.getText().length() > 0 ? "\n" : "");
        textView.setText(textView.getText() + nl + text);
        while (textView.canScrollVertically(1)) {
            textView.scrollBy(0, 10);
        }
    }


}
