package co.paystack.android.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.*;

/**
 * PinPadView
 *
 * <pre>
 * &lt;co.paystack.android.design.widget.PinPadView
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     xmlns:app="http://schema.android.com/apk/res/android.support.design"
 *      android:layout_width="match_parent"
 *      android:layout_height="0dp"
 *      android:layout_weight="7"
 *      android:background="#292929"
 *      app:text_prompt="@string/prompt"
 *      app:pin_indicator_spacing="28dp"
 *      app:pin_indicator_color="@android:color/white"
 *      app:pin_indicator_size="15dp"
 *      app:pin_indicator_stroke_width="4dp"
 *      app:prompt_textsize="15sp"
 *      app:button_numeric_textsize="18sp"
 *      app:button_alpha_textsize="8sp"
 *      app:button_drawable_size="32dp"
 *      app:text_color="@android:color/white" /&gt;
 * </pre>
 */
public class PinPadView extends FrameLayout {
    private static final int DEFAULT_INDICATOR_COLOR = Color.WHITE;
    private static final int DEFAULT_INDICATOR_SIZE = 24;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_PIN_LENGTH = 4;
    private static final float DEFAULT_TEXT_SIZE_NUMERIC = 18f;
    private static final float DEFAULT_TEXT_SIZE_ALPHA = 12f;
    private static final float DEFAULT_TEXT_SIZE_PROMPT = 18f;
    private static final int DEFAULT_INDICATOR_SPACING = 8;
    private static final boolean DEFAULT_PLACE_DIGITS_RANDOMLY = true;

    @ColorInt
    private int mIndicatorColor = DEFAULT_INDICATOR_COLOR;
    private int mIndicatorSize;
    private int mIndicatorSpacing;

    @ColorInt
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private String mPromptText;
    private int mPinLength = DEFAULT_PIN_LENGTH;
    private float mTextSizeNumeric;
    private float mTextSizeAlpha;
    private float mTextSizePrompt;
    private int mDrawableSize;

    private boolean mPlaceDigitsRandomly = DEFAULT_PLACE_DIGITS_RANDOMLY;

    private PinPadButton mButton0;
    private PinPadButton mButton1;
    private PinPadButton mButton2;
    private PinPadButton mButton3;
    private PinPadButton mButton4;
    private PinPadButton mButton5;
    private PinPadButton mButton6;
    private PinPadButton mButton7;
    private PinPadButton mButton8;
    private PinPadButton mButton9;
    private PinPadButton mButtonBack;
    private PinPadButton mButtonDone;
    private TextView mTextViewPrompt;
    private LinearLayout mLayoutIndicator;

    private List<PinPadButton> mButtons;

    private OnPinChangedListener mPinChangeListener;

    /**
     * StringBuilder for the pin text
     */
    private StringBuilder mPinBuilder;

    public interface OnPinChangedListener {
        /**
         * Listener method invoked when the pin changed (either a new digit added or an old one removed)
         * @param oldPin - old pin
         * @param newPin - new pin
         */
        void onPinChanged(String oldPin, String newPin);

        /**
         * Listener method called when the "enter/done" key is pressed
         * @param pin - pin
         */
        void onCompletedListener(String pin);
    }

    public PinPadView(Context context) {
        super(context);
    }

    public PinPadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PinPadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private AttributeSet mAttrs;
    private AttributeSet getAttrs(){
        return mAttrs;
    }

    private void init(Context context, AttributeSet attrs) {
        if (context != null && attrs != null) {
            mAttrs = attrs;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinPadView);

            mPinLength = a.getInteger(R.styleable.PinPadView_pin_length, DEFAULT_PIN_LENGTH);
            mTextSizeNumeric = a.getDimension(R.styleable.PinPadView_button_numeric_textsize,
                    DEFAULT_TEXT_SIZE_NUMERIC);
            mTextSizeAlpha = a.getDimension(R.styleable.PinPadView_button_alpha_textsize,
                    DEFAULT_TEXT_SIZE_ALPHA);
            mTextSizePrompt = a.getDimension(R.styleable.PinPadView_prompt_textsize,
                    DEFAULT_TEXT_SIZE_PROMPT);
            mDrawableSize = a.getDimensionPixelSize(R.styleable.PinPadView_button_drawable_size,
                    24);
            mIndicatorSize = a.getDimensionPixelSize(R.styleable.PinPadView_pin_indicator_size,
                    DEFAULT_INDICATOR_SIZE);
            mIndicatorSpacing = a.getDimensionPixelSize(R.styleable.PinPadView_pin_indicator_spacing,
                    DEFAULT_INDICATOR_SPACING);
            mPlaceDigitsRandomly = a.getBoolean(R.styleable.PinPadView_place_digits_randomly,
                    DEFAULT_PLACE_DIGITS_RANDOMLY);

            if (a.hasValue(R.styleable.PinPadView_pin_indicator_color)) {
                mIndicatorColor = a.getColor(R.styleable.PinPadView_pin_indicator_color,
                        DEFAULT_INDICATOR_COLOR);
            }

            if (a.hasValue(R.styleable.PinPadView_text_prompt)) {
                mPromptText = a.getString(R.styleable.PinPadView_text_prompt);
            }

            if (a.hasValue(R.styleable.PinPadView_text_color)) {
                mTextColor = a.getColor(R.styleable.PinPadView_text_color, DEFAULT_TEXT_COLOR);
            }

            a.recycle();

            // inflate compound view;
            View parent = inflate(context, R.layout.layout_pinpad, this);

            mButton0 = (PinPadButton) parent.findViewById(R.id.ps_btn_0);
            mButton1 = (PinPadButton) parent.findViewById(R.id.ps_btn_1);
            mButton2 = (PinPadButton) parent.findViewById(R.id.ps_btn_2);
            mButton3 = (PinPadButton) parent.findViewById(R.id.ps_btn_3);
            mButton4 = (PinPadButton) parent.findViewById(R.id.ps_btn_4);
            mButton5 = (PinPadButton) parent.findViewById(R.id.ps_btn_5);
            mButton6 = (PinPadButton) parent.findViewById(R.id.ps_btn_6);
            mButton7 = (PinPadButton) parent.findViewById(R.id.ps_btn_7);
            mButton8 = (PinPadButton) parent.findViewById(R.id.ps_btn_8);
            mButton9 = (PinPadButton) parent.findViewById(R.id.ps_btn_9);
            mButtonBack = (PinPadButton) parent.findViewById(R.id.ps_btn_back);
            mButtonDone = (PinPadButton) parent.findViewById(R.id.ps_btn_done);
            mTextViewPrompt = (TextView) parent.findViewById(R.id.pinpad_prompt);
            mLayoutIndicator = (LinearLayout) parent.findViewById(R.id.indicator_layout);

            mButtons = Arrays.asList(
                    mButton0, mButton1, mButton2, mButton3, mButton4,
                    mButton5, mButton6, mButton7, mButton8, mButton9);

            int[] numbersArray = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

            if(mPlaceDigitsRandomly){
                shuffleArray(numbersArray);
            }

            for(int i=0; i<numbersArray.length; i++){
                assignNumber(mButtons.get(i), numbersArray[i]);
            }

            mPinBuilder = new StringBuilder();

            // crate the indicators
            createIndicators(context, attrs);

            //set properties;
            setNumericTextSize(mTextSizeNumeric, false);
            setAlphabetTextSize(mTextSizeAlpha, false);
            setImageButtonSize(mDrawableSize, false);
            setTextColor(mTextColor, false);
            setPromptTextSize(mTextSizePrompt, false);
            setPromptText(mPromptText);
            setPinLength(mPinLength);
            setButtonClickListeners();
            updateIndicators(mPinBuilder.toString());
        }
    }

    private void assignNumber(PinPadButton p, Integer i){
        mButtonNumbers.put(p, i);
        p.setNumericText(Integer.toString(i));

    }

    private HashMap<PinPadButton, Integer> mButtonNumbers = new HashMap<>();

    private void shuffleArray(int[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
    /**
     * Sets the listener to receive changes to the entered pin
     * @param listener - {@link OnPinChangedListener} listener
     */
    public void setOnPinChangedListener(OnPinChangedListener listener) {
        mPinChangeListener = listener;
    }

    /**
     * Sets the pinpad prompt text
     * @param promptText - text to display on the prompt field
     */
    public void setPromptText(String promptText) {
        mPromptText = promptText;
        mTextViewPrompt.setVisibility(TextUtils.isEmpty(promptText) ? GONE : VISIBLE);
        mTextViewPrompt.setText(mPromptText);
        requestLayout();
    }

    /**
     * Sets the textsize for the prompt text
     * @param textSize - textsize for the prompt text in pixels
     */
    public void setPromptTextSize(float textSize) {
        setPromptTextSize(textSize, true);
    }

    /**
     * Sets the textSize for the numeric text
     * @param textSize - textisze for the numeric text in pixles
     */
    public void setNumericTextSize(float textSize) {
        setNumericTextSize(textSize, true);
    }

    public void setAlphabetTextSize(float textSize) {
        setAlphabetTextSize(textSize, true);
    }

    public void setTextColor(@ColorInt int color) {
        setTextColor(color, true);
    }

    public void setImageButtonSize(int size) {
        setImageButtonSize(size, true);
    }

    /**
     * Sets the pin length for the
     * @param length - length for the pin
     */
    public void setPinLength(int length) {
        if (length < 0) return;
        mPinLength = length;
        createIndicators(getContext(), getAttrs());
        requestLayout();
    }

    /**
     * Gets the length for the pin
     * @return int
     */
    public int getPinLength() {
        return mPinLength;
    }

    /**
     * Sets necessary click listeners
     */
    private void setButtonClickListeners() {
        for (PinPadButton button : mButtons) {
            button.setButtonClickListener(mDigitClickListener);
        }

        mButtonDone.setButtonClickListener(mDoneButtonClickListener);
        mButtonBack.setButtonClickListener(mBackButtonClickListener);
    }

    /**
     * Updates the pin by updating the indicators as well as updating the listener
     * @param oldPin - old pin
     * @param newPin - new pin
     */
    private void updatePin(String oldPin, String newPin) {
        // update indicators
        updateIndicators(newPin);

        // update listener
        if (mPinChangeListener != null) {
            mPinChangeListener.onPinChanged(oldPin, newPin);
        }
    }

    private void createIndicators(Context context, AttributeSet attrs) {
        mLayoutIndicator.removeAllViews();
        for (int i=0; i<mPinLength; i++) {
            Indicator indicator = new Indicator(context, attrs);
            indicator.setChecked(false);
            indicator.setIndicatorSize(mIndicatorSize);
            indicator.setColor(mIndicatorColor);

            int left, right;
            if (i == 0) {
                left = 0;
                right = mIndicatorSpacing;
            } else if (i == mPinLength - 1) {
                left= mIndicatorSpacing;
                right  = 0;
            } else {
                left = mIndicatorSpacing;
                right = mIndicatorSpacing;
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mIndicatorSize, mIndicatorSize);
            params.gravity = Gravity.CENTER;
            params.setMargins(left, 0, right, 0);
            indicator.setLayoutParams(params);

            mLayoutIndicator.addView(indicator);
        }
    }

    private void updateIndicators(String pin) {
        if (pin.length() <= mLayoutIndicator.getChildCount()) {
            for (int i=0; i<pin.length(); i++) {
                Indicator indicator = (Indicator) mLayoutIndicator.getChildAt(i);
                indicator.setChecked(true);
            }
            for (int i=pin.length(); i<mLayoutIndicator.getChildCount(); i++) {
                Indicator indicator = (Indicator) mLayoutIndicator.getChildAt(i);
                indicator.setChecked(false);
            }
        }
        requestLayout();
    }

    /**
     * Click listener for the 0-9 buttons
     */
    private PinPadButton.OnButtonClickListener mDigitClickListener = new PinPadButton.OnButtonClickListener() {
        @Override
        public void onButtonClick(PinPadButton button) {
            if (mPinBuilder.length() < getPinLength()) {
                String oldPin = mPinBuilder.toString();

                String number = getValueForButton(button);
                mPinBuilder.append(number);
                updatePin(oldPin, mPinBuilder.toString());
            }
        }
    };

    /**
     * Click listener for the back button
     */
    private PinPadButton.OnButtonClickListener mBackButtonClickListener = new PinPadButton.OnButtonClickListener() {
        @Override
        public void onButtonClick(PinPadButton button) {
            if (mPinBuilder.length() > 0) {
                String oldPin = mPinBuilder.toString();
                mPinBuilder.replace(mPinBuilder.length() - 1, mPinBuilder.length(), "");
                updatePin(oldPin, mPinBuilder.toString().trim());
            }
        }
    };

    /**
     * Click listener for the done button
     */
    private PinPadButton.OnButtonClickListener mDoneButtonClickListener = new PinPadButton.OnButtonClickListener() {
        @Override
        public void onButtonClick(PinPadButton button) {
            if (mPinChangeListener != null) {
                mPinChangeListener.onCompletedListener(mPinBuilder.toString());
            }
        }
    };

    private String getValueForButton(PinPadButton button) {
        if (button != null) {
            return mButtonNumbers.get(button).toString();
        }
        return "";
    }

    /***************************
     * private overloaded methods
     ***************************/
    private void setTextColor(@ColorInt int color, boolean requestLayout) {
        for (PinPadButton button : mButtons) {
            if (button != null) {
                button.setTextColor(color);
            }
        }
        mTextViewPrompt.setTextColor(color);
        mButtonDone.setTextColor(color);
        if (requestLayout) {
            requestLayout();
        }
    }

    private void setNumericTextSize(float textSize, boolean requestLayout) {
        for (PinPadButton button : mButtons) {
            if (button != null) {
                button.setNumericTextSize(textSize);
            }
        }
        mButtonDone.setNumericTextSize(textSize);
        if (requestLayout) {
            requestLayout();
        }
    }

    private void setPromptTextSize(float textSize, boolean requestLayout) {
        mTextViewPrompt.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        if (requestLayout) {
            requestLayout();
        }
    }

    private void setImageButtonSize(int size, boolean requestLayout) {
        mButtonBack.setImageIconSize(size);
        mButtonDone.setImageIconSize(size);
        if (requestLayout) {
            requestLayout();
        }
    }

    /**
     * Sets the alphabet textsize with an option to request layout afterwards
     * @param textSize - textSize for the alphabet text on the pinpad
     * @param requestLayout - flag whether or not to call {@link #requestLayout()}
     */
    private void setAlphabetTextSize(float textSize, boolean requestLayout) {
        for (PinPadButton button : mButtons) {
            if (button != null) {
                button.setAlphabetTextSize(textSize);
            }
        }
        if (requestLayout) {
            requestLayout();
        }
    }
}
