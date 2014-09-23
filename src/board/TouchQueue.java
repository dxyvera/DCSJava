package board;

import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Sets;

import board.Circuit.NetList;

public class TouchQueue implements Runnable {

	public TouchQueue(Circuit circuit) {
		if(circuit==null)throw new NullPointerException();
		this.circuit=circuit;
	}
	public void put(NetList nl)
	{
		touchs.add(nl);
	}
	
	@Override
	public void run() {
		Iterator<NetList>it=touchs.iterator();
		NetList nl;
		boolean und=false;
		do
		{
			while(it.hasNext())
			{
				nl=it.next();
				if(nl.ready())
				{
					nl.out();
					und=true;
				}
			}
		}while(und);
	}
	private Circuit circuit;
	private Set<NetList>touchs=Sets.newHashSet();
}
