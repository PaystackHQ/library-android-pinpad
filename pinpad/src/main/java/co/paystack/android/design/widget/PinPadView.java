package co.paystack.android.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * PinPadView
 */
public class PinPadView extends FrameLayout {
    private static final int DEFAULT_INDICATOR_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_PIN_LENGTH = 4;

    @ColorInt
    private int mIndicatorColor = DEFAULT_INDICATOR_COLOR;

    @ColorInt
    private int mTextColor = DEFAULT_TEXT_COLOR;
    private String mTextPrompt;
    private int mPinLength = DEFAULT_PIN_LENGTH;

    public PinPadView(Context context) {
        super(context);
    }

    public PinPadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinPadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PinPadView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinPadView);

            mPinLength = a.getInteger(R.styleable.PinPadView_pin_length, DEFAULT_PIN_LENGTH);
            if (a.hasValue(R.styleable.PinPadView_pin_indicator_color)) {
                mIndicatorColor = a.getColor(R.styleable.PinPadView_pin_indicator_color,
                        DEFAULT_INDICATOR_COLOR);
            }

            if (a.hasValue(R.styleable.PinPadView_text_prompt)) {
                mTextPrompt = a.getString(R.styleable.PinPadView_text_prompt);
            }

            if (a.hasValue(R.styleable.PinPadView_textColor)) {
                mTextColor = a.getColor(R.styleable.PinPadView_textColor, DEFAULT_TEXT_COLOR);
            }
            a.recycle();
        }
    }
}
