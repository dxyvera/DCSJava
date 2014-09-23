package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import com.google.common.collect.Range;

import util.Point;
import board.Board;

public class WorkSpace extends JComponent implements MouseInputListener,MouseWheelListener,ComponentListener {

	public WorkSpace()
	{
		this.board=Circuit.board;
		this.addMouseWheelListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addComponentListener(this);
		this.setMinimumSize(new Dimension(10,10));
	}
	public void reset()
	{
		this.board=Circuit.board;
		opx=0.5;
		opy=0.5;
		origin.x=getWidth()/2;
		origin.y=getHeight()/2;
		repaint();
	}
	
	static final int min_inv=10;
	static final int wire_width=2;
	
	private int inv=50;
	private Point origin=new Point();
	private double opx=0.5,opy=0.5;

	Point D2B(Point pt)
	{
		if(pt==null)throw new IllegalArgumentException();
		Point np=pt.sub(origin).toPoint();
		if(np.x>0)np.x+=inv/2;
		else np.x-=inv/2;
		np.x/=inv;

		if(np.y>0)np.y+=inv/2;
		else np.y-=inv/2;
		np.y/=inv;
		
		return np;
	}
	Point B2D(Point pt)
	{
		if(pt==null)throw new IllegalArgumentException();
		return origin.add(pt.toVector().mul(inv));
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLUE);
		for(int i=origin.x%inv;i<getWidth();i+=inv)g.drawLine(i, origin.y-5,i,origin.y+5);
		for(int i=origin.y%inv;i<getHeight();i+=inv)g.drawLine(origin.x-5,i,origin.x+5,i);
		g.drawLine(0, origin.y, getWidth(), origin.y);
		g.drawLine(origin.x, 0, origin.x, getHeight());
		g.setColor(Color.BLACK);
		
		Point lu=new Point(-origin.x,-origin.y);
		lu.x/=inv;
		lu.y/=inv;
		Point rd=new Point(getWidth()-origin.x,getHeight()-origin.y);
		rd.x/=inv;
		rd.y/=inv;
		
		Map<Integer,Set<Range<Integer>>>ws=board.getWireX(lu.y, rd.y, lu.x-1, rd.x+1);
		Iterator<Integer>kit=ws.keySet().iterator();
		Iterator<Range<Integer>>rit;
		Range<Integer> r;
		Point sp=new Point(),ep=new Point();
		g.setColor(Color.black);
		while(kit.hasNext())
		{
			int p=kit.next();
			rit=ws.get(p).iterator();
			while(rit.hasNext())
			{
				r=rit.next();
				sp.y=ep.y=p;
				sp.x=r.lowerEndpoint();
				ep.x=r.upperEndpoint();
				sp=B2D(sp);
				ep=B2D(ep);
				draWX(g,sp.x,ep.x,sp.y);
			}
		}
		ws=board.getWireY(lu.x, rd.x, lu.y-1, rd.y+1);
		kit=ws.keySet().iterator();
		while(kit.hasNext())
		{
			int p=kit.next();
			rit=ws.get(p).iterator();
			while(rit.hasNext())
			{
				r=rit.next();
				sp.x=ep.x=p;
				sp.y=r.lowerEndpoint();
				ep.y=r.upperEndpoint();
				sp=B2D(sp);
				ep=B2D(ep);
				draWY(g,sp.y,ep.y,sp.x);
			}
		}
		
	}
	private Board board;
	private void draWX(Graphics g,int xs,int xe,int y)
	{
		g.fillRect(xs, y-wire_width/2, xe-xs, wire_width);
		int[] xpl={xs+wire_width,xs-wire_width*2,xs-wire_width*2};
		int[] xpr={xe-wire_width,xe+wire_width*2,xe+wire_width*2};
		int[] yp={y,y-wire_width*2,y+wire_width*2};
		g.fillPolygon(xpl, yp, 3);
		g.fillPolygon(xpr, yp, 3);
	}
	private void draWY(Graphics g,int ys,int ye,int x)
	{
		g.fillRect(x-wire_width/2,ys,wire_width,ye-ys);
		int[] xp={x,x-wire_width*2,x+wire_width*2};
		int[] ypl={ys+wire_width,ys-wire_width*2,ys-wire_width*2};
		int[] ypr={ye-wire_width,ye+wire_width*2,ye+wire_width*2};
		g.fillPolygon(xp, ypl, 3);
		g.fillPolygon(xp, ypr, 3);
	}
	public static double per_scale=1.1;
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		origin.x=(int)(getWidth()*opx);
		origin.y=(int)(getHeight()*opy);
		repaint();
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
