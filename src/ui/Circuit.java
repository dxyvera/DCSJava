package ui;

import board.Board;


public class Circuit {

	public static void main(String[] args) {
		wnd=new MainFrame();
		wnd.setLocation(283, 84);
		wnd.setSize(800,600);
		wnd.setVisible(true);
		
		board.addWireX(1, -5, 8);
		wnd.repaint();
		
	}
	static MainFrame wnd;
	static Board board=new Board();
	
}
