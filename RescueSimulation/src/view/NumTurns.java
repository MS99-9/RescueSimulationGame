package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NumTurns extends JPanel{
   
   private static JLabel label = new JLabel();
   public static int turns = 0;

 public NumTurns () {
	 label.setPreferredSize(new Dimension(100, 50));
	 this.add(label);
	 updateTurns();
	 this.setVisible(true);
	// this.setBounds(600, 0, 300, 500);
	 
	 
	 this.setBackground(Color.gray);
 }
	 

 public static void updateTurns() {
	 label.setText(" "+turns+" Turns" );
 }

}
