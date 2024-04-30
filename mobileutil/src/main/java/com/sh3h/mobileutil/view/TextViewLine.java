package com.sh3h.mobileutil.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.sh3h.mobileutil.R;

/**
 * Created by xulongjun on 2016/2/16.
 */
public class TextViewLine extends AppCompatTextView {

    private Paint _paint = null;

    public TextViewLine(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);

        // 获得xml里定义的属性,格式为 名称_属性名 后面是默认值
        int borderColor = a.getColor(R.styleable.CustomTextView_BorderColor, getResources()
                .getColor(R.color.textview_line_color));
        float textSize = a.getDimension(R.styleable.CustomTextView_TextSize, getResources()
                .getDimension(R.dimen.textview_textsize));

        _paint = new Paint();
        _paint.setColor(borderColor);
        _paint.setTextSize(textSize);
        // 为了保持以后使用该属性一致性,返回一个绑定资源结束的信号给资源
        a.recycle();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, _paint);
    }
}
