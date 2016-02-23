package src.polygon_test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import math.geom2d.AffineTransform2D;
import math.geom2d.Point2D;
import math.geom2d.polygon.*;

public class Drawing extends JFrame{
	
	Polygon2D polygonDraw;
	int xSize = 500;
	int ySize = 500;
	
	public Drawing() {
        super("Polygon Drawing Demo");
        
        setSize(xSize, ySize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }
	
	public Drawing(Polygon2D polygon){
        super("Polygon Drawing Demo");
        this.polygonDraw = polygon;
        
        setSize(xSize, ySize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	
 
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
//      Polygon2D x = new SimplePolygon2D();
//		Point2D p1 = new Point2D(-50, -100);
//		Point2D p2 = new Point2D(100, 0);
//		Point2D p3 = new Point2D(0, 100);
//		
//		Polygon2D y = new SimplePolygon2D();
//		Point2D p4 = new Point2D(-100,50);
//		Point2D p5 = new Point2D(-75,100);
//		Point2D p6 = new Point2D(0,20);
		
//		x.addVertex(p1);
//		x.addVertex(p2);
//		x.addVertex(p3);
//		
//		y.addVertex(p4);
//		y.addVertex(p5);
//		y.addVertex(p6);
		
//		ArrayList<Polygon2D> polygons = new ArrayList();
//		polygons.add(x);
//		polygons.add(y);
		
		double minX = 0;
		double maxX = 0;
		double minY = 0;
		double maxY = 0;
		
		for(int j = 0; j < polygonDraw.vertexNumber(); j++){
				if(polygonDraw.vertex(j).x()<minX){
					minX = polygonDraw.vertex(j).x();
					System.out.println("New X translation : " + minX);
				}
				if(polygonDraw.vertex(j).x()>xSize){
					maxX = polygonDraw.vertex(j).x();
					System.out.println("X goes beyond canvas : " + maxX);
				}
				if(polygonDraw.vertex(j).y()<minY){
					minY = polygonDraw.vertex(j).y();
					System.out.println("New Y translation : " + minY);
				}
				if(polygonDraw.vertex(j).x()>ySize){
					maxY = polygonDraw.vertex(j).y();
					System.out.println("Y goes beyond canvas : " + maxY);
				}
		}
		
		minX = Math.abs(minX)*2;
		minY = Math.abs(minY)*2;
		
		Point2D centreOfScale = new Point2D(xSize/2.0,ySize/2.0);
		AffineTransform2D grid_Correction = new AffineTransform2D();
		AffineTransform2D z = grid_Correction.createTranslation(100,100);
//		AffineTransform2D scale = z.createScaling(centreOfScale,0.5, 0.5);
		Color myNewPurple1 = new Color(103,58,196);
		g2d.setColor(myNewPurple1);
		polygonDraw.transform(z).draw(g2d);
		polygonDraw.transform(z).fill(g2d);
    }
 
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }
    
    public void drawPolygon(Polygon2D polygon){
    	this.polygonDraw = polygon;
    	System.out.println(polygonDraw.vertices());
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Drawing(polygonDraw).setVisible(true);
            }
        });

    }


}
