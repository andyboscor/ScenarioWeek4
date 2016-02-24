package src.polygon_test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collection;

import javax.swing.SwingUtilities;

import java.awt.*;

import math.geom2d.Point2D;
import math.geom2d.polygon.*;

public class fileReader {
	
	ViewPolygon viewer = new ViewPolygon();
	static Drawing drawer = new Drawing();
	
	public static void readFile() throws IOException{
		FileReader r = new FileReader("/Users/andy/Desktop/Eclipse/Specifications/guards.pol");
		BufferedReader reader = new BufferedReader(r);
		String line;
		line = reader.readLine();
		line = reader.readLine();
		line = reader.readLine();
		line = reader.readLine();
		line = reader.readLine();
		line = reader.readLine();
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
//		int n = polygon.vertexNumber();
//		
//		for(int i = 0; i<n;i++){
//			System.out.println(polygon.vertex(i));
//		}
//		
	//	drawer.drawPolygon(polygon);
		
		
	}
	
	public static void readSecondFile() throws IOException{
		ViewPolygon chguards = new ViewPolygon();
		FileReader r2 = new FileReader("/Users/andy/Desktop/Eclipse/Specifications/check.pol.txt");
		BufferedReader reader2 = new BufferedReader(r2);
		String line = null;
		for(int j =1 ;j<=10; j++)
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
		String[] arr2 = map_guards[1].split(", ");
		Polygon2D guards = new SimplePolygon2D();
		for(int i = 0; i<arr2.length; i+=2){
		    double x = Double.parseDouble(arr2[i]);
		    double y = Double.parseDouble(arr2[i+1]);
			Point2D point = new Point2D(x,y);
			guards.addVertex(point);
		}
		Collection<Point2D> guardscol = guards.vertices();
		drawer.drawPolygon(polygon,guardscol);
		System.out.print(arr[0] + ": ");
		//chguards.checkGuards(polygon, guardscol);
	//	}
		
	}

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
	
}
