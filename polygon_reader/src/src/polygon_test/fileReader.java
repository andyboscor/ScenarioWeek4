package src.polygon_test;

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
import java.util.Collection;

import javax.swing.SwingUtilities;

import java.awt.*;

import math.geom2d.Point2D;
import math.geom2d.polygon.*;

public class fileReader {
	
	ViewPolygon viewer = new ViewPolygon();
	Part1 guarder = new Part1();
	static Drawing drawer = new Drawing();
	static int second_Index = 1;
	private static BufferedReader reader;
	private static BufferedReader reader2;
	private static BufferedReader reader22;
	private static String name = "hamster";
	private static String password = "sml4l1i6aamsvhoi9irui5jdv8";

	
	public static void readFile(int index) throws IOException{
		ArrayList <Polygon2D> polygons = new ArrayList<Polygon2D>();
		FileReader r = new FileReader("/Users/rajind/Google Drive/Year 2/COMP205P/Scenario Week IV/guards.pol");
		reader = new BufferedReader(r);
		String line = null;
		for(int i = 1; i <= index ; i++){
			line = reader.readLine();
		}
		line = line.replace(":", ",");
		line = line.replace(")","");
		line= line.replace("(", "");
		String[] arr= line.split(", ");
//		for (String anArr : arr){
//			System.out.println(anArr);
//		}
		
		Polygon2D polygon = new SimplePolygon2D();
		
		for(int i = 1; i<arr.length; i+=2){
		    double x = Double.parseDouble(arr[i]);
		    double y = Double.parseDouble(arr[i+1]);
			Point2D point = new Point2D(x,y);
			polygon.addVertex(point);
		}
		
		polygons.add(polygon);
		drawer.drawPolygon(polygons);
		
	}

	public static void Part2() throws IOException{
		ArrayList <Polygon2D> polygons = new ArrayList<Polygon2D>();
		FileReader r = new FileReader("/Users/rajind/Google Drive/Year 2/COMP205P/Scenario Week IV/multidraw.pol");
		PrintWriter writer = new PrintWriter("/Users/rajind/Desktop/outputy.txt");
		ViewPolygon chguards = new ViewPolygon();
		writer.println(name);
		writer.println(password);
		reader = new BufferedReader(r);
		String line = null;
		for(int j =1 ;j<=20; j++)
		{
		line = reader.readLine();
		
		line = line.replace(":", ",");
		line = line.replace(")","");
		line= line.replace("(", "");
		String []map_guards = line.split("; ");
		
		String[] arr= map_guards[0].split(", ");
		
		Polygon2D polygon = new SimplePolygon2D();
		
		for(int i = 1; i<arr.length; i+=2){
		    double x = Double.parseDouble(arr[i]);
		    double y = Double.parseDouble(arr[i+1]);
			Point2D point = new Point2D(x,y);
			polygon.addVertex(point);
		}
		
		String[] arr2 = map_guards[1].split(", ");
		Polygon2D guards = new SimplePolygon2D();
		for(int i = 0; i<arr2.length; i+=2){
		    double x = Double.parseDouble(arr2[i]);
		    double y = Double.parseDouble(arr2[i+1]);
			Point2D point = new Point2D(x,y);
			guards.addVertex(point);
		}
		Collection<Point2D> guardscol = guards.vertices();
		//System.out.println("Twice");
		
		Point2D result = chguards.checkGuards(polygon, guardscol);
		if(result!=null)
		writer.println(arr[0] + ": " + "("+result.getX() + ", " + result.getY()+")");
		writer.flush();
		
		}
		
		//drawer.drawPolygon(polygons,guardscol);
		
		//}
		//System.out.print(arr[0] + ": ");
		
	//	}
		
		
	}
	public static void readSecondFile(int index) throws IOException{
		ArrayList <Polygon2D> polygons = new ArrayList<Polygon2D>();
		ViewPolygon chguards = new ViewPolygon();
		FileReader r2 = new FileReader("/Users/rajind/Google Drive/Year 2/COMP205P/Scenario Week IV/multidraw.pol");
		
		reader2 = new BufferedReader(r2);
		String line = null;
		for(int j =1 ;j<=index; j++)
		{
		line = reader2.readLine();
		}
		line = line.replace(":", ",");
		line = line.replace(")","");
		line= line.replace("(", "");
		String []map_guards = line.split("; ");
		
		String[] arr= map_guards[0].split(", ");
		
		Polygon2D polygon = new SimplePolygon2D();
		
		for(int i = 1; i<arr.length; i+=2){
		    double x = Double.parseDouble(arr[i]);
		    double y = Double.parseDouble(arr[i+1]);
			Point2D point = new Point2D(x,y);
			polygon.addVertex(point);
		}
		
		polygons.add(polygon);
		//System.out.println(polygons.get(j-1));
		
		String[] arr2 = map_guards[1].split(", ");
		Polygon2D guards = new SimplePolygon2D();
		for(int i = 0; i<arr2.length; i+=2){
		    double x = Double.parseDouble(arr2[i]);
		    double y = Double.parseDouble(arr2[i+1]);
			Point2D point = new Point2D(x,y);
			guards.addVertex(point);
		}
		Collection<Point2D> guardscol = guards.vertices();
		Polygon2D test;
		Polygon2D g_union = null;
		for(Point2D guard : guardscol){
			System.out.println("Printing guard " + guard);
			Polygon2D extract = new SimplePolygon2D();
			extract = chguards.getViewPolygon(polygons.get(0), guard);
			if(g_union == null)
				g_union = extract;
			else{
			test = Polygons2D.union(g_union, extract);
			g_union = test;
			}
			
		}
		polygons.add(g_union);
		//System.out.println("Twice");
		
//		Point2D result = chguards.checkGuards(polygon, guardscol);
//		if(result!=null){
//			guardscol.add(result);
//		}
		
		drawer.drawPolygon(polygons,guardscol);
		
		//}
		//System.out.print(arr[0] + ": ");
		
	//	}
		
	}
	public static void multiRead(int index) throws IOException{
		ArrayList <Polygon2D> polygons = new ArrayList<Polygon2D>();
		FileReader r2 = new FileReader("/Users/rajind/Google Drive/Year 2/COMP205P/Scenario Week IV/part1.pol");
		reader22 = new BufferedReader(r2);
		String line = null;
		for(int j =1 ;j<=index; j++)
		{
		line = reader22.readLine();
		}
		
		line = line.replace(":", ",");
		line = line.replace(")","");
		line= line.replace("(", "");
		String []map_guards = line.split("; ");
		
		String[] arr= map_guards[0].split(", ");
		
		Polygon2D polygon = new SimplePolygon2D();
		
		for(int i = 1; i<arr.length; i+=2){
		    double x = Double.parseDouble(arr[i]);
		    double y = Double.parseDouble(arr[i+1]);
			Point2D point = new Point2D(x,y);
			polygon.addVertex(point);
		}
		
		polygons.add(polygon);
		
		
		
		drawer.drawPolygon(polygons);
		//System.out.print(arr[0] + ": ");
	//	}
		
	}

/*
	public static void writing() {
        try {
            File statText = new File("/Users/andy/Desktop/Eclipse/Specifications/stattext.txt");
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);    
            Writer w = new BufferedWriter(osw);
            w.write("POTATO!!!");
            w.close();
        } catch (IOException e) {
            System.err.println("Problem writing to the file statsTest.txt");
        }
    }
/*
	public static void main(String[] args) {	
		
		try {
			//readFile();
			readSecondFile();
		
			//writing();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
	
}
