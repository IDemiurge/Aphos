package utils.collection;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Created by Alexander on 11/20/2023
 */
public class SortUtils {
    public static <T> Comparator<T>
    comparator(Function<T, Integer> f) {
        return (t, t1) -> {
            Integer i = f.apply(t);
            Integer i1 = f.apply(t1);
            if (i > i1)
                return 1;
            if (i < i1)
                return -1;
            return 0;
        };
    }
}
