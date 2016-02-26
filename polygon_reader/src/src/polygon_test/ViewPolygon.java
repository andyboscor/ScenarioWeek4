package src.polygon_test;

import java.util.ArrayList;
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
	private static Polygon2D result;
	private static Polygon2D g;
	
	// Generates the field of view from Start as a polygon
	public static Polygon2D getViewPolygon(Polygon2D gallery, Point2D start)
	{
		result = copyPoly(gallery);
		g = gallery;
		Collection<Point2D> vertices = gallery.vertices();
		Point2D a;
		for(Point2D v : vertices)
		{
			if(!start.almostEquals(v, epsilon))
			{
				if(isReachable(new LineSegment2D(start, v), 0))
				{
					a = intersectionPoly(start, v);
					if(a != null)
					{
						int index = getPosition(a);
						if (index != -1) {
							result.insertVertex(index, a);
						}
					}
				}
			}
		}
		int i = 0;
		while(i < result.vertexNumber())
		{
			Point2D v = result.vertex(i);
			if(!start.almostEquals(v, epsilon))
			{
				if(!isReachable(new LineSegment2D(start, v),0))
				{
					v = null;
					result.removeVertex(i);
					//System.out.println("now at i" + g.vertex(i));
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
		return result;
	}
	
	//Returns the position where a should be inserted in the polygon
	private static int getPosition(Point2D a)
	{
		int i = 0;
		for(LineSegment2D edge : result.edges())
		{
			if (edge.firstPoint().almostEquals(a, epsilon) || edge.lastPoint().almostEquals(a, epsilon)) {
				return -1;
			}
			if(edge.contains(a))
			{
				return i+1;
			}
			i++;
		}
		return -1;
	}
	
	// Returns the intersection of ray from start in the direction of end 
	// with the polygon, and null if there is none. End needs to be reachable from start.
	private static Point2D intersectionPoly(Point2D start, Point2D end)
	{
		Ray2D ray = new Ray2D(start, end);
		Point2D intersection = null;
		for(LineSegment2D edge : g.edges())
		{
			intersection = ray.intersection(edge);
			if(intersection != null)
			{
				if(isReachable(new LineSegment2D(end, intersection), 0))
				{	
					// Intersection point needs to be different from start and end (to not get a Degenerated Line exception)
					if (intersection.almostEquals(start, epsilon) || intersection.almostEquals(end, epsilon))
					{
						continue;
					}
					// If it's reachable it has to be the closest point.
					return intersection;
				}
			}
		}
		return intersection;
	}
	
	private static Polygon2D copyPoly(Polygon2D p)
	{
		Polygon2D result = new SimplePolygon2D();
		Collection<Point2D> vertices = p.vertices();
		for(Point2D v : vertices)
		{
			result.addVertex(v);
		}
		return result;
	}
	
	public static void printEdges(Polygon2D p)
	{
		System.out.println("Edges: ");
		int i = 1;
		for(LineSegment2D edge : p.edges())
		{
			System.out.println(i + ": " + edge.firstPoint() + "->" + edge.lastPoint());
			i++;
		}
		System.out.println();
	}
	private static boolean isReachable(LineSegment2D l, int k)
	{
		if(k == 8)
		{
			return true;
		}
		Point2D mid = new Point2D((l.firstPoint().getX() + l.lastPoint().getX())/2, (l.firstPoint().getY() + l.lastPoint().getY())/2);
		LineSegment2D lhs = new LineSegment2D(l.firstPoint(), mid);
		LineSegment2D rhs = new LineSegment2D(mid, l.lastPoint());
		return g.contains(mid) && isReachable(lhs, k+1) && isReachable(rhs, k+1);
	}
	
	public static void printPoly (Polygon2D p, String tag) {
		System.out.println(tag);
		for(Point2D vertex : p.vertices())
		{
				System.out.println(vertex);
		}
		System.out.println();
	}

	public static ArrayList<Polygon2D> polyDiff(Polygon2D p1, Polygon2D p2)
	{
			ArrayList<Polygon2D> difference = new ArrayList<Polygon2D>();
			difference.add(Polygons2D.difference(p1, p2));
			return difference;
	}
	public static Point2D checkGuards(Polygon2D gallery, Collection<Point2D> guards)
	{
		Polygon2D g_union = new SimplePolygon2D(),temp = new SimplePolygon2D();
		g_union.addVertex(new Point2D(0,0));
		g_union.addVertex(new Point2D(0,0));
		g_union.addVertex(new Point2D(0,0));
		temp.addVertex(new Point2D(0,0));
		temp.addVertex(new Point2D(0,0));
		temp.addVertex(new Point2D(0,0));
		for (Point2D guard : guards)
		{
			Polygon2D viewPoly = getViewPolygon(gallery,guard);
			temp = Polygons2D.union(g_union,viewPoly);
			g_union = temp;
		}
		Polygon2D diff = Polygons2D.difference(gallery, g_union);
		Polygon2D test = new SimplePolygon2D();
		
		int len = diff.vertexNumber();
	//	printPoly(diff,"difference:");
		int j = 0;
		/*
		for(int i = 0; i < len; i++)
		{	if(gallery.contains(diff.vertex(i)))
			{
				test.addVertex(diff.vertex(i));
				j++;
			}
			if(j==3)
				break;
			
		}
		*/
		for(int i = len - 1; i >= 0; i--)
		{	if(gallery.contains(diff.vertex(i)))
			{
				test.addVertex(diff.vertex(i));
				j++;
			}
			if(j==3)
				break;
			
		}
		
		//printPoly(test,"test:");
		Point2D point = test.centroid();
		//	System.out.print ("(" +point.getX() + ", ");
		//	System.out.print(point.getY()+ ")");
		//	System.out.println();
		return point;
		
		
	}
/*
	public void checkGuards(Polygon2D gallery, Collection<Point2D> guards)
	{
		Collection<Point2D> vertices = gallery.vertices();
	    //System.out.println(vertices);
		//System.out.println(guards);
		boolean flag = false;
		for (Point2D vertex : vertices)
		{
			flag = false;
			Polygon2D viewPoly = getViewPolygon(gallery, vertex);
			for (Point2D guard : guards)
			{
				if (viewPoly.contains(guard))
					flag = true;
			}
			if (flag == false)
			{	
			System.out.print ("(" +vertex.getX() + ", ");
			System.out.print(vertex.getY()+ ")");
			//System.out.println();
			break;
			}
			
		}
		System.out.println();
	}

/* Test main */
/*
	public static void main(String[] args)
	{
		Polygon2D gallery = new SimplePolygon2D();
		gallery.addVertex(new Point2D(0, 0));
		gallery.addVertex(new Point2D(2, 0));
		gallery.addVertex(new Point2D(2, 1));
		gallery.addVertex(new Point2D(1, 1));
		gallery.addVertex(new Point2D(1, 2));
		gallery.addVertex(new Point2D(3, 2));
		gallery.addVertex(new Point2D(3, 3));
		gallery.addVertex(new Point2D(0, 3));
		Polygon2D guards = new SimplePolygon2D();
		guards.addVertex(new Point2D(2, 0));
		guards.addVertex(new Point2D(3, 2.5));
		Collection<Point2D> guardscol = guards.vertices();
		checkGuards(gallery, guardscol);
		// System.out.println(g.contains(2, 0));
		Collection<LineSegment2D> edges = (Collection<LineSegment2D>) g.edges();
	}
	// /*
	// Test main
	// public static void main(String[] args)
	// {
	// Polygon2D gallery = new SimplePolygon2D();
	// gallery.addVertex(new Point2D(-8,10));
	// gallery.addVertex(new Point2D(-8,5));
	// gallery.addVertex(new Point2D(-5,2));
	// gallery.addVertex(new Point2D(-7,0));
	// gallery.addVertex(new Point2D(0,0));
	// gallery.addVertex(new Point2D(4,4));
	// gallery.addVertex(new Point2D(3,1));
	// gallery.addVertex(new Point2D(8,-4));
	// gallery.addVertex(new Point2D(8,4));
	// gallery.addVertex(new Point2D(12,6));
	// gallery.addVertex(new Point2D(15,0));
	// gallery.addVertex(new Point2D(18,6));
	// gallery.addVertex(new Point2D(10,11));
	// gallery.addVertex(new Point2D(3,11));
	// gallery.addVertex(new Point2D(5,15));
	// gallery.addVertex(new Point2D(-1,12));
	// gallery.addVertex(new Point2D(0,10));
	// gallery.addVertex(new Point2D(-2,8));
	// gallery.addVertex(new Point2D(-4,8));
	// g = gallery;
	// //System.out.println(g.contains(3.5, 7.5));
	// Collection<LineSegment2D> edges = (Collection<LineSegment2D>) g.edges();
	// Polygon2D viewPoly = getViewPolygon(gallery, new Point2D(4,4));
	// Collection<Point2D> vertices = viewPoly.vertices();
	// for(Point2D vertex : vertices)
	// {
	// System.out.println(vertex);
	// }
	// }*/
}