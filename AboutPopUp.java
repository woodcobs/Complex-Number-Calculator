package rimplex;
//package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;

/**
 * Controls About information.
 * 
 * @author cervarbn & woodcobs
 * @version 25 April 2021
 */
public class AboutPopUp implements ActionListener {

	private JFrame window;
	private String Done = "Done";

	/**
	 * default constructor.
	 */
	AboutPopUp() {

	}

	/**
	 * builds the swing component.
	 * 
	 * @param location where to put it.
	 */
	public void build(final Point location, final ResourceBundle r) {
		JButton button = new JButton(r.getString("Done"));
		Done = r.getString("Done");
		button.addActionListener(this);

		JTextPane pane = new JTextPane();
		pane.setEditable(false);
		// pane.setText(" Rimplex Calculator\n\n"
		// + " Version: (2021-05)\n "
		// + "Build: Sprint 3\n Effort: Arduous\n\n(c) Copyright Team25 contributors and
		// others 2021. "
		// + "All rights reserved. Rimplex and the Rimplex logo are trademarks "
		// + "of the Rimplex Foundation. The alteration of Rimplex or the "
		// + "Rimplex logo is not permitted without permission.");
		pane.setText(r.getString("AboutTxtL1") + "\n\n" + r.getString("AboutTxtL2") + "\n   "
				+ r.getString("AboutTxtL3") + "\n   " + r.getString("AboutTxtL4") + "\n\n" + r.getString("AboutTxtL5"));

		JPanel lowPanel = new JPanel(new BorderLayout());
		lowPanel.add(button, BorderLayout.LINE_END);

		JScrollPane scroll = new JScrollPane(pane);

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(lowPanel, BorderLayout.PAGE_END);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// Use the default
		}

		window = new JFrame();
		window.setTitle(r.getString("About"));
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.getContentPane().add(panel);
		window.setSize(350, 250);
		window.setLocation(location);
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.contentEquals(Done)) {
			window.dispose();
		}

	}
}
