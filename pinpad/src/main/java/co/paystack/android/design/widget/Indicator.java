package co.paystack.android.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.core.content.res.ResourcesCompat;

class Indicator extends LinearLayout implements Checkable {

    /**
     * Interface definition for a callback to be invoked when the checked state of this View is
     * changed.
     */
    public interface OnCheckedChangeListener {

        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param checkableView The view whose state has changed.
         * @param isChecked     The new checked state of checkableView.
         */
        void onCheckedChanged(View checkableView, boolean isChecked);
    }

    private static final int DEFAULT_INDICATOR_SIZE = 12;
    private static final int DEFAULT_INDICATOR_STROKE_WIDTH = 4;
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean mChecked = false;

    @ColorInt
    private int mIndicatorFilledColor = Color.WHITE;
    private int mIndicatorEmptyColor = Color.WHITE;
    private int mIndicatorSize;
    private int mIndicatorStrokeWidth = DEFAULT_INDICATOR_STROKE_WIDTH;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public Indicator(Context context) {
        super(context);
        init(context, null);
    }

    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Indicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Indicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (context != null) {
            if (attrs != null) {
                TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinPadView);

                mIndicatorSize = a.getDimensionPixelSize(R.styleable.PinPadView_pin_indicator_size,
                        convertDpToPixel(DEFAULT_INDICATOR_SIZE));
                mIndicatorStrokeWidth = a.getDimensionPixelOffset(R.styleable.PinPadView_pin_indicator_stroke_width,
                        DEFAULT_INDICATOR_STROKE_WIDTH);
                if (a.hasValue(R.styleable.PinPadView_pin_indicator_filled_color)) {
                    mIndicatorFilledColor = a.getColor(R.styleable.PinPadView_pin_indicator_filled_color,
                            ResourcesCompat.getColor(getResources(), R.color.pstck_pinpad_default_pin_indicator_filled_color, null));
                }
                if (a.hasValue(R.styleable.PinPadView_pin_indicator_empty_color)) {
                    mIndicatorEmptyColor = a.getColor(R.styleable.PinPadView_pin_indicator_empty_color,
                            ResourcesCompat.getColor(getResources(), R.color.pstck_pinpad_default_pin_indicator_empty_color, null));
                }
                a.recycle();
            }

            setIndicatorSize(mIndicatorSize);
            // create drawable
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(createDrawable());
            } else {
                setBackgroundDrawable(createDrawable());
            }
            setChecked(false);
        }

    }

    private StateListDrawable createDrawable() {
        StateListDrawable drawable = new StateListDrawable();

        drawable.addState(new int[]{android.R.attr.state_checked}, createFilledDrawable());
        drawable.addState(StateSet.WILD_CARD, createEmptyDrawable());
        return drawable;
    }

    private Drawable createEmptyDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(mIndicatorStrokeWidth, mIndicatorEmptyColor);
        drawable.setColor(Color.TRANSPARENT);
        return drawable;
    }

    private Drawable createFilledDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(mIndicatorFilledColor);
        return drawable;
    }

    public void setIndicatorSize(int size) {
        mIndicatorSize = size;
        LinearLayout.LayoutParams params = new LayoutParams(size, size);
        setLayoutParams(params);
    }

    public void setFilledColor(@ColorInt int color) {
        mIndicatorFilledColor = color;
        requestLayout();
    }

    public void setEmptyColor(@ColorInt int color) {
        mIndicatorEmptyColor = color;
        requestLayout();
    }

    /**
     * Register a callback to be invoked when the checked state of this view changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void setChecked(boolean b) {
        if (b != mChecked) {
            mChecked = b;
            refreshDrawableState();

            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    private int convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
