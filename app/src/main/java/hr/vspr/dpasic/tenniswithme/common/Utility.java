package hr.vspr.dpasic.tenniswithme.common;

/**
 * Created by edjapas on 30.1.2017..
 */

public class Utility {

    private Utility() {
    }

    public static int getIndexOfItemInArray(String item, String[] items) {
        int index = 0;
        for (String str : items) {
            if (str.equals(item)) {
                return index;
            }
            index++;
        }
        return 0;
    }
}
