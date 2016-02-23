
import math.geom2d.Point2D;
import math.geom2d.polygon.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class guardFinder
{
	public static void main(String[] args) throws IOException
	{
		ArrayList<Point2D> guards = new ArrayList<Point2D>();
		Polygon2D gallery = fileReader.readFile();
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
					Polygon2D aux = Polygons2D.intersection(viewPoly, intersection);
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
		System.out.println();
		for(Point2D guard : guards)
		{
			System.out.println(guard);
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
