import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.sql.*;




@SuppressWarnings("serial")
public class CreateApt extends JPanel{
	static DBConnect dbc = new DBConnect();
    JPanel createAptPanel = new JPanel();
	Action createApt;
    String uName = MainPanel.uName.getText();
    String date = NewButtonCalendar.getDate();
    JTextField clientName = new JTextField();
	String[] time = new String[]{"6:00 AM","6:15 AM","6:30 AM","6:45 AM","7:00 AM","7:15 AM","7:30 AM","7:45 AM",
			"8:00 AM","8:15 AM","8:30 AM","8:45 AM","9:00 AM","9:15 AM","9:30 AM","9:45 AM","10:00 AM","10:15 AM","10:30 				AM","10:45 AM","11:00 AM","11:15 AM","11:30 AM","11:45 AM",
			"12:00 PM","12:15 PM","12:30 PM","12:45 PM","1:00 PM","1:15 PM","1:30 PM","1:45 PM","2:00 PM","2:15 PM","2:30 				PM","2:45 PM","3:00 PM","3:15 PM","3:30 PM","3:45 PM",
			"4:00 PM","4:15 PM","4:30 PM","4:45 PM","5:00 PM","5:15 PM","5:30 PM","5:45 PM","6:00 PM","6:15 PM","6:30 				PM","6:45 PM","7:00 PM","7:15 PM","7:30 PM","7:45 PM",
			"8:00 PM","8:15 PM","8:30 PM","8:45 PM","9:00 PM","9:15 PM","9:30 PM","9:45 PM"};
	JComboBox<String> timeinSelect = new JComboBox<String>(time);
	JComboBox<String> timeoutSelect = new JComboBox<String>(time);


    public CreateApt() {	
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel contentPane = new JLabel();
        ImageIcon icon = new ImageIcon("camenLogo.png");
        contentPane.setIcon(icon);
        contentPane.setOpaque(true);
        contentPane.setAlignmentX(CENTER_ALIGNMENT);
        setAlignmentX(CENTER_ALIGNMENT);
        add(contentPane);
        createUserPage();
        setBackground(Color.white);
        add(createAptPanel);
    }
    
    public void createUserPage(){
    	createAptPanel.setLayout(new BoxLayout(createAptPanel, BoxLayout.PAGE_AXIS));
        Dimension maxsize = new Dimension(200, 400);
        Dimension minsize = new Dimension(200, 50);
        createAptPanel.setMaximumSize(maxsize);
        createAptPanel.setMinimumSize(minsize);
        createAptPanel.setPreferredSize(maxsize);
        createAptPanel.setAlignmentX(CENTER_ALIGNMENT);
        createAptPanel.setBackground(new Color(152,215,215));
        createAptPanel.setOpaque(true);
        createApt = new CreateAptA();
        JButton createU = new JButton(createApt);
        createU.setText("Create Appointment");     
        createAptPanel.add(new JLabel("Client Name:"));
        createAptPanel.add(clientName);
        createAptPanel.add(new JLabel("Time in:"));
        createAptPanel.add(timeinSelect);        
        createAptPanel.add(new JLabel("Time out:"));
        createAptPanel.add(timeoutSelect);
        createAptPanel.add(createU); 

    }
    

	public class CreateAptA extends AbstractAction{
		public void actionPerformed(ActionEvent e) {
			addApt(uName, clientName.getText(), timeinSelect.getSelectedItem().toString(),timeoutSelect.getSelectedItem().toString(), date);
		}
	}
	
	private static void addApt(String username, String cName, String timeIn, String timeOut, String date){
		   Statement stmt = dbc.getStatement();
		   try{
		      String sql;
		      sql = "INSERT INTO `Appointments` (`username`, `Client_Name`, `Date`, `isSigned`, `isApproved`, `isLate`, 				`timein`, `timeout`)VALUES" + "('" + username + "', '" + cName + "', '" + date + "', 1, 1, 1, '" + 				timeIn + "', '" + timeOut + "')";
		      stmt.executeUpdate(sql);
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}//end try	


}
