package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.JFrame;

public class ENDGAME extends JFrame {
	private JTextArea label2;
	public ENDGAME() {
	this.setSize(new Dimension(1900, 1000));
	
	this.setTitle("Rescue City");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.setLocationRelativeTo(null);
	JPanel panel=new JPanel();
	JLabel label=new JLabel();
	label.setBounds(0, 0, 1000, 1000);
	label.setIcon(new ImageIcon(new ImageIcon("endgame_large.jpg").getImage()
		.getScaledInstance(1900, 1000, Image.SCALE_SMOOTH)));
	 label2=new JTextArea();
	label2.setFont(new Font(Font.MONOSPACED,Font.BOLD,50));
	label2.setBounds(500,100, 700, 100);
	label2.setBackground(Color.white);
	label.add(label2);
	this.validate();
	this.repaint();
	panel.add(label);
    this.getContentPane().add(panel);
    panel.setVisible(true);
	label.setVisible(true);
	label2.setVisible(true);
	this.setVisible(true);
	
	}
	public void updatexas(int x) {
	label2.setText("ENDGAME !!! "+x+" ARE DEAD");
	}
	public static void main(String[]args) {
		ENDGAME e=new ENDGAME();
		
	}
}
