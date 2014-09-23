package board;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;

import pin.Pin;
import pin.Port;
import type.Signal;
import board.chip.Chip;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class Circuit {
	public void addPort(Port port)
	{
		if(port==null)throw new NullPointerException();
		 chips.add(port.owner);
		 addPin(port);
	}
	public void addPort(Port port,Pin upin)
	{
		if(port==null)throw new NullPointerException();
		chips.add(port.owner);
		addPin(port,upin);
	}
	public void addPin(Pin pin)
	{
		if(pin==null)throw new NullPointerException();
		NetList pu;
		if(pins.containsKey(pin))return;
		pu=new NetList();
		pu.addPin(pin);
		pins.put(pin, pu);
	}
	public void addPin(Pin pin,Pin upin)
	{
		if(pin==null||upin==null)throw new NullPointerException();
		if(Env.running!=null)throw new IllegalStateException();
		if(pins.containsKey(pin)&&!pins.containsKey(upin))
		{
			Pin tp=pin;
			pin=upin;
			upin=tp;
		}
		NetList nl;
		if((nl=pins.get(upin))==null)
		{
			nl=new NetList();
			nl.addPin(upin);
			pins.put(upin, nl);
		}
		if(pins.containsKey(pin))
		{
			NetList n2=pins.get(pin);
			Pin p;
			Iterator<Pin>it=n2.ips.iterator();
			while(it.hasNext())
			{
				p=it.next();
				nl.addPin(p);
				pins.put(p, nl);
			}
			it=n2.ops.keySet().iterator();
			while(it.hasNext())
			{
				p=it.next();
				nl.addPin(p);
				pins.put(p, nl);
			}
		}
		else
		{
			nl.addPin(pin);
			pins.put(pin, nl);
		}
	}
	public void run()
	{
		Env.dbg=false;
		Env.running=this;
		for(Chip ch:chips)ch.start();
	}
	public void debug()
	{
		Env.dbg=true;
		Env.running=this;
		for(Chip ch:chips)ch.start();
	}
	public void pause()
	{
		
	}
	public void stop()
	{
		Env.running=null;
	}
	public void delay(Chip chip,int ms)
	{
		
	}
	public void touch(Port port,Signal sig)
	{
		NetList nl=pins.get(port);
		if(nl==null)return;
		queue.put(nl);
		nl.touch(port, sig);
	}
	public void touch(Port port)
	{
		NetList nl=pins.get(port);
		if(nl==null)return;
		queue.put(nl);
		nl.touch(port);
	}
	public void reset()
	{
		Iterator<NetList>it=pins.values().iterator();
		while(it.hasNext())it.next().reset();
	}
	public Signal getSig(Pin pin)
	{
		NetList nl=pins.get(pin);
		if(nl==null)throw new IllegalArgumentException();
		return nl.sig;
	}
	
	private int time=0;
	private ConcurrentSkipListMap<Integer,Chip> tasks=new ConcurrentSkipListMap<Integer, Chip>();//////////////////
	
	private TouchQueue queue=new TouchQueue(this);
	private Map<Pin,NetList>pins=Maps.newHashMap();
	private Set<Chip>chips=Sets.newHashSet();
	
	public class NetList
	{
		public NetList()
		{
			reset();
		}
		public void reset()
		{
			nst.put(Signal.HIGH, 0);
			nst.put(Signal.LOW, 0);
			nst.put(Signal.UNKNOWN, 0);
			pst=nst.clone();
			sig=null;
			step=0;
		}
		private void addPin(Pin pin)
		{
			if(pin==null)throw new NullPointerException();
			if(Env.running!=null)throw new IllegalStateException();
			if(pin.ie)ips.add(pin);
			if(pin.oe)ops.put(pin,null);
		}
		public boolean ready()
		{
			return cps.containsAll(ops.keySet());
		}
		private void touch(Pin pin)
		{
			if(!ops.containsKey(pin))throw new IllegalStateException();
			cps.add(pin);
		}
		private void touch(Pin pin,Signal sig)
		{
			if(!ops.containsKey(pin))throw new IllegalStateException();
			cps.add(pin);
			Signal s=ops.get(pin);
			if(s==sig)return;
			ops.put(pin, sig);
			if(s!=null)nst.put(s, nst.get(s)-1);
			if(sig!=null)nst.put(sig, nst.get(sig)+1);
		}
		public void out()
		{
			if((nst.get(Signal.UNKNOWN)>0)==(pst.get(Signal.UNKNOWN)>0)||(nst.get(Signal.HIGH)>0)==(pst.get(Signal.HIGH)>0)||(nst.get(Signal.LOW)>0)==(pst.get(Signal.LOW)>0))return;
			pst=nst.clone();
			if(nst.get(Signal.UNKNOWN)>0||nst.get(Signal.HIGH)>0&&nst.get(Signal.LOW)>0)sig=Signal.UNKNOWN;
			else if(nst.get(Signal.HIGH)>0)sig=Signal.HIGH;
			else if(nst.get(Signal.LOW)>0)sig=Signal.LOW;
			else sig=null;
			Iterator<Pin>it=ips.iterator();
			while(it.hasNext())it.next().in(sig);
			step++;
			if(step>Env.maxPerStep)
			{
				System.err.println("Circuit Exception Happened.");
			}
		}
		
		private int step=0;
		
		private Signal sig;
		private Set<Pin>ips=Sets.newHashSet();
		private Map<Pin,Signal>ops=Maps.newHashMap();
		private Set<Pin>cps=Sets.newHashSet();
		private EnumMap<Signal,Integer>nst=Maps.newEnumMap(Signal.class);//////////////////////////////////////?????????????
		private EnumMap<Signal,Integer>pst;
	}
}