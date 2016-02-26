

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
		int j = 0;
		for(j = 0; j < 30; j++)
		{
			System.out.println(j+1);
			Polygon2D poly = fileReader.readFile(reader);
			gallery = copyPoly(poly);
			triangulate(poly);
			System.out.println("Triangles: ");
			int i = 1;
			for(Polygon2D triangle : triangles)
			{
				System.out.print(i + ": ");
				//System.out.println("in for");
				printPoly(triangle, "Triangle: ");
				++i;
			}
			generateHash(); //And lots of it!!!
			ArrayList<Point2D> guards = positionGuards();
			fileReader.write(writer, j+1, guards);
			
			//Reset
			map = new HashMap<Point2D, ArrayList<Polygon2D>>();
			triangles = new ArrayList<Polygon2D>();
			points = new ArrayList<Point2D>();
			
			//Checking
//			if(guards.size() > Math.floor(poly.vertexNumber()/3))
//			{
//				System.out.println("Gallery " +(j+1) + " has " + poly.vertexNumber() + " vertices and " + guards.size() + " guards");
//			}
//			printPoly(poly, "gallery: ");
//			for(Point2D p : guards)
//			{
//				if(poly.contains(p))
//				{
//					System.out.println("Error: The guard at " + p + " is not in the polygon");
//				}
//			}
		}
	}
	public static void triangulate(Polygon2D p)
	{
		Polygon2D aux = copyPoly(p);
		int i = 0;
		Polygon2D triangle;
		int count = 0;
		while(aux.vertexNumber() > 3 && aux.area() != 0)
		{
			if(i == aux.vertexNumber())
			{
				i = 0;
			}
			int u = getIndexBefore(i, aux.vertexNumber());
			int w = getIndexAfter(i, aux.vertexNumber());
			//System.out.println(u + " " + i + " " + w);
			if(isEar(aux, u, i, w))
			{
				triangle = new SimplePolygon2D();
				triangle.addVertex(aux.vertex(u));
				triangle.addVertex(aux.vertex(i));
				triangle.addVertex(aux.vertex(w));
				
				triangles.add(triangle);
				++count;
				System.out.print(count + ": ");
				printPoly(triangle, "");
				
				aux.removeVertex(i);
			}
			else 
			{
				LineSegment2D ui = new LineSegment2D(aux.vertex(u), aux.vertex(i));
				LineSegment2D uw = new LineSegment2D(aux.vertex(u), aux.vertex(w));
				if(ui.isColinear(uw))
				{
					aux.removeVertex(i);
				}
				else
				{
					++i;
				}
			}
		}
		if(isEar(aux, 0, 1, 2))
		{
			triangles.add(aux);
		}
	}
	public static Polygon2D getFirstTriangle(Polygon2D p)
	{
		Polygon2D aux = copyPoly(p);
		int i = 0;
		Polygon2D triangle = null;
		int count = 0;
		while(aux.vertexNumber() > 3 && aux.area() != 0)
		{
			if(i == aux.vertexNumber())
			{
				i = 0;
			}
			int u = getIndexBefore(i, aux.vertexNumber());
			int w = getIndexAfter(i, aux.vertexNumber());
			//System.out.println(u + " " + i + " " + w);
			if(isEar(aux, u, i, w))
			{
				triangle = new SimplePolygon2D();
				triangle.addVertex(aux.vertex(u));
				triangle.addVertex(aux.vertex(i));
				triangle.addVertex(aux.vertex(w));
				
				++count;
				System.out.print(count + ": ");
				printPoly(triangle, "");
				
				aux.removeVertex(i);
				break;
			}
			else 
			{
				LineSegment2D ui = new LineSegment2D(aux.vertex(u), aux.vertex(i));
				LineSegment2D uw = new LineSegment2D(aux.vertex(u), aux.vertex(w));
				if(ui.isColinear(uw))
				{
					aux.removeVertex(i);
				}
				else
				{
					++i;
				}
			}
		}
		return triangle;
	}
	private static int getIndexAfter(int i, int vertexNumber)
	{
		return (i+1) % vertexNumber;
	}
	private static int getIndexBefore(int i, int vertexNumber)
	{
		if(i == 0)
		{
			return vertexNumber - 1;
		}
		return i - 1;
	}
	private static boolean isEar(Polygon2D aux, int u, int i, int w) 
	{
		LineSegment2D ui = new LineSegment2D(aux.vertex(u), aux.vertex(i));
		LineSegment2D uw = new LineSegment2D(aux.vertex(u), aux.vertex(w));
		return isReachable(uw, 0) && !ui.isColinear(uw);
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
		if(k == 10)
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
	
	public static void printPoly (Polygon2D p, String tag) 
	{
		//System.out.println(tag);
		int i = 0;
		for(Point2D vertex : p.vertices())
		{
				if(i == p.vertexNumber() - 1)
				{
					System.out.print("(" + vertex.getX() + ", " + vertex.getY() + ")");
				}
				else
				{
					System.out.print("(" + vertex.getX() + ", " + vertex.getY() + "), ");
				}
				++i;
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
