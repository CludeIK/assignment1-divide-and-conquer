import java.util.Random;

public class QuickSorter {
    private static final Random RANDOM = new Random();

    public static int maxDepth = 0;
    public static long swaps = 0;

    public static void sort(int[] array) {
        maxDepth = 0;
        swaps = 0;
        if (array == null || array.length <= 1) return;
        sort(array, 0, array.length - 1, 1);
    }

    private static void sort(int[] array, int lo, int hi, int depth) {
        while (lo < hi) {
            if (depth > maxDepth) maxDepth = depth;

            int pivotIndex = lo + RANDOM.nextInt(hi - lo + 1);
            swap(array, pivotIndex, hi);

            int p = partition(array, lo, hi);

            if (p - lo < hi - p) {
                sort(array, lo, p - 1, depth + 1);
                lo = p + 1;
            } else {
                sort(array, p + 1, hi, depth + 1);
                hi = p - 1;
            }
        }
    }

    private static int partition(int[] array, int lo, int hi) {
        int pivot = array[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, hi);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j) {
        if (i != j) swaps++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
