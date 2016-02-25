package Colouring;

import java.util.*;
import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.SimplePolygon2D;

public class Part1
{
	static HashMap<Point2D, ArrayList<Polygon2D>> map = new HashMap<Point2D, ArrayList<Polygon2D>>();
	static ArrayList<Polygon2D> triangles = new ArrayList<Polygon2D>();
	static Polygon2D gallery;
	public static void main(String[] args)
	{
		Polygon2D poly = new SimplePolygon2D();
		poly.addVertex(new Point2D(1,-1));
		poly.addVertex(new Point2D(3,-1));
		poly.addVertex(new Point2D(3,0));
		poly.addVertex(new Point2D(2,0));
		poly.addVertex(new Point2D(2,1));
		poly.addVertex(new Point2D(3,1));
		poly.addVertex(new Point2D(3,2));
		poly.addVertex(new Point2D(2,2.5));
		poly.addVertex(new Point2D(1,2.5));
		poly.addVertex(new Point2D(1,2));
		poly.addVertex(new Point2D(0,1));
		poly.addVertex(new Point2D(0,0));
		gallery = copyPoly(poly);
		triangulate(poly);
		//printTriangles();
	}
	public static void triangulate(Polygon2D p)
	{
		printPoly(p, "Poly");
		if(p.vertexNumber() == 3)
		{
			triangles.add(copyPoly(p));
			return;
		}
		Point2D v = p.vertex(1), u = p.vertex(0), w = p.vertex(2);
		Polygon2D LHS;
		Polygon2D RHS;
		if(isReachable(new LineSegment2D(u, w), 0))
		{
			System.out.println(u + "->" + w + "->" + v);
			LHS = new SimplePolygon2D();
			LHS.addVertex(u);
			LHS.addVertex(v);
			LHS.addVertex(w);
			RHS = copyPoly(p);
			RHS.removeVertex(1);
		}
		else
		{
			Polygon2D aux = copyPoly(p);
			aux.removeVertex(0);
			aux.removeVertex(2);
			int v1 = closestReachableVertex(aux, v);
			LHS = removeSequence(p, 1, v1);
			RHS = removeSequence(p, v1, 1);
		}
		printPoly(LHS, "LHS:");
		printPoly(RHS, "RHS:");
		triangulate(LHS);
		triangulate(RHS);
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
	
	private static boolean isReachable(LineSegment2D l, int k)
	{
		if(k == 8)
		{
			return true;
		}
		Point2D mid = new Point2D((l.firstPoint().getX() + l.lastPoint().getX())/2, (l.firstPoint().getY() + l.lastPoint().getY())/2);
		LineSegment2D lhs = new LineSegment2D(l.firstPoint(), mid);
		LineSegment2D rhs = new LineSegment2D(mid, l.lastPoint());
		return gallery.contains(mid) && isReachable(lhs, k+1) && isReachable(rhs, k+1);
	}
	
	private static Polygon2D removeSequence(Polygon2D p, int start, int end)
	{
		Polygon2D result = copyPoly(p);
		int i = (start + 1) % p.vertexNumber();
		do
		{
			result.removeVertex(i);
			i = (i + 1) % result.vertexNumber();
		}while(i != end);
		return result;
	}
	
	public static void printTriangles()
	{
		for(Polygon2D triangle : triangles)
		{
			for(Point2D p : triangle.vertices())
			{
				System.out.println(p);
			}
			System.out.println();
		}
	}
	
	public static void printPoly (Polygon2D p, String tag) {
		System.out.println(tag);
		for(Point2D vertex : p.vertices())
		{
				System.out.println(vertex);
		}
		System.out.println();
	}
	public static int closestReachableVertex(Polygon2D aux, Point2D v)
	{
		int result = aux.closestVertexIndex(v);
		while(!isReachable(new LineSegment2D(v, aux.vertex(result)),0))
		{
			aux.removeVertex(result);
			result = aux.closestVertexIndex(v);
		}
		return result;
	}
}
