package view;

import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Log extends JPanel{
 private JTextArea  textarea;
 
 public Log () {
	 textarea = new JTextArea();
	 textarea.setEditable(false);
	 textarea.setLineWrap(true);
	 textarea.setWrapStyleWord(true);
	 textarea.setPreferredSize(new Dimension (300,400));
	 JScrollPane scrollPane = new JScrollPane(textarea);
	 scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	 this.add(scrollPane);
	 this.setVisible(true);
	
 }
public JTextArea getTextarea() {
	return textarea;
}
public void setTextarea(String textarea) {
	this.textarea.setText(textarea);
}
}
