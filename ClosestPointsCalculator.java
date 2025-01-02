import java.util.Arrays;

public class ClosestPointsCalculator {

    public static Point[] findClosestPairOfPoints(Point[] points) {
        // Sort points by x-coordinate
        Arrays.sort(points, (p1, p2) -> Integer.compare(p1.x(), p2.x()));

        return findClosestPair(points, 0, points.length - 1);
    }

    // help method to find the closest pair
    private static Point[] findClosestPair(Point[] points, int first, int last) {

        // If there are 3 or less points, run the brute force method to find closest pair
        if (last - first <= 3) {
            return bruteForceClosestPair(points, first, last);
        }

        // If there are more than 3 points, perform divide and conquer strategy by dividing the
        // sorted array of points into two halves
        int mid = (first + last) / 2;  // index of the middle point, based on x-value
        Point[] leftHalf = findClosestPair(points, first, mid);
        Point[] rightHalf = findClosestPair(points, mid + 1, last);

        // Determine the minimum distance pair between left and right halves
        Point[] minPair = (distance(leftHalf) < distance(rightHalf)) ? leftHalf : rightHalf;

        // Merge the two halves and find the closest pair across the division
        Point[] strip = new Point[last - first + 1];
        int stripSize = 0;
        for (int i = first; i <= last; i++) {
            if (Math.abs(points[i].x() - points[mid].x()) < distance(minPair)) {
                strip[stripSize++] = points[i];
            }
        }

        // sort the strip based on the points's y value
        Arrays.sort(strip, 0, stripSize, (p1, p2) -> Integer.compare(p1.y(), p2.y()));

        // Compare the points in the strip to see if there are points closer to each other than the pair already
        // found in the two sides
        for (int i = 0; i < stripSize; i++) {
            for (int j = i + 1; j < stripSize && (strip[j].y() - strip[i].y()) < distance(minPair); j++) {
                if (strip[i].distanceTo(strip[j]) < distance(minPair)) {
                    minPair[0] = strip[i];
                    minPair[1] = strip[j];
                }
            }
        }
        return minPair;
    }

    private static Point[] bruteForceClosestPair(Point[] points, int first, int last) {
        // initialize the minDistance variable to start as the highest possible
        double minDistance = Double.MAX_VALUE;
        Point[] closestPair = new Point[2];

        for (int i = first; i <= last; i++) {
            for (int j = i + 1; j <= last; j++) {
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPair[0] = points[i];
                    closestPair[1] = points[j];
                }
            }
        }
        return closestPair;
    }

    // help method that returns the distance between the points in a pair
    private static double distance(Point[] pair) {
        return pair[0].distanceTo(pair[1]);
    }
}
