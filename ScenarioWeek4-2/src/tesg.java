import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.SimplePolygon2D;

public class tesg
{
	public static void main(String[] args)
	{
		Polygon2D gallery = new SimplePolygon2D();
		gallery.addVertex(new Point2D(5.0, 2.0));
		gallery.addVertex(new Point2D(4.5, 1.0));
		gallery.addVertex(new Point2D(4.0, 1.0));
		gallery.addVertex(new Point2D(3.5, 2.0));
		gallery.addVertex(new Point2D(3.0, 1.0));
		gallery.addVertex(new Point2D(2.5, 1.0));
		gallery.addVertex(new Point2D(2.0, 2.0));
		gallery.addVertex(new Point2D(1.5, 1.0));
		gallery.addVertex(new Point2D(1.0, 1.0));
		gallery.addVertex(new Point2D(0.5, 2.0));
		gallery.addVertex(new Point2D(0.0, 0.0));
		gallery.addVertex(new Point2D(5.0, 0.0));
		LineSegment2D l = new LineSegment2D(0, 0, 0.5, 2);
		
	}
}
