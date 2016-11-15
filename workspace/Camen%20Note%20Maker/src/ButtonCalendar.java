import java.awt.*;

import java.awt.event.*;
import java.util.GregorianCalendar;

import javax.swing.*;

import javax.swing.table.TableModel;

import javax.swing.table.DefaultTableModel;

import javax.swing.table.*;

class ButtonCalendar extends JFrame

{

	private JPanel topPanel, topPanel1;

	private JTable table;

	private JScrollPane scrollPane, scrollPane1;

	private String[] columnNames = new String[7];

	private String[][] dataValues = new String[6][7];

	JButton button = new JButton();

public ButtonCalendar()

{

setTitle("Button in JTable Cell");

setSize(1000, 700);

topPanel= new JPanel();

topPanel.setLayout(new BorderLayout());

getContentPane().add(topPanel);

columnNames=new String[] {"Sunday" , "Monday" , "Tuesday" , "Wednesday" , "Thursday" , "Friday", "Saturday"};
 GregorianCalendar cal = new GregorianCalendar(); //Create calendar
 int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
 int som = cal.get(GregorianCalendar.DAY_OF_WEEK);

 dataValues = new String[6][7];
 //Draw calendar
 for (int i=1; i<=nod; i++){
     int row = new Integer((i+som-2)/7);
     int column  =  (i+som-2)%7;
     dataValues[row][column]="" + i;
 }
TableModel model=new myTableModel("owntable");

table =new JTable( );

table.setModel(model);

table.setRowHeight(100);

for(int i = 1; i < columnNames.length; i++){
	
table.getColumn(columnNames[i]).setCellRenderer(new ButtonRenderer());

table.getColumn(columnNames[i]).setCellEditor(new ButtonEditor(new JCheckBox()));

}

scrollPane=new JScrollPane(table);

topPanel.add(scrollPane,BorderLayout.CENTER);  

button.addActionListener(

new ActionListener()

{

public void actionPerformed(ActionEvent event)

{

JOptionPane.showMessageDialog(null, table.getSelectedRow());

}

}

);

}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		public ButtonRenderer() {

			setOpaque(true);

		}

public Component getTableCellRendererComponent(JTable table, Object value,

boolean isSelected, boolean hasFocus, int row, int column) {

setText((value == null) ? "" : value.toString());

return this;

}

	}

	class ButtonEditor extends DefaultCellEditor {

		private String label;

		public ButtonEditor(JCheckBox checkBox) {

			super(checkBox);

		}

public Component getTableCellEditorComponent(JTable table, Object value,

boolean isSelected, int row, int column) {

label = (value == null) ? "" : value.toString();

button.setText(label);

return button;

}

		public Object getCellEditorValue() {

			return new String(label);

		}

	}

	public class myTableModel extends DefaultTableModel

	{

		String dat;

JButton button=new JButton("");

		myTableModel(String tname)

		{

			super(dataValues, columnNames);

			dat = tname;

		}

public boolean isCellEditable(int row,int cols)

{

if( dat=="owntable")

{

if(cols==0){return false;}

 }

return true;

 }

	}

	public static void main(String args[])

	{

		ButtonCalendar mainFrame = new ButtonCalendar();

		mainFrame.setVisible(true);

	}

}