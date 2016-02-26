package src.polygon_test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import math.geom2d.AffineTransform2D;
import math.geom2d.Box2D;
import math.geom2d.Point2D;
import math.geom2d.polygon.Polygon2D;
import math.geom2d.conic.Ellipse2D;

public class Drawing extends JFrame{
	
	ViewPolygon tester = new ViewPolygon();
	static int i = 1;
	ArrayList <Polygon2D> polygonDraw = new ArrayList<Polygon2D>();
	Collection<Point2D> guardsdraw;
	static fileReader reader = new fileReader();
	int xSize = 1020;
	int ySize = 800;
	
	KeyListener listener = new KeyListener(){
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_RIGHT && (i<30)){
				System.out.println("Loading map " + i);
				i++;
				try {
					dispose();
					//reader.multiRead(i);
					reader.readSecondFile(i);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				System.out.println("Loading map " + i);
				i--;
				try {
					dispose();
					//reader.multiRead(i);
					reader.readSecondFile(i);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public Drawing() {
        super("Polygon Drawing Demo");
        
        setSize(xSize, ySize);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
    }
	
	public Drawing(Polygon2D polygon){
        super("Polygon Drawing Demo");
        this.polygonDraw.add(polygon);
        setSize(xSize, ySize);
        addKeyListener(listener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}

	public Drawing(ArrayList <Polygon2D> polygon){
        super("Polygon Drawing Demo");
        this.polygonDraw = polygon;
        setSize(xSize, ySize);
        addKeyListener(listener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	
	public Drawing(Polygon2D polygon, Collection<Point2D> guards){
        super("Polygon Drawing Demo");
        this.polygonDraw.add(polygon);
        this.guardsdraw = guards;
        setSize(xSize, ySize);
        addKeyListener(listener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	
	public Drawing(ArrayList <Polygon2D> polygon, Collection<Point2D> guards){
        super("Polygon Drawing Demo");
        polygonDraw = polygon;
        guardsdraw = guards;
        setSize(xSize, ySize);
        addKeyListener(listener);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
	}
	
    void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        double xFactor = 0;
        double xTranslate = 0;
        double yFactor = 0;
        double yTranslate = 0;
		AffineTransform2D scale = null;
        
        for(int s = 0; s<polygonDraw.size(); s++){
        	if(s == 0){
        	  	double minX = 0;
        		int maxX = 0;
        		double minY = 0;
        		int maxY = 0;
        		
        		for(int j = 0; j < polygonDraw.get(s).vertexNumber(); j++){
        				if(polygonDraw.get(s).vertex(j).x()<minX){
        					minX = polygonDraw.get(s).vertex(j).x();
        					//System.out.println("New X translation : " + minX);
        				}
        				if(polygonDraw.get(s).vertex(j).x()>maxX){
        					maxX = (int) polygonDraw.get(s).vertex(j).x();
        				}
        				if(polygonDraw.get(s).vertex(j).y()<minY){
        					minY = polygonDraw.get(s).vertex(j).y();
        					//System.out.println("New Y translation : " + minY);
        				}
        				if(polygonDraw.get(s).vertex(j).x()>maxY){
        					maxY = (int) polygonDraw.get(s).vertex(j).y();
        				}
        		}
        		
        		
        		//These values determine how much the shape should translate in the canvas
        		minX = Math.abs(minX)+30;
        		xTranslate = minX;
        		minY = Math.abs(minY)+30;
        		yTranslate = minY;
        		
        		Box2D shape = polygonDraw.get(s).boundingBox();
        		
        		double factor = (shape.getWidth()/shape.getHeight())*0.90;
        		xFactor = (xSize/(shape.getWidth()))*0.90;
        		yFactor = (xFactor*factor)*0.90;
        		System.out.println(factor);
        		
        		Point2D centreOfScale = new Point2D(30.0,30.0);
        		AffineTransform2D grid_Correction = new AffineTransform2D();
        		AffineTransform2D z = AffineTransform2D.createTranslation(minX,minY);
        		scale = AffineTransform2D.createScaling(centreOfScale,xFactor, yFactor);
        		
        		Box2D shapeScaled = polygonDraw.get(s).transform(z).transform(scale).boundingBox();

        		//System.out.println(xFactor);
        		//System.out.println(yFactor);
        		
        		Color outline = new Color(0,0,0);
        		g2d.setColor(outline);
        		polygonDraw.get(s).transform(z).transform(scale).draw(g2d);
        		
        		Color fill = new Color(175,175,175);
            	g2d.setColor(fill);
        		polygonDraw.get(s).transform(z).transform(scale).fill(g2d);
        		
        	}
    		else{
        		
        		Box2D shape = polygonDraw.get(s).boundingBox();
        		
        		Point2D centreOfScale = new Point2D(30.0,30.0);
        		AffineTransform2D grid_Correction = new AffineTransform2D();
        		AffineTransform2D z = grid_Correction.createTranslation(xTranslate,yTranslate);
        		scale = grid_Correction.createScaling(centreOfScale,xFactor, yFactor);
        		
        		Box2D shapeScaled = polygonDraw.get(s).transform(z).transform(scale).boundingBox();

//        		System.out.println(xFactor);
//        		System.out.println(yFactor);
        		
        		Color outline = new Color(0,0,0);
        		g2d.setColor(outline);
        		polygonDraw.get(s).transform(z).transform(scale).draw(g2d);
        		
        		Color fill = new Color(255,255,125);
            	g2d.setColor(fill);
        		polygonDraw.get(s).transform(z).transform(scale).fill(g2d);
        		tester.printPoly(polygonDraw.get(s), "Triangle");
//        		try {
//					TimeUnit.MILLISECONDS.sleep(1000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
    		}
        }
		if(guardsdraw!=null){
			ArrayList<Ellipse2D> guards = new ArrayList();
			
			Color myNewColor = new Color (30,192,51);
			g2d.setColor(myNewColor);
			
			double size = 50.00;
			
			for (Point2D guard:guardsdraw)
			{
			Ellipse2D point = new Ellipse2D(guard.getX()+xTranslate,guard.getY()+yTranslate, (size)/500,size/500);
			point.fill(g2d);
			guards.add(point);
			
			}
			
			for(int i = 0; i < guards.size(); i++){
				guards.get(i).transform(scale).draw(g2d);
				guards.get(i).transform(scale).fill(g2d);
			}
		}
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        drawLines(g);
    }
    
    public void drawPolygon(Polygon2D polygon){
    	this.polygonDraw.add(polygon);
    	System.out.println(polygonDraw.get(0).vertices());
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Drawing(polygonDraw).setVisible(true);
            }
        });

    }
    
    public void drawPolygon(Polygon2D polygon, Collection<Point2D> guards){
    	this.polygonDraw.add(polygon);
    	this.guardsdraw = guards;
    	System.out.println(polygonDraw.get(0).vertices());
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Drawing(polygonDraw.get(0),guardsdraw).setVisible(true);
            }
        });

    }

    public void drawPolygon(ArrayList <Polygon2D> polygon, Collection<Point2D> guards){
    	polygonDraw = polygon;
    	guardsdraw = guards;
    	System.out.println(polygonDraw.get(0).vertices());
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Drawing(polygonDraw,guardsdraw).setVisible(true);
            }
        });

    }

    
    public void drawPolygon(ArrayList<Polygon2D> polygon){
    	this.polygonDraw = polygon;
    	System.out.println(polygonDraw.get(0).vertices());
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Drawing(polygonDraw).setVisible(true);
            }
        });

    }
    
	public static void main(String[] args) {
		try {
			//reader.multiRead(1);
			reader.readSecondFile(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}