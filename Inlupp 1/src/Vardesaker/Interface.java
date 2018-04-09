package Vardesaker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interface extends JFrame implements ActionListener {
	private JTextField fält;
	
	public void actionPerformed(ActionEvent ave) {
		String str =fält.getText();
	}
	
	public Interface () {
		super ("Värdesaker");
		setLayout(new FlowLayout());
		
		JLabel namnLabel = new JLabel("Ange Värdesak:");
		add(namnLabel);
		fält = new JTextField(8);
		add(fält);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
	}
	
	public static void main (String [] args) {
		new Interface ();
	}
}
