package view;

import java.awt.Dimension;
import java.awt.TextArea;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ActiveDisasterPanel extends JPanel{
 private JTextArea  textarea;
 private JButton button = new JButton("HHH");
 public ActiveDisasterPanel () {
	 textarea = new JTextArea();
	 textarea.setEditable(false);
	 textarea.setLineWrap(true);
	 textarea.setWrapStyleWord(true);
	 textarea.setPreferredSize(new Dimension (500,500));
	 JScrollPane scrollPane = new JScrollPane(textarea);
	 this.add(scrollPane);
	 this.setVisible(true);
	 textarea.setText("HI");
 }
public JTextArea getTextarea() {
	return textarea;
}
public void setTextarea(String textarea) {
	this.textarea.setText(textarea);
}
}
