/*
 * ******THINGS_TO_ADD******
 * NEED TO SET UP A CHANGE
 * PASSWORD TAB
 * 
 * NEED TO ADD TAB FOR NOTE
 * FIXING WITH INPUT FOR 
 * UNIQUE NOTE ID THAT WILL 
 * AUTOFILL THE OLD NOT INFO
 * 
 * NEED TO IMPLEMENT USER
 * LEVEL RESTRICTIONS
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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




public class MainPanel {
	DBConnect dbc = new DBConnect();
	static int usrLvl = 0; 
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
    ReviseNote rn = new ReviseNote();
	static Connection conn;
	static Statement stmt;

    public MainPanel() {
    	dbc.loginDB();
    	dbc.regDriver();
    	dbc.setConnection();
    	dbc.setStatement();
    	conn = dbc.getConnection();
    	stmt = dbc.getStatement();
    	login = new Login();
    	logout = new Logout();
        createLoginPage();
        createLogoutPage();
    	tabbedPane.add("Login", mainPanel);
        frame.getContentPane().add(tabbedPane);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(frame, 
                    "Are you sure to close this window?", "Really Closing?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                	dbc.CloseConnections();
                	System.exit(0);
                }
            }
        });
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
        contentPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(contentPane);
        mainPanel.setBackground(Color.white);
        mainPanel.setOpaque(true);
        JButton loginb = new JButton(login);
        loginb.setText("Login");
        mainPanel.add(loginb);
        mainPanel.add(new JLabel("Username:"));
        uName.setSize(100, 25);
        mainPanel.add(uName);
        mainPanel.add(new JLabel("Password:"));
        pass.setSize(100, 25);
        mainPanel.add(pass);
        
    }
    

	@SuppressWarnings("serial")
	public class Login extends AbstractAction{
		public void actionPerformed(ActionEvent e) {
	        char[] input = pass.getPassword();
	        String inputs = new String(input);
	        String hash = BCrypt.hashpw(inputs, BCrypt.gensalt());
	        String origPass = getPassHash(uName.getText(), hash);
	        if (BCrypt.checkpw(inputs, origPass)) {
	            JOptionPane.showMessageDialog(mainPanel,
	                "Success! You typed the right password.");
	            if(usrLvl == 1){
	            	tabbedPane.add("Note Maker", nm);
	            	tabbedPane.add("Logout", logoutPanel);
	            	tabbedPane.add("Revise Note", rn);
	            }
	            if(usrLvl == 2){
	            	tabbedPane.add("Note Maker", nm);
	            	tabbedPane.add("Logout", logoutPanel);
	            	tabbedPane.add("Note Authorizer", sp);
	            	tabbedPane.add("Revise Note", rn);
	            }
	            if(usrLvl >= 3){
	            	tabbedPane.add("Note Maker", nm);
	            	tabbedPane.add("Logout", logoutPanel);
	            	tabbedPane.add("Note Authorizer", sp);
	            	tabbedPane.add("Create User", cu);
	            	tabbedPane.add("Revise Note", rn);
	            }
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
			dbc.CloseConnections();
		    frame.dispose();
		}
	}
	
	
	private static String getPassHash(String username, String hash){
		String sql;
		   try{
		      sql = "SELECT * FROM Employees WHERE username = '" + username + "'";
		      ResultSet rs = stmt.executeQuery(sql);
		      rs.next();
		      hash = rs.getString("pass");
		      usrLvl = rs.getInt("Employee Level");
		      //System.out.println(password);
		      //password = BCrypt.hashpw(password, BCrypt.gensalt());
		      rs.close();
		      return hash;
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
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
	