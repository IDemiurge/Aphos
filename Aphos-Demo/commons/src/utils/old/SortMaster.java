package utils.old;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class SortMaster<T> {


    public static void sortByExpression
            (List<? extends Object> list, Function<Object, Integer> function) {
        sortByExpression(false, list, function);
    }

    public static void sortByExpression
            (boolean ascending, List<? extends Object> list, Function<Object, Integer> function) {
        Collections.sort(list, getSorterByExpression(ascending, function));
    }

    public static Comparator<File> getSorterByDate(boolean newestFirst) {
        return getIntSorter(file -> Math.toIntExact(FileManager.getDateCreated(file) / 10000), newestFirst);
    }

    public static <T> Comparator<T> getIntSorter(Function<T, Integer> func, boolean ascending) {
        int n = ascending ? -1 : 1;
        return (o1, o2) -> {
            Integer res1 = n * func.apply(o1);
            Integer res2 = n * func.apply(o2);
            if (res1 > res2)
                return 1;
            if (res1 < res2)
                return -1;
            return 0;
        };
    }

    public void sortByExpression_
            (List<? extends T> list, Function<T, Integer> function) {
        Collections.sort(list, getSorterByExpression_(function));
    }

    public static <A> Comparator<? super A> sort
            (Function<A, Integer> function) {
        return new SortMaster<A>().getSorterByExpression_(function);
    }

    public static Comparator<? super Object> getSorterByExpression
            (Function<Object, Integer> function) {
        return getSorterByExpression(false, function);
    }

    public static Comparator<? super Object> getSorterByExpression
            (boolean ascending, Function<Object, Integer> function) {
        if (ascending)
            return Comparator.comparingInt(o -> function.apply(o));
        else
            return Comparator.comparingInt(o -> function.apply(o)).reversed();

    }

    public Comparator<? super T> getSorterByExpression_
            (Function<T, Integer> function) {
        return Comparator.comparingInt(o -> function.apply((T) o)).reversed();

    }

}
