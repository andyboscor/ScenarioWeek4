import java.util.ArrayList;

import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.Polygons2D;
import math.geom2d.polygon.SimplePolygon2D;

public class tesg
{
	public static void main(String[] args)
	{
		LineSegment2D l1 = new LineSegment2D(0, 0, 1, 1);
		LineSegment2D l2 = new LineSegment2D(2, 0, 0, 2);
//		
//		System.out.println(LineSegment2D.intersects(l2, l2));
//		System.out.println(LineSegment2D.getIntersection(l1, l2));
		System.out.println(l1.intersection(l2));
		System.out.println(l2.intersection(l1));
	}
	public static ArrayList<Polygon2D> polyDiff(Polygon2D p1, Polygon2D p2)
	{
		ArrayList<Polygon2D> difference = new ArrayList<Polygon2D>();
		difference.add(Polygons2D.difference(p1, p2));
		return difference;
	}
}
