package rimplex;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AboutPopUpTest
{

  @Test
  void test()
  {
    Locale l = new Locale("en", "US");
    ActionEvent e = new ActionEvent(new String(""), 0, "Done");
    ActionEvent s = new ActionEvent(new String(""), 0, "no");

    ResourceBundle r = ResourceBundle.getBundle("ui.Strings", l);
    Point p = new Point(1, 1);
    AboutPopUp hm = new AboutPopUp();
    hm.build(p, r);
    hm.actionPerformed(e);
    hm.actionPerformed(s);

  }

}
