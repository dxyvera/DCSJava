package board.chip;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

import pin.Port;
import board.Env;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import type.Signal;
import util.Point;
import util.Rect;

public abstract class Chip implements Serializable {

	private static final long serialVersionUID = 2313821862300945804L;

	protected Chip(Rect rect)
	{
		if(rect==null)throw new NullPointerException();
		this.rect=rect;
	}
	public void setup(){}
	public abstract void start();
	public abstract void draw(Graphics g,Point pos);
	public void draw_brief(Graphics g,Point pos)
	{
		draw(g,pos);
	}
	public abstract void onIn(Port port,Signal sig);
	public void onDelay(){}
	
	public abstract void setProp(String name,Object value);
	public abstract Object getProp(String name);
	public LinkedHashSet<String>enumProp()
	{
		return null;
	}
	protected void outAll()
	{
		Iterator<Port>it=ports.values().iterator();
		while(it.hasNext())it.next().out();
	}
	protected void delay(int ms)
	{
		Env.running.delay(this,ms);
	}

	private final Rect rect;
	
	protected Port getPin(String name,int no)
	{
		return ports.get(ports.inverse().get(new Port(this,name,no)));
	}
	
	protected BiMap<Point,Port>ports=HashBiMap.create();
}
