package src.polygon_test;
import java.util.Collection;

import math.geom2d.Point2D;
import math.geom2d.polygon.*;

public class test
{
	public static void main(String[] args)
	{
		Polygon2D x = new SimplePolygon2D();
		Point2D p1 = new Point2D(-0.5, -0.5);
		Point2D p2 = new Point2D(-0.5, 0.5);
		Point2D p3 = new Point2D(0.5, -0.5);
		
		x.addVertex(p1);
		x.addVertex(p2);
		x.addVertex(p3);
		
		Polygon2D x1 = new SimplePolygon2D();
		Point2D p4 = new Point2D(-0.5, 0);
		Point2D p5 = new Point2D(.5, .5);
		Point2D p6 = new Point2D(0, -.5);
		
		x1.addVertex(p4);
		x1.addVertex(p5);
		x1.addVertex(p6); 
		
		Polygon2D x3 = Polygons2D.intersection(x, x1);
		int n = x3.vertexNumber();
		for(int i = 0; i < n; i++)
		{
			System.out.println(x3.vertex(i));
		}
		
		
		
	}
}
