package util;


public class Size {
	public Size()
	{
		width=height=0;
	}
	public Size(int Width,int Height)
	{
		if(Width<0||Height<0)throw new IllegalArgumentException();
		width=Width;
		height=Height;
	}
	public Size(Point p1,Point p2)
	{
		if(p1==null||p2==null)throw new NullPointerException();
		width=p1.x-p2.x;
		height=p1.y-p2.y;
		if(width<0)width=-width;
		if(height<0)height=-height;
	}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public void setWidth(int Width)
	{
		if(Width<0)throw new IllegalArgumentException();
		width=Width;
	}
	public void setHeight(int Height)
	{
		if(Height<0)throw new IllegalArgumentException();
		height=Height;
	}
	public long getArea(){return width*height;}
	private int width,height;
	public static boolean eq(Size s1,Size s2)
	{
		if(s1==null||s2==null)throw new NullPointerException();
		return s1.width==s2.width&&s1.height==s2.height;
	}
}
