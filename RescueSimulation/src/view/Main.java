package view;

import java.awt.*;

import javax.swing.*;

import controller.CommandCenter;

public class Main extends JFrame {

	public Main(Grid g,Info info,UnitsPanel unitsPanel, Log log, ActiveDisasterPanel adp,NumTurns numTurns,Respond respond,Casualtiespanel c) {
		
		
		this.setSize(new Dimension(1900, 1000));
		this.setVisible(true);
		this.setTitle("Rescue City");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.validate();
		this.repaint();
		
		numTurns.setBounds(700, 850, 150, 100);

		
		c.setBounds(50, 850, 100, 50);

		E2flomELGame e = new E2flomELGame();
		e.setBounds(400, 850, 100, 50);
		
		
		respond.setBounds(250, 850, 100, 50);

		JPanel Centerpanel = new JPanel();
		Centerpanel.setLayout(null);
		this.getContentPane().add(Centerpanel, BorderLayout.CENTER);
		Centerpanel.setVisible(true);
		Centerpanel.add(g);
		Centerpanel.add(numTurns);
		Centerpanel.add(c);
		Centerpanel.add(e);
		Centerpanel.add(respond);

		
		log.setBounds(10, 500, 400, 400);

		
		info.setBounds(10, 10, 400, 400);

		JPanel left = new JPanel();
		this.getContentPane().add(left, BorderLayout.WEST);
		left.setVisible(true);
		left.setPreferredSize(new Dimension(500, 1000));
		// left.setBackground(Color.blue);
	
		left.add(info);
		left.add(log);

		
		adp.setBounds(10, 800, 400, 400);

	
		unitsPanel.setBounds(10, 10, 400, 400);

		JPanel right = new JPanel();
		this.getContentPane().add(right, BorderLayout.EAST);
		right.setVisible(true);
		right.setPreferredSize(new Dimension(500, 1000));
		// right.setBackground(Color.blue);
		right.add(unitsPanel);
		right.add(adp);

	}
	public static void main(String[]args) {
		
	}

}
