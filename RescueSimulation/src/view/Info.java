package view;

import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Info extends JPanel{
 private JTextArea  textarea;
 private JButton button = new JButton("HHH");
 public Info () {
	 textarea = new JTextArea();
	 textarea.setEditable(false);
	 textarea.setLineWrap(true);
	 textarea.setWrapStyleWord(true);
	 textarea.setLineWrap(true);
	 textarea.setWrapStyleWord(true);
	 textarea.setPreferredSize(new Dimension (300,500));
	 JScrollPane scrollPane = new JScrollPane(textarea);
	 this.add(scrollPane);
	 this.setVisible(true);
	
 }
public JTextArea getTextarea() {
	return textarea;
}
public void setTextarea(String t) {
	this.textarea.setText(t);
}
public JButton getButton() {
	return button;
}
public void setButton(JButton button) {
	this.button = button;
}
}
