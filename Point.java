// A class for a point that has 2 values for its position, x and y.
public record Point(int x, int y) {

	// method to calculate distance to another point
	public final double distanceTo(Point point) {
		int dx = x - point.x;
		int dy = y - point.y;
		double d = Math.sqrt(dx * dx + dy * dy);
		return d;
	}

	public String toString() {
		return "(x=%d, y=%d)".formatted(x,y);
	}
	
}
