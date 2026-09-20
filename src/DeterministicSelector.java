public class DeterministicSelector {

    public static int select(int[] array, int k) {
        if (array == null || array.length == 0 || k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid input or k out of bounds");
        }
        return select(array, 0, array.length - 1, k);
    }

    private static int select(int[] array, int lo, int hi, int k) {
        if (hi - lo + 1 <= 5) {
            insertionSort(array, lo, hi);
            return array[k];
        }

        int numGroups = (hi - lo + 5) / 5;
        for (int i = 0; i < numGroups; i++) {
            int groupLo = lo + i * 5;
            int groupHi = Math.min(groupLo + 4, hi);

            insertionSort(array, groupLo, groupHi);
            int medianIndex = groupLo + (groupHi - groupLo) / 2;

            swap(array, lo + i, medianIndex);
        }

        int midOfMediansIndex = lo + numGroups / 2;
        int pivotValue = select(array, lo, lo + numGroups - 1, midOfMediansIndex);

        int pivotIndex = partition(array, lo, hi, pivotValue);

        if (k == pivotIndex) {
            return array[k];
        } else if (k < pivotIndex) {
            return select(array, lo, pivotIndex - 1, k);
        } else {
            return select(array, pivotIndex + 1, hi, k);
        }
    }

    private static int partition(int[] array, int lo, int hi, int pivotValue) {
        for (int i = lo; i <= hi; i++) {
            if (array[i] == pivotValue) {
                swap(array, i, hi);
                break;
            }
        }

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

    private static void insertionSort(int[] array, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= lo && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}