package board;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import pin.Port;
import util.RectMap;
import board.chip.Chip;

import com.google.common.collect.BoundType;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Table;
import com.google.common.collect.TreeRangeSet;

public class Board {
	public void addChip()
	{
	}
	public Chip getChipAt()
	{
		return null;
	}
	public void addWireX(int y,int s,int e)
	{
		if(s>=e)return;
		RangeSet<Integer> rs=wireX.get(y);
		if(rs==null)
		{
			rs=TreeRangeSet.create();
			wireX.put(y, rs);
		}
		addWire(y,s,e,rs,wireY);
	}
	public void addWireY(int x,int s,int e)
	{
		if(s>=e)return;
		RangeSet<Integer> rs=wireY.get(x);
		if(rs==null)
		{
			rs=TreeRangeSet.create();
			wireY.put(x, rs);
		}
		addWire(x,s,e,rs,wireX);
	}
	
	public Range<Integer> getWireAtX(int y,int x)
	{
		if(!wireX.containsKey(y))return null;
		else return wireX.get(y).rangeContaining(x);
	}
	public Range<Integer> getWireAtY(int x,int y)
	{
		if(!wireY.containsKey(x))return null;
		else return wireY.get(x).rangeContaining(y);
	}
	
	public void removeWireX(int y,int s,int e)
	{
		if(s>=e)return;
		RangeSet<Integer>rs=wireX.get(y);
		if(rs==null)return;
		removeWire(y,s,e,rs,wireY);
	}
	public void removeWireY(int x,int s,int e)
	{
		if(s>=e)return;
		RangeSet<Integer>rs=wireY.get(x);
		if(rs==null)return;
		removeWire(x,s,e,rs,wireX);
	}
	public void removeWireInX(int y,int s,int e)
	{
		if(s>=e)return;
		RangeSet<Integer> rs=wireX.get(y);
		if(rs==null)return;
		Range<Integer>r=rs.rangeContaining(s);
		if(r!=null)s=r.upperEndpoint();
		r=rs.rangeContaining(e);
		if(r!=null)s=r.lowerEndpoint();
		removeWire(y,s,e,rs,wireY);
	}
	public void removeWireInY(int x,int s,int e)
	{
		if(s>=e)return;
		RangeSet<Integer> rs=wireY.get(x);
		if(rs==null)return;
		Range<Integer>r=rs.rangeContaining(s);
		if(r!=null)s=r.upperEndpoint();
		r=rs.rangeContaining(e);
		if(r!=null)s=r.lowerEndpoint();
		removeWire(x,s,e,rs,wireX);
	}
	public void removeWireOnX(int y,int s,int e)
	{
		if(s>=e)return;
		RangeSet<Integer> rs=wireX.get(y);
		if(rs==null)return;
		Range<Integer>r=rs.rangeContaining(s);
		if(r!=null)s=r.lowerEndpoint();
		r=rs.rangeContaining(e);
		if(r!=null)s=r.upperEndpoint();
		removeWire(y,s,e,rs,wireY);
	}
	public void removeWireOnY(int x,int s,int e)
	{
		if(s>=e)return;
		RangeSet<Integer> rs=wireY.get(x);
		if(rs==null)return;
		Range<Integer>r=rs.rangeContaining(s);
		if(r!=null)s=r.lowerEndpoint();
		r=rs.rangeContaining(e);
		if(r!=null)s=r.upperEndpoint();
		removeWire(x,s,e,rs,wireX);
	}
	public void removeWireAtX(int y,int x)
	{
		RangeSet<Integer>rs=wireX.get(y);
		if(rs==null||!rs.contains(x))return;
		Range<Integer>r=rs.rangeContaining(x);
		removeWire(y,r.lowerEndpoint(),r.upperEndpoint(),rs,wireY);
	}
	public void removeWireAtY(int x,int y)
	{
		RangeSet<Integer>rs=wireY.get(x);
		if(rs==null||!rs.contains(y))return;
		Range<Integer>r=rs.rangeContaining(x);
		removeWire(x,r.lowerEndpoint(),r.upperEndpoint(),rs,wireX);
	}
	
	private void addWire(int p,int s,int e,RangeSet<Integer>rs,Map<Integer,RangeSet<Integer>>wv)
	{
		if(s>=e)throw new IllegalArgumentException();
		if(rs==null||wv==null)throw new NullPointerException();
		Iterator<Range<Integer>>rit=rs.subRangeSet(Range.closed(s-1, e+1)).asRanges().iterator();
		Set<Integer>hed=new HashSet<Integer>();
		Range<Integer>r,rr;
		RangeSet<Integer>rsn;
		int i=s+1,j;
		while(rit.hasNext())
		{
			r=rit.next();
			j=r.lowerEndpoint();
			if(r.lowerBoundType()==BoundType.OPEN)hed.add(j);
			for(++i;i<j;++i)
			{
				if((rsn=wv.get(i))!=null)
				{
					if((rr=rsn.rangeContaining(p))!=null&&(rr.lowerEndpoint()==p||rr.upperEndpoint()==p))
					{
						wv.get(i).remove(Range.singleton(p));
						hed.add(i);
					}
				}
			}
			i=r.upperEndpoint();
			if(r.upperBoundType()==BoundType.OPEN)hed.add(i);
		}
		j=e;
		for(++i;i<j;++i)
		{
			if((rsn=wv.get(i))!=null)
			{
				rr=rsn.rangeContaining(p);
				if(rr!=null&&(rr.lowerEndpoint()==p||rr.upperEndpoint()==p))
				{
					wv.get(i).remove(Range.singleton(p));
					hed.add(i);
				}
			}
		}
		rs.add(Range.closed(s, e));
		Iterator<Integer>hit=hed.iterator();
		while(hit.hasNext())rs.remove(Range.singleton(hit.next()));
		if((rsn=wv.get(s))!=null)
			if((rr=rsn.rangeContaining(p))!=null&&rr.lowerEndpoint()<p&&rr.upperEndpoint()>p)
				rsn.remove(Range.singleton(p));
		if((rsn=wv.get(e))!=null)
			if((rr=rsn.rangeContaining(p))!=null&&rr.lowerEndpoint()<p&&rr.upperEndpoint()>p)
				rsn.remove(Range.singleton(p));
	}
	private void removeWire(int p,int s,int e,RangeSet<Integer>rs,Map<Integer,RangeSet<Integer>>wv)
	{
		if(s>=e)throw new IllegalArgumentException();
		if(rs==null||wv==null)throw new NullPointerException();
		Iterator<Range<Integer>>rit=rs.subRangeSet(Range.open(s-1, e+1)).asRanges().iterator();
		Set<Integer>hed=new HashSet<Integer>();
		Range<Integer>r;
		RangeSet<Integer>rsn;
		while(rit.hasNext())
		{
			r=rit.next();
			if(r.lowerBoundType()==BoundType.OPEN)hed.add(r.lowerEndpoint());
			if(r.upperBoundType()==BoundType.OPEN)hed.add(r.upperEndpoint());
		}
		if(hed.contains(s-1))
		{
			hed.remove(s-1);
			hed.remove(s);
		}
		if(hed.contains(e+1))
		{
			hed.remove(e+1);
			hed.remove(e);
		}
		Iterator<Integer>hit=hed.iterator();
		while(hit.hasNext())
		{
			rsn=wv.get(hit.next());
			rsn.add(Range.singleton(p));
		}
		rs.remove(Range.open(s, e));
		if((r=rs.rangeContaining(s))!=null)
			if(r.lowerEndpoint()==r.upperEndpoint())rs.remove(Range.singleton(s));
		if((r=rs.rangeContaining(e))!=null)
			if(r.lowerEndpoint()==r.upperEndpoint())rs.remove(Range.singleton(e));
	}
	
	public Map<Integer,Set<Range<Integer>>> getWireX(int ys,int ye,int xs,int xe)
	{
		if(ys>ye||xs>xe)throw new IllegalArgumentException();
		Map<Integer,Set<Range<Integer>>>ws=Maps.newHashMap();
		RangeSet<Integer>rs;
		for(int i=ys;i<=ye;i++)
			if(((rs=wireX.get(i))!=null))
				if(!(rs=rs.subRangeSet(Range.closed(xs, xe))).isEmpty())
					ws.put(i, rs.asRanges());
		return ws;
	}
	public Map<Integer,Set<Range<Integer>>> getWireY(int xs,int xe,int ys,int ye)
	{
		Map<Integer,Set<Range<Integer>>>ws=Maps.newHashMap();
		RangeSet<Integer>rs;
		for(int i=xs;i<=xe;i++)
			if(((rs=wireY.get(i))!=null)&&!(rs=rs.subRangeSet(Range.closed(ys, ye))).isEmpty())
				ws.put(i, rs.asRanges());
		return ws;
	}

	
	
	Map<Integer,RangeSet<Integer>>wireX=Maps.newTreeMap();
	Map<Integer,RangeSet<Integer>>wireY=Maps.newTreeMap();
	private Table<Integer,Integer,Port> ports=HashBasedTable.create();
	private RectMap<Chip>chips=new RectMap<Chip>();
}
