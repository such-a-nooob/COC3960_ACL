import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class JarvisMarchCH {

    int n;
    Point points[];
    Vector<Point> hull;
    int c;

    JarvisMarchCH(int n) {
        c = 0;
        this.n = n;
        points = new Point[n];
        hull = new Vector<Point>();
    }

    void input(int c, int x, int y) {
        points[c] = new Point(x, y);
    }

    int orientation(Point p, Point q, Point r) {
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0)
            return 0; // collinear
        return (val > 0) ? 1 : 2; // clock or counterclock wise
    }

    void convexhull() {
        if (n < 3)
            return;

        int left = 0;
        for (int i = 1; i < n; i++)
            if (points[i].x < points[left].x)
                left = i;

        int prev = left, next;
        do {
            hull.add(points[prev]);

            next = (prev + 1) % n;

            for (int i = 0; i < n; i++) {
                if (orientation(points[prev], points[i], points[next]) == 2)
                    next = i;
            }

            prev = next;

        } while (prev != left);

        System.out.println("Convex hull :");
        System.out.print("[ ");
        for (Point temp : hull)
            System.out.print("(" + temp.x + ", " + temp.y + ") ");
        System.out.print("]");
    }
}

public class ConvexHull extends JPanel {

    JarvisMarchCH h = init();
    int mar = 60;

    protected void paintComponent(Graphics g) {
        
        Graphics2D g1 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        double xscale = (double) (width - 2 * mar) / getMaxX();
        double yscale = (double) (height - 2 * mar) / getMaxY();
        g1.setPaint(Color.BLUE);
        for (int i = 0; i < (h.points).length; i++) {
            double x1 = width - mar - xscale * h.points[i].x;
            double y1 = height - mar - yscale * h.points[i].y;
            x1 = width - x1;
            g1.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 4, 4));
            g1.drawString((" ( " + h.points[i].x + ", " + h.points[i].y + " )"), (float) (x1 + 2), (float) (y1 + 10));
        }

        g1.setPaint(Color.RED);
        int i;
        for (i = 0; i < (h.hull).size() - 1; i++) {
            double x1 = width - mar - xscale * h.hull.get(i).x;
            double y1 = height - mar - yscale * h.hull.get(i).y;
            x1 = width - x1;
            double x2 = width - mar - xscale * h.hull.get(i + 1).x;
            double y2 = height - mar - yscale * h.hull.get(i + 1).y;
            x2 = width - x2;
            g1.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }

        double x1 = width - mar - xscale * h.hull.get(0).x;
        double y1 = height - mar - yscale * h.hull.get(0).y;
        x1 = width - x1;
        double x2 = width - mar - xscale * h.hull.get(i).x;
        double y2 = height - mar - yscale * h.hull.get(i).y;
        x2 = width - x2;
        g1.drawLine((int) x1, (int) y1, (int) x2, (int) y2);

    }

    private JarvisMarchCH init() {
        int n, x, y;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of points : ");
        n = sc.nextInt();
        JarvisMarchCH jm = new JarvisMarchCH(n);
        System.out.println("Enter the cordinates of the points :");
        for (int i = 0; i < n; i++) {
            System.out.print("Point " + (i + 1) + " \t (x,y) : ");
            x = sc.nextInt();
            y = sc.nextInt();
            jm.input(i, x, y);
        }
        jm.convexhull();
        return jm;
    }

    private int getMaxX() {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < (h.points).length; i++) {
            if (h.points[i].x > max)
                max = h.points[i].x;

        }
        return max;
    }

    private int getMaxY() {
        int max = -Integer.MAX_VALUE;
        for (int i = 0; i < (h.points).length; i++) {
            if (h.points[i].y > max)
                max = h.points[i].y;

        }
        return max;
    }

    public static void main(String args[]) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ConvexHull());
        frame.setSize(400, 400);
        frame.setLocation(200, 200);
        frame.setVisible(true);
    }
}