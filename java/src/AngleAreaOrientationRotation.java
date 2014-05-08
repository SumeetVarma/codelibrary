import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class AngleAreaOrientationRotation {

	public static double angleBetween(long ax, long ay, long bx, long by) {
		double a = Math.atan2(ax * by - ay * bx, ax * bx + ay * by);
		return a < 0 ? a + 2 * Math.PI : a;
	}

	public static double angleBetween2(long ax, long ay, long bx, long by) {
		double a = Math.atan2(by, bx) - Math.atan2(ay, ax);
		return a < 0 ? a + 2 * Math.PI : a;
	}

	public static long doubleSignedArea(int[] x, int[] y) {
		int n = x.length;
		long area = 0;
		for (int i = 0, j = n - 1; i < n; j = i++) {
			area += (long) (x[i] - x[j]) * (y[i] + y[j]);
//			area += (long) x[i] * y[j] - (long) x[j] * y[i];
		}
		return area;
	}

	// Returns -1 for clockwise, 0 for straight line, 1 for counterclockwise orientation
	public static int orientation(long ax, long ay, long bx, long by, long cx, long cy) {
		bx -= ax;
		by -= ay;
		cx -= ax;
		cy -= ay;
		long cross = bx * cy - by * cx;
		return cross < 0 ? -1 : cross > 0 ? 1 : 0;
	}

	public Point2D.Double rotateCCW(Point2D.Double p, double angle) {
		return new Point2D.Double(p.x * Math.cos(angle) - p.y * Math.sin(angle), p.x * Math.sin(angle) + p.y * Math.cos(angle));
	}

	// random test
	public static void main(String[] args) {
		Random rnd = new Random(1);
		int range = 100;
		for (int step = 0; step < 100_000; step++) {
			long ax = rnd.nextInt(range) - range / 2;
			long ay = rnd.nextInt(range) - range / 2;
			long bx = rnd.nextInt(range) - range / 2;
			long by = rnd.nextInt(range) - range / 2;
			long cx = rnd.nextInt(range) - range / 2;
			long cy = rnd.nextInt(range) - range / 2;
			int orientation1 = orientation(ax, ay, bx, by, cx, cy);
			int orientation2 = -Line2D.relativeCCW(ax, ay, bx, by, cx, cy);
			if (orientation1 == 0)
				continue;
			if (orientation1 != orientation2)
				throw new RuntimeException();
			if (ax == 0 && ay == 0 || bx == 0 && by == 0)
				continue;
			double res1 = angleBetween(ax, ay, bx, by);
			double res2 = angleBetween2(ax, ay, bx, by);
			if (Math.abs(res1 - res2) > 1e-9)
				throw new RuntimeException();
		}
	}
}
