# Android Pinpad

This is an Android library for a pin pad view.

# Usage

Typical usage of this library is via Java code or in the XML layouts.

## Installation

### Android Studio (using Gradle)
You do not need to clone this repository or download the files. Just add the following lines to your app's `build.gradle`:

```gradle
repositories {
  maven {
      url 'https://dl.bintray.com/paystack/maven/'
  }
}
dependencies {
  compile 'co.paystack.android.design.widget:pinpad:1.0'
}
```

### XML layout usage:

```xml
<co.paystack.android.design.widget.PinPadView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"

    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@android:color/black"
    app:pin_indicator_color="@android:color/white"
    app:pin_indicator_size="15dp"
    app:pin_indicator_stroke_width="4dp"
    app:pin_indicator_spacing="24dp"
    app:pin_length="4"
    app:text_color="@android:color/white"
    app:place_digits_randomly="true"
    app:text_prompt="Please enter your PIN"
    app:prompt_textsize="15sp"
    app:button_numeric_textsize="18sp"
    app:button_alpha_textsize="8sp"
    app:button_drawable_size="32dp"/>
```

### Java Usage
You can also make use of the `PinPadView` via Java code. Typical usage looks like:


```java
pinPadView.setPromptText("Please enter your PIN");
pinPadView.setTextColor(Color.WHITE);
pinPadView.setPinLength(4);
pinPadView.setOnPinChangedListener(new PinPadView.OnPinChangedListener() {
    @Override
    public void onPinChanged(String oldPin, String newPin) {
        // listen for pin changes
    }
});
pinPadView.onCompletedListener(new PinPadView.OnPinChangedListener() {
    @Override
    public void onCompleted(String pin) {
        // listen for when the "done" button is clicked
    }
});
```

You can ultimately combine both XML usage and Java usage to suit your usecase.

# Contributing
Contributions are welcome. Contributions guide is coming soon.