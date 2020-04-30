package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.CommandCenter;

public class Grid extends JPanel{

	
	
	
	public Grid() {
		
		//addButtonsToArray();
		this.setLayout(new GridLayout(10,10));
		
		
		 this.setVisible(true);
		 this.setBounds(50, 0, 800, 800);
		
		 this.setBackground(Color.gray);
	}
}