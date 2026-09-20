import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {

    public static double findClosestPair(Point[] points) {
        if (points == null || points.length < 2) {
            return Double.POSITIVE_INFINITY;
        }

        Point[] pointsByX = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsByX, Comparator.comparingDouble(p -> p.x));

        Point[] pointsByY = Arrays.copyOf(pointsByX, pointsByX.length);

        Point[] aux = new Point[points.length];

        return closest(pointsByX, pointsByY, aux, 0, points.length - 1);
    }

    private static double closest(Point[] pointsByX, Point[] pointsByY, Point[] aux, int lo, int hi) {
        if (hi <= lo) return Double.POSITIVE_INFINITY;
        if (hi - lo == 1) {
            if (pointsByY[lo].y > pointsByY[hi].y) swap(pointsByY, lo, hi);
            return pointsByX[lo].distanceTo(pointsByX[hi]);
        }
        if (hi - lo == 2) {
            insertionSortByY(pointsByY, lo, hi);
            double d1 = pointsByX[lo].distanceTo(pointsByX[lo + 1]);
            double d2 = pointsByX[lo].distanceTo(pointsByX[hi]);
            double d3 = pointsByX[lo + 1].distanceTo(pointsByX[hi]);
            return Math.min(d1, Math.min(d2, d3));
        }

        int mid = lo + (hi - lo) / 2;
        Point median = pointsByX[mid];

        double delta1 = closest(pointsByX, pointsByY, aux, lo, mid);
        double delta2 = closest(pointsByX, pointsByY, aux, mid + 1, hi);
        double delta = Math.min(delta1, delta2);

        merge(pointsByY, aux, lo, mid, hi);

        int stripCount = 0;
        for (int i = lo; i <= hi; i++) {
            if (Math.abs(pointsByY[i].x - median.x) < delta) {
                aux[stripCount++] = pointsByY[i];
            }
        }

        for (int i = 0; i < stripCount; i++) {
            for (int j = i + 1; j < stripCount && (aux[j].y - aux[i].y) < delta; j++) {
                double distance = aux[i].distanceTo(aux[j]);
                if (distance < delta) {
                    delta = distance;
                }
            }
        }

        return delta;
    }

    private static void merge(Point[] array, Point[] aux, int lo, int mid, int hi) {
        System.arraycopy(array, lo, aux, lo, hi - lo + 1);
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) array[k] = aux[j++];
            else if (j > hi) array[k] = aux[i++];
            else if (aux[j].y < aux[i].y) array[k] = aux[j++];
            else array[k] = aux[i++];
        }
    }

    private static void swap(Point[] array, int i, int j) {
        Point temp = array[i]; array[i] = array[j]; array[j] = temp;
    }

    private static void insertionSortByY(Point[] array, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            Point key = array[i];
            int j = i - 1;
            while (j >= lo && array[j].y > key.y) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    public static double bruteForce(Point[] points) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double d = points[i].distanceTo(points[j]);
                if (d < min) min = d;
            }
        }
        return min;
    }
}