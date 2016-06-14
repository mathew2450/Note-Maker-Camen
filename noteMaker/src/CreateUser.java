import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;




@SuppressWarnings("serial")
public class CreateUser extends JPanel{
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://192.168.2.4:3306/Camen_Info";
	static Properties loginDetails = new Properties();
	private static String USER, PASS;
    JPanel createUPanel = new JPanel();
	Action login, logout, createUser;
    JTextField uName = new JTextField(15);
    JPasswordField pass = new JPasswordField(15);
    JPasswordField passConf = new JPasswordField(15);
    JTextField phoneNum = new JTextField(10);


    public CreateUser() {	
    	
	try (FileReader in = new FileReader("login.properties")) {
	    loginDetails.load(in);} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	USER = loginDetails.getProperty("username");
	PASS = loginDetails.getProperty("password");
        createUserPage();
        add(createUPanel);
    }
    
    public void createUserPage(){
    	JLabel contentPane = new JLabel();
        ImageIcon icon = new ImageIcon("camenLogo.png");
        contentPane.setIcon(icon);
        contentPane.setOpaque(true);
        createUPanel.add(contentPane);
        createUPanel.setBackground(Color.white);
        createUPanel.setOpaque(true);
        createUser = new CreateUserA();
        JButton createU = new JButton(createUser);
        createU.setText("Create User");
        createUPanel.add(createU);        
        createUPanel.add(new JLabel("Username:"));
        createUPanel.add(uName);
        createUPanel.add(new JLabel("Password:"));
        createUPanel.add(pass);
        createUPanel.add(new JLabel("Confirm Password:"));
        createUPanel.add(passConf);
        createUPanel.add(new JLabel("Phone Number:"));
        createUPanel.add(phoneNum);
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
	        System.out.println(hash);
	        addUser(uName.getText(), hash, phoneNum.getText());

	        //Zero out the possible password, for security.
	        Arrays.fill(input, '0');

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
	    
		}
	}
	
	private static String addUser(String username, String hash, String phoneNum){
		Connection conn = null;
		   Statement stmt = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      String sql;
		      //sql = "UPDATE `Employees` SET `pass`= '" + password + "' WHERE `username` = '" + username + "'";
		      //int pu = stmt.executeUpdate(sql);
		      sql = "INSERT INTO `Employees` (`Employee Name`, `Employee Level`, " +
		    		  "`Employee Phone`, `pass`, `username`) VALUES" +
		    		  "('Jimmy Carter', '2', '" + phoneNum + "', '" + hash + "', '" + username + "')";
		      stmt.executeUpdate(sql);
		      //System.out.println(password);
		      //password = BCrypt.hashpw(password, BCrypt.gensalt());
		      stmt.close();
		      conn.close();
		      return hash;
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
		return(null);	
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
