package route;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RouteEditor extends JFrame {
	private JPanel panel;
  
	public static void main(String[] args) {
		RouteEditor frame = new RouteEditor();
	}
  
	public RouteEditor() {
	    super("Route Editor");
	    this.panel = new JPanel();
	    setDefaultCloseOperation(3);
	    setLayout(new BorderLayout());
	    setSize(700, 250);
	    setResizable(false);
	    this.panel.setLayout((LayoutManager)null);
	    this.panel.setBackground(Color.lightGray);
	    JLabel title = new JLabel("ATC Route Cleaner");
	    getContentPane().add(this.panel, "Center");
	    setVisible(true);
	    this.panel.add(title);
	    title.setFont(new Font("Arial", 1, 20));
	    title.setSize(400, 25);
	    title.setLocation(10, 10);
	    final JTextField route = new JTextField();
	    this.panel.add(route);
	    route.setSize(650, 25);
	    route.setLocation(10, 50);
	    final JTextArea output = new JTextArea();
	    this.panel.add(output);
	    output.setSize(650, 50);
	    output.setLocation(10, 100);
	    JButton reset = new JButton("Reset");
	    this.panel.add(reset);
	    reset.setSize(100, 25);
	    reset.setLocation(300, 175);
	    reset.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            output.setText("");
            route.setText("");
          }
        });
	    route.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	            String badRoute = route.getText();
	            boolean issue = false;
	            int timeOut = 0;
	            while (badRoute.indexOf("DCT ") != -1 || timeOut >= 150) {
	              int index = badRoute.indexOf("DCT ");
	              String beg = badRoute.substring(0, index);
	              String end = "";
	              if (index + 4 < badRoute.length())
	                end = badRoute.substring(index + 4); 
	              badRoute = String.valueOf(beg) + end;
	              timeOut++;
            } 
            if (badRoute.substring(badRoute.length() - 3).equals("DCT"))
            	badRoute = badRoute.substring(0, badRoute.length() - 3); 
            if (timeOut >= 150)
            	issue = true; 
            timeOut = 0;
            while (badRoute.indexOf("/") != -1 || timeOut >= 150) {
	              int index = badRoute.indexOf("/");
	              String beg = badRoute.substring(0, index);
	              String end = "";
	              int count = index + 1;
	              for (int i = index + 1; i < badRoute.length(); i++) {
	            	  if (badRoute.charAt(i) == ' ') {
	            		  count = i;
	            		  break;
	            	  } 
	              } 
	              if (count < badRoute.length())
	            	  end = badRoute.substring(count); 
		              badRoute = String.valueOf(beg) + end;
		              timeOut++;
	            } 
	            if (badRoute.charAt(0) == 'N' && Character.isDigit(badRoute.charAt(1))) {
		              int ind = 1;
		              timeOut = 0;
		              while (badRoute.charAt(ind) != ' ' || timeOut >= 150) {
		            	  ind++;
		            	  timeOut++;
		              } 
		              badRoute = badRoute.substring(ind + 1);
	            } 
	            if (timeOut >= 150)
	            	issue = true; 
		            timeOut = 0;
		            while (badRoute.indexOf(".") != -1 || timeOut >= 150) {
		            	badRoute.replace('.', ' ');
		            	timeOut++;
	            } 
	            if (timeOut >= 150)
	            	issue = true; 
	            if (issue) {
	            	output.append("Error Occured during parsing");
	            } else {
	            	output.append(badRoute);
	            	StringSelection goodRoute = 
	            			new StringSelection(badRoute);
		              Clipboard clip = Toolkit.getDefaultToolkit()
		                .getSystemClipboard();
		              clip.setContents(goodRoute, null);
	            } 
	    	}
        });
	}
}
