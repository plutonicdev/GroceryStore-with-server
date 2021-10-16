package com.quintus.labs.grocerystore.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.quintus.labs.grocerystore.R;

public class CircleImage extends ImageView {

    private float radius;
    private Path path;
    private RectF rect;

    public CircleImage(Context context) {
        super(context);
        init(null);
    }

    public CircleImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet set) {
        path = new Path();
        if (set == null)
            return;

        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.CircleImage);
        radius = ta.getFloat(R.styleable.CircleImage_img_radius,18.0f);
        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}