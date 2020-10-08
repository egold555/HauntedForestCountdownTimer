package org.golde.hauntedforest.countdowntimer;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class UISettings extends JFrame {

	private static final long serialVersionUID = -1039122382153934957L;

	public UISettings() {
		setSize(185, 144);
		setTitle("Settings");
		getContentPane().setLayout(null);

		JTimeSelectBox spinnerTimeMin = new JTimeSelectBox();
		spinnerTimeMin.setToolTipText("Countdown Min");
		spinnerTimeMin.setBounds(31, 13, 111, 22);
		getContentPane().add(spinnerTimeMin);

		JButton btnNewButton = new JButton("Start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new UICountdown(spinnerTimeMin.getMills());

				UISettings.this.setVisible(false);
				UISettings.this.dispose();
			}
		});
		btnNewButton.setBounds(31, 48, 111, 25);
		getContentPane().add(btnNewButton);

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent w)
			{
				dispose();
			}       
		});

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

	}
}
