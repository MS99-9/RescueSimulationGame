package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class E2flomELGame extends JPanel{
	   private JButton zehe2t = new JButton("ZEHE2T");
	   

	 public E2flomELGame () {
		 
		 
		 zehe2t.addActionListener(new zehe2tActionListener());
		 this.add(zehe2t);
		 this.setVisible(true);		 
		// this.setBackground(Color.gray);
	 }
		 

	 public class zehe2tActionListener implements ActionListener {

	 	@Override
	 	public void actionPerformed(ActionEvent e) {
	 		NumTurns.turns = 0;
	 		NumTurns.updateTurns();

	 	}

	 }

	}
 

