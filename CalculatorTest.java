package rimplex;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest
{

  @Test
  void testCalculator() throws IOException
  {
    Calculator calc = new Calculator();
    calc.main(null);
    String l = "";
   

    // ActionEvent e = new ActionEvent(l, 0, "About");
    // calc.actionPerformed(e);
    ActionEvent e = new ActionEvent(l, 0, "1");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "2");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "3");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "4");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "5");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "6");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "7");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "8");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "9");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "0");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, ".");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "+");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "-");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "X");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "/");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "%");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "INV");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "Reset");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "Clear");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "=");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "<HTML><b><i>i</i></b></HTML>");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "Conj");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "Exp");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "Polar");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "Frmt");
    calc.actionPerformed(e);
    e = new ActionEvent(l, 0, "Log");
    calc.actionPerformed(e);


  }

}
