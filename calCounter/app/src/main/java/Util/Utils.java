package Util;

import java.text.DecimalFormat;

public class Utils {
    public static String numberFormat(int val){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formatted = formatter.format(val);
        return formatted;
    }
}
