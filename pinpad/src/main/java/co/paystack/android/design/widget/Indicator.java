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
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.StateSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

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

    private static final int DEFAULT_INDICATOR_COLOR = Color.WHITE;
    private static final int DEFAULT_INDICATOR_SIZE = 12;
    private static final int[] CHECKED_STATE_SET = {android.R.attr.state_checked};

    private boolean mChecked = false;

    @ColorInt
    private int mIndicatorColor = DEFAULT_INDICATOR_COLOR;
    private int mIndicatorSize;
    private int mIndicatorStrokeWidth = 4;

    private OnCheckedChangeListener mOnCheckedChangeListener;

    public Indicator(Context context) {
        super(context);
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
        if (context != null && attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PinPadView);

            mIndicatorSize = a.getDimensionPixelSize(R.styleable.PinPadView_pin_indicator_size,
                    convertDpToPixel(DEFAULT_INDICATOR_SIZE));
            mIndicatorStrokeWidth = a.getDimensionPixelOffset(R.styleable.PinPadView_pin_indicator_stroke_width,
                    4);
            if (a.hasValue(R.styleable.PinPadView_pin_indicator_color)) {
                mIndicatorColor = a.getColor(R.styleable.PinPadView_pin_indicator_color,
                        DEFAULT_INDICATOR_COLOR);
            }
            a.recycle();

            LinearLayout.LayoutParams params = new LayoutParams(mIndicatorSize, mIndicatorSize);
            // create drawable
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(createDrawable());
            } else {
                setBackgroundDrawable(createDrawable());
            }
            setLayoutParams(params);
            setChecked(false);
        }

    }

    private StateListDrawable createDrawable() {
        StateListDrawable drawable = new StateListDrawable();

        drawable.addState(new int[] { android.R.attr.state_checked}, createFilledDrawable());
        drawable.addState(StateSet.WILD_CARD, createEmptyDrawable());
        return drawable;
    }

    private Drawable createEmptyDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setStroke(4, mIndicatorColor);
        drawable.setColor(Color.TRANSPARENT);
        return drawable;
    }

    private Drawable createFilledDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(mIndicatorColor);
        return drawable;
    }

    public void setColor(@ColorInt int color) {
        mIndicatorColor = color;
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

    private int convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
