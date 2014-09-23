package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.net.URL;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;

import org.jdesktop.swingx.JXTreeTable;

import board.chip.Chip;


public class MainFrame extends JFrame {

	public MainFrame()
	{
		super("Circuit");
		setMenu();
		lpanel.setLayout(new BorderLayout());
		
		workspace=new WorkSpace();
		chip.setMaximumSize(new Dimension(100,100));
		try {
			toolprop=new JTree(Introspector.getBeanInfo(Chip.class).getPropertyDescriptors());
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("beanerr");
		}
		ldlpanel=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,chip,toolprop);
		ldlpanel.setDividerSize(5);
		ldlpanel.setResizeWeight(.6);
		ldpanel=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,ldlpanel,workspace);
		ldpanel.setDividerSize(5);
		ldpanel.setResizeWeight(.2);
		tool.setLayout(new BoxLayout(tool,BoxLayout.X_AXIS));
		tool.add(new JButton("at"));
		tool.add(Box.createHorizontalGlue());
		level.setMaximumSize(new Dimension(200,50));
		level.setPreferredSize(new Dimension(180,10));
		tool.add(level);
		tool.setFloatable(false);
		tool.setMaximumSize(new Dimension(2000,10));
		lpanel.add(tool,BorderLayout.NORTH);
		lpanel.add(ldpanel,BorderLayout.CENTER);
		rpanel=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,new JLabel("r111111111111111"),propview);
		rpanel.setDividerSize(5);
		rpanel.setResizeWeight(.4);
		topanel=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,lpanel,rpanel);
		topanel.setDividerSize(5);
		topanel.setResizeWeight(.8);
		add(topanel,BorderLayout.CENTER);
		
		stat.setFloatable(false);
		stat.setLayout(new BoxLayout(stat,BoxLayout.X_AXIS));
		stat.add(info);
		stat.add(Box.createGlue());
		stat.add(new JButton(new ImageIcon(getClass().getResource("/res/ico/62.png"))));
		stat.add(scaler);
		stat.add(new JButton(new ImageIcon(getClass().getResource("/res/ico/35.png"))));
		stat.add(new JLabel("100"));
		stat.add(new JLabel("%"));
		scaler.setMaximumSize(new Dimension(50,50));
		add(stat,BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private void setMenu()
	{
		ImageIcon ico=new ImageIcon(getClass().getResource("/res/loading-ani.gif"));
		JMenu mn=new JMenu("Menu");
		mn.add(new JMenuItem(ico));
		menu.add(mn);
		setJMenuBar(menu);
	}
	
	private WorkSpace workspace;
	private JMenuBar menu=new JMenuBar();
	private JToolBar stat=new JToolBar();
	private JToolBar tool=new JToolBar();
	private JComboBox<String> level=new JComboBox<String>();
	private JSplitPane topanel;
	private JPanel lpanel=new JPanel();
	private JSplitPane rpanel;
	private JSplitPane ldpanel;
	private JSplitPane ldlpanel;
	private JList<String> chip=new JList<String>();
	private JLabel info=new JLabel("Circuit v0.1");
	private JSlider scaler=new JSlider(1,100,10);
	private JXTreeTable propview=new JXTreeTable();
	private JTree toolprop;//=new JTree();
	
}
