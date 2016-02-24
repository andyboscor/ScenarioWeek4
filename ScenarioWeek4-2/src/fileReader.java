

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import math.geom2d.Point2D;
import math.geom2d.polygon.*;

import java.awt.*;

public class fileReader {
	static Scanner Sc;
	public static Polygon2D readFile(BufferedReader reader) throws IOException{
		
		String line;
		line = reader.readLine();
		line = line.replace(":", ",");
		line = line.replace(")","");
		line= line.replace("(", "");
		String[] arr= line.split(", ");
		
		Polygon2D polygon = new SimplePolygon2D();
		
		for(int i = 1; i<arr.length; i+=2){
		    double x = Double.parseDouble(arr[i]);
		    double y = Double.parseDouble(arr[i+1]);
			Point2D point = new Point2D(x,y);
			polygon.addVertex(point);
		}
		
		return polygon;
		
		
	}
	private void doDrawing(Graphics g,Polygon2D polygon) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);
        
        polygon.draw(g2d);
       
	}
	public static void write(PrintWriter writer, int number, ArrayList<Point2D> guards) {
        String line = "";
        	line += number + ":";
        	for(Point2D guard : guards)
        	{
        		line += " (" + guard.getX() + ", " + guard.getY() + "),";
        	}
        	line = line.substring(0, line.length()-1);
        	writer.println(line);
        	writer.flush();
    }

	public static void main(String[] args) {
		try {
			FileReader r = new FileReader("/Users/williambhot/Documents/Uni/Year2/Term2/Scenario-Week-4/guards.pol.txt");
			BufferedReader reader = new BufferedReader(r);
			Polygon2D poly = readFile(reader);
			for(Point2D p : poly.vertices())
			{
				System.out.println(p);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}