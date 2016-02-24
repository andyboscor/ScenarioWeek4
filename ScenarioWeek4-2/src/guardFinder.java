
import math.geom2d.Point2D;
import math.geom2d.polygon.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public class guardFinder
{
	public static void main(String[] args) throws IOException
	{
		FileReader r = new FileReader("/Users/williambhot/Documents/Uni/Year2/Term2/Scenario-Week-4/guards.pol");
		BufferedReader reader = new BufferedReader(r);
		PrintWriter writer = new PrintWriter("/Users/williambhot/Desktop/output.txt", "UTF-8");
		for(int j = 0; j < 30; j++)
		{
			System.out.println((j+1) + ": ");
			ArrayList<Point2D> guards = new ArrayList<Point2D>();
			Polygon2D gallery = fileReader.readFile(reader);
			Drawing d = new Drawing(gallery);
			d.drawPolygon(gallery);
			for(Point2D p : gallery.vertices())
			{
				System.out.println(p);
			}
			System.out.println(gallery.vertexNumber());
			ArrayList<Point2D> unseen = makeCopy(gallery.vertices());
			Polygon2D viewPoly = null, intersection = null;
			boolean ok;
			int i;
			while(!unseen.isEmpty())
			{
				i = 0;
				while(i < unseen.size())
				{
					ok = true;
					Point2D vertex = unseen.get(i);
					viewPoly = ViewPolygon.getViewPolygon(gallery, vertex);
					if(intersection == null)
					{
						intersection = viewPoly;
						unseen.remove(vertex);
						ok = false;
					}
					else 
					{
						Polygon2D aux = null;
						try
						{
							aux = Polygons2D.intersection(viewPoly, intersection);
						}
						catch(IllegalStateException e)
						{
							System.out.println("View Polygon");
							for(Point2D x : viewPoly.vertices())
							{
								System.out.println(x);
							}
							System.out.println();
							System.out.println("Intersection");
							for(Point2D x : intersection.vertices())
							{
								System.out.println(x);
							}
							System.out.println();
//							Drawing d1 = new Drawing(viewPoly);
//							d1.drawPolygon(viewPoly);
//							Drawing d2 = new Drawing(gallery);
//							d1.drawPolygon(gallery);
//							Drawing d3 = new Drawing(intersection);
//							d1.drawPolygon(intersection);
						}
						if(aux.vertexNumber() != 0)
						{
							intersection = aux;
							unseen.remove(vertex);
							ok = false;
						}
					}
					if(ok == true)
					{
						i++;
					}
				}
				guards.add(intersection.centroid());
				intersection = null;
			}
			/*for(Point2D p : guards)
			{
				System.out.println(p);
			}*/
			fileReader.write(writer, j+1, guards);
		}
	}
	public static ArrayList<Point2D> makeCopy(Collection<Point2D> c)
	{
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		for(Point2D x : c)
		{
			result.add(new Point2D(x.getX(), x.getY()));
		}
		return result;
	}
}
