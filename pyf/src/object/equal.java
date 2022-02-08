package object;

public class equal {
    public static void main(String[] args) {
        Point p = new Point(1, 2);
        Point p1 = new Point(1, 2);
        System.out.println( p == p1 );
        System.out.println( p.equals(p1) );

    }
}
