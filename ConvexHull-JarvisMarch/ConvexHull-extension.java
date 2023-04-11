import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JButton;
import java.util.*;
import java.util.List;

public class ConvexHull extends JPanel implements MouseListener {
    private List<Point> points = new ArrayList<Point>();
    private List<Point> hull = new ArrayList<Point>();
    
    public ConvexHull() {
        
        addMouseListener(this);
        
        JButton generateButton = new JButton("Generate Hull");
        generateButton.addActionListener(new ActionListener(){  
            public void actionPerformed(ActionEvent e){  
                        hull = jarvisMarch(points);
                        repaint(); 
                    }  
                }); 
        add(generateButton);
        
        JButton resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                points.clear();
                hull.clear();
				//revalidate();
				repaint();
			}
		});
        
        add(resetButton);        
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw input points
        for (Point p : points) {
            g.fillOval(p.x - 4, p.y - 4, 8, 8);
        }
        // draw convex hull
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < hull.size(); i++) {
            Point p1 = hull.get(i);
            Point p2 = hull.get((i + 1) % hull.size());
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        
        points.add(new Point(e.getX(), e.getY()));
        //hull = jarvisMarch(points);
        repaint();
    }
    
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    
    private List<Point> jarvisMarch(List<Point> points) {
        List<Point> hull = new ArrayList<Point>();
        Point leftmost = points.get(0);
        for (Point p : points) {
            if (p.x < leftmost.x) {
                leftmost = p;
            }
        }
        Point p = leftmost;
        do {
            hull.add(p);
            Point q = points.get(0);
            for (int i = 1; i < points.size(); i++) {
                if (q == p || polarAngle(p, q, points.get(i)) < 0) {
                    q = points.get(i);
                }
            }
            p = q;
        } while (p != leftmost);
        return hull;
    }
    
    private double polarAngle(Point p, Point q, Point r) {
        return (q.x - p.x) * (r.y - p.y) - (r.x - p.x) * (q.y - p.y);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Jarvis March Algorithm");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(new ConvexHull());
        frame.setVisible(true);
    }
}
