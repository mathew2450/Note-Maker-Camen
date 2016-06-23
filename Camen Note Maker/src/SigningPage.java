import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class SigningPage extends JPanel{

	JPanel mainPanel = new JPanel();
	Action send;
	Action newNote;
	ArrayList<JCheckBox> signed = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> authorized = new ArrayList<JCheckBox>();
	ArrayList<JTextField> clientNames = new ArrayList<JTextField>();
	ArrayList<JTextField> employeeNames = new ArrayList<JTextField>();
	ArrayList<JTextArea> seshFocus = new ArrayList<JTextArea>();
	ArrayList<JTextArea> theraInt = new ArrayList<JTextArea>();
	ArrayList<JTextArea> plandInt = new ArrayList<JTextArea>();
	

	 ArrayList<JFormattedTextField> signitures = new ArrayList<JFormattedTextField>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> timeIn = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> timeOut = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> serviceTypes = new ArrayList<JComboBox>();
	@SuppressWarnings("rawtypes")
	ArrayList<JComboBox> locations = new ArrayList<JComboBox>();
	int clientNum = 0;
	String[] location = new String[]{"Primary Location", "Home Services"};
	JComboBox<String> locationSelect;
	String[] time = new String[]{"5:00 AM","5:15 AM","5:30 AM","5:45 AM","6:00 AM","6:15 AM","6:30 AM","6:45 AM","7:00 AM","7:15 AM","7:30 AM","7:45 AM",
			"8:00 AM","8:15 AM","8:30 AM","8:45 AM","9:00 AM","9:15 AM","9:30 AM","9:45 AM","10:00 AM","10:15 AM","10:30 AM","10:45 AM","11:00 AM","11:15 AM","11:30 AM","11:45 AM",
			"12:00 PM","12:15 PM","12:30 PM","12:45 PM","1:00 PM","1:15 PM","1:30 PM","1:45 PM","2:00 PM","2:15 PM","2:30 PM","2:45 PM","3:00 PM","3:15 PM","3:30 PM","3:45 PM",
			"4:00 PM","4:15 PM","4:30 PM","4:45 PM","5:00 PM","5:15 PM","5:30 PM","5:45 PM","6:00 PM","6:15 PM","6:30 PM","6:45 PM","7:00 PM","7:15 PM","7:30 PM","7:45 PM",
			"8:00 PM","8:15 PM","8:30 PM","8:45 PM","9:00 PM","9:15 PM","9:30 PM","9:45 PM","10:00 PM","10:15 PM","10:30 PM","10:45 PM","11:00 PM","11:15 PM","11:30 PM","11:45 PM","12:00 AM"};
	JComboBox<String> timeSelect;
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
        addNote.setText("Add A New Note");
        JButton sendNote = new JButton(send);
        sendNote.setText("Send Note");
   
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
        Dimension size = new Dimension(1300, 220);
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
        employeeNames.get(clientNum).setEditable(false);
        minipanel.add(employeeNames.get(clientNum));
        
        clientNames.add(clientNum, new JTextField(12));
        clientNames.get(clientNum).setToolTipText("FirstName LastName");
        clientNames.get(clientNum).setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.black),"Client Name",TitledBorder.LEFT,TitledBorder.ABOVE_TOP)); 
        clientNames.get(clientNum).setBackground(new Color(152,215,215));
        clientNames.get(clientNum).setEditable(false);
        minipanel.add(clientNames.get(clientNum));
        
        timeIn.add(clientNum, timeSelect = new JComboBox<String>(time));
        timeIn.get(clientNum).setBorder(BorderFactory.createTitledBorder("Time In"));
        timeIn.get(clientNum).setBackground(new Color(152,215,215));
        timeIn.get(clientNum).setEditable(false);
        minipanel.add(timeIn.get(clientNum));
        
        timeOut.add(clientNum, timeSelect = new JComboBox<String>(time));
        timeOut.get(clientNum).setBorder(BorderFactory.createTitledBorder("Time Out"));
        timeOut.get(clientNum).setBackground(new Color(152,215,215));
        timeOut.get(clientNum).setEditable(false);
        minipanel.add(timeOut.get(clientNum));
        
        locations.add(clientNum, locationSelect = new JComboBox<String>(location));
        locations.get(clientNum).setBorder(BorderFactory.createTitledBorder("Location"));
        locations.get(clientNum).setBackground(new Color(152,215,215));
        locations.get(clientNum).setEditable(false);
        minipanel.add(locations.get(clientNum));
        
        serviceTypes.add(clientNum, serviceTypeSelect = new JComboBox<String>(serviceType));
        serviceTypes.get(clientNum).setBorder(BorderFactory.createTitledBorder("Service Type"));
        serviceTypes.get(clientNum).setBackground(new Color(152,215,215));
        serviceTypes.get(clientNum).setEditable(false);
        minipanel.add(serviceTypes.get(clientNum));
        
        signed.add(clientNum, new JCheckBox("Check Here to Sign"));
        signed.get(clientNum).setBorder(BorderFactory.createLineBorder(Color.black));
        signed.get(clientNum).setBackground(new Color(152,215,215));
        signed.get(clientNum).setEnabled(false);
        minipanel.add(signed.get(clientNum));       
        
        seshFocus.add(clientNum, new JTextArea(5, 30)); 
        seshFocus.get(clientNum).setLineWrap(true);
        seshFocus.get(clientNum).setWrapStyleWord(true);
        seshFocus.get(clientNum).setToolTipText("Please Put Detailed Notes!");
        JScrollPane scrollPane = new JScrollPane(seshFocus.get(clientNum)); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Session Focus"));
        scrollPane.setBackground(new Color(152,215,215));
        seshFocus.get(clientNum).setEditable(false);
        minipanel.add(scrollPane); 
        
        theraInt.add(clientNum, new JTextArea(5, 30)); 
        theraInt.get(clientNum).setLineWrap(true);
        theraInt.get(clientNum).setWrapStyleWord(true);
        theraInt.get(clientNum).setToolTipText("Please Put Detailed Notes!");
        JScrollPane scrollPane2 = new JScrollPane(theraInt.get(clientNum)); 
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane2.setBorder(BorderFactory.createTitledBorder("Theraeputic Intervention"));
        scrollPane2.setBackground(new Color(152,215,215));
        theraInt.get(clientNum).setEditable(false);
        minipanel.add(scrollPane2);
        
        plandInt.add(clientNum, new JTextArea(5, 30)); 
        plandInt.get(clientNum).setLineWrap(true);
        plandInt.get(clientNum).setWrapStyleWord(true);
        plandInt.get(clientNum).setToolTipText("Please Put Detailed Notes!");
        JScrollPane scrollPane3 = new JScrollPane(plandInt.get(clientNum)); 
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane3.setBorder(BorderFactory.createTitledBorder("Planned Intervention"));
        scrollPane3.setBackground(new Color(152,215,215));
        plandInt.get(clientNum).setEditable(false);
        minipanel.add(scrollPane3);
        
        authorized.add(clientNum, new JCheckBox("Check Here to Authorize"));
        authorized.get(clientNum).setBorder(BorderFactory.createLineBorder(Color.black));
        authorized.get(clientNum).setBackground(new Color(152,215,215));
        minipanel.add(authorized.get(clientNum)); 
        
        
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
        JFrame frame = new JFrame("Note Maker");
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
		
		try {
			System.out.println("sent");
			FileReader fileReader;
			fileReader = new FileReader("TEMP_NOTES.txt");
		
		
    	BufferedReader bufferedWriter = new BufferedReader(fileReader);
    	bufferedWriter.readLine();
    	String newNote = bufferedWriter.readLine();
    	
    	String[] prsdString = newNote.split("~");
    	
    	for(int i = 0; i < prsdString.length; i++){
    		System.out.println(i + " " + prsdString[i]);
    	}
		employeeNames.get(0).setText(prsdString[0]);
		clientNames.get(0).setText(prsdString[1]);
		timeIn.get(0).setSelectedItem(prsdString[2]);
		timeOut.get(0).setSelectedItem(prsdString[3]);
		locations.get(0).setSelectedItem(prsdString[4]);
		serviceTypes.get(0).setSelectedItem(prsdString[5]);
		if(prsdString[6].equals(true))
			signed.get(0).setSelected(true);
		else
			signed.get(0).setSelected(false);
		seshFocus.get(0).setText(prsdString[7]);
		theraInt.get(0).setText(prsdString[8]);
		plandInt.get(0).setText(prsdString[9]);  	
    	 	
    	bufferedWriter.close();
    	} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		};//end catch

    }
}
	
	
    /*
     * Adds a new panel for client information
     */
public class NewNote extends AbstractAction{
    public void actionPerformed(ActionEvent e) {
    	mainPanel.add(createClientInfoPanel());
    	mainPanel.revalidate();
    	clientNum++;
    }
    }
	
	
	
	/**
	 * set up to be runnable and start the code
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.macintosh.MacintoshLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

}