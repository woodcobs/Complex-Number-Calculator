package rimplex;
//package main;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Controls Help information.
 * 
 * @author brantleycervarich
 * @version 25 April 2021
 */
public class HelpMenu implements ActionListener {
	private JFrame window;
	String save = "Save";
	String goToForum = "Go To Forum";

	/**
	 * default constructor.
	 */
	HelpMenu() {

	}

	/**
	 * builds the swing component.
	 * 
	 * @param location where to put it.
	 */
	public void build(final Point location, ResourceBundle r) {
		JButton button = new JButton(r.getString("GoToForum"));
		button.addActionListener(this);
		goToForum = r.getString("GoToForum");
		
		save = r.getString("Save");

		JTextPane pane = new JTextPane();
		pane.setEditable(false);
		pane.setText(r.getString("HelpL1"));

		StyledDocument doc = pane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

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
		// push
		window = new JFrame();
		window.setTitle(save);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.getContentPane().add(panel);
		window.setSize(350, 250);
		window.setLocation(location);
		window.setVisible(true);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		String s = e.getActionCommand();
		if (s.contentEquals(goToForum)) {
			//creates desktop to go to website
			Desktop desktop = Desktop.getDesktop();
			URI uri = null;
			try {
				uri = new URI("https://rimplex.weebly.com/");
			} catch (URISyntaxException e1) {
			}

			//browses using desktop and uri
			try {
				desktop.browse(uri);
			} catch (IOException e1) {
			}
			window.dispose();
		}

	}
}
