import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Arrays;

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

public class MainPanel {
    JFrame frame = new JFrame();
    JPanel mainPanel = new JPanel();
    JPanel logoutPanel = new JPanel();
	boolean approved = false;
	Action login, logout;
    JTextField uName = new JTextField(15);
    JPasswordField pass = new JPasswordField(15);
    JTabbedPane tabbedPane = new JTabbedPane();
    NoteMaker nm = new NoteMaker();
    SigningPage sp = new SigningPage();

    public MainPanel() {
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
	        if (isPasswordCorrect(input)) { 
	            JOptionPane.showMessageDialog(mainPanel,
	                "Success! You typed the right password.");
	            tabbedPane.add("Note Maker", nm);
	            tabbedPane.add("Note Authorizer", sp);
	            tabbedPane.add("Logout", logoutPanel);
	            frame.getContentPane().add(tabbedPane);	            	
	        } else {
	            JOptionPane.showMessageDialog(mainPanel,
	                "Invalid password. Try again.",
	                "Error Message",
	                JOptionPane.ERROR_MESSAGE);
	           
	        }

	        //Zero out the possible password, for security.
	        Arrays.fill(input, '0');

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
	    
		}
	}
	
	private static boolean isPasswordCorrect(char[] input) {
	    boolean isCorrect = true;
	    char[] correctPassword = { 'g', 't', 'i', 'v', 'r', '6' };

	    if (input.length != correctPassword.length) {
	        isCorrect = false;
	    } else {
	        isCorrect = Arrays.equals (input, correctPassword);
	    }

	    //Zero out the password.
	    Arrays.fill(correctPassword,'0');

	    return isCorrect;
	}
	
	public void setApproved(boolean t){
		
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
