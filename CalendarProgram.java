
//Import packages
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
	
	public class CalendarProgram extends JPanel{
	    static JLabel lblMonth, lblYear;
	    static JButton btnPrev, btnNext;
	    static JTable tblCalendar;
	    static JComboBox cmbYear;
	    //static JFrame frmMain;
	    //static Container pane;
	    static DefaultTableModel mtblCalendar; //Table model
	    static JScrollPane stblCalendar; //The scrollpane
	    static JPanel pnlCalendar, pnlDate;
	    static int realYear, realMonth, realDay, currentYear, currentMonth;
	    static JPanel mainPanel = new JPanel();
	    
	    public CalendarProgram(){
	        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
	        catch (ClassNotFoundException e) {}
	        catch (InstantiationException e) {}
	        catch (IllegalAccessException e) {}
	        catch (UnsupportedLookAndFeelException e) {}
	        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	        //Create controls
	        lblMonth = new JLabel ("January");
	        lblYear = new JLabel ("Change year:");
	        cmbYear = new JComboBox();
	        btnPrev = new JButton ("<<");
	        btnNext = new JButton (">>");
	        mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
	        tblCalendar = new JTable(mtblCalendar);
	        stblCalendar = new JScrollPane(tblCalendar);
	        pnlDate = new JPanel();
	        pnlCalendar = new JPanel();
	        //pnlCalendar.setLayout(new BorderLayout());
	        //Register action listeners
	        btnPrev.addActionListener(new btnPrev_Action());
	        btnNext.addActionListener(new btnNext_Action());
	        cmbYear.addActionListener(new cmbYear_Action());
	        //Add controls to pane
	        pnlDate.add(btnPrev);
	        pnlDate.add(lblMonth);
	        pnlDate.add(btnNext);
	        pnlDate.add(lblYear);
	        pnlDate.add(cmbYear);
	        pnlCalendar.add(stblCalendar);
	        //Get real month/year
	        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
	        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
	        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
	        realYear = cal.get(GregorianCalendar.YEAR); //Get year
	        currentMonth = realMonth; //Match month and year
	        currentYear = realYear;
	        //Add headers
	        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
	        for (int i=0; i<7; i++){
	            mtblCalendar.addColumn(headers[i]);
	        }
	        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
	        //No resize/reorder
	        tblCalendar.getTableHeader().setResizingAllowed(false);
	        tblCalendar.getTableHeader().setReorderingAllowed(false);
	        //Single cell selection
	        tblCalendar.setColumnSelectionAllowed(true);
	        tblCalendar.setRowSelectionAllowed(true);
	        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        //Set row/column count
	        tblCalendar.setRowHeight(38);
	        mtblCalendar.setColumnCount(7);
	        mtblCalendar.setRowCount(6);
	        //Populate table
	        for (int i=realYear-100; i<=realYear+100; i++){
	            cmbYear.addItem(String.valueOf(i));
	        }
	        //Refresh calendar
	        refreshCalendar (realMonth, realYear); //Refresh calendar
	        JLabel contentPane = new JLabel();
	        ImageIcon icon = new ImageIcon("camenLogo.png");
	        contentPane.setIcon(icon);
	        contentPane.setOpaque(true);
	    	//mainPanel.setLayout(new GridBagLayout());//custom layout
	    	//GridBagConstraints c = new GridBagConstraints();
	        //c.fill = GridBagConstraints.BOTH;
	        //c.gridx = 0;
	        //c.gridy = 0;
	        mainPanel.add(contentPane, BorderLayout.CENTER);
	        mainPanel.add(pnlCalendar);
	        mainPanel.add(pnlDate);
	        Dimension maxsize = new Dimension(1700, 5000);
	        Dimension minsize = new Dimension(1700, 50);
	        mainPanel.setMaximumSize(maxsize);
	        mainPanel.setMinimumSize(minsize);
	        mainPanel.setPreferredSize(maxsize);
	        mainPanel.setBackground(Color.white);
	        mainPanel.setOpaque(true);
	        JScrollPane scrollPane = new JScrollPane(mainPanel);
	        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	        scrollPane.setForeground(Color.white);
	        add(scrollPane, BorderLayout.CENTER);
	        setBackground(Color.white);
	        this.setForeground(Color.white);
	    }
	    
	    private static void createAndShowGUI() { 
	        JFrame frame = new JFrame("Calendar");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        CalendarProgram newContentPane = new CalendarProgram();
	        newContentPane.setOpaque(true);
	        frame.setContentPane(newContentPane);
	        frame.pack();
	        frame.setVisible(true);
	    }
	    
	    public static void main (String args[]){
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
	    
	    public static void refreshCalendar(int month, int year){
	        //Variables
	        String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	        int nod, som; //Number Of Days, Start Of Month
	        //Allow/disallow buttons
	        btnPrev.setEnabled(true);
	        btnNext.setEnabled(true);
	        if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
	        if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
	        lblMonth.setText(months[month]); //Refresh the month label (at the top)
	        lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
	        cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
	        //Clear table
	        for (int i=0; i<6; i++){
	            for (int j=0; j<7; j++){
	                mtblCalendar.setValueAt(null, i, j);
	            }
	        }
	        //Get first day of month and number of days
	        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
	        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
	        //Draw calendar
	        for (int i=1; i<=nod; i++){
	            int row = new Integer((i+som-2)/7);
	            int column  =  (i+som-2)%7;
	            mtblCalendar.setValueAt(i, row, column);
	        }
	        //Apply renderers
	        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	    }
	    
	    static class tblCalendarRenderer extends DefaultTableCellRenderer{
	        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
	            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
	            if (column == 0 || column == 6){ //Week-end
	                setBackground(new Color(255, 220, 220));
	            }
	            else{ //Week
	                setBackground(new Color(255, 255, 255));
	            }
	            if (value != null){
	                if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
	                    setBackground(new Color(220, 220, 255));
	                }
	            }
	            setBorder(null);
	            setForeground(Color.black);
	            return this; 
	        }
	    }
	    
	    static class btnPrev_Action implements ActionListener{
	        public void actionPerformed (ActionEvent e){
	            if (currentMonth == 0){ //Back one year
	                currentMonth = 11;
	                currentYear -= 1;
	            }
	            else{ //Back one month
	                currentMonth -= 1;
	            }
	            refreshCalendar(currentMonth, currentYear);
	        }
	    }
	    
	    static class btnNext_Action implements ActionListener{
	        public void actionPerformed (ActionEvent e){
	            if (currentMonth == 11){ //Foward one year
	                currentMonth = 0;
	                currentYear += 1;
	            }
	            else{ //Foward one month
	                currentMonth += 1;
	            }
	            refreshCalendar(currentMonth, currentYear);
	        }
	    }
	    
	    static class cmbYear_Action implements ActionListener{
	        public void actionPerformed (ActionEvent e){
	            if (cmbYear.getSelectedItem() != null){
	                String b = cmbYear.getSelectedItem().toString();
	                currentYear = Integer.parseInt(b);
	                refreshCalendar(currentMonth, currentYear);
	            }
	        }
	    }
	}