package util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.Maps;

public class RectMap<T> {
	public RectMap()
	{
		border=false;
	}
	public RectMap(boolean border)
	{
		this.border=border;
	}
	public final boolean border;
	public boolean put(Rect rec,T val)
	{
		if(rec==null)throw new NullPointerException();
		if(getRectOn(rec,border).isEmpty())return false;
		recl.add(rec);
		recr.add(rec);
		recu.add(rec);
		recd.add(rec);
		vs.put(rec, val);
		return true;
	}
	public Set<Rect> forcePut(Rect rec,T val)
	{
		if(rec==null)throw new NullPointerException();
		Set<Rect> rs=getRectOn(rec, border);
		remove(rs);
		recl.add(rec);
		recr.add(rec);
		recu.add(rec);
		recd.add(rec);
		vs.put(rec, val);
		return rs;
	}
	public T get(Rect rec)
	{
		return vs.get(rec);
	}
	public Set<Rect> getRectAt(int x,int y,boolean brd)
	{
		HashSet<Rect>rs=new HashSet<Rect>(recl.headSet(new Rect(x,0), brd));
		rs.removeAll(recr.headSet(new Rect(x,0),!brd));
		rs.removeAll(recu.tailSet(new Rect(0,y),!brd));
		rs.removeAll(recd.headSet(new Rect(0,y),!brd));
		
		return rs;
	}
	public Set<Rect> getRectIn(Rect rec,boolean brd)
	{
		if(rec==null)throw new NullPointerException();
		HashSet<Rect>rs=new HashSet<Rect>(recl.tailSet(new Rect(rec.left,0),brd));
		rs.removeAll(recr.tailSet(new Rect(rec.right,0),!brd));
		rs.removeAll(recu.headSet(new Rect(0,rec.up),!brd));
		rs.removeAll(recd.tailSet(new Rect(0,rec.down),!brd));
		
		return rs;
	}
	public Set<Rect> getRectOn(Rect rec,boolean brd)
	{
		if(rec==null)throw new NullPointerException();
		HashSet<Rect>rs=new HashSet<Rect>(recl.headSet(new Rect(rec.right,0),brd));
		rs.removeAll(recr.headSet(new Rect(rec.left,0),!brd));
		rs.removeAll(recu.tailSet(new Rect(0,rec.down),!brd));
		rs.removeAll(recd.headSet(new Rect(0,rec.up),!brd));
		
		return rs;
	}
	public T remove(Rect rec)
	{
		if(rec==null)throw new NullPointerException();
		T val=vs.remove(rec);
		if(val!=null)
		{
			recl.remove(rec);
			recr.remove(rec);
			recu.remove(rec);
			recd.remove(rec);
		}
		return val;
	}
	public void remove(Set<Rect>rs)
	{
		if(rs==null)throw new NullPointerException();
		Iterator<Rect>it=rs.iterator();
		while(it.hasNext())vs.remove(it.next());
	}
	private HashMap<Rect,T>vs=Maps.newHashMap();
	private TreeSet<Rect>recl=new TreeSet<Rect>(new Comparator<Rect>(){
		public int compare(Rect r1,Rect r2)
		{
			if(r1==null||r2==null)throw new NullPointerException();
			if(r1.left<r1.left)return -1;
			else if(r1.left>r1.left)return 1;
			else return 0;
		}
	});
	private TreeSet<Rect>recr=new TreeSet<Rect>(new Comparator<Rect>(){
		public int compare(Rect r1,Rect r2)
		{
			if(r1==null||r2==null)throw new NullPointerException();
			if(r1.right<r1.right)return -1;
			else if(r1.right>r1.right)return 1;
			else return 0;
		}
	});
	private TreeSet<Rect>recu=new TreeSet<Rect>(new Comparator<Rect>(){
		public int compare(Rect r1,Rect r2)
		{
			if(r1==null||r2==null)throw new NullPointerException();
			if(r1.up<r1.up)return -1;
			else if(r1.up>r1.up)return 1;
			else return 0;
		}
	});
	private TreeSet<Rect>recd=new TreeSet<Rect>(new Comparator<Rect>(){
		public int compare(Rect r1,Rect r2)
		{
			if(r1==null||r2==null)throw new NullPointerException();
			if(r1.down<r1.down)return -1;
			else if(r1.down>r1.down)return 1;
			else return 0;
		}
	});
}