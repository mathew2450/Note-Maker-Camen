import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
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




public class MainPanel {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://192.168.2.4:3306/Camen_Info";
	static Properties loginDetails = new Properties();
	static String USER;
	static String PASS;
    JFrame frame = new JFrame();
    JPanel mainPanel = new JPanel();
    JPanel logoutPanel = new JPanel();
	boolean approved = false;
	Action login, logout;
    JTextField uName = new JTextField(15);
    static JPasswordField pass = new JPasswordField(15);
    JTabbedPane tabbedPane = new JTabbedPane();
    NoteMaker nm = new NoteMaker();
    SigningPage sp = new SigningPage();
    CreateUser cu = new CreateUser();

    public MainPanel() {
    	final Properties loginDetails = new Properties();
    	{
    	try (FileReader in = new FileReader("login.properties")) {
    		System.out.println("prop file completed");
    	    loginDetails.load(in);} catch (FileNotFoundException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	USER = loginDetails.getProperty("username");
    	PASS = loginDetails.getProperty("password");
    	login = new Login();
    	logout = new Logout();
        createLoginPage();
        createLogoutPage();
    	tabbedPane.add("Login", mainPanel);
        frame.getContentPane().add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1700, 1000);
        // frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    public void createLogoutPage(){
    	JLabel contentPane = new JLabel();
        ImageIcon icon = new ImageIcon("camenLogo.png");
        contentPane.setIcon(icon);
        contentPane.setOpaque(true);
        logoutPanel.add(contentPane);
        logoutPanel.setBackground(Color.white);
        logoutPanel.setOpaque(true);
        JButton logoutb = new JButton(logout);
        logoutb.setText("Logout");
        logoutPanel.add(logoutb);
    }
    
    public void createLoginPage(){
    	JLabel contentPane = new JLabel();
        ImageIcon icon = new ImageIcon("camenLogo.png");
        contentPane.setIcon(icon);
        contentPane.setOpaque(true);
        mainPanel.add(contentPane);
        mainPanel.setBackground(Color.white);
        mainPanel.setOpaque(true);
        JButton loginb = new JButton(login);
        loginb.setText("Login");
        mainPanel.add(loginb);
        mainPanel.add(new JLabel("Username:"));
        mainPanel.add(uName);
        mainPanel.add(new JLabel("Password:"));
        mainPanel.add(pass);
    }
    

	@SuppressWarnings("serial")
	public class Login extends AbstractAction{
		public void actionPerformed(ActionEvent e) {
	        char[] input = pass.getPassword();
	        System.out.println(input);
	        String inputs = new String(input);
	        System.out.println(inputs);
	        String hash = BCrypt.hashpw(inputs, BCrypt.gensalt());
	        System.out.println(hash);
	        String origPass = getPassHash(uName.getText(), hash);
	        if (BCrypt.checkpw(inputs, origPass)) {
	            JOptionPane.showMessageDialog(mainPanel,
	                "Success! You typed the right password.");
	            tabbedPane.add("Note Maker", nm);
	            tabbedPane.add("Note Authorizer", sp);
	            tabbedPane.add("Logout", logoutPanel);
	            tabbedPane.add("Create User", cu);
	            frame.getContentPane().add(tabbedPane);	            	
	        } else {
	        	System.out.println(origPass + " " + hash);
	            JOptionPane.showMessageDialog(mainPanel,
	                "Invalid password. Try again.",
	                "Error Message",
	                JOptionPane.ERROR_MESSAGE);
	           
	        }

	        pass.selectAll();
	        pass.requestFocus();
	        pass.setText("");
	    
		}
	}
	
	@SuppressWarnings("serial")
	public class Logout extends AbstractAction{
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(mainPanel,
	                "Logging Out!");
			tabbedPane.remove(nm);
			tabbedPane.remove(sp);
			tabbedPane.remove(logoutPanel);
			tabbedPane.remove(cu);
	    
		}
	}
	
	
	private static String getPassHash(String username, String hash){
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
		      sql = "SELECT pass FROM Employees WHERE username = '" + username + "'";
		      ResultSet rs = stmt.executeQuery(sql);
		      rs.next();
		      hash = rs.getString("pass");
		      //System.out.println(password);
		      //password = BCrypt.hashpw(password, BCrypt.gensalt());
		      rs.close();
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
        new MainPanel();      
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
