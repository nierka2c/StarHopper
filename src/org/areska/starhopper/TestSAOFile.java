package org.areska.starhopper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;


public class TestSAOFile
{
    
    public static void main(String[] args)
    {
        try
        {
            FileInputStream fis = new FileInputStream("h:/temp/sao/SAO");
            
            final int ENTRY_BYTES = 28;
            
            byte[] buf = new byte[ENTRY_BYTES];
            System.out.println( "Avail: " + fis.available());
            fis.read(buf, 0, ENTRY_BYTES); // header
            
            for( ;; ) {
                fis.read(buf, 0, ENTRY_BYTES);
                byte[] ascBytes = Arrays.copyOfRange(buf, 0, 8); // B1950 Right Ascension (radians)
                double asc = 0;
                double longBitsToDouble = Double.longBitsToDouble(toLong(ascBytes));
                byte[] decBytes = Arrays.copyOfRange(buf, 8, 16); // B1950 Right Ascension (radians)
                byte[] magBytes = Arrays.copyOfRange(buf, 18, 20); // Magnitude * 100
                int mag = 0;
                mag |= magBytes[0] & 0xFF;
                mag <<= 8;
                mag |= magBytes[1] & 0xFF;
                mag = mag + 1;
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

}
