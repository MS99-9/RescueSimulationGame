package view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Casualtiespanel extends JPanel{
	public int dead = 0;
	private JLabel label = new JLabel();
	
	public Casualtiespanel() {
		 label.setPreferredSize(new Dimension(100, 50));
		 this.add(label);
		 label.setText(" "+dead+" are dead !!!");
		 this.setVisible(true);
		// this.setBackground(Color.gray);
	}

	public void updateTurns(int n) {
		 label.setText(" "+dead+n+" are dead !!!" );
		
	}
}
