package util;

import java.util.zip.Adler32;


public class Point {
	public Point()
	{
		x=y=0;
	}
	public Point(int X,int Y)
	{
		x=X;
		y=Y;
	}
	public void flip()
	{
		int t=x;
		x=y;
		y=t;
	}
	public Vector toVector()
	{
		return new Vector(x,y);
	}
	public Point clone()
	{
		return new Point(x,y);
	}
	public boolean in(Rect rect)
	{
		if(rect==null)throw new NullPointerException();
		return x>rect.left&&x<rect.right&&y>rect.up&&y<rect.down;
	}
	public Point add(Vector v)
	{
		if(v==null)throw new NullPointerException();
		return new Point(x+v.x,y+v.y);
	}
	public Vector sub(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		return new Vector(x-pt.x,y-pt.y);
	}
	public static double dis(Point p1,Point p2)
	{
		if(p1==null||p2==null)throw new NullPointerException();
		return Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
	}
	public int x,y;
	public boolean equals(Object o)
	{
		if(o==null||!(o instanceof Point))return false;
		Point pt=(Point)o;
		return x==pt.x&&y==pt.y;
	}
	public String toString()
	{
		return "["+x+","+y+"]";
	}
	public int hashCode()
	{
		Adler32 adl=new Adler32();
		adl.update(x);
		adl.update(y);
		return (int) adl.getValue();
	}
	public static boolean eq(Point p1,Point p2)
	{
		if(p1==null||p2==null)throw new NullPointerException();
		return p1.x==p2.x&&p1.y==p2.y;
	}
}
