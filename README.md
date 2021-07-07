# Android Pinpad
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/co.paystack.android.design.widget/pinpad/badge.svg)](https://maven-badges.herokuapp.com/maven-central/co.paystack.android.design.widget/pinpad)



This is an Android library for a pin pad view.

# Usage

Typical usage of this library is via Java code or in the XML layouts.

## Installation

### Android Studio (using Gradle)
You do not need to clone this repository or download the files. Just add the following lines to your app's `build.gradle`:

```gradle
dependencies {
  compile 'co.paystack.android.design.widget:pinpad:1.0.8'
}
```

### XML layout usage:

```xml
    <co.paystack.android.design.widget.PinPadView xmlns:android="http://schemas.android.com/apk/res/android"
          xmlns:app="http://schemas.android.com/apk/res-auto"
            android:id="@+id/pinpadView"
            android:layout_width="match_parent"
            app:auto_submit="true"
            android:layout_height="0dp"
            android:layout_weight="5"
            android:background="#292929"
            app:pin_indicator_spacing="25dp"
            app:prompt_text="To confirm you're the owner of this card, please enter your card pin"
            app:prompt_textsize="15sp"
            app:button_numeric_textsize="13sp"
            app:button_alpha_textsize="0sp"
            app:button_drawable_size="24dp"
            app:pin_length="4"
            app:pin_indicator_size="15sp"
            app:pin_indicator_stroke_width="1dp"/>
```

### Java Usage
You can also make use of the `PinPadView` via Java code. Typical usage looks like:


```java
pinPadView.setPromptText("Please enter your PIN");
pinPadView.setPromptTextColor(Color.WHITE);
pinPadView.setPinLength(4);
pinPadView.setOnPinChangedListener(new PinPadView.OnPinChangedListener() {
    @Override
    public void onPinChanged(String oldPin, String newPin) {
        // listen for pin changes
    }
});
pinPadView.onSubmitListener(new PinPadView.onSubmitListener() {
    @Override
    public void onCompleted(String pin) {
        // listen for when the "done" button is clicked
        // and the pin is complete
    }
    @Override
    public void onIncompleteSubmit(String pin) {
        // listen for when the "done" button is clicked
        // and the pin is incomplete
    }
});
```

You can ultimately combine both XML usage and Java usage to suit your use case.

# Contributing
Contributions are welcome. Contributions guide is coming soon.
