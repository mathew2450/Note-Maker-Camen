
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class NewButtonCalendar extends JPanel {
CreateApt newApt;
static String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
String[] lateNotes = new String[100];
static int rows = 6;
static int cols = 7;
private String[] dataValues = new String[42];
Font font = new Font("Comic Sams Ms", Font.BOLD, 18);
static int numLate = 0; 
static String dateSelected;
static NoteMaker note;
static BufferedImage img = null;
static int realDay, realMonth, realYear, currentMonth, currentYear;
final GregorianCalendar calendar = new GregorianCalendar();
@SuppressWarnings("static-access")
final int month = calendar.get(calendar.MONTH);
@SuppressWarnings("static-access")
final int year = calendar.get(calendar.YEAR);
@SuppressWarnings("static-access")
final int day = calendar.get(calendar.DAY_OF_MONTH);

public NewButtonCalendar() {
	  	//set up the array with the numbers of the month correlating to the place they will be on the calendar
	  	//System.out.println(month + " " + day + ", " + year);
	  	GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  = new Integer(i+som-2)%7;
			dataValues[cols*row+column]=(i + "");
		}
		findLateNotes();
		//set up the gridlayout with the right number of rows and columns
    setLayout(new GridLayout(rows, cols));
    	//put the buttons where they need to be by adding them in one at a time into the grid layout
    	//the null values in the array will be set to empty space and the numbered values in the array
    	//will be made buttons with the label of their number. 
    for (int i = 0; i < 42; i++) {
      final JButton button = new JButton(dataValues[i]);
      button.setBackground(Color.WHITE);
      button.setFont(font);
      if(button.getText().equals(""))
    	  button.setVisible(false);
      button.setPreferredSize(new Dimension(100, 100));
      add(button);
      
      //If the number matches the current day then it will be marked with a blue background
      if(button.getText().equals(day + "")){
    	  button.setBackground(new Color(152,215,215));  		    	  
      }
      
      checkDate(button);
      
      //if a button on the calendar is pressed it will invoke the add appointment class, and afterwards 
      //will ask if you would like to make a note for that same appointment. 
      button.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					dateSelected = months[month] + " " + button.getText() + ", " + year;
					if(JOptionPane.showConfirmDialog(null, 
							"Create a new appointment for: " + dateSelected + "?", "Create Appointment?",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						JOptionPane.showMessageDialog(null,new CreateApt(),"Information",JOptionPane.INFORMATION_MESSAGE);					
					if(JOptionPane.showConfirmDialog(null, 
							"Create a note for: " + dateSelected + "?", "Create Note?",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						JOptionPane.showMessageDialog(null,note = new NoteMaker(),"Information",JOptionPane.INFORMATION_MESSAGE);
						}
			    	//find the late notes
					findLateNotes();
					checkDate(button);
					}
				}
			});
     	}
   }
	//check to see the dates from the button match up with the late notes found from earlier 
	public void checkDate(JButton button){ 

	      String date = months[month] + " " + button.getText() + ", " + year;
	      for(int j = 0; j<lateNotes.length; j++)
	      {
		  if(date.equals(lateNotes[j]))
		  		{
			    System.out.println("found late date " + date);
		    	  Image img;
		  		try {
		  			if(button.getToolTipText() == null)
		  				button.setToolTipText(date);
		  			else
		  				button.setToolTipText(button.getToolTipText() + "; " + date);
		  			img = ImageIO.read(new File("lateAccepted.png"));
		  			ImageIcon icon = new ImageIcon(img.getScaledInstance(64, 64, BufferedImage.TYPE_INT_ARGB));
		  			button.setIcon(icon);
		  			button.setVerticalTextPosition(SwingConstants.TOP);
		  		} catch (IOException e) {
		  			e.printStackTrace();
		  		}
		  		}
		  }
	}
  	/*
  	 * TODO:
  	 * Change the way late notes are counted. Right now they are only counted if they are in the current month
  	 * later they should be counted always with a way to find them if they are not in the current month.
  	 * maybe they can be displayed to the right of the calendar or on the bottom, or as a pop up when a button is clicked
  	 */

	/*
	 * Check the table with the notes with the current username to see if they are late or not
	 * if they are then make a note of them. 
	 */
	public void findLateNotes(){
		numLate = 0; 
		System.out.println("finding late notes...");
		int i = 0;
		Statement stmt = MainPanel.dbc.getStatement();
		   try{
		      String sql;
		      sql = "SELECT date FROM Appointments WHERE isLate = 1";
		      ResultSet rs = stmt.executeQuery(sql);
		      while(rs.next()!=false){
		    	  if(rs.getString("date").contains(months[month]));
		    		  lateNotes[i] = rs.getString("date");
		    	  //System.out.println(lateNotes[i]);
		    	  if(lateNotes[i].contains(months[month]))
		    		  numLate++;
		    	  i++;
		      }
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception fe){
		      //Handle errors for Class.forName
		      fe.printStackTrace();
		   }
	}

	//allow for other classes to access the date information so that when appointments and notes are made
	//they can easily and grab it. 
	public static String getDate(){
		//System.out.println(dateSelected);
		return dateSelected;
	}

}
