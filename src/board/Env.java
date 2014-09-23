package board;

import system.Log;

public class Env {
	static
	{
		System.out.println("I am in the static block of Environment.");
	}
	public static void repaint()
	{
		
	}
	public void setMaxStep(int step)
	{
		if(step<1)step=1;
		maxStep=step;
	}
	public int getMaxStep()
	{
		return maxStep;
	}
	public static boolean dbg=false;
	public static Circuit running;
	public static final Log log=new Log();
	private static int maxStep=120;
	public static final int maxPerStep=10;
	public static double scale=1.0;
}
