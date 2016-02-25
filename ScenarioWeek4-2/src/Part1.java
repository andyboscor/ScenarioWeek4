

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import math.geom2d.Point2D;
import math.geom2d.line.LineSegment2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.polygon.SimplePolygon2D;

public class Part1
{
	private final static double epsilon = 0.0000000001;
	private final static String name = "hamster";
	private final static String password = "sml4l1i6aamsvhoi9irui5jdv8";
	
	static HashMap<Point2D, ArrayList<Polygon2D>> map = new HashMap<Point2D, ArrayList<Polygon2D>>();
	static ArrayList<Polygon2D> triangles = new ArrayList<Polygon2D>();
	static ArrayList<Point2D> points = new ArrayList<Point2D>();
	static Polygon2D gallery;
	public static void main(String[] args) throws IOException
	{
		FileReader r = new FileReader("/Users/williambhot/Documents/Uni/Year2/Term2/Scenario-Week-4/guards.pol");
		BufferedReader reader = new BufferedReader(r);
		PrintWriter writer = new PrintWriter("/Users/williambhot/Desktop/output.txt", "UTF-8");
		writer.println(name);
		writer.println(password);
		for(int j = 0; j < 30; j++)
		{
			System.out.println(j+1);
			Polygon2D poly = fileReader.readFile(reader);
			gallery = copyPoly(poly);
			triangulate(poly);
			printTriangles();
			generateHash(); //And lots of it!!!
			ArrayList<Point2D> guards = positionGuards();
			fileReader.write(writer, j+1, guards);
			map = new HashMap<Point2D, ArrayList<Polygon2D>>();
			triangles = new ArrayList<Polygon2D>();
			points = new ArrayList<Point2D>();
		}
	}
	public static void triangulate(Polygon2D p)
	{
//		printPoly(p, "Poly");
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
			//System.out.println(u + "->" + w + "->" + v);
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
			int v1 = closestReachableVertex(aux, v);
			//System.out.println("The closest reachable point from " + v + " is " + aux.vertex(v1));
			LHS = removeSequence(p, 1, v1);
			RHS = removeSequence(p, v1, 1);
		}
//		printPoly(LHS, "LHS:");
//		printPoly(RHS, "RHS:");
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
	
	private static Polygon2D removeSequence(Polygon2D p, int startIndex, int endIndex)
	{
		Point2D start = p.vertex(startIndex);
		Point2D end = p.vertex(endIndex);
		//System.out.println("Start: " + start);
		//System.out.println("End: " + end);
		Polygon2D result = copyPoly(p);
		//System.out.println(result.vertexNumber());
		int i = (startIndex + 1) % result.vertexNumber();
		do
		{
			//System.out.println(i + ": " + result.vertex(i));
			result.removeVertex(i);
			if(i == result.vertexNumber())
			{
				i = 0;
			}
		}while(!result.vertex(i).almostEquals(end, epsilon));
		return result;
	}
	
	public static void printTriangles()
	{
		//System.out.println("Triangles: ");
		for(Polygon2D triangle : triangles)
		{
			/*for(Point2D p : triangle.vertices())
			{
				System.out.println(p);
			}
			System.out.println();*/
		}
	}
	
	public static void printPoly (Polygon2D p, String tag) 
	{
		System.out.println(tag);
		for(Point2D vertex : p.vertices())
		{
				System.out.println(vertex);
		}
		System.out.println();
	}
	public static int closestReachableVertex(Polygon2D p, Point2D v)
	{
		int min = 0;
		Point2D x;
		double minDist = 0;
		for(int i = 3; i < p.vertexNumber(); i++)
		{
			x = p.vertex(i);
			if(min == 0)
			{
				min = i;
				minDist = v.distance(x);
			}
			else
			{
				double dist = v.distance(x);
				if((dist < minDist) && isReachable(new LineSegment2D(v, x), 0))
				{
					min = i;
					minDist = dist;
				}
			}
		}
		//System.out.println("The closest reachable point from " + v + " is actually " + p.vertex(min));
		return min;
	}
	public static void generateHash()
	{
		ArrayList<Polygon2D> aux = null;
		for(Polygon2D triangle : triangles)
		{
			for(Point2D vertex : triangle.vertices())
			{
				if(points.contains(vertex))
				{
					//Update
					aux = map.get(vertex);
					aux.add(triangle);
				}
				else
				{
					//Add
					points.add(vertex);
					aux = new ArrayList<Polygon2D>();
					aux.add(triangle);
					map.put(vertex, aux);
				}
			}
		}
	}
	public static ArrayList<Point2D> positionGuards()
	{
		ArrayList<Point2D> guards = new ArrayList<Point2D>();
		int maxLength;
		Point2D maxPoint = null;
		ArrayList<Polygon2D> seen;
		while(!map.isEmpty())
		{
			Point2D guard;
			maxLength = -1;
			for(Point2D vertex : points)
			{
				int aux = map.get(vertex).size();
				if(maxLength < aux)
				{
					maxLength = aux;
					maxPoint = vertex;
				}
			}
			guards.add(maxPoint);
			//System.out.println(maxPoint);
			seen = map.get(maxPoint);
			ArrayList<Point2D> pointsCopy = (ArrayList<Point2D>) points.clone();
			if(seen == null)
			{
				break;
			}
			for(Polygon2D triangle : seen)
			{
				for(Point2D p : pointsCopy)
				{
					if(!p.almostEquals(maxPoint, epsilon))
					{
						map.get(p).remove(triangle);
						if(map.get(p).isEmpty())
						{
							map.remove(p);
							points.remove(p);
						}
					}
				}
				pointsCopy = (ArrayList<Point2D>) points.clone();
			}
////			System.out.println("before for");
//			while(seen.size() > 0)
//			{
////				System.out.println("in for");
//				seen.remove(0);
//			}
			map.remove(maxPoint);
			points.remove(maxPoint);
		}
		return guards;
	}
}
