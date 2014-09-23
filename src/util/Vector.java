package util;

import java.util.zip.Adler32;


public class Vector {
	public Vector()
	{
		x=y=0;
	}
	public Vector(int X,int Y)
	{
		x=X;
		y=Y;
	}
	public Point toPoint()
	{
		return new Point(x,y);
	}
	public Vector neg(){return new Vector(-x,-y);}
	public Vector mul(double n)
	{
		return new Vector((int)(x*n),(int)(y*n));
	}
	public static Vector add(Vector v1,Vector v2)
	{
		if(v1==null||v2==null)throw new NullPointerException();
		return new Vector(v1.x+v2.x,v1.y+v2.y);
	}
	public static long mul(Vector v1,Vector v2)
	{
		if(v1==null||v2==null)throw new NullPointerException();
		return v1.x*v2.x+v1.y*v2.y;
	}
	public int x,y;
	public static boolean eq(Vector v1,Vector v2)
	{
		if(v1==null||v2==null)throw new IllegalArgumentException();
		return v1.x==v2.x&&v1.y==v2.y;
	}
	public boolean equals(Object o)
	{
		if(o==null||!(o instanceof Vector))return false;
		Vector v=(Vector)o;
		return x==v.x&&y==v.y;
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
}
