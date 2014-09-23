package pin;

import type.Signal;
import board.Env;

public abstract class Pin {

	public Pin(String name, int no, boolean ie, boolean oe) {
		if(name==null||!ie&&!oe||no<0)throw new IllegalArgumentException();
		this.name=name;
		this.no=no;
		this.ie=ie;
		this.oe=oe;
	}
	public abstract void in(Signal sig);
	public abstract void out(Signal sig);
	public Signal get()
	{
		if(Env.running==null)throw new IllegalStateException();
		return Env.running.getSig(this);
	}
	public boolean equals(Object o)
	{
		if(o==null)return false;
		if(!(o instanceof Pin))return false;
		Pin pin=(Port)o;
		return name.equals(pin.name)&&no==pin.no;
	}
	public int hashCode()
	{
		return name.hashCode()*Integer.valueOf(no).hashCode();
	}
	
	public final String name;
	public final int no;
	public final boolean ie;
	public final boolean oe;

}
