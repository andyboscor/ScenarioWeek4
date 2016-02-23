package src.polygon_test;

import java.util.Collection;

import math.geom2d.Point2D;
import math.geom2d.line.DegeneratedLine2DException;
import math.geom2d.line.Line2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.line.Ray2D;
import math.geom2d.line.StraightLine2D;
import math.geom2d.polygon.*;

public class ViewPolygon
{
	private final static double epsilon = 0.0000000001;
	private static Polygon2D g;
	public static Polygon2D getViewPolygon(Polygon2D gallery, Point2D start)
	{
		g = copyPoly(gallery);
//		System.out.println("The Copy: ");
//		printEdges();
		Collection<Point2D> vertices = gallery.vertices();
		Point2D a;
		for(Point2D v : vertices)
		{
			if(!start.almostEquals(v, epsilon))
			{
				//System.out.println(start + "->" + v);
				//System.out.println(isReachable(start, v));
				if(isReachable(start, v))
				{
//					System.out.println("Before the intersection: ");
//					printEdges();
					a = intersectionPoly(start, v);
					if(a != null)
					{
						int index = getPosition(a);
						g.insertVertex(index, a);
					}
//					System.out.println("After the intersection with " + a + ": ");
//					printEdges();
				}
			}
		}
//		System.out.println("The End of part 1: ");
//		printEdges();
		//System.out.println("I'm out");
		int i = 0;
		while(i < g.vertexNumber())
		{
			//System.out.println(g.vertex(i));
			Point2D v = g.vertex(i);
			if(!start.almostEquals(v, epsilon))
			{
				//System.out.println(isReachable(start, v));
				if(!isReachable(start, v))
				{
					g.removeVertex(i);
				}
				else
				{
					i++;
				}
			}
			else
			{
				i++;
			}
		}
		//System.out.println("I'm outx2");
		return g;
	}
	//Returns the position where a should be inserted in the polygon
	private static int getPosition(Point2D a)
	{
		//WTF
		Collection<LineSegment2D> edges = (Collection<LineSegment2D>) g.edges();
		int i = 0;
		for(LineSegment2D edge : edges)
		{
			if(edge.contains(a))
			{
				return i+1;
			}
			i++;
		}
		return -1;
	}
	private static boolean isReachable(Point2D start, Point2D end)
	{
		double x1 = start.getX();
		double y1 = start.getY();
		double x2 = end.getX();
		double y2 = end.getY();
		LineSegment2D line = new LineSegment2D(start, end);
		//System.out.println((x1+x2)/2 + " " + (y1+y2)/2);
		if(!g.contains((x1+x2)/2,(y1+y2)/2))
		{
			return false;
		}
		//WTF
		Collection<LineSegment2D> edges = (Collection<LineSegment2D>) g.edges();
		for(LineSegment2D edge : edges)
		{
			if(LineSegment2D.isColinear(edge, line))
			{
				continue;
			}
			if(intersects(edge, line))
			{
				//System.out.println(LineSegment2D.getIntersection(edge, line));
				//System.out.println("Edge: " + edge.firstPoint() + " " + edge.lastPoint());
				return false;
			}
		}
		return true;
	}
	//L1 does not include end points.
	private static boolean intersects(LineSegment2D l1, LineSegment2D l2)
	{
		if(!LineSegment2D.intersects(l1, l2))
		{
			return false;
		}
		Point2D result = LineSegment2D.getIntersection(l1, l2);
		if(result.almostEquals(l1.firstPoint(), epsilon) || result.almostEquals(l1.lastPoint(), epsilon) || result.almostEquals(l2.firstPoint(), epsilon))
		{
			return false;
		}
		return true;
	}
	private static Point2D intersectionPoly(Point2D start, Point2D end)
	{
		Ray2D line = new Ray2D(start, end);
		//WTF
		Collection<LineSegment2D> edges = (Collection<LineSegment2D>) g.edges();
		Point2D intersection = null;
		for(LineSegment2D edge : edges)
		{
			if(line.intersection(edge) != null)
			{
				if(polyContains(end, line.intersection(edge)))
				{
					if(line.intersection(edge).almostEquals(edge.firstPoint(), epsilon) || line.intersection(edge).almostEquals(edge.lastPoint(), epsilon))
					{
						continue;
					}
					if(intersection == null)
					{
						intersection = line.intersection(edge);
					}
					else
					{
						if(start.distance(line.intersection(edge)) < start.distance(intersection))
						{
							intersection = line.intersection(edge);
						}
					}
				}
			}
		}
		return intersection;
	}
	private static boolean polyContains(Point2D p1, Point2D p2)
	{
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = p2.getX();
		double y2 = p2.getY();
		if(g.contains((x1+x2)/2,(y1+y2)/2))
		{
			return true;
		}
		return false;
	}
	private static Polygon2D copyPoly(Polygon2D p)
	{
		Polygon2D result = new SimplePolygon2D();
		Collection<Point2D> vertices = p.vertices();
		for(Point2D v : vertices)
		{
			result.addVertex(v);
			//System.out.println(v);
		}
		return result;
	}
	public static void printEdges()
	{
		System.out.println("Edges: ");
		Collection<LineSegment2D> edges = (Collection<LineSegment2D>) g.edges();
		int i = 1;
		for(LineSegment2D edge : edges)
		{
			System.out.println(i + ": " + edge.firstPoint() + "->" + edge.lastPoint());
			i++;
		}
		System.out.println();
	}
//	/*
//	Test main
//	public static void main(String[] args)
//	{
//		Polygon2D gallery = new SimplePolygon2D();
//		gallery.addVertex(new Point2D(-8,10));
//		gallery.addVertex(new Point2D(-8,5));
//		gallery.addVertex(new Point2D(-5,2));
//		gallery.addVertex(new Point2D(-7,0));
//		gallery.addVertex(new Point2D(0,0));
//		gallery.addVertex(new Point2D(4,4));
//		gallery.addVertex(new Point2D(3,1));
//		gallery.addVertex(new Point2D(8,-4));
//		gallery.addVertex(new Point2D(8,4));
//		gallery.addVertex(new Point2D(12,6));
//		gallery.addVertex(new Point2D(15,0));
//		gallery.addVertex(new Point2D(18,6));
//		gallery.addVertex(new Point2D(10,11));
//		gallery.addVertex(new Point2D(3,11));
//		gallery.addVertex(new Point2D(5,15));
//		gallery.addVertex(new Point2D(-1,12));
//		gallery.addVertex(new Point2D(0,10));
//		gallery.addVertex(new Point2D(-2,8));
//		gallery.addVertex(new Point2D(-4,8));
//		g = gallery;
//		//System.out.println(g.contains(3.5, 7.5));
//		Collection<LineSegment2D> edges = (Collection<LineSegment2D>) g.edges();
//		Polygon2D viewPoly = getViewPolygon(gallery, new Point2D(4,4));
//		Collection<Point2D> vertices = viewPoly.vertices();
//		for(Point2D vertex : vertices)
//		{
//			System.out.println(vertex);
//		}
//	}*/
}
