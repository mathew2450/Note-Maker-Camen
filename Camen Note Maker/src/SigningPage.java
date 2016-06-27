/*
 * ******THINGS_TO_ADD*********
 * NEED TO SET UP A UNIQUE ID
 * FOR EACH OF THE NOTES SO 
 * THAT DUPLICATES ARE NOT 
 * UPLOADED TO THE DB
 * 
 * SET UP SO THAT GETTING THE
 * NEXT NOTE DOESN'T MAKE A NEW
 * BOX, JUST REFILLS OLD ONE
 * 
 * CONFIGURE FOR VARIABLE EMAIL
 * ADDRESSES SO THAT THE CORRECT
 * PERSON IS EMAILED
 * 
 * ADD SIGNITURES FOR THE STAFF
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SigningPage extends JPanel{
	Calendar rightNow = Calendar.getInstance();
	static DBConnect dbc = new DBConnect();
	JPanel mainPanel = new JPanel();
	Action send;
	Action newNote;
	ArrayList<JCheckBox> signed = new ArrayList<JCheckBox>();
	ArrayList<JTextArea> comments = new ArrayList<JTextArea>();
	ArrayList<JTextField> clientNames = new ArrayList<JTextField>();
	ArrayList<JTextField> employeeNames = new ArrayList<JTextField>();
	ArrayList<JTextArea> seshFocus = new ArrayList<JTextArea>();
	ArrayList<JTextArea> futRec = new ArrayList<JTextArea>();
	ArrayList<JTextArea> target = new ArrayList<JTextArea>();
	ArrayList<JTextArea> repProg = new ArrayList<JTextArea>();
	ArrayList<String> dates = new ArrayList<String>();
	ArrayList<Integer> qhs = new ArrayList<Integer>();
	int sig = 0;
	 ArrayList<JFormattedTextField> signitures = new ArrayList<JFormattedTextField>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> timeIn = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> timeOut = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> serviceTypes = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> locations = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> years = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> months = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> days = new ArrayList<JComboBox>();
	int clientNum = 0;
	String[] location = new String[]{"Primary Location", "Home Services"};
	JComboBox<String> locationSelect;
	String[] time = new String[]{"5:00 AM","5:15 AM","5:30 AM","5:45 AM","6:00 AM","6:15 AM","6:30 AM","6:45 AM","7:00 AM","7:15 AM","7:30 AM","7:45 AM",
			"8:00 AM","8:15 AM","8:30 AM","8:45 AM","9:00 AM","9:15 AM","9:30 AM","9:45 AM","10:00 AM","10:15 AM","10:30 AM","10:45 AM","11:00 AM","11:15 AM","11:30 AM","11:45 AM",
			"12:00 PM","12:15 PM","12:30 PM","12:45 PM","1:00 PM","1:15 PM","1:30 PM","1:45 PM","2:00 PM","2:15 PM","2:30 PM","2:45 PM","3:00 PM","3:15 PM","3:30 PM","3:45 PM",
			"4:00 PM","4:15 PM","4:30 PM","4:45 PM","5:00 PM","5:15 PM","5:30 PM","5:45 PM","6:00 PM","6:15 PM","6:30 PM","6:45 PM","7:00 PM","7:15 PM","7:30 PM","7:45 PM",
			"8:00 PM","8:15 PM","8:30 PM","8:45 PM","9:00 PM","9:15 PM","9:30 PM","9:45 PM","10:00 PM","10:15 PM","10:30 PM","10:45 PM","11:00 PM","11:15 PM","11:30 PM","11:45 PM","12:00 AM"};
	JComboBox<String> timeSelect;
	String[] year = new String[]{"1999","2000","2001","2002","2003","2004","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016","2017","2018","2019","2020"};
	JComboBox<String> yearSelect;
	String[] day = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22",
				"23","24","25","26","27","28","29","30","31"};
	JComboBox<String> daySelect;
	String[] month = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
	JComboBox<String> monthSelect;
	String[] serviceType = new String[]{"0-No Service Type","1-Behavior Assessment","2-Behavior Assessment WV", "3-Behavior Therapy, BA",
			"4-Behavior Therapy, BA WV", "5-Behavior Therapy BA, Indirect", "6-Behavior Therapy, Level 1", "7-Behavior Therapy, Level 1 WV", 
			"8-Behavior Therapy, Level 1 Indirect", "9-Behavior Therapy, Level 2", "10-Behavior Therapy, Level 2 WV", "11-Behavior Therapy, Level 2 Indirect", 
			"12-Behavior Therapy, Level 3", "13-Behavior Therapy, Level 3 WV", "14-Behavior Therapy, Level 3 Indirect", "15-Private BA -Applied Behavior" +
					" Assistant-", "16-Private Day Camp", "17-Private Level 1 Analyst -Applied Behavior Analysis Level 1-", "18-Private Level 2", 
					"19-Private Level 3","20-Admin"};
	JComboBox<String> serviceTypeSelect;
	
	public SigningPage() {
		super(new BorderLayout());
        send = new Send();
        newNote = new NewNote();
        
        JLabel contentPane = new JLabel();
        ImageIcon icon = new ImageIcon("camenLogo.png");
        contentPane.setIcon(icon);
        contentPane.setOpaque(true);
        mainPanel.add(contentPane);
        
        JButton addNote = new JButton(newNote);
        addNote.setText("Get Next Note");
        JButton sendNote = new JButton(send);
        sendNote.setText("Finish Authorization");
   
        //BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        //mainPanel.setLayout(layout);
        //mainPanel.add(createYAlignmentExample());
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
        addNote.setBackground(Color.white);
        sendNote.setBackground(Color.white);
        //add(contentPane, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);
        add(addNote, BorderLayout.NORTH);       
        add(sendNote, BorderLayout.SOUTH);
        setBackground(Color.white);
        this.setForeground(Color.white);
	}
	

	protected JPanel createClientInfoPanel() {
        JPanel pane = new JPanel();
        JComponent minipanel = new JPanel();
        Dimension size = new Dimension(1400, 270);
        minipanel.setAlignmentX(LEFT_ALIGNMENT);
        minipanel.setMaximumSize(size);
        minipanel.setPreferredSize(size);
        minipanel.setMinimumSize(size);
        minipanel.setBackground(new Color(152,215,215));
        
        TitledBorder border = new TitledBorder(
                                  new LineBorder(Color.black),
                                  "",
                                  TitledBorder.CENTER,
                                  TitledBorder.BELOW_TOP);
        border.setTitleColor(Color.black);
        minipanel.setBorder(border);
        
        employeeNames.add(clientNum, new JTextField(12));
        employeeNames.get(clientNum).setToolTipText("FirstName LastName");        
        employeeNames.get(clientNum).setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"Employee Name",TitledBorder.LEFT,TitledBorder.ABOVE_TOP)); 
        employeeNames.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(employeeNames.get(clientNum));
        
        clientNames.add(clientNum, new JTextField(12));
        clientNames.get(clientNum).setToolTipText("FirstName LastName");
        clientNames.get(clientNum).setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"Client Name",TitledBorder.LEFT,TitledBorder.ABOVE_TOP)); 
        clientNames.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(clientNames.get(clientNum));
        
        timeIn.add(clientNum, timeSelect = new JComboBox<String>(time));
        timeIn.get(clientNum).setBorder(BorderFactory.createTitledBorder("Time In"));
        timeIn.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(timeIn.get(clientNum));
        
        timeOut.add(clientNum, timeSelect = new JComboBox<String>(time));
        timeOut.get(clientNum).setBorder(BorderFactory.createTitledBorder("Time Out"));
        timeOut.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(timeOut.get(clientNum));
        
        locations.add(clientNum, locationSelect = new JComboBox<String>(location));
        locations.get(clientNum).setBorder(BorderFactory.createTitledBorder("Location"));
        locations.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(locations.get(clientNum));
        
        serviceTypes.add(clientNum, serviceTypeSelect = new JComboBox<String>(serviceType));
        serviceTypes.get(clientNum).setBorder(BorderFactory.createTitledBorder("Service Type"));
        serviceTypes.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(serviceTypes.get(clientNum));
        
        years.add(clientNum, yearSelect = new JComboBox<String>(year));
        years.get(clientNum).setSelectedItem(String.valueOf(rightNow.get(Calendar.YEAR)));
        years.get(clientNum).setBorder(BorderFactory.createTitledBorder("YYYY"));
        years.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(years.get(clientNum));
        
        months.add(clientNum, monthSelect = new JComboBox<String>(month));
        months.get(clientNum).setSelectedItem(String.valueOf(rightNow.get(Calendar.MONTH)));
        months.get(clientNum).setBorder(BorderFactory.createTitledBorder("MM"));
        months.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(months.get(clientNum));
        
        days.add(clientNum, daySelect = new JComboBox<String>(day));
        days.get(clientNum).setSelectedItem(String.valueOf(rightNow.get(Calendar.DAY_OF_MONTH)));
        days.get(clientNum).setBorder(BorderFactory.createTitledBorder("DD"));
        days.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(days.get(clientNum));
        
        signed.add(clientNum, new JCheckBox("Check Here to Sign"));
        signed.get(clientNum).setBorder(BorderFactory.createLineBorder(Color.black));
        signed.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(signed.get(clientNum));       
        
        seshFocus.add(clientNum, new JTextArea(5, 25)); 
        seshFocus.get(clientNum).setLineWrap(true);
        seshFocus.get(clientNum).setWrapStyleWord(true);
        seshFocus.get(clientNum).setToolTipText("Please Put Detailed Notes!");
        JScrollPane scrollPane = new JScrollPane(seshFocus.get(clientNum)); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Session Focus"));
        scrollPane.setBackground(new Color(152,215,215));
        minipanel.add(scrollPane); 
        
        target.add(clientNum, new JTextArea(5, 25)); 
        target.get(clientNum).setLineWrap(true);
        target.get(clientNum).setWrapStyleWord(true);
        target.get(clientNum).setToolTipText("Please Put Detailed Notes!");
        JScrollPane scrollPane2 = new JScrollPane(target.get(clientNum)); 
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane2.setBorder(BorderFactory.createTitledBorder("Target Behaviors for Decceleration"));
        scrollPane2.setBackground(new Color(152,215,215));
        minipanel.add(scrollPane2);
        
        futRec.add(clientNum, new JTextArea(5, 25)); 
        futRec.get(clientNum).setLineWrap(true);
        futRec.get(clientNum).setWrapStyleWord(true);
        futRec.get(clientNum).setToolTipText("Please Put Detailed Notes!");
        JScrollPane scrollPane3 = new JScrollPane(futRec.get(clientNum)); 
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane3.setBorder(BorderFactory.createTitledBorder("Future Recommendations"));
        scrollPane3.setBackground(new Color(152,215,215));
        minipanel.add(scrollPane3);
        
        repProg.add(clientNum, new JTextArea(5, 25)); 
        repProg.get(clientNum).setLineWrap(true);
        repProg.get(clientNum).setWrapStyleWord(true);
        repProg.get(clientNum).setToolTipText("Please Put Detailed Notes!");
        JScrollPane scrollPane4 = new JScrollPane(repProg.get(clientNum)); 
        scrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane4.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane4.setBorder(BorderFactory.createTitledBorder("Replacement Programs"));
        scrollPane4.setBackground(new Color(152,215,215));
        minipanel.add(scrollPane4);
        
        comments.add(clientNum, new JTextArea(5, 100)); 
        comments.get(clientNum).setLineWrap(true);
        comments.get(clientNum).setWrapStyleWord(true);
        comments.get(clientNum).setToolTipText("Please Put Detailed Notes!");
        JScrollPane scrollPane5 = new JScrollPane(comments.get(clientNum)); 
        scrollPane5.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane5.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane5.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane5.setBorder(BorderFactory.createTitledBorder("Revision Comments"));
        scrollPane5.setBackground(new Color(152,215,215));
        minipanel.add(scrollPane5);
        
        pane.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(0,0,0)),
                "Note",
                TitledBorder.LEFT,
                TitledBorder.BELOW_TOP));
        pane.setBackground(new Color(152,215,215));
        pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
        pane.setAlignmentX(LEFT_ALIGNMENT);
        pane.add(minipanel, pane);
        pane.revalidate();
        return pane;
    }
	
    /*
     * Create the GUI and show it. 
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Note Authorizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        SigningPage newContentPane = new SigningPage();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }
    

    
    /*
     * when the button is clicked, the information is read from the GUI and the input files and the schedule is created
     * by matching quarter hours. Then the results are output to a text file.  
     */
public class Send extends AbstractAction{
	public void actionPerformed(ActionEvent e) {		

		   Statement stmt = dbc.getStatement();
		   

		   try{
			   
			   for(int i =0; i< clientNum; i++){
				   if(signed.get(i).isSelected() == true){
					   sig = 1;
				   
				   String dateNum = (years.get(i).getSelectedItem().toString() + "-" +
						   months.get(i).getSelectedItem().toString() + "-" + 
						   days.get(i).getSelectedItem().toString());
			        dates.add(i, dateNum);
			        qhs.add(i, calcHours(timeIn.get(i).getSelectedItem().toString(), timeOut.get(i).getSelectedItem().toString()));
				   
		      String sql;
		      sql = "INSERT INTO `SavedNotes` (`EName`, `CName`, `TIn`, `TOut`, `Loc`, `SType`, `Sign`, `BIPDate`,"+
		    		  " `Date`, `QH`, `Target`, `RepProg`, `SessDesc`, `FutRec`) VALUES" +
		    		  "('" + employeeNames.get(i).getText() + "', '" + clientNames.get(i).getText() + 
		    		  "', '" + timeIn.get(i).getSelectedItem() + "', '" + timeOut.get(i).getSelectedItem() +   
		    		  "', '" + locations.get(i).getSelectedItem().toString() + "', '" + serviceTypes.get(i).getSelectedItem().toString() + 
		    		  "', '" + sig + "',NULL, '" + dates.get(i) + 
		    		  "', '" + qhs.get(i) + "', '" + target.get(i).getText() + "', '" + repProg.get(i).getText() + "', '" + seshFocus.get(i).getText() + 
		    		  "', '" + futRec.get(i).getText() + "')";
		      stmt.executeUpdate(sql);}
			   else{
					   sig = 0;
					   @SuppressWarnings("unused")
					SendEmail email = new SendEmail(comments.get(i).getText());
			   }
				   }
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception fe){
		      //Handle errors for Class.forName
		      fe.printStackTrace();
		   }	
		  }//end try	
}
	
	
    /*
     * Adds a new panel for client information
     */
public class NewNote extends AbstractAction{
    public void actionPerformed(ActionEvent e) {
    	mainPanel.add(createClientInfoPanel());
    	//FILL PANEL WITH MADE NOTE INFO
    	Statement stmt = dbc.getStatement();
		   

		   try{

		      String sql;
		      sql = "SELECT * FROM `NewNotes` WHERE 1";
		      ResultSet rs = stmt.executeQuery(sql);
		      rs.next();
		      employeeNames.get(clientNum).setText(rs.getString("EName"));
		      clientNames.get(clientNum).setText(rs.getString("CName")); 
    		  timeIn.get(clientNum).setSelectedItem(rs.getString("TIn"));
    		  timeOut.get(clientNum).setSelectedItem(rs.getString("TOut"));
    		  locations.get(clientNum).setSelectedItem(rs.getString("Loc"));
    		  serviceTypes.get(clientNum).setSelectedItem(rs.getString("SType"));
    		  target.get(clientNum).setText(rs.getString("Target"));
    		  repProg.get(clientNum).setText(rs.getString("RepProg"));
    		  seshFocus.get(clientNum).setText(rs.getString("SessDesc")); 
    		  futRec.get(clientNum).setText(rs.getString("FutRec"));
			   
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception fe){
		      //Handle errors for Class.forName
		      fe.printStackTrace();
		   }
    	mainPanel.revalidate();
    	clientNum++;
    }
    }
	

/*
 * calculates the number of quarter hours for the given time frame
 */
public int calcHours(String sTime, String eTime){    	
	sTime = sTime.substring(0, 4).trim();
	eTime = eTime.substring(0, 4).trim();
	WorkTime startTime = new WorkTime(sTime);
	WorkTime endTime = new WorkTime(eTime);
	if(endTime.hrs <= 9)
		endTime.hrs += 12;
	if(startTime.hrs < 6)
		startTime.hrs += 12;
	int total = subTimes(startTime, endTime);//
	return(total);
}

/*
 * subtracts the start time from the end time to determine number of quarter hours
 */
public int subTimes(WorkTime startTime, WorkTime endTime){

	int total = endTime.hrs - startTime.hrs;
	int mins = 0; 
	if(endTime.mins >= startTime.mins)
		mins = endTime.mins - startTime.mins;
	else{
		mins = (60 - startTime.mins) + endTime.mins;
		total--;
	}
	total = (total*4)+(mins/15);
	//System.out.println(endTime.hrs + " - " + startTime.hrs);
	return(total);
}
	
	
	/**
	 * set up to be runnable and start the code
	 */
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

}
