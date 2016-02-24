
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
	private static String name = "hamster";
	private static String password = "sml4l1i6aamsvhoi9irui5jdv8";
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
			Polygon2D gallery = fileReader.readFile(reader);
			
			ArrayList<Point2D> guards = new ArrayList<Point2D>();
			ArrayList<Point2D> unseen = makeCopy(gallery.vertices());
			Polygon2D viewPoly = null, intersection = null;
			boolean flag;
			int i;
			while(!unseen.isEmpty())
			{
				i = 0;
				while(i < unseen.size())
				{
					flag = true;
					Point2D vertex = unseen.get(i);
					viewPoly = ViewPolygon.getViewPolygon(gallery, vertex);
					if(intersection == null)
					{
						intersection = viewPoly;
						unseen.remove(vertex);
						flag = false;
					}
					else 
					{
						Polygon2D aux = null;
						aux = Polygons2D.intersection(viewPoly, intersection);
						if(aux.area() != 0)
						{
							intersection = aux;
							unseen.remove(vertex);
							flag = false;
						}
					}
					if(flag == true)
					{
						i++;
					}
				}
				if (intersection.area() == 0) {
					ViewPolygon.printPoly(intersection, "intersection");
				}
				guards.add(intersection.centroid());
				intersection = null;
			}
			for(Point2D guard : guards)
			{
				if(!gallery.contains(guard))
				{
					System.out.println("!!! Not in gallery: " + guard);
				}
			}
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
