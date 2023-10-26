package system.utils;

/**
 * Created by Alexander on 10/26/2023
 */
public class MathUtils {

    public static int getMinMax(int i, int min, int max) {
        if (i >= max) {
            return max;
        }
        if (i <= min) {
            return min;
        }
        return i;
    }
    public static float getMinMax(float i, float min, float max) {
        if (i >= max) {
            return max;
        }
        if (i <= min) {
            return min;
        }
        return i;
    }

    public static float minMax(float i, float min, float max) {
        if (i >= max) {
            return max;
        }
        if (i <= min) {
            return min;
        }
        return i;
    }
}
