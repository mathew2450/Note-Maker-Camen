
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NewButtonCalendar extends JPanel {
CreateApt newApt;
static String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
static GregorianCalendar cal = new GregorianCalendar(); //Create calendar
static String month = months[cal.get(GregorianCalendar.MONTH)];
static int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
static int year = cal.get(GregorianCalendar.YEAR);
String[] lateNotes = new String[100];
static int rows = 6;
static int cols = 7;
private String[] dataValues = new String[42];
Font font = new Font("Comic Sams Ms", Font.BOLD, 18);
static int numLate = 0; 
static String dateSelected;


  public NewButtonCalendar() {
		int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		dataValues = new String[42];
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			dataValues[cols*row+column]=(i + "");
		}
    setLayout(new GridLayout(rows, cols));
    findLateNotes(); 
    for (int i = 0; i < 42; i++) {
      final JButton button = new JButton(dataValues[i]);
      button.setBackground(Color.WHITE);
      button.setFont(font);
      if(button.getText().equals(""))
    	  button.setVisible(false);
      button.setPreferredSize(new Dimension(100, 100));
      add(button);
      if(button.getText().equals(day + ""))
    	  button.setBackground(new Color(152,215,215));
      String date = month + " " + button.getText() + ", " + year;
      for(int j = 0; j < numLate; j++){
	  if(date.equals(lateNotes[j])){
		    System.out.println("found late date " + date);
			button.setBackground(Color.RED);
			j++;
	  }}
      button.addActionListener(
			new ActionListener(){
				public void actionPerformed(ActionEvent event){
					dateSelected = month + " " + button.getText() + ", " + year;
					if(JOptionPane.showConfirmDialog(null, 
							"Create a new appointment for: " + dateSelected + "?", "Create Appointment?",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						JOptionPane.showMessageDialog(null,new CreateApt(),"Information",JOptionPane.INFORMATION_MESSAGE);					
					if(JOptionPane.showConfirmDialog(null, 
							"Create a for for: " + dateSelected + "?", "Create Note?",
		                    JOptionPane.YES_NO_OPTION,
		                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						JOptionPane.showMessageDialog(null,new NoteMaker(),"Information",JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			});
     	}
   }
  
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
		    	  lateNotes[i] = rs.getString("date");
		    	  System.out.println(lateNotes[i]);
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

	
	public static String getDate(){
		System.out.println(dateSelected);
		return dateSelected;
	}

}
