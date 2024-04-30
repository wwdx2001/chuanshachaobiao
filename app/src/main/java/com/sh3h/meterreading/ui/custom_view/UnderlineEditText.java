package com.sh3h.meterreading.ui.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.sh3h.meterreading.R;

/**
 * 自定义EditText，改变下划线样式
 *
 * @author xiaochao
 * @date 2018/11/19
 */
@SuppressLint("AppCompatCustomView")
public class UnderlineEditText extends EditText {
    public UnderlineEditText(Context context) {
        super(context);
        init();
    }

    public UnderlineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UnderlineEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // 画笔 用来画下划线
    private Paint paint;

    private void init() {
        setBackground(new ColorDrawable(Color.TRANSPARENT));
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.color_gray_7f));
        // 开启抗锯齿 较耗内存
//        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int lineCount = getLineCount();
        int lineHeight = getMeasuredHeight() - getPaddingBottom() / 2;
        float lineWidth = getMeasuredWidth();

        // 获取EditText中文字的物理长度
        TextPaint mTextPaint = getPaint();
        float textWidth = mTextPaint.measureText(getText().toString());
        if (textWidth > lineWidth) {
            lineWidth = textWidth;
        }

        // 根据行数循环画线
        for (int i = 0; i < lineCount; i++) {
            int lineY = (i + 1) * lineHeight;
            canvas.drawLine(0, lineY, lineWidth + getPaddingLeft(), lineY, paint);
        }
    }
}
