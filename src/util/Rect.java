package util;

import java.util.zip.Adler32;


public class Rect {
	public Rect()
	{
		left=right=up=down=0;
	}
	public Rect(int x,int y)
	{
		left=right=x;
		up=down=y;
	}
	public Rect(int Left,int Right,int Up,int Down)
	{
		if(Right<Left||Down<Up)throw new IllegalArgumentException();
		left=Left;
		right=Right;
		up=Up;
		down=Down;
	}
	public Rect(Point pos,Size size)
	{
		left=pos.x;
		up=pos.y;
		right=pos.x+size.getWidth();
		down=pos.y+size.getHeight();
	}
	public Rect(Point pos,Vector vec)
	{
		if(vec.x<0)
		{
			left=pos.x+vec.x;
			right=pos.x;
		}
		else
		{
			left=pos.x;
			right=pos.x+vec.x;
		}
		
		if(vec.y<0)
		{
			up=pos.y+vec.y;
			down=pos.y;
		}
		else
		{
			up=pos.y;
			down=pos.y+vec.y;
		}
	}
	public Rect(Point p1,Point p2)
	{
		if(p1.x>p2.x)
		{
			left=p2.x;
			right=p1.x;
		}
		else
		{
			left=p1.x;
			right=p2.x;
		}
		
		if(p1.y>p2.y)
		{
			up=p2.y;
			down=p1.y;
		}
		else
		{
			up=p1.y;
			down=p2.y;
		}
	}
	public Size getSize(){return new Size(right-left,down-up);}
	
	public Point getPos(){return getLU();}
	public Point getLU(){return new Point(left,up);}
	public Point getRU(){return new Point(right,up);}
	public Point getLD(){return new Point(left,down);}
	public Point getRD(){return new Point(right,down);}
	
	public Rect setLU(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		if(pt.x>right||pt.y>down)throw new IllegalArgumentException();
		return new Rect(pt.x,right,pt.y,down);
	}
	public Rect setRU(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		if(pt.x<left||pt.y>down)throw new IllegalArgumentException();
		return new Rect(left,pt.x,pt.y,down);
	}
	public Rect setLD(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		if(pt.x>right||pt.y<up)throw new IllegalArgumentException();
		return new Rect(pt.x,right,up,pt.y);
	}
	public Rect setRD(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		if(pt.x<left||pt.y<up)throw new IllegalArgumentException();
		return new Rect(left,pt.x,up,pt.y);
	}
	public Rect setLeft(int Left)
	{
		if(Left>right)throw new IllegalArgumentException();
		return new Rect(Left,right,up,down);
	}
	public Rect setRight(int Right)
	{
		if(Right<left)throw new IllegalArgumentException();
		return new Rect(left,Right,up,down);
	}
	public Rect setUp(int Up)
	{
		if(Up>down)throw new IllegalArgumentException();
		return new Rect(left,right,Up,down);
	}
	public Rect setDown(int Down)
	{
		if(Down<up)throw new IllegalArgumentException();
		return new Rect(left,right,up,Down);
	}
	public Rect move(Vector vec)
	{
		if(vec==null)throw new NullPointerException();
		return new Rect(left+vec.x,right+vec.x,up+vec.y,down+vec.y);
	}
	public Rect moveLU(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		return new Rect(pt.x,right-left+pt.x,pt.y,down-up+pt.y);
	}
	public Rect moveRU(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		return new Rect(left-right+pt.x,pt.x,pt.y,down-up+pt.y);
	}
	public Rect moveLD(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		return new Rect(pt.x,right-left+pt.x,up-down+pt.y,pt.y);
	}
	public Rect moveRD(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		return new Rect(left-right+pt.x,pt.x,up-down+pt.y,pt.y);
	}
	
	public boolean isPoint(){return left==right&&up==down;}
	
	public boolean in(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		return pt.x>=left&&pt.x<=right&&pt.y>=up&&pt.y<=down;
	}
	public boolean in(Rect rect)
	{
		if(rect==null)throw new NullPointerException();
		return rect.left>=left&&rect.right<=right&&rect.up>=up&&rect.down<=down;
	}
	
	public static boolean intersects(Rect r1,Rect r2)
	{
		if(r1==null||r2==null)throw new NullPointerException();
		return !(r1.left>r2.right||r1.right<r2.left||r1.up>r2.down||r1.down<r2.up);
	}
	public static Rect intersection(Rect r1,Rect r2)
	{
		if(r1==null||r2==null)throw new NullPointerException();
		return new Rect(r1.left>r2.left?r1.left:r2.left,r1.right<r2.right?r1.right:r2.right,r1.up>r2.up?r1.up:r2.up,r1.down<r2.down?r1.down:r2.down);
	}
	public Rect union(Point pt)
	{
		if(pt==null)throw new NullPointerException();
		return new Rect(pt.x<left?pt.x:left,pt.x>right?pt.x:right,pt.y<up?pt.y:up,pt.y>down?pt.y:down);
	}
	public static Rect union(Rect r1,Rect r2)
	{
		if(r1==null||r2==null)throw new NullPointerException();
		return new Rect(r1.left<r2.left?r1.left:r2.left,r1.right>r2.right?r1.right:r2.right,r1.up<r2.up?r1.up:r2.up,r1.down>r2.down?r1.down:r2.down);
	}
	public static boolean eq(Rect r1,Rect r2)
	{
		if(r1==null||r2==null)throw new NullPointerException();
		return r1.left==r2.left&&r1.right==r2.right&&r1.up==r2.up&&r1.down==r2.down;
	}
	
	public boolean equals(Object o)
	{
		if(o==null||!(o instanceof Rect))return false;
		Rect r=(Rect)o;
		return left==r.left&&right==r.right&&up==r.up&&down==r.down;
	}
	public String toString()
	{
		return "{Left:"+left+",Right:"+right+",Up:"+up+",Down:"+down+"}";
	}
	public int hashCode()
	{
		Adler32 adl=new Adler32();
		adl.update(left);
		adl.update(right);
		adl.update(up);
		adl.update(down);
		return (int) adl.getValue();
	}
	public Rect clone(){return new Rect(left,right,up,down);}
	
	public final int left,right,up,down;
}
