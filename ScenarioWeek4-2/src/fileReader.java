
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import math.geom2d.Point2D;
import math.geom2d.polygon.*;

import java.awt.*;

public class fileReader {
	
	public static Polygon2D readFile() throws IOException{
		FileReader r = new FileReader("/Users/williambhot/Documents/Uni/Year2/Term2/Scenario-Week-4/guards.pol.txt");
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
		/*int n = polygon.vertexNumber();
		
		for(int i = 0; i<n;i++){
			System.out.println(polygon.vertex(i));
		}*/
		
		return polygon;
		
		
	}
	private void doDrawing(Graphics g,Polygon2D polygon) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);
        
        polygon.draw(g2d);
       
	}
	public static void writing() {
        try {
            File statText = new File("/Users/rajind/Desktop/statText.txt");
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
			readFile();
			writing();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}