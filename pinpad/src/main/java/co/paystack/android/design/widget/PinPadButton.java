package co.paystack.android.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

class PinPadButton extends ForegroundRelativeLayout {
    private static final float DEFAULT_TEXT_SIZE_NUMERIC = 18f;
    private static final float DEFAULT_TEXT_SIZE_ALPHA = 12f;
    private static final int DEFAULT_DRAWABLE_SIZE = 15;
    @ColorInt
    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;

    private float mTextSizeNumeric;
    private float mTextSizeAlpha;
    private int mDrawableSize;
    private Drawable mButtonDrawable;
    private String mTextNumeric;
    private String mTextAlphabet;

    private TextView mTextViewNumeric;
    private TextView mTextViewAlphabet;
    private ImageView mImageIcon;

    private ColorStateList mTextColor = ColorStateList.valueOf(DEFAULT_TEXT_COLOR);
    private OnButtonClickListener mButtonClickListener;

    public interface OnButtonClickListener {
        void onButtonClick(PinPadButton button);
    }

    public PinPadButton(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PinPadButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinPadButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(@NonNull Context context, AttributeSet attrs, int defStyle) {
        View view = inflate(context, R.layout.layout_button, this);
        mTextViewNumeric = (TextView) view.findViewById(R.id.numeric_text);
        mTextViewAlphabet = (TextView) view.findViewById(R.id.alphabet_text);
        mImageIcon = (ImageView) view.findViewById(R.id.pinbutton_icon);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinPadView, defStyle, 0);

            mTextSizeNumeric = a.getDimension(R.styleable.PinPadView_button_numeric_textsize,
                    DEFAULT_TEXT_SIZE_NUMERIC);
            mTextSizeAlpha = a.getDimension(R.styleable.PinPadView_button_alpha_textsize,
                    DEFAULT_TEXT_SIZE_ALPHA);
            mDrawableSize = a.getDimensionPixelSize(R.styleable.PinPadView_button_drawable_size,
                    15);
            if (a.hasValue(R.styleable.PinPadView_button_drawable)) {
                mButtonDrawable = a.getDrawable(R.styleable.PinPadView_button_drawable);
            }
            if (a.hasValue(R.styleable.PinPadView_button_text_numeric)) {
                mTextNumeric = a.getString(R.styleable.PinPadView_button_text_numeric);
            }
            if (a.hasValue(R.styleable.PinPadView_button_text_alpha)) {
                mTextAlphabet = a.getString(R.styleable.PinPadView_button_text_alpha);
            }
            if (a.hasValue(R.styleable.PinPadView_button_textcolor)) {
                mTextColor = a.getColorStateList(R.styleable.PinPadView_button_textcolor);
            }

            a.recycle();
        }

        if (mButtonDrawable == null) {
            if (mTextNumeric != null && !mTextNumeric.isEmpty()) {
                // create numeric textview
                mImageIcon.setVisibility(GONE);
                mTextViewNumeric.setVisibility(VISIBLE);
                mTextViewNumeric.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeNumeric);
                mTextViewNumeric.setText(mTextNumeric);

            }
            if (mTextAlphabet != null && !mTextAlphabet.isEmpty()) {
                // create alphabet textview
                mImageIcon.setVisibility(GONE);
                mTextViewAlphabet.setVisibility(VISIBLE);
                mTextViewAlphabet.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeAlpha);
                mTextViewAlphabet.setText(mTextAlphabet);
            }
        } else {
            mImageIcon.setVisibility(VISIBLE);
            mTextViewAlphabet.setVisibility(GONE);
            mTextViewNumeric.setVisibility(GONE);
            // create icon
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mDrawableSize, mDrawableSize);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mImageIcon.setLayoutParams(params);
            mImageIcon.setImageDrawable(mButtonDrawable);
        }

        setTextColor(mTextColor);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        setLayoutParams(lp);
        setClickable(true);
    }

    /**
     * Sets a button click listener for the button
     *
     * @param listener - {@link OnButtonClickListener} listener
     */
    public void setButtonClickListener(OnButtonClickListener listener) {
        mButtonClickListener = listener;
    }

    public void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            mTextViewAlphabet.setTextColor(colorStateList);
            mTextViewNumeric.setTextColor(colorStateList);
        }
    }

    public void setNumericTextSize(float textSize) {
        if (mTextViewNumeric != null) {
            mTextSizeNumeric = textSize;
            mTextViewNumeric.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            requestLayout();
        }
    }

    public void setAlphabetTextSize(float textSize) {
        if (mTextViewAlphabet != null) {
            mTextSizeAlpha = textSize;
            mTextViewAlphabet.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            requestLayout();
        }
    }

    /**
     * Gets the numeric text for the button
     */
    public String getNumericText() {
        return mTextNumeric;
    }

    /**
     * Sets the numeric text on the button
     *
     * @param text - numeric text to display on the button
     */
    public void setNumericText(String text) {
        if (mTextViewNumeric != null) {
            mTextNumeric = text;
            mTextViewNumeric.setText(text);
            requestLayout();
        }
    }

    /**
     * Gets the alphabet text for the button
     */
    public String getAlphabetText() {
        return mTextAlphabet;
    }

    /**
     * Sets the alphabet text on the button
     *
     * @param text - alphabet text to display on the button
     */
    public void setAlphabetText(String text) {
        if (mTextViewAlphabet != null) {
            mTextAlphabet = text;
            mTextViewAlphabet.setText(text);
            requestLayout();
        }
    }

    /**
     * @return true if the button is an image button, false otherwise
     */
    public boolean isImageButton() {
        return mButtonDrawable != null;
    }

    /**
     * Sets the image size to use for images set on the button
     *
     * @param imageSize - required image size in pixels
     */
    public void setImageIconSize(int imageSize) {
        if (mImageIcon != null) {
            mDrawableSize = imageSize;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mDrawableSize, mDrawableSize);
            params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mImageIcon.setLayoutParams(params);
            requestLayout();
        }
    }

    /**
     * Sets the text color to use for both the alphabet and numeric texts
     *
     * @param color - @{@link ColorInt} representation of the color
     */
    public void setTextColor(@ColorInt int color) {
        setTextColor(ColorStateList.valueOf(color));
        requestLayout();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mButtonClickListener != null) {
                mButtonClickListener.onButtonClick(this);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP && (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            if (mButtonClickListener != null) {
                mButtonClickListener.onButtonClick(this);
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
