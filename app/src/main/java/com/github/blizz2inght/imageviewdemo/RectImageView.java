package com.github.blizz2inght.imageviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RectImageView extends androidx.appcompat.widget.AppCompatImageView {
    private static final String TAG = "RectImageView";
    private final int mBlurRadius;
    private Paint mPainter;

    private int mCornerRadius;
    private Path mClipPath;

    public RectImageView(@NonNull  Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectImageView(@NonNull Context context, @Nullable  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RectImageView);
        mCornerRadius = ta.getDimensionPixelSize(R.styleable.RectImageView_cornerRadius, 0);
        mBlurRadius = ta.getDimensionPixelSize(R.styleable.RectImageView_shadowRadius, 0);

        ta.recycle();
        mPainter = new Paint();
        mPainter.setColor(Color.YELLOW);
        mPainter.setMaskFilter(new BlurMaskFilter(mBlurRadius, BlurMaskFilter.Blur.OUTER));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mClipPath == null) {
            mClipPath = new Path();
        }
        mClipPath.reset();
        mClipPath.addRoundRect(getLeft(), getTop(), getRight(), getBottom(), mCornerRadius ,mCornerRadius, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int count = canvas.save();

        canvas.drawPath(mClipPath, mPainter);
        canvas.clipPath(mClipPath);
        super.onDraw(canvas);
        canvas.restoreToCount(count);
    }

}
