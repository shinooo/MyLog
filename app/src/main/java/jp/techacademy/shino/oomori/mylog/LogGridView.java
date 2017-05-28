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

public class LogGridView extends GridLayout {

    public LogGridView(Context context) {
        // Javaのソースコードから呼び出す用のコンストラクタ
        this(context,null);
    }

    public LogGridView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public LogGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);

        for (int i = 0 ; i < 24 ;i++){
            final View view = new View(context);
            view.setId(i);
            view.setLayoutParams(new ViewGroup.LayoutParams(50,100));

            if(i % 2 == 0) {
                view.setBackgroundColor(Color.BLUE);
            } else {
                view.setBackgroundColor(Color.YELLOW);
            }
            addView(view);
        }

        // LayoutInflater.from(context).inflate(R.layout.custom_grid_view,this,true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        //描画に使う色を指定
        canvas.drawLine(0, 0, 100, 100, paint);
        //線を引く。

        invalidate();

    }
}
