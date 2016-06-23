import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;




@SuppressWarnings("serial")
public class CreateUser extends JPanel{
	static DBConnect dbc = new DBConnect();
    JPanel createUPanel = new JPanel();
	Action createUser;
    JTextField uName = new JTextField(15);
    JTextField fName = new JTextField(15);
    JTextField lName = new JTextField(15);
    String[] level = new String[]{"0","1","2","3","4"};
    JComboBox<String> levelSelect;
    JPasswordField pass = new JPasswordField(15);
    JPasswordField passConf = new JPasswordField(15);
    JTextField phoneNum = new JTextField(10);


    public CreateUser() {	
    	levelSelect = new JComboBox<String>(level);
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
        add(createUPanel);
    }
    
    public void createUserPage(){
    	createUPanel.setLayout(new BoxLayout(createUPanel, BoxLayout.PAGE_AXIS));
        Dimension maxsize = new Dimension(200, 300);
        Dimension minsize = new Dimension(200, 50);
        createUPanel.setMaximumSize(maxsize);
        createUPanel.setMinimumSize(minsize);
        createUPanel.setPreferredSize(maxsize);
        createUPanel.setAlignmentX(CENTER_ALIGNMENT);
        createUPanel.setBackground(new Color(152,215,215));
        createUPanel.setOpaque(true);
        createUser = new CreateUserA();
        JButton createU = new JButton(createUser);
        createU.setText("Create User");
        createUPanel.add(createU);        
        createUPanel.add(new JLabel("First Name:"));
        createUPanel.add(fName);
        createUPanel.add(new JLabel("Last Name:"));
        createUPanel.add(lName);        
        createUPanel.add(new JLabel("Username:"));
        createUPanel.add(uName);
        createUPanel.add(new JLabel("Password:"));
        createUPanel.add(pass);
        createUPanel.add(new JLabel("Confirm Password:"));
        createUPanel.add(passConf);
        createUPanel.add(new JLabel("Phone Number:"));
        createUPanel.add(phoneNum);
        createUPanel.add(new JLabel("Select Level:"));
        createUPanel.add(levelSelect);

    }
    

	public class CreateUserA extends AbstractAction{
		public void actionPerformed(ActionEvent e) {
	        char[] input = pass.getPassword();
	        char[] inputConf = passConf.getPassword();
	        String origPass = new String(input);
	        String passc = new String(inputConf);
	        if(!origPass.equals(passc))
	        {
	        	JOptionPane.showMessageDialog(createUPanel,
		                "Passwords Do Not Match. Try again.",
		                "Error Message",
		                JOptionPane.ERROR_MESSAGE);
		        pass.selectAll();
		        pass.requestFocus();
		        pass.setText("");
		        passConf.selectAll();
		        passConf.requestFocus();
		        passConf.setText("");
	        	return;
	        }
	        String hash = BCrypt.hashpw(origPass, BCrypt.gensalt());
	        addUser(uName.getText(), hash, phoneNum.getText(), fName.getText(), lName.getText(), levelSelect.getSelectedItem().toString());

	        //Zero out the possible password, for security.
	        Arrays.fill(input, '0');
	        JOptionPane.showMessageDialog(createUPanel,
	                "Congrates! User:" + uName.getText() + "has been created!",
	                "User Creation Successful",
	                JOptionPane.INFORMATION_MESSAGE);
	        pass.selectAll();
	        pass.requestFocus();
	        pass.setText("");
	        passConf.selectAll();
	        passConf.requestFocus();
	        passConf.setText("");
	        phoneNum.selectAll();
	        phoneNum.requestFocus();
	        phoneNum.setText("");
	        uName.selectAll();
	        uName.requestFocus();
	        uName.setText("");
	        fName.setText("");
	        lName.setText("");
	        levelSelect.setSelectedItem("0");
		}
	}
	
	private static void addUser(String username, String hash, String phoneNum, String firstName, String lastName, String level){
		   Connection conn = dbc.getConnection();
		   Statement stmt = dbc.getStatement();
		   String name = new String(firstName + " " + lastName);
		   try{
		      String sql;
		      sql = "INSERT INTO `Employees` (`Employee Name`, `Employee Level`, " +
		    		  "`Employee Phone`, `pass`, `username`) VALUES" +
		    		  "('" + name + "', '" + level + "', '" + phoneNum + "', '" + hash + "', '" + username + "')";
		      stmt.executeUpdate(sql);
		      //System.out.println(password);
		      //password = BCrypt.hashpw(password, BCrypt.gensalt());
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try	
	}

    /*
     * Create the GUI and show it. 
     */
    private static void createAndShowGUI() {
        new CreateUser();      
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	}

}
