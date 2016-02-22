package src.polygon_test;

import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
		Point2D p1 = new Point2D(-50, 10);
		Point2D p2 = new Point2D(100, 150);
		Point2D p3 = new Point2D(200, 20);
		
		x.addVertex(p1);
		x.addVertex(p2);
		x.addVertex(p3);
	    x.draw(g2d);
	     
 
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
