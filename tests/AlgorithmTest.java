import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class AlgorithmTest {
    private static final Random RANDOM = new Random();

    @Test
    public void testSorts() {
        int[][] testCases = {
                {},
                {42},
                generateRandomArray(1000),
                generateSortedArray(1000),
                generateReverseSortedArray(1000),
                generateDuplicateArray(1000)
        };

        for (int[] original : testCases) {
            int[] expected = Arrays.copyOf(original, original.length);
            Arrays.sort(expected);

            int[] mergeResult = Arrays.copyOf(original, original.length);
            MergeSorter.sort(mergeResult);
            assertArrayEquals(expected, mergeResult, "MergeSort failed!");

            int[] quickResult = Arrays.copyOf(original, original.length);
            QuickSorter.sort(quickResult);
            assertArrayEquals(expected, quickResult, "QuickSort failed!");
        }
    }

    @Test
    public void testDeterministicSelect() {
        for (int i = 0; i < 100; i++) {
            int[] arr = generateRandomArray(500);
            int k = RANDOM.nextInt(arr.length);

            int[] copy = Arrays.copyOf(arr, arr.length);
            Arrays.sort(copy);
            int expected = copy[k];

            int actual = DeterministicSelector.select(arr, k);
            assertEquals(expected, actual, "Deterministic Select failed on test " + i);
        }
    }

    @Test
    public void testClosestPair() {
        int n = 1500;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(RANDOM.nextDouble() * 1000, RANDOM.nextDouble() * 1000);
        }

        double expected = ClosestPairSolver.bruteForce(points);
        double actual = ClosestPairSolver.findClosestPair(points);

        assertEquals(expected, actual, 1e-6, "Closest Pair failed!");
    }

    private int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt(10000);
        return arr;
    }
    private int[] generateSortedArray(int n) {
        int[] arr = generateRandomArray(n);
        Arrays.sort(arr);
        return arr;
    }
    private int[] generateReverseSortedArray(int n) {
        int[] arr = generateSortedArray(n);
        for (int i = 0; i < n / 2; i++) {
            int temp = arr[i]; arr[i] = arr[n - 1 - i]; arr[n - 1 - i] = temp;
        }
        return arr;
    }
    private int[] generateDuplicateArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt(5);
        return arr;
    }
}