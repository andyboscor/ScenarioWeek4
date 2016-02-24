
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
	
	// Generates the field of view from Start as a polygon
	public static Polygon2D getViewPolygon(Polygon2D gallery, Point2D start)
	{
		g = copyPoly(gallery);
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
							g.insertVertex(index, a);
						}
					}
				}
			}
		}
		int i = 0;
		while(i < g.vertexNumber())
		{
			Point2D v = g.vertex(i);
			if(!start.almostEquals(v, epsilon))
			{
				if(!isReachable(new LineSegment2D(start, v),0))
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
		return g;
	}
	
	//Returns the position where a should be inserted in the polygon
	private static int getPosition(Point2D a)
	{
		int i = 0;
		for(LineSegment2D edge : g.edges())
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
	/*private static boolean isReachable(Point2D start, Point2D end)
	{
		LineSegment2D line = new LineSegment2D(start, end);
		if(!containsLine(line, 0))
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
				return false;
			}
		}
		return true;
	}*/
	/*private static boolean isVertex(Point2D p)
	{
		for(Point2D x : g.vertices())
		{
			if(p.almostEquals(x, epsilon) == true)
			{
				return true;
			}
		}
		return false;
	}
	//L1 does not include end points.
	/*private static boolean intersects(LineSegment2D l1, LineSegment2D l2)
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
	}*/
	
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
					//WTF (means it's not a vertex)
					//if(ray.intersection(edge).almostEquals(edge.firstPoint(), epsilon) || ray.intersection(edge).almostEquals(edge.lastPoint(), epsilon))
					
					// Intersection point needs to be different from start and end (to not get a Degenerated Line exception)
					if (intersection.almostEquals(start, epsilon) || intersection.almostEquals(end, epsilon))
					{
						continue;
					}
					// If it's reachable it has to be the closest point.
					return intersection;
//					if(result == null)
//					{
//						result = intersection;
//					}
//					else
//					{
//						if(start.distance(intersection) < start.distance(result))
//						{
//							result = intersection;
//						}
//					}
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
	
	public static void printEdges()
	{
		System.out.println("Edges: ");
		int i = 1;
		for(LineSegment2D edge : g.edges())
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
	/*public static void main(String[] args)
	{
		Polygon2D gallery = new SimplePolygon2D();
		gallery.addVertex(new Point2D(-7, 0));
		gallery.addVertex(new Point2D(-5, 0));
		gallery.addVertex(new Point2D(-5, -1));
		gallery.addVertex(new Point2D(-4, -1));
		gallery.addVertex(new Point2D(-4, 2));
		gallery.addVertex(new Point2D(-3, 2));
		gallery.addVertex(new Point2D(-3, 0));
		gallery.addVertex(new Point2D(-2, 0));
		gallery.addVertex(new Point2D(-2, 4));
		gallery.addVertex(new Point2D(1, 4));
		gallery.addVertex(new Point2D(-1, 3));
		gallery.addVertex(new Point2D(-1, -2));
		gallery.addVertex(new Point2D(-7, -2));
		g = gallery;
		//Drawing d = new Drawing(g);
		//d.drawPolygon(g);
		//LineSegment2D l = new LineSegment2D(-1, 0, 0.5, 2);
		//Collection<LineSegment2D> edges = (Collection<LineSegment2D>) g.edges();
		Polygon2D viewPoly = getViewPolygon(gallery, new Point2D(-7,0));
		Collection<Point2D> vertices = viewPoly.vertices();
		System.out.println("View");
		for(Point2D vertex : vertices)
		{
			System.out.println(vertex);
		}
	}*/
}