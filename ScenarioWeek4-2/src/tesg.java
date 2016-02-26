import java.util.ArrayList;
import java.util.HashMap;

import org.jgrapht.UndirectedGraph;

import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.Polygons2D;
import math.geom2d.polygon.SimplePolygon2D;

public class tesg
{
	static HashMap<Point2D, ArrayList<Polygon2D>> map = new HashMap<Point2D, ArrayList<Polygon2D>>();
	public static void main(String[] args)
	{
		Polygon2D p = new SimplePolygon2D();
		p.addVertex(new Point2D(0,0));
		p.addVertex(new Point2D(6,0));
		p.addVertex(new Point2D(6,6));
		p.addVertex(new Point2D(0,6));
		p.addVertex(new Point2D(0,4));
		p.addVertex(new Point2D(1,4));
		p.addVertex(new Point2D(1,5));
		p.addVertex(new Point2D(5,5));
		p.addVertex(new Point2D(5,1));
		p.addVertex(new Point2D(1,1));
		p.addVertex(new Point2D(1,3));
		p.addVertex(new Point2D(0,3));
		
		System.out.println(p.centroid());
	}
	public static ArrayList<Polygon2D> polyDiff(Polygon2D p1, Polygon2D p2)
	{
		ArrayList<Polygon2D> difference = new ArrayList<Polygon2D>();
		difference.add(Polygons2D.difference(p1, p2));
		return difference;
	}
}
