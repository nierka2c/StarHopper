package org.areska.starhopper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MainView extends View
{

    public MainView(Context context)
    {
        super(context);
    }

    public MainView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public MainView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        canvas.drawLine( 10f, 10f, 30f, 30f, paint);
        super.onDraw(canvas);
    }
    
    

}
