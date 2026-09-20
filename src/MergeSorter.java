public class MergeSorter {
    private static final int CUTOFF = 15;

    public static int maxDepth = 0;
    public static long comparisons = 0;

    public static void sort(int[] array) {
        maxDepth = 0;
        comparisons = 0;
        if (array == null || array.length <= 1) return;
        int[] aux = new int[array.length];
        sort(array, aux, 0, array.length - 1, 1);
    }

    private static void sort(int[] array, int[] aux, int lo, int hi, int depth) {
        if (depth > maxDepth) maxDepth = depth;

        if (hi - lo <= CUTOFF - 1) {
            insertionSort(array, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort(array, aux, lo, mid, depth + 1);
        sort(array, aux, mid + 1, hi, depth + 1);

        comparisons++;
        if (array[mid] <= array[mid + 1]) {
            return;
        }
        merge(array, aux, lo, mid, hi);
    }

    private static void merge(int[] array, int[] aux, int lo, int mid, int hi) {
        System.arraycopy(array, lo, aux, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                array[k] = aux[j++];
            } else if (j > hi) {
                array[k] = aux[i++];
            } else {
                comparisons++;
                if (aux[j] < aux[i]) {
                    array[k] = aux[j++];
                } else {
                    array[k] = aux[i++];
                }
            }
        }
    }

    private static void insertionSort(int[] array, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= lo) {
                comparisons++;
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
        }
    }
}