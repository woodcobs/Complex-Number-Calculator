package rimplex;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.print.*;

import javax.swing.event.*;

/**
 * Builds the calculator and handles events.
 * 
 * @author team25
 * @version 27 April 2021
 */
public class Calculator extends JFrame
    implements ActionListener, WindowListener, ComponentListener, Printable
{
  private Locale l = new Locale("en", "US");
  private ResourceBundle r = ResourceBundle.getBundle("ui.Strings", l);
  private int lang = 1;
  private ArrayList<String> historyArr = new ArrayList<String>();
  private Boolean polar = false;
  private AboutPopUp aboutInfo = new AboutPopUp();
  private SaveMenu saveIt = new SaveMenu();
  private HelpMenu helpMen = new HelpMenu();

  // the text field for our input
  private JMenuBar menuBar;
  private JMenu settings, languageMenu, file, rimplex, help;
  private JMenuItem english, spanish, german, print, save, about, forum;
  private JTextField tfield;
  private JLabel previous, spacer;
  private JButton oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton,
      eightButton, nineButton, zeroButton, clearButton, resetButton, plusButton, minusButton,
      divideButton, multiplyButton, decimalButton, posNegButton, equalsButton, complexButton,
      historyButton, backButton, openParenButton, closeParenButton, inverseButton, histButtonClose,
      conj, sqrt, exp, log, frmt, polarButton;
  // test push
  // the container for our buttons
  private Container cont;
  private JPanel textpanel, historypanel, numberpanel, buttonpanel, spacepanel;
  private JPanel eastPanel = new JPanel();
  private JPanel centerPanel = new JPanel();
  private JList list;
  private DefaultListModel listModel = new DefaultListModel();
  private JScrollPane pane;
  private JWindow histWind;
  private WindowListener windlisten;

  // The first and second equations
  // imageee
  private ComplexNumber cn1, cn2, cnEval;
  private char op = '+';
  private boolean hasFirst, hasSecond = false;

  // constructs a calculator object
  /**
   * default constructor.
   * 
   * @throws IOException
   */
  Calculator() throws IOException
  {

    menuBar = new JMenuBar();

    settings = new JMenu(r.getString("Settings"));
    languageMenu = new JMenu(r.getString("Languages"));
    rimplex = new JMenu("Rimplex");
    file = new JMenu(r.getString("File"));
    help = new JMenu(r.getString("Help"));

    english = new JMenuItem("English");
    english.addActionListener(this);
    languageMenu.add(english);

    spanish = new JMenuItem("Spanish");
    spanish.addActionListener(this);
    languageMenu.add(spanish);

    german = new JMenuItem("German");
    german.addActionListener(this);
    languageMenu.add(german);

    about = new JMenuItem(r.getString("About"));
    about.addActionListener(this);
    rimplex.add(about);

    save = new JMenuItem(r.getString("Save"));
    save.addActionListener(this);
    file.add(save);

    print = new JMenuItem(r.getString("Print"));
    print.addActionListener(this);
    file.add(print);

    forum = new JMenuItem(r.getString("Forum"));
    forum.addActionListener(this);
    help.add(forum);

    settings.add(languageMenu);

    menuBar.add(rimplex);
    menuBar.add(file);
    menuBar.add(settings);
    menuBar.add(help);

    this.setJMenuBar(menuBar);

    cont = getContentPane();
    cont.setLayout(new BorderLayout(5, 10));
    cont.setBackground(Color.LIGHT_GRAY);
    tfield = new JTextField(25);
    tfield.setHorizontalAlignment(SwingConstants.RIGHT);
    tfield.setBackground(new Color(203, 230, 245));
    tfield.setBorder(BorderFactory.createLineBorder(new Color(203, 230, 245), 5));

    // allows for keyboard input
    tfield.addKeyListener(new KeyAdapter()
    {
      public void keyTyped(KeyEvent keyevent)
      {
        char c = keyevent.getKeyChar();

        if (c >= '0' && c <= '9')
        {
        }
        else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '.' || c == '=' || c == 'i'
            || c == '(' || c == ')')
        {
        }
        else
        {
          keyevent.consume();
        }
      }
    });
    // this is the panel that contains the input
    textpanel = new JPanel();
    textpanel.setLayout(new GridLayout(3, 1, 0, 0));
    textpanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
    textpanel.setBackground(Color.LIGHT_GRAY);
    previous = new JLabel();
    previous.setHorizontalAlignment(SwingConstants.RIGHT);
    previous.setOpaque(true);
    previous.setBorder(BorderFactory.createLineBorder(new Color(203, 230, 245), 2));

    spacer = new JLabel();
    spacer.setOpaque(true);
    spacer.setBackground(Color.DARK_GRAY);
    spacepanel = new JPanel();
    spacepanel.setLayout(new GridBagLayout());
    spacepanel.setBackground(Color.LIGHT_GRAY);
    spacepanel.add(spacer);

    BufferedImage wPic = ImageIO.read(this.getClass().getResource("rimplex.png"));
    JLabel wIcon = new JLabel(new ImageIcon(wPic));
    wIcon.setHorizontalAlignment(SwingConstants.LEFT);
    previous.setBackground(new Color(203, 230, 245));

    histWind = new JWindow();
    histWind.setBackground(new Color(203, 230, 245));
    histWind.setForeground(new Color(203, 230, 245));
    histWind.setAlwaysOnTop(true);
    eastPanel = new JPanel();

    textpanel.add(wIcon);

    textpanel.add(previous);
    textpanel.add(tfield);

    // this panel contains buttons such as = and clear
    buttonpanel = new JPanel();
    buttonpanel.setLayout(new GridLayout(5, 5, 2, 2));

    // this panel contains our operators
    historypanel = new JPanel();
    historypanel.setLayout(new GridBagLayout());
    historypanel.setBackground(Color.LIGHT_GRAY);

    // this panel contains the number buttons
    numberpanel = new JPanel();
    numberpanel.setLayout(new GridBagLayout());
    numberpanel.setBackground(Color.LIGHT_GRAY);
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;

    // places the number buttons inside the numberpanel
    c.insets = new Insets(10, 10, 0, 0);

    posNegButton = new JButton("(-)");
    posNegButton.setPreferredSize(new Dimension(50, 50));
    posNegButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    posNegButton.setForeground(new Color(179, 179, 71));
    posNegButton.setBackground(Color.LIGHT_GRAY);
    c.weightx = 0.5;
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    numberpanel.add(posNegButton, c);
    posNegButton.addActionListener(this);

    c.insets = new Insets(10, 10, 0, 0);

    clearButton = new JButton(r.getString("Clear"));
    clearButton.setOpaque(true);
    clearButton.setPreferredSize(new Dimension(50, 50));
    clearButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    clearButton.setForeground(new Color(179, 179, 71));
    clearButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 1;
    numberpanel.add(clearButton, c);
    clearButton.addActionListener(this);

    backButton = new JButton("<~");
    backButton.setPreferredSize(new Dimension(50, 50));
    backButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    backButton.setForeground(new Color(179, 179, 71));
    backButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 2;
    c.gridy = 0;
    numberpanel.add(backButton, c);
    backButton.addActionListener(this);

    plusButton = new JButton("+");
    plusButton.setPreferredSize(new Dimension(50, 50));
    plusButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    plusButton.setForeground(new Color(54, 158, 130));
    plusButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 3;
    c.gridy = 0;
    numberpanel.add(plusButton, c);
    plusButton.setMnemonic(KeyEvent.VK_EQUALS);
    plusButton.addActionListener(this);

    resetButton = new JButton(r.getString("Reset"));
    resetButton.setPreferredSize(new Dimension(50, 50));
    resetButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    resetButton.setForeground(new Color(54, 158, 130));
    resetButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 4;
    c.gridy = 0;
    numberpanel.add(resetButton, c);
    resetButton.addActionListener(this);

    conj = new JButton(r.getString("Conj"));
    conj.setPreferredSize(new Dimension(50, 50));
    conj.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    conj.setForeground(new Color(54, 158, 130));
    conj.setBackground(Color.LIGHT_GRAY);
    c.gridx = 5;
    c.gridy = 0;
    numberpanel.add(conj, c);
    conj.addActionListener(this);

    oneButton = new JButton("1");
    oneButton.setPreferredSize(new Dimension(50, 50));
    oneButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    oneButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 0;
    c.gridy = 1;
    numberpanel.add(oneButton, c);
    oneButton.addActionListener(this);

    twoButton = new JButton("2");
    twoButton.setPreferredSize(new Dimension(50, 50));
    twoButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    twoButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 1;
    c.gridy = 1;
    numberpanel.add(twoButton, c);
    twoButton.addActionListener(this);

    threeButton = new JButton("3");
    threeButton.setPreferredSize(new Dimension(50, 50));
    threeButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    threeButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 2;
    c.gridy = 1;
    numberpanel.add(threeButton, c);
    threeButton.addActionListener(this);

    minusButton = new JButton("-");
    minusButton.setPreferredSize(new Dimension(50, 50));
    minusButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    minusButton.setForeground(new Color(54, 158, 130));
    minusButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 3;
    c.gridy = 1;
    numberpanel.add(minusButton, c);
    minusButton.setMnemonic(KeyEvent.VK_0);
    minusButton.addActionListener(this);

    inverseButton = new JButton(r.getString("INV"));
    inverseButton.setPreferredSize(new Dimension(50, 50));
    inverseButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    inverseButton.setForeground(new Color(54, 158, 130));
    inverseButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 4;
    c.gridy = 1;
    numberpanel.add(inverseButton, c);
    inverseButton.addActionListener(this);

    sqrt = new JButton(r.getString("Sqrt"));
    sqrt.setPreferredSize(new Dimension(50, 50));
    sqrt.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    sqrt.setForeground(new Color(54, 158, 130));
    sqrt.setBackground(Color.LIGHT_GRAY);
    c.gridx = 5;
    c.gridy = 1;
    numberpanel.add(sqrt, c);
    sqrt.addActionListener(this);

    fourButton = new JButton("4");
    fourButton.setPreferredSize(new Dimension(50, 50));
    fourButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    fourButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 0;
    c.gridy = 2;
    numberpanel.add(fourButton, c);
    fourButton.addActionListener(this);

    fiveButton = new JButton("5");
    fiveButton.setPreferredSize(new Dimension(50, 50));
    fiveButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    fiveButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 1;
    c.gridy = 2;
    numberpanel.add(fiveButton, c);
    fiveButton.addActionListener(this);

    sixButton = new JButton("6");
    sixButton.setPreferredSize(new Dimension(50, 50));
    sixButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    sixButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 2;
    c.gridy = 2;
    numberpanel.add(sixButton, c);
    sixButton.addActionListener(this);

    multiplyButton = new JButton("X");
    multiplyButton.setPreferredSize(new Dimension(50, 50));
    multiplyButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    multiplyButton.setForeground(new Color(54, 158, 130));
    multiplyButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 3;
    c.gridy = 2;
    numberpanel.add(multiplyButton, c);
    multiplyButton.setMnemonic(KeyEvent.VK_8);
    multiplyButton.addActionListener(this);

    openParenButton = new JButton("(");
    openParenButton.setPreferredSize(new Dimension(50, 50));
    openParenButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    openParenButton.setForeground(new Color(54, 158, 130));
    openParenButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 4;
    c.gridy = 2;
    numberpanel.add(openParenButton, c);
    openParenButton.addActionListener(this);

    exp = new JButton(r.getString("Exp"));
    exp.setPreferredSize(new Dimension(50, 50));
    exp.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    exp.setForeground(new Color(54, 158, 130));
    exp.setBackground(Color.LIGHT_GRAY);
    c.gridx = 5;
    c.gridy = 2;
    numberpanel.add(exp, c);
    exp.addActionListener(this);

    sevenButton = new JButton("7");
    sevenButton.setPreferredSize(new Dimension(50, 50));
    sevenButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    sevenButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 0;
    c.gridy = 3;
    numberpanel.add(sevenButton, c);
    sevenButton.addActionListener(this);

    eightButton = new JButton("8");
    eightButton.setPreferredSize(new Dimension(50, 50));
    eightButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    eightButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 1;
    numberpanel.add(eightButton, c);
    eightButton.addActionListener(this);

    nineButton = new JButton("9");
    nineButton.setPreferredSize(new Dimension(50, 50));
    nineButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    nineButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 2;
    numberpanel.add(nineButton, c);
    nineButton.addActionListener(this);

    divideButton = new JButton("%");
    divideButton.setPreferredSize(new Dimension(50, 50));
    divideButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    divideButton.setForeground(new Color(54, 158, 130));
    divideButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 3;
    numberpanel.add(divideButton, c);
    divideButton.setMnemonic(KeyEvent.VK_SLASH);
    divideButton.addActionListener(this);

    closeParenButton = new JButton(")");
    closeParenButton.setPreferredSize(new Dimension(50, 50));
    closeParenButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    closeParenButton.setForeground(new Color(54, 158, 130));
    closeParenButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 4;
    numberpanel.add(closeParenButton, c);
    closeParenButton.addActionListener(this);

    log = new JButton(r.getString("Log"));
    log.setPreferredSize(new Dimension(50, 50));
    log.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    log.setForeground(new Color(54, 158, 130));
    log.setBackground(Color.LIGHT_GRAY);
    c.gridx = 5;
    c.gridy = 3;
    numberpanel.add(log, c);
    log.addActionListener(this);

    zeroButton = new JButton("0");
    zeroButton.setPreferredSize(new Dimension(50, 50));
    zeroButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    zeroButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 0;
    c.gridy = 4;
    numberpanel.add(zeroButton, c);
    zeroButton.addActionListener(this);

    complexButton = new JButton("<HTML><b><i>i</i></b></HTML>");
    complexButton.setPreferredSize(new Dimension(50, 50));
    complexButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    complexButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 1;
    numberpanel.add(complexButton, c);
    complexButton.addActionListener(this);

    equalsButton = new JButton("=");
    equalsButton.setPreferredSize(new Dimension(50, 50));
    equalsButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    equalsButton.setForeground(new Color(54, 158, 130));
    equalsButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 2;
    numberpanel.add(equalsButton, c);
    equalsButton.setMnemonic(KeyEvent.VK_ENTER);
    equalsButton.addActionListener(this);

    decimalButton = new JButton(".");
    decimalButton.setPreferredSize(new Dimension(50, 50));
    decimalButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    decimalButton.setForeground(new Color(54, 158, 130));
    decimalButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 3;
    numberpanel.add(decimalButton, c);
    decimalButton.addActionListener(this);

    frmt = new JButton(r.getString("Frmt"));
    frmt.setPreferredSize(new Dimension(50, 50));
    frmt.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    frmt.setForeground(new Color(54, 158, 130));
    frmt.setBackground(Color.LIGHT_GRAY);
    c.gridx = 4;
    c.gridy = 4;
    numberpanel.add(frmt, c);
    frmt.addActionListener(this);

    polarButton = new JButton("Polar");
    polarButton.setPreferredSize(new Dimension(50, 50));
    polarButton.setBorder(BorderFactory.createLineBorder(Color.black, 2));
    polarButton.setForeground(new Color(54, 158, 130));
    polarButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 5;
    c.gridy = 4;
    numberpanel.add(polarButton, c);
    polarButton.addActionListener(this);

    historyButton = new JButton(">");
    historyButton.setPreferredSize(new Dimension(20, 40));
    historyButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
    historyButton.setBackground(Color.LIGHT_GRAY);
    c.gridx = 0;
    c.gridy = 2;
    historypanel.add(historyButton, c);
    historyButton.addActionListener(this);

    histButtonClose = new JButton("<");
    histButtonClose.setPreferredSize(new Dimension(20, 40));
    histButtonClose.setBorder(BorderFactory.createLineBorder(new Color(203, 230, 245), 2));
    histButtonClose.setBackground(new Color(203, 230, 245));
    c.gridx = 0;
    c.gridy = 2;
    histButtonClose.addActionListener(this);

    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception e)
    {
    }

    // sets the position of each panel in the frame
    cont.add("Center", numberpanel);
    cont.add("North", textpanel);
    cont.add("East", historypanel);
    cont.add("South", spacepanel);
    addComponentListener(this);
    addWindowListener(this);

    // cont.add("East", oppanel);
    // cont.add("South", buttonpanel);

    // terminates the program when the x is clicked
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  /**
   * provides animation for the hist tab.
   * 
   * @param isOpening
   *          is opening if true
   * @throws InterruptedException
   */
  public void animate(final boolean isOpening) throws InterruptedException
  {
    int height = (int) ((zeroButton.getLocationOnScreen().getY()
        - posNegButton.getLocationOnScreen().getY()) + zeroButton.getHeight());
    if (isOpening)
    {
      histWind.setSize(0, height);
      histWind.setSize((int) (numberpanel.getWidth() * .1), height);
      histWind.setVisible(true);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .2), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .3), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .4), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .5), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .6), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .7), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .8), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .9), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth()), height);
    }
    else
    {
      histWind.setSize((int) (numberpanel.getWidth() * .9), height);
      histWind.setVisible(true);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .8), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .7), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .6), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .5), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .4), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .3), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .2), height);
      Thread.sleep(25);
      histWind.setSize((int) (numberpanel.getWidth() * .1), height);
      Thread.sleep(25);
      histWind.setVisible(false);
    }
  }

  @Override
  // this is the method that tells the buttons what to do
  public void actionPerformed(final ActionEvent e)
  {

    String s = e.getActionCommand();
    if (s.contentEquals(r.getString("About")))
    {
      aboutInfo.build(posNegButton.getLocationOnScreen(), r);
    }
    if (s.contentEquals(r.getString("Save")))
    {
      try
      {
        write();
        saveIt.build(posNegButton.getLocationOnScreen(), r);
      }
      catch (IOException e1)
      {
        System.out.println(r.getString("FailedToWrite"));
      }

    }
    if (s.contentEquals(r.getString("Print")))
    {
      PrinterJob job = PrinterJob.getPrinterJob();
      job.setPrintable(this);
      boolean ok = job.printDialog();
      if (ok)
      {
        try
        {
          job.print();
        }
        catch (PrinterException ex)
        {
          System.out.print("Job did not successfully complete");
        }
      }
    }
    if (s.contentEquals(r.getString("Forum")))
    {
      helpMen.build(posNegButton.getLocationOnScreen(), r);
      // push
    }
    if (s.contentEquals("English"))
    {
      lang = 1;
      System.out.println("The language is now English");
      setLanguage(lang);
      settings.setText(r.getString("Settings"));
      help.setText(r.getString("Help"));
      about.setText(r.getString("About"));
      file.setText(r.getString("File"));
      save.setText(r.getString("Save"));
      print.setText(r.getString("Print"));
      forum.setText(r.getString("Forum"));
      languageMenu.setText(r.getString("Languages"));
      inverseButton.setText(r.getString("INV"));
      inverseButton.addActionListener(this);
      sqrt.setText(r.getString("Sqrt"));
      sqrt.addActionListener(this);
      conj.setText(r.getString("Conj"));
      conj.addActionListener(this);
      log.setText(r.getString("Log"));
      log.addActionListener(this);
      exp.setText(r.getString("Exp"));
      exp.addActionListener(this);
      frmt.setText(r.getString("Frmt"));
      frmt.addActionListener(this);
      clearButton.setText(r.getString("Clear"));
      clearButton.addActionListener(this);
      resetButton.setText(r.getString("Reset"));
      resetButton.addActionListener(this);
    }
    else if (s.contentEquals("Spanish"))
    {
      lang = 2;
      System.out.println("The language is now Spanish");
      setLanguage(lang);
      settings.setText(r.getString("Settings"));
      help.setText(r.getString("Help"));
      about.setText(r.getString("About"));
      file.setText(r.getString("File"));
      save.setText(r.getString("Save"));
      print.setText(r.getString("Print"));
      forum.setText(r.getString("Forum"));
      languageMenu.setText(r.getString("Languages"));
      inverseButton.setText(r.getString("INV"));
      inverseButton.addActionListener(this);
      sqrt.setText(r.getString("Sqrt"));
      sqrt.addActionListener(this);
      conj.setText(r.getString("Conj"));
      conj.addActionListener(this);
      log.setText(r.getString("Log"));
      log.addActionListener(this);
      exp.setText(r.getString("Exp"));
      exp.addActionListener(this);
      frmt.setText(r.getString("Frmt"));
      frmt.addActionListener(this);
      clearButton.setText(r.getString("Clear"));
      clearButton.addActionListener(this);
      resetButton.setText(r.getString("Reset"));
      resetButton.addActionListener(this);
    }
    else if (s.contentEquals("German"))
    {
      lang = 3;
      System.out.println("The language is now German");
      setLanguage(lang);
      settings.setText(r.getString("Settings"));
      help.setText(r.getString("Help"));
      about.setText(r.getString("About"));
      file.setText(r.getString("File"));
      save.setText(r.getString("Save"));
      print.setText(r.getString("Print"));
      forum.setText(r.getString("Forum"));
      languageMenu.setText(r.getString("Languages"));
      inverseButton.setText(r.getString("INV"));
      inverseButton.addActionListener(this);
      sqrt.setText(r.getString("Sqrt"));
      sqrt.addActionListener(this);
      conj.setText(r.getString("Conj"));
      conj.addActionListener(this);
      log.setText(r.getString("Log"));
      log.addActionListener(this);
      exp.setText(r.getString("Exp"));
      exp.addActionListener(this);
      frmt.setText(r.getString("Frmt"));
      frmt.addActionListener(this);
      clearButton.setText(r.getString("Clear"));
      clearButton.addActionListener(this);
      resetButton.setText(r.getString("Reset"));
      resetButton.addActionListener(this);

    }

    if (s.equals(r.getString("INV")))
    {
      // if tfield is empty print error
      if (tfield.getText().isEmpty() || !(tfield.getText().contains("i")))
      {
        tfield.setText("");
        previous.setText(r.getString("notInv"));
      }
      else
      {
        ComplexNumber cn = new ComplexNumber(tfield.getText());
        if (polar)
        {
          cn.getInverse().toPolar();
          previous.setText("<HTML>" + cn.getInverse().toPolar() + "</HTML>");
          historyArr.add(
              "<HTML>Inverse(" + tfield.getText() + ") = " + cn.getInverse().toPolar() + "</HTML>");
        }
        else
        {
          previous.setText("<HTML>" + cn.getInverse().toString() + "</HTML>");
          historyArr.add("<HTML>Inverse(" + tfield.getText() + ") = " + cn.getInverse().toString()
              + "</HTML>");
        }
        tfield.setText("");
        if (histWind.isVisible())
        {
          histWind.setVisible(false);
          histWind.setLocation(
              (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
              (int) posNegButton.getLocationOnScreen().getY());
          updateList();
          histWind.setVisible(true);
        }
      }

      // try to pass tfield as complex number
    }

    // Operation for the clear button
    if (s.equals(r.getString("Reset")))
    {
      previous.setText("");
      tfield.setText("");
      hasFirst = false;
      hasSecond = false;
      cn1 = null;
      cn2 = null;
      cnEval = null;
    }

    // Operation for the Reset button
    if (s.contentEquals(r.getString("Clear")))
    {
      tfield.setText("");
    }

    if (s.contentEquals("="))
    {
      if (tfield.getText() == null || tfield.getText().length() == 0)
      {
        tfield.setText("");
        previous.setText(r.getString("Invalid"));
      }
      else
      {
        if (!tfield.getText().contains("i"))
        {
          ComplexNumber cn = new ComplexNumber();
          cn.realNumber(tfield.getText()); // ***********Implement ArrayList********
          if (polar)
          {
            previous.setText(tfield.getText() + "=" + cn.toPolar());
            historyArr.add(tfield.getText() + "=" + cn.toPolar());
          }
          else
          {
            previous.setText(tfield.getText() + "=" + cn.toString());
            historyArr.add(tfield.getText() + "=" + cn.toString());
          }
          if (histWind.isVisible())
          {
            histWind.setVisible(false);
            histWind.setLocation(
                (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
                (int) posNegButton.getLocationOnScreen().getY());
            updateList();
            histWind.setVisible(true);
          }
          hasSecond = false;
          cn1 = cnEval;
          tfield.setText("");
        }
      }
    }
    if (s.equals("<HTML><b><i>i</i></b></HTML>"))
    {
      if (hasFirst && !hasSecond)
      {
        System.out.println("this should make = work now");
        // hasSecond = true;
        // cn2 = new ComplexNumber(tfield.getText() + "i");
      }
      tfield.setText(tfield.getText() + "i");
    }

    // after the first equation and second equation have gone through, clicking an operator will
    // perform the operation
    if ((hasFirst && hasSecond)
        && (s.equals("+") || s.equals("-") || s.equals("X") || s.equals("/")))
    {
      if (s.equals("X"))
        s = "*";

      System.out.println("Both complex numbers are now entered, performing calculation: ");
      System.out.println("the first equation is " + cn1.toString());
      System.out.println("the second equation is " + cn2.toString());
      if (cn1 != null && cn2 != null)
      {
        cnEval = cn1.Evaluate(cn2, op);
        System.out.println(
            "After performing \"" + s + "\" this evaluation is: " + cnEval.toString() + " \n");

        if (polar)
        {
          previous.setText(cnEval.toPolar());
          historyArr.add(cnEval.toPolar());
        }
        else
        {
          previous.setText(cnEval.toString());
          historyArr.add(cnEval.toString());
        }
        if (histWind.isVisible())
        {
          histWind.setVisible(false);
          histWind.setLocation(
              (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
              (int) posNegButton.getLocationOnScreen().getY());
          updateList();
          histWind.setVisible(true);
        }
        hasSecond = false;
        cn1 = cnEval;
      }
      if (cn1 == null)
      {
        System.out.println("cn1 is null");
      }
      if (cn2 == null)
      {
        System.out.println("cn2 is null");
      }

    }

    // after the operator is chosen, sets the first and second
    if ((!hasFirst || !hasSecond)
        && (s.equals("+") || s.equals("-") || s.equals("X") || s.equals("%") || s.equals("=")))
    {
      if (s.equals("X"))
        s = "*";
      if (s.contentEquals("%"))
        s = "/";

      if ((tfield.getText().length() > 0
          && tfield.getText().charAt(tfield.getText().length() - 1) == 'i')
          || (tfield.getText().length() > 1
              && tfield.getText().charAt(tfield.getText().length() - 2) == 'i'))
      {
        System.out.print("An operator has been entered: ");
        // after the first equation is entered, sets it to cn1
        if (!hasFirst)
        {
          System.out.println("it was the first half");

          op = s.charAt(0);
          cn1 = new ComplexNumber(tfield.getText());
          if (polar)
          {
            previous.setText("<HTML>" + cn1.toPolar() + " " + s + "</HTML>");

          }
          else
          {
            previous.setText("<HTML>" + cn1.toString() + " " + s + "</HTML>");
          }
          tfield.setText("");
          hasFirst = true;
          System.out.println("hasFirst is now true\n");
        }
        // after the second equation is entered, sets it to cn2
        else if (hasFirst && !hasSecond)
        {
          System.out.println("it was the second half");
          cn2 = new ComplexNumber(tfield.getText());
          cnEval = cn1.Evaluate(cn2, op);
          if (s.equals("="))
          {
            System.out.println("YOU SHOULD GET HERE");
            if (polar)
            {
              previous.setText("<HTML>" + cn1.toPolar() + " " + op + " " + cn2.toPolar() + " " + s
                  + " " + cnEval.toPolar() + "</HTML>");
              historyArr.add("<HTML>" + cn1.toPolar() + " " + op + " " + cn2.toPolar() + " " + s
                  + " " + cnEval.toPolar() + "</HTML>");
            }
            else
            {
              previous.setText("<HTML>" + cn1.toString() + " " + op + " " + cn2.toString() + " " + s
                  + " " + cnEval.toString() + "</HTML>");
              historyArr.add("<HTML>" + cn1.toString() + " " + op + " " + cn2.toString() + " " + s
                  + " " + cnEval.toString() + "</HTML>");
            }

            if (histWind.isVisible())
            {
              histWind.setVisible(false);
              histWind.setLocation(
                  (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
                  (int) posNegButton.getLocationOnScreen().getY());
              updateList();
              histWind.setVisible(true);
            }
            cn1 = null;
            cn2 = null;
            cnEval = null;
            op = '+';
            hasFirst = false;
            hasSecond = false;
            tfield.setText("");
          }
          else
          {
            op = s.charAt(0);
            cn1 = cnEval;
            cn2 = null;
            cnEval = null;
            if (polar)
            {
              previous.setText("<HTML>" + cn1.toPolar() + " " + op + " </HTML>");
              historyArr.add("<HTML>" + cn1.toPolar() + " " + op + " </HTML>");
            }
            else
            {
              previous.setText("<HTML>" + cn1.toString() + " " + op + " </HTML>");
              historyArr.add("<HTML>" + cn1.toString() + " " + op + " </HTML>");
            }
            if (histWind.isVisible())
            {
              histWind.setVisible(false);
              histWind.setLocation(
                  (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
                  (int) posNegButton.getLocationOnScreen().getY());
              updateList();
              histWind.setVisible(true);
            }
            tfield.setText("");
          }

          // hasSecond = true;
          // System.out.println("hasSecond is now true\n");
        }
      }
      else
      {
        tfield.setText(tfield.getText() + s);
      }

    }

    // decimal button
    if (s.contentEquals("."))

    {
      tfield.setText(tfield.getText() + s);
    }

    // buttons for numbers
    if (s.charAt(0) >= '0' && s.charAt(0) <= '9')
      tfield.setText(tfield.getText() + s);

    // backspace button
    if (s.contentEquals("<~") && tfield.getText().length() > 0)
    {
      tfield.setText(tfield.getText().substring(0, tfield.getText().length() - 1));
    }

    // Sign button
    if (s.contentEquals("(-)"))
    {
      if (tfield.getText().length() > 0)
      {
        if (tfield.getText().charAt(0) == '-')
        {
          tfield.setText(tfield.getText().substring(1));
        }
        else
        {
          tfield.setText("-" + tfield.getText());
        }
      }
    }

    // Open and Close Parenthesis
    if (s.contentEquals("(") || s.contentEquals(")"))
    {
      tfield.setText(tfield.getText() + s);
    }
    if (s.contentEquals(">"))
    {
      histWind.setLocation((int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
          (int) posNegButton.getLocationOnScreen().getY());

      historyButton.setForeground(Color.LIGHT_GRAY);

      try
      {
        animate(true);
      }
      catch (InterruptedException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      try
      {
        Thread.sleep(25);
      }
      catch (InterruptedException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      updateList();
    }
    if (s.contentEquals("<"))
    {
      try
      {
        animate(false);
      }
      catch (InterruptedException e1)
      {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
      histWind.setVisible(false);
      historyButton.setForeground(Color.BLACK);
    }
    if (s.contentEquals(r.getString("Conj")))
    {
      if (tfield.getText().contains("i"))
      {
        ComplexNumber cn = new ComplexNumber(tfield.getText());
        if (polar)
        {
          previous.setText("<HTML>" + cn.Conjugate().toPolar() + "</HTML>");
          historyArr.add("<HTML>Conjugate(" + tfield.getText() + ") = " + cn.Conjugate().toPolar()
              + "</HTML>");
        }
        else
        {
          previous.setText("<HTML>" + cn.Conjugate().toString() + "</HTML>");
          historyArr.add("<HTML>Conjugate(" + tfield.getText() + ") = " + cn.Conjugate().toString()
              + "</HTML>");
        }
        if (histWind.isVisible())
        {
          histWind.setVisible(false);
          histWind.setLocation(
              (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
              (int) posNegButton.getLocationOnScreen().getY());
          updateList();
          histWind.setVisible(true);
        }
        tfield.setText("");
      }
      else
      {
        tfield.setText("");
        previous.setText(r.getString("notConj"));
      }
    }
    if (tfield.getText().equals("="))
    {
      tfield.setText("");
    }

    // exponentiation button
    if (s.contentEquals(r.getString("Exp")))
    {
      // get the exponent
      ComplexNumber cn = new ComplexNumber();
      cn.realNumber(tfield.getText());
      float base = cn.getReal();
      if (s.charAt(0) >= 0 && s.charAt(0) <= 9)
      {
        // for loop until reaches the exponent
        for (int i = 0; i < s.charAt(0); i++)
        {
          // eval exponent times base
          // save the outcome
          base *= base;
        }
      }
      // return the outcome of the loop
      if (polar)
      {
        previous.setText(String.valueOf(base));
        historyArr.add("<HTML>Exponentation(" + tfield.getText() + ") = " + previous.getText());
      }
      else
      {
        previous.setText(String.valueOf(base));
        historyArr.add("<HTML>Exponentation(" + tfield.getText() + ") = " + previous.getText());
      }
      tfield.setText("");
      if (histWind.isVisible())
      {
        histWind.setVisible(false);
        histWind.setLocation(
            (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
            (int) posNegButton.getLocationOnScreen().getY());
        updateList();
        histWind.setVisible(true);
      }
    }

    // squareRoot Button
    if (s.contentEquals(r.getString("Sqrt")))
    {
      // take the number typed before
      // Math.Sqrt(number)
      // display number
      ComplexNumber cn = new ComplexNumber(tfield.getText());
      float base = cn.getReal();
      float imag = cn.getImag();
      String result = String.valueOf(Math.sqrt(base) + "+" + Math.sqrt(imag));
      System.out.print(result);
      previous.setText(result);
      historyArr.add("<HTML>SquareRoot(" + tfield.getText() + ") = " + previous.getText());
      tfield.setText("");
      if (histWind.isVisible())
      {
        histWind.setVisible(false);
        histWind.setLocation(
            (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
            (int) posNegButton.getLocationOnScreen().getY());
        updateList();
        histWind.setVisible(true);
      }
    }

    // log button
    if (s.contentEquals(r.getString("Log")))
    {
      // return the log base 10 of a number
      ComplexNumber cn = new ComplexNumber(tfield.getText());
      float base = cn.getReal();
      float imag = cn.getImag();

      previous.setText(String.valueOf(Math.log10(base) + "+" + imag));
      historyArr.add("<HTML>Logarithm(" + tfield.getText() + ") = " + previous.getText());
      tfield.setText("");
      if (histWind.isVisible())
      {
        histWind.setVisible(false);
        histWind.setLocation(
            (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
            (int) posNegButton.getLocationOnScreen().getY());
        updateList();
        histWind.setVisible(true);
      }
    }

    // format Button
    if (s.contentEquals(r.getString("Frmt")))
    {
      // determine if it is in decimal or fraction format
      // if Fraction
      if (!tfield.getText().contains("."))
      {
        // divide the two numbers
        // display the outcome
        previous.setText(String.valueOf(cn1.Evaluate(cn2, '/')));
        historyArr.add("<HTML>OutputFormat(" + tfield.getText() + ") = " + previous.getText());
        tfield.setText("");
        if (histWind.isVisible())
        {
          histWind.setVisible(false);
          histWind.setLocation(
              (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
              (int) posNegButton.getLocationOnScreen().getY());
          updateList();
          histWind.setVisible(true);
        }
        // if decimal
      }
      else
      {
        // place the decimal value over the
        int decDigits = tfield.getText().length() - 1 - tfield.getText().indexOf(".");
        int denom = 1;
        ComplexNumber cn = new ComplexNumber();

        cn.realNumber(tfield.getText());
        float numer = cn.getReal();

        for (int i = 0; i < decDigits; i++)
        {
          denom *= 10;
          numer *= 10;
        }

        int num = (int) Math.round(numer);
        int g = gcdHelper(num, denom);
        previous.setText(String.valueOf(num / g) + "/" + String.valueOf(denom / g));
        historyArr.add("<HTML>Outputformat(" + tfield.getText() + ") = " + previous.getText());
        tfield.setText("");
        if (histWind.isVisible())
        {
          histWind.setVisible(false);
          histWind.setLocation(
              (int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
              (int) posNegButton.getLocationOnScreen().getY());
          updateList();
          histWind.setVisible(true);
        }

      }
    }

    if (s.contentEquals("Polar"))
    {
      if (polar)
      {
        polar = false;
        if (previous.getText().contains("="))
        {
          previous.setText("<HTML>" + cn1.toString() + " " + op + " " + cn2.toString() + " = "
              + cnEval.toString() + "</HTML>");
        }
        else if (cn1 != null)
          previous.setText("<HTML>" + cn1.toString() + " " + op + "</HTML>");
      }
      else
      {
        polar = true;
        if (previous.getText().contains("="))
        {
          previous.setText("<HTML>" + cn1.toPolar() + " " + op + " " + cn2.toPolar() + " = "
              + cnEval.toPolar() + "</HTML>");
        }
        else if (cn1 != null)
          previous.setText("<HTML>" + cn1.toPolar() + " " + op + "</HTML>");
      }
    }

  }

  @Override
  public void componentHidden(final ComponentEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  @Override
  // If the window is moved, the hist tab will follow
  public void componentMoved(final ComponentEvent arg0)
  {
    if (histWind.isVisible())
    {
      histWind.setLocation((int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
          (int) posNegButton.getLocationOnScreen().getY());
      histWind.setVisible(true);
    }

  }

  @Override
  // hist tab will also resize to match.
  public void componentResized(final ComponentEvent arg0)
  {
    if (histWind.isVisible())
    {
      histWind.setLocation((int) posNegButton.getLocationOnScreen().getX() + numberpanel.getWidth(),
          (int) posNegButton.getLocationOnScreen().getY());
      histWind.setSize(numberpanel.getWidth(), (int) ((zeroButton.getLocationOnScreen().getY()
          - posNegButton.getLocationOnScreen().getY()) + zeroButton.getHeight()));
      histWind.setVisible(true);
    }

  }

  @Override
  public void componentShown(final ComponentEvent arg0)
  {
    // TODO Auto-generated method stub

  }

  /**
   * assists with gcd calculations.
   * 
   * @param num
   *          incoming number
   * @param denom
   *          the denomination
   * @return the gcd
   */
  public int gcdHelper(final int num, final int denom)
  {
    int gcd = 1;
    for (int i = 1; i < num && i < denom; i++)
    {
      if (num % i == 0 && denom % i == 0)
      {
        gcd = i;
      }
    }
    return gcd;

  }

  /**
   * Main method.
   * 
   * @param args
   *          incoming arg
   * @throws IOException
   */
  public static void main(String args[]) throws IOException
  {

    // creates a calculator, gives it a name, and makes it visible
    Calculator rimplex = new Calculator();
    rimplex.setTitle("Rimplex");
    // this makes the initial frame size fit all the objects inside
    rimplex.pack();
    rimplex.setVisible(true);
  }

  @Override
  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
  {
    if (pageIndex > 0)
    {
      return NO_SUCH_PAGE;
    }
    Graphics2D g2d = (Graphics2D) graphics;
    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
    graphics.drawString(historyArr.toString(), 100, 100);
    return PAGE_EXISTS;
  }

  /**
   * Sets the languages.
   * 
   * @param lang
   *          the int representation of each language
   */
  public void setLanguage(final int lang)
  {
    if (lang == 1)
    {
      l = new Locale("en", "US");
      r = ResourceBundle.getBundle("ui.Strings", l);
      ResourceBundle.clearCache();
    }
    else if (lang == 2)
    {
      l = new Locale("es", "MX");
      r = ResourceBundle.getBundle("ui.Strings", l);
      ResourceBundle.clearCache();
    }
    else if (lang == 3)
    {
      l = new Locale("de", "GM");
      r = ResourceBundle.getBundle("ui.Strings", l);
      ResourceBundle.clearCache();
    }
  }

  /**
   * updates history tab in the event it is open.
   */
  public void updateList()
  {
    listModel.clear();
    if (pane != null)
    {
      centerPanel.remove(pane);
    }
    for (int i = 0; i < historyArr.size(); i++)
    {
      listModel.addElement(historyArr.get(i));
    }

    list = new JList(listModel);
    list.setBackground(new Color(203, 230, 245));

    pane = new JScrollPane(list);
    pane.setHorizontalScrollBar(null);
    pane.setBorder(BorderFactory.createLineBorder(new Color(203, 230, 245), 1));
    pane.setBackground(new Color(203, 230, 245));
    pane.grabFocus();
    pane.setVisible(true);

    centerPanel.setLayout(new BorderLayout());
    centerPanel.setBorder(BorderFactory.createLineBorder(new Color(203, 230, 245), 1));
    centerPanel.setBackground(new Color(203, 230, 245));
    centerPanel.setVisible(true);

    eastPanel.setLayout(new BorderLayout());
    eastPanel.setBackground(new Color(203, 230, 245));
    eastPanel.add(histButtonClose, BorderLayout.EAST);

    centerPanel.add(pane, BorderLayout.PAGE_START);

    histWind.add(centerPanel, BorderLayout.CENTER);
    histWind.add("East", eastPanel);
  }

  @Override
  public void windowActivated(final WindowEvent arg0)
  {
  }

  public void windowClosed(final WindowEvent arg0)
  {
  }

  @Override
  // Auto saves history to a file upon close
  public void windowClosing(final WindowEvent arg0)
  {
    try
    {
      write();
    }
    catch (IOException e1)
    {
      System.out.println("Failed to write history to file");
    }
  }

  @Override
  public void windowDeactivated(final WindowEvent arg0)
  {
  }

  @Override
  public void windowDeiconified(final WindowEvent arg0)
  {
  }

  @Override
  public void windowIconified(final WindowEvent arg0)
  {
  }

  @Override
  public void windowOpened(final WindowEvent arg0)
  {
  }

  /**
   * writes the history to file.
   * 
   * @throws IOException
   */
  public void write() throws IOException
  {
    File file = new File("Rimplex_Calculations.txt");
    PrintWriter writer = new PrintWriter(new FileWriter(file));
    for (int i = 0; i < historyArr.size(); i++)
    {
      writer.println(historyArr.get(i));
    }

    writer.close();
  }

}
