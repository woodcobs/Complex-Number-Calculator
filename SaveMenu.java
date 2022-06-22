package rimplex;
//package main;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Controls Save information.
 * 
 * @author brantleycervarich
 * @version 26 April 2021
 */
public class SaveMenu implements ActionListener
{
  private JFrame window;
  private String strContinue = "Continue";

  /**
   * default constructor.
   */
  SaveMenu()
  {

  }

  /**
   * builds the swing component.
   * 
   * @param location
   *          where to put it.
   * @param r
   *          resourceBundle
   */
  public void build(final Point location, final ResourceBundle r)
  {
    JButton button = new JButton(r.getString(strContinue));
    button.addActionListener(this);
    strContinue = r.getString(strContinue);

    JTextPane pane = new JTextPane();
    pane.setEditable(false);
    pane.setText(r.getString("FileSaved"));

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
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (ClassNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (InstantiationException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IllegalAccessException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (UnsupportedLookAndFeelException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    window = new JFrame();
    window.setTitle(r.getString("Save"));
    window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    window.getContentPane().add(panel);
    window.setSize(350, 250);
    window.setLocation(location);
    window.setVisible(true);
  }

  @Override
  public void actionPerformed(final ActionEvent e)
  {
    String s = e.getActionCommand();
    if (s.contentEquals(strContinue))
    {
      window.dispose();
    }

  }
}
