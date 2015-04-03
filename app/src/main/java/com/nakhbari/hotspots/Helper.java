package com.nakhbari.hotspots;

/**
 * Created by Nima on 4/3/2015.
 */
public class Helper {
    public static String FormatTemperatureToString(double d) {
        if (d == (int) d)
            return String.format("%d", (int) d);
        else
            return String.format("%.2f", d);
    }
}

