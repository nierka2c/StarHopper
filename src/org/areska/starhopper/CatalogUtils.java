package org.areska.starhopper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CatalogUtils
{
    
    public static List<StarCoord> getStarCoords( double maxMagnitude, int width, int height, InputStream inputStream ) {
        List<StarCoord> points = new ArrayList<StarCoord>();
        List<Star> stars = getStars(maxMagnitude, inputStream);
        
        float circleRadius = height / 2;
        for( Star star : stars ) {
            double ascRadians = star.getAscensionRadians();
            double decRadians = star.getDeclinationRadians();
            if( decRadians < 0 ) {
                continue;
            }
            double radius = circleRadius * Math.cos(decRadians);
            int x = (int)(radius * Math.cos(ascRadians));
            int y = (int)(radius * Math.sin(ascRadians));
            x += width / 2;
            y += height / 2;
            StarCoord starCoord = new StarCoord();
            starCoord.setX(x);
            starCoord.setY(y);
            starCoord.setMagnitude(star.getMagnitude());
            points.add( starCoord );
        }
        return points;
    }
    
    public static List<Star> getStars(double maxMagnitude, InputStream inputStream)
    {
        List<Star> stars = new ArrayList<Star>();
        try
        {
            //final int ENTRY_BYTES = 32;
            final int ENTRY_BYTES = 28; // SAO
            
            byte[] buf = new byte[ENTRY_BYTES];
            inputStream.read(buf, 0, ENTRY_BYTES); // header

            for( ;inputStream.available() > 10; ) {
                inputStream.read(buf, 0, ENTRY_BYTES);
                byte[] ascBytes = Arrays.copyOfRange(buf, 0, 8); // B1950 Right Ascension (radians)
                byte[] decBytes = Arrays.copyOfRange(buf, 8, 16); // B1950 Declination (radians)
                byte[] magBytes = Arrays.copyOfRange(buf, 18, 20); // Magnitude * 100
                int mag = toShort(magBytes);
                long asc = toLong(ascBytes);
                long dec = toLong(decBytes);
                double ascDouble = Double.longBitsToDouble(asc);
                double decDouble = Double.longBitsToDouble(dec);
                                
                if( mag < maxMagnitude ) {
                     Star star = new Star();
                     star.setMagnitude(mag);
                     star.setAscensionRadians(ascDouble);
                     star.setDeclinationRadians(decDouble);
                     stars.add(star);
                }
                
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return stars;
    }
    
    public static long toLong(byte[] data) {
        if (data == null || data.length != 8) return 0x0;
        // ----------
        return (
                // (Below) convert to longs before shift because digits
                //         are lost with ints beyond the 32-bit limit
                (long)(0xff & data[0]) << 56  |
                (long)(0xff & data[1]) << 48  |
                (long)(0xff & data[2]) << 40  |
                (long)(0xff & data[3]) << 32  |
                (long)(0xff & data[4]) << 24  |
                (long)(0xff & data[5]) << 16  |
                (long)(0xff & data[6]) << 8   |
                (long)(0xff & data[7]) << 0
                );
    }
    
    public static short toShort(byte[] data) {
        if (data == null || data.length != 2) return 0x0;
        // ----------
        return (short)(
                (0xff & data[0]) << 8   |
                (0xff & data[1]) << 0
                );
    }
    
    public static byte[] toByta(double[] data) {
        if (data == null) return null;
        // ----------
        byte[] byts = new byte[data.length * 8];
        for (int i = 0; i < data.length; i++)
            System.arraycopy(toByta(data[i]), 0, byts, i * 8, 8);
        return byts;
    }
    
    public static byte[] toByta(double data) {
        return toByta(Double.doubleToRawLongBits(data));
    }
    
    public static byte[] longToByteArray(long data) {
        return new byte[] {
        (byte)((data >> 56) & 0xff),
        (byte)((data >> 48) & 0xff),
        (byte)((data >> 40) & 0xff),
        (byte)((data >> 32) & 0xff),
        (byte)((data >> 24) & 0xff),
        (byte)((data >> 16) & 0xff),
        (byte)((data >> 8 ) & 0xff),
        (byte)((data >> 0) & 0xff),
        };
        }

}

