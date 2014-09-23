package ui;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.treetable.AbstractTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

import board.chip.Chip;

public class ChipBeanTreeTableModel extends AbstractTreeTableModel {
	public ChipBeanTreeTableModel(Chip chip) throws IntrospectionException
	{
		if(chip==null)throw new NullPointerException();
		root=new Prop(chip);
	}
	
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 4;
	}
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		if(column==0)return "*";
		else if(column==1)return "Property";
		else if(column==2)return "Type";
		else if(column==3)return "Value";
		else throw new IllegalArgumentException();
	}
	@Override
	public Object getValueAt(Object node, int column) {
		// TODO Auto-generated method stub
		if(column==0)return "?";
		else if(column==1)return ((Prop)node).pd.getName();
		else if(column==2)return ((Prop)node).pd.getPropertyType().getName();
		else if(column==3)return ((Prop)node).pr;
		else return null;
	}
	public boolean isCellEditable(Object node,int column)
	{
		if(column==0||column==1||column==2)return false;
		else if(column==3)
		{
			if(((Prop)node).pd.getWriteMethod()==null)return false;
			return node.getClass().isPrimitive();
		}
		else throw new IllegalArgumentException();
	}
	@Override
	public Object getChild(Object parent, int index) {
		// TODO Auto-generated method stub
		try {
			PropertyDescriptor pd;
			pd = Introspector.getBeanInfo(((Prop)parent).pr.getClass()).getPropertyDescriptors()[index];
			Method md=((Prop)parent).pd.getReadMethod();
			if(md!=null)return new Prop(pd,md.invoke(((Prop)parent).pr, null));
			else return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public int getChildCount(Object parent) {
		// TODO Auto-generated method stub
		try {
			return Introspector.getBeanInfo(((Prop)parent).pr.getClass()).getPropertyDescriptors().length;
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		PropertyDescriptor[] p;
		try {
			p = Introspector.getBeanInfo(((Prop)parent).pr.getClass()).getPropertyDescriptors();
			PropertyDescriptor c=((Prop)child).pd;
			for(int i=0;i<p.length;i++)
			{
				if(p.equals(c))return i;
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public boolean isLeaf(Object node)
	{
		if(node==null)return true;
		return ((Prop)node).pr.getClass().isPrimitive();
	}


	@Override
	public int getHierarchicalColumn() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void setValueAt(Object value, Object node, int column) {
		// TODO Auto-generated method stub
		
	}
}
class Prop
{
	Prop(PropertyDescriptor pd,Object obj)
	{
		this.pd=pd;
		this.pr=obj;
	}
	Prop(Object root) throws IntrospectionException
	{
		pd=new PropertyDescriptor(root.getClass().getName(),root.getClass());
		pr=root;
	}
	PropertyDescriptor pd;
	Object pr;
}