package polygon_test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import math.geom2d.Point2D;
import math.geom2d.polygon.*;

public class fileReader {
	
	public static void readFile() throws IOException{
		FileReader r = new FileReader("/Users/rajind/Documents/Github Projects/Scenario Week IV/Specifications/guards.pol");
		BufferedReader reader = new BufferedReader(r);
		String line;
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
		int n = polygon.vertexNumber();
		
		for(int i = 0; i<n;i++){
			System.out.println(polygon.vertex(i));
		}
	}

	public static void main(String[] args) {
		try {
			readFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
