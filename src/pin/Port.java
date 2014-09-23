package pin;

import type.Signal;
import board.Env;
import board.chip.Chip;

public class Port extends Pin{
	public Port(Chip owner,String name,int no,boolean ie,boolean oe)
	{
		super(name,no,ie,oe);
		if(owner==null)throw new IllegalArgumentException();
		this.owner=owner;
	}
	public Port(Chip owner,String name,int no)
	{
		super(name,no,false,false);
		if(owner==null)throw new IllegalArgumentException();
		this.owner=owner;
	}
	public boolean isLegal()
	{
		return owner!=null&&no>=0&&!(!ie&&!oe);
	}
	public boolean equals(Object o)
	{
		if(o==null)return false;
		if(!(o instanceof Port))return false;
		Port port=(Port)o;
		return owner==port.owner&&name.equals(port.name)&&no==port.no;
	}
	public int hashCode()
	{
		return owner.hashCode()*name.hashCode()*Integer.valueOf(no).hashCode();
	}
	public final Chip owner;
	@Override
	public void in(Signal sig) {
		owner.onIn(this,sig);
	}
	@Override
	public void out(Signal sig) {
		Env.running.touch(this,sig);
	}
	public void out()
	{
		Env.running.touch(this);
	}
}