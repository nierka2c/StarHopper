package org.areska.starhopper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        try
        {
            InputStream inputStream = getContext().getAssets().open("SAO.stars");
            List<StarCoord> starCoords = CatalogUtils.getStarCoords(400, width, height, inputStream);
            for( StarCoord point : starCoords ) {
                if( point.getMagnitude() < 80 ) {
                    canvas.drawCircle(point.getX(), point.getY(), 5, paint);
                } else if( point.getMagnitude() < 160 ) {
                    canvas.drawCircle(point.getX(), point.getY(), 4, paint);
                } else if( point.getMagnitude() < 240 ) {
                    canvas.drawCircle(point.getX(), point.getY(), 3, paint);
                } else if( point.getMagnitude() < 320 ) {
                    canvas.drawCircle(point.getX(), point.getY(), 2, paint);
                } else {
                    canvas.drawCircle(point.getX(), point.getY(), 1, paint);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //canvas.drawLine( 10f, 10f, 30f, 30f, paint);
//        canvas.drawCircle(100f, 100f, 5, paint);
//        canvas.drawCircle(800f, 800f, 5, paint);
        super.onDraw(canvas);
    }
    
    

}
