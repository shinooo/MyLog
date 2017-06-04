package jp.techacademy.shino.oomori.mylog;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogGridView extends GridLayout {
    List<TextView> childList = new ArrayList<>();

    public LogGridView(Context context) {
        // Javaのソースコードから呼び出す用のコンストラクタ
        this(context,null);
    }

    public LogGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);

        for (int i = 0 ; i < 96 ;i++){
            TextView view = new TextView(context);
            childList.add(view);
            view.setId(i);
            if(i%4 == 0) {
                view.setText(String.valueOf(i/4));
            }
            view.setLayoutParams(new ViewGroup.LayoutParams(80,200));
            view.setBackgroundResource(R.drawable.waku2);
            addView(view);
        }
        setColumnCount(16);
    }

    public void setColorAtPosition(Date startDate, Date endDate, int color)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        int s_h = cal.get(Calendar.HOUR_OF_DAY);
        int s_m = cal.get(Calendar.MINUTE);
        int startId = s_h * 4 + (s_m / 15);

        cal.setTime(endDate);
        int e_h = cal.get(Calendar.HOUR_OF_DAY);
        int e_m = cal.get(Calendar.MINUTE);
        int endId = e_h * 4 + (e_m / 15);
        for (int i = startId; i < endId; i++) {
            View view = childList.get(i);
            view.setBackgroundColor(color);
        }
    }

    public void clearColor() {
        for (TextView t : childList) {
            t.setBackgroundResource(R.drawable.waku2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
