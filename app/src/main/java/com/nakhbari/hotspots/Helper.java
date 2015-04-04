package com.nakhbari.hotspots;

/**
 * Created by Nima on 4/3/2015.
 */
public class Helper {
    public static String FormatTemperatureToString(double d) {
        String out;
        if (d == (int) d)
            out = String.format("%d", (int) d);
        else if ((int) (d * 10) == d * 10)
            out = String.format("%.1f", d);
        else
            out = String.format("%.2f", d);

        return out + "\u00b0C";
    }
}

