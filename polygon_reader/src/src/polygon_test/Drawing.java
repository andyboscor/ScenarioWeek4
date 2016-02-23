package src.polygon_test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import math.geom2d.AffineTransform2D;
import math.geom2d.Point2D;
import math.geom2d.polygon.*;

public class Drawing extends JFrame{
	
	public Drawing() {
        super("Polygon Drawing Demo");
 
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }
 
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
     
        Polygon2D x = new SimplePolygon2D();
		Point2D p1 = new Point2D(-50, -100);
		Point2D p2 = new Point2D(100, 0);
		Point2D p3 = new Point2D(0, 100);
		
		Polygon2D y = new SimplePolygon2D();
		Point2D p4 = new Point2D(-100,50);
		Point2D p5 = new Point2D(-75,100);
		Point2D p6 = new Point2D(0,20);
		
		x.addVertex(p1);
		x.addVertex(p2);
		x.addVertex(p3);
		
		y.addVertex(p4);
		y.addVertex(p5);
		y.addVertex(p6);
		
		ArrayList<Polygon2D> polygons = new ArrayList();
		polygons.add(x);
		polygons.add(y);
		
		double minX = 0;
		double minY = 0;
		
		for(int i = 0; i < polygons.size(); i++){
			for(int j = 0; j < polygons.get(i).vertexNumber(); j++){
				if(polygons.get(i).vertex(j).x()<minX){
					minX = polygons.get(i).vertex(j).x();
					System.out.println("New X translation : " + minX);
				}
				if(polygons.get(i).vertex(j).y()<minY){
					minY = polygons.get(i).vertex(j).y();
					System.out.println("New Y translation : " + minY);
				}
			}
		}
		
		minX = Math.abs(minX)*2;
		minY = Math.abs(minY)*2;
		
		AffineTransform2D grid_Correction = new AffineTransform2D();
		AffineTransform2D z = grid_Correction.createTranslation(minX,minY);
		
		x.transform(z).draw(g2d);	     
		y.transform(z).draw(g2d);
 
    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Drawing().setVisible(true);
            }
        });
    }

}
