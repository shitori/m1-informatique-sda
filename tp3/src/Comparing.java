import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cisco WebEx.
 * User: vegaz
 * Date: 2010-10-20
 * Time: 15:18:50
 */
public class Comparing {
    private Comparing() {
    }

    public static <T> boolean equal(T arg1, T arg2) {
        if (arg1 == null || arg2 == null) {
            return arg1 == arg2;
        } else if (arg1 instanceof Object[] && arg2 instanceof Object[]) {
            Object[] arr1 = (Object[]) arg1;
            Object[] arr2 = (Object[]) arg2;
            return Arrays.equals(arr1, arr2);
        } else if (arg1 instanceof String && arg2 instanceof String) {
            return equal((String) arg1, (String) arg2, true);
        } else {
            return arg1.equals(arg2);
        }
    }

    public static <T> boolean equal(T[] arr1, T[] arr2) {
        if (arr1 == null || arr2 == null) {
            return arr1 == arr2;
        }
        return Arrays.equals(arr1, arr2);
    }


    public static boolean equal(String arg1, String arg2) {
        return equal(arg1, arg2, true);
    }


    public static boolean equal(String arg1, String arg2, boolean caseSensitive) {
        if (arg1 == null || arg2 == null) {
            return arg1 == arg2;
        } else {
            return caseSensitive ? arg1.equals(arg2) : arg1.equalsIgnoreCase(arg2);
        }
    }

    public static boolean strEqual(String arg1, String arg2) {
        return strEqual(arg1, arg2, true);
    }

    public static boolean strEqual(String arg1, String arg2, boolean caseSensitive) {
        return equal(arg1 == null ? "" : arg1, arg2 == null ? "" : arg2, caseSensitive);
    }

    public static <T> boolean haveEqualElements(Collection<T> a, Collection<T> b) {
        if (a.size() != b.size()) {
            return false;
        }

        Set<T> aSet = new HashSet<T>(a);
        for (T t : b) {
            if (!aSet.contains(t)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean haveEqualElements(T[] a, T[] b) {
        if (a == null || b == null) {
            return a == b;
        }

        if (a.length != b.length) {
            return false;
        }

        Set<T> aSet = new HashSet<T>(Arrays.asList(a));
        for (T t : b) {
            if (!aSet.contains(t)) {
                return false;
            }
        }
        return true;
    }

    public static int hashcode(Object obj) {
        return obj == null ? 0 : obj.hashCode();
    }

    public static int hashcode(Object obj1, Object obj2) {
        return hashcode(obj1) ^ hashcode(obj2);
    }

    public static int compare(int name1, int name2) {
        return name1 < name2 ? -1 : name1 == name2 ? 0 : 1;
    }

    public static <T extends Comparable<T>> int compare(final T name1, final T name2) {
        if (name1 == null) return name2 == null ? 0 : -1;
        if (name2 == null) return 1;
        return name1.compareTo(name2);
    }
}
