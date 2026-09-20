import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Experiment {
    private static final Random RANDOM = new Random();

    public static int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt(n * 10);
        return arr;
    }

    public static int[] generateSortedArray(int n) {
        int[] arr = generateRandomArray(n);
        Arrays.sort(arr);
        return arr;
    }

    public static int[] generateReverseSortedArray(int n) {
        int[] arr = generateSortedArray(n);
        for (int i = 0; i < n / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[n - 1 - i];
            arr[n - 1 - i] = temp;
        }
        return arr;
    }

    public static int[] generateDuplicateHeavyArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = RANDOM.nextInt(10);
        return arr;
    }

    public static void runExperiments() {
        int[] sizes = {10000, 20000, 40000, 80000, 160000};
        String[] types = {"Random", "Sorted", "Reverse", "Duplicates"};

        try (PrintWriter writer = new PrintWriter(new FileWriter("results/results.csv"))) {
            writer.println("Algorithm,InputSize,InputType,Time(ns)");

            System.out.println("Warming up JVM...");
            for (int i = 0; i < 50; i++) {
                MergeSorter.sort(generateRandomArray(5000));
                QuickSorter.sort(generateRandomArray(5000));
            }

            System.out.println("Running actual experiments...");
            for (int n : sizes) {
                for (String type : types) {
                    int[] original = switch (type) {
                        case "Random" -> generateRandomArray(n);
                        case "Sorted" -> generateSortedArray(n);
                        case "Reverse" -> generateReverseSortedArray(n);
                        case "Duplicates" -> generateDuplicateHeavyArray(n);
                        default -> new int[0];
                    };

                    int[] copyForMerge = Arrays.copyOf(original, original.length);
                    long startMerge = System.nanoTime();
                    MergeSorter.sort(copyForMerge);
                    long timeMerge = System.nanoTime() - startMerge;
                    writer.println("MergeSort," + n + "," + type + "," + timeMerge);

                    int[] copyForQuick = Arrays.copyOf(original, original.length);
                    long startQuick = System.nanoTime();
                    QuickSorter.sort(copyForQuick);
                    long timeQuick = System.nanoTime() - startQuick;
                    writer.println("QuickSort," + n + "," + type + "," + timeQuick);
                }
            }
            System.out.println("Experiments finished! Results are saved to results/results.csv");
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}