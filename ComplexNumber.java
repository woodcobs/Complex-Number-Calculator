package rimplex;
//package util;

import java.util.ArrayList;

/**
 * Complex Number - handles complex and real calculations.
 * 
 * @author team25
 * @version 27 April 2021
 *
 */
public class ComplexNumber
{
  private float realNum;
  private float imagNum;
  private char operator;
  private boolean hasReal;
  private boolean hasImag;
  private boolean hasFirst = false;
 
  /**
   * Default Constructor.
   */
  public ComplexNumber()
  {
    realNum = 0.0F;
    imagNum = 0.0F;
    hasReal = true;
    hasImag = false;
    operator = '+';
  }
  /**
   * handles real only operations constructor.
   * @param incReal incoming real number
   */
  public ComplexNumber(final float incReal)
  {
    realNum = incReal;
    imagNum = 0.0F;
    hasReal = true;
    hasImag = false;
    operator = '+';
  }
  /**
   * Explicit constructor.  Searches string for all components of equations.
   * 
   * @param equation incoming string to parse
   */
  public ComplexNumber(String equation)
  {
    if (equation.charAt(equation.length() - 1) == ')')
    {
      if (equation.charAt(0) == '-')
      {
        equation = equation.substring(2, equation.length() - 1);
        equation = "-" + equation;
      }
      else
      {
        equation = equation.substring(1, equation.length() - 1);
      }
    }
    boolean realNeg = false;
    if (equation.charAt(0) == '-')
    {
      realNeg = true;
      equation = equation.substring(1, equation.length());
    }
    if (!equation.contains("i"))
    {
      System.out.println(equation + " does not contain i");

      realNum = 0.0F;
      imagNum = 0.0F;
      operator = '+';
    }
    else
    {
      int index = 0;
      if (equation.contains("+"))
      {
        index = equation.indexOf("+");
        realNum = Float.parseFloat(equation.substring(0, index));
        hasReal = true;
        if (realNeg)
          realNum *= -1;
        try
        {
          imagNum = Float.parseFloat(equation.substring(index + 1, equation.length() - 1));
        }
        catch (NumberFormatException e)
        {
          imagNum = 1.0F;
        }
      }
      else if (equation.contains("-"))
      {

        index = equation.indexOf("-");
        realNum = Float.parseFloat(equation.substring(0, index));
        hasReal = true;
        if (realNeg)
          realNum *= -1;
        try
        {
          // if this number exists, set it to imagNum
          imagNum = Float.parseFloat(equation.substring(index + 1, equation.length() - 1)) * -1;
        }
        catch (NumberFormatException e)
        {
          // if it doesn't exist, set it to 1
          imagNum = 1.0F;
        }
        operator = '+';
        equation = equation.replace('-', '+');
        // equation = "" + realNum + " + " + imagNum + "i";
      }
      else if (equation.contains("*"))
      {
        index = equation.indexOf("*");
        realNum = Float.parseFloat(equation.substring(0, index));
        hasReal = true;
        if (realNeg)
          realNum *= -1;
        try
        {
          imagNum = Float.parseFloat(equation.substring(index + 1, equation.length() - 1))
              * realNum;
        }
        catch (NumberFormatException e)
        {
          imagNum = realNum;
        }
        // imagNum = Float.parseFloat(equation.substring(index + 1, equation.length() - 1));
        realNum = 0.0F;
      }
      else if (equation.contains("/"))
      {
        index = equation.indexOf("/");
        realNum = Float.parseFloat(equation.substring(0, index));
        hasReal = true;
        if (realNeg)
          realNum *= -1;
        imagNum = realNum / Float.parseFloat(equation.substring(index + 1, equation.length() - 1));
        realNum = 0.0F;
      }
      else
      {
        realNum = 0.0F;
        hasReal = false;
        hasImag = true;
        if (equation.length() == 1 && equation.charAt(0) == 'i')
        {
          imagNum = 1;
        }

        else if (equation.length() > 1 && equation.charAt(0) != 'i')
        {
          imagNum = Float.parseFloat(equation.substring(0, equation.length() - 1));
        } else
          imagNum = 1;
        //if (realNeg)
        //  realNum *= -1;

      }
      hasImag = true;
      System.out.println(equation);
      operator = equation.charAt(index);
    }
  }
  
  /**
   * Conjugates the imagNum.
   * @return conjugated num
   */
  public ComplexNumber Conjugate()
  {
    System.out.println(imagNum);
    if (imagNum != 0)
      imagNum = imagNum * -1;
    System.out.println(imagNum);
    return this;
  }

  /**
   * Evaluates the parsed string.
   * @param n2 the second part of the equation
   * @param operator the operation to do
   * @return the solution
   */
  public ComplexNumber Evaluate(final ComplexNumber n2, final char operator)
  {
    ComplexNumber cn = null;
    float num1;
    float num2;
    boolean wasSuccessful = false;
    if (operator == '+')
    {
      num1 = realNum + n2.getReal();
      num2 = imagNum + n2.getImag();
      cn = new ComplexNumber(num1 + "+" + num2 + "i");
      wasSuccessful = true;
    }
    else if (operator == '-')
    {
      num1 = realNum - n2.getReal();
      num2 = imagNum - n2.getImag();
      cn = new ComplexNumber(num1 + "+" + num2 + "i");
      wasSuccessful = true;
    }
    else if (operator == '*')
    {
      // (ac-bd)+(ad+bc)i
      // if (!hasReal)
      // {
      // num1 = imagNum * n2.getImag() * -1;
      // cn = new ComplexNumber(num1);
      // wasSuccessful = true;
      // }
      // else
      // {
      num1 = (realNum * n2.getReal()) - (imagNum * n2.getImag());
      num2 = (realNum * n2.getImag()) + (imagNum * n2.getReal());
      cn = new ComplexNumber(num1 + "+" + num2 + "i");
      wasSuccessful = true;
      // }
    }
    else if (operator == '/')
    {
      float denom = (float) (Math.pow(n2.getReal(), 2) + Math.pow(n2.getImag(), 2));
      num1 = (realNum * n2.getReal()) + (imagNum * n2.getImag());
      num1 = num1 / denom;
      num2 = (imagNum * n2.getReal()) - (realNum * n2.getImag());
      num2 = num2 / denom;
      if (num2 == 0.0F)
      {
        cn = new ComplexNumber(num1);
      }
      else
      {
        cn = new ComplexNumber(num1 + "+" + num2 + "i");
      }
      wasSuccessful = true;
    }

    if (!wasSuccessful)
      return new ComplexNumber("0.0+0.0i");
    else
      return cn;
  }
  /**
   * gets imagNum.
   * @return imagNum
   */
  public float getImag()
  {
    return imagNum;
  }
  /**
   * gets the inverse of complex equation.
   * @return the inverse
   */
  public ComplexNumber getInverse()
  {
    float imag1 = 0F;
    if (this.getReal() == 0F && this.getImag() == 0F)
      return new ComplexNumber(0 + "+" + 0 + "i");
    // find a-bi
    float real1 = this.getReal();
    if (imagNum != 0F)
      imag1 = this.getImag() * -1;

    // first find (a+bi)(c-di)

    // (ac-bd)+(ad+bc)i
    float denom = (real1 * real1) + (imag1 * imag1);
    // denomonator

    // (a-bi) / (a+bi)(a-bi)
    real1 = real1 / denom;
    imag1 = imag1 / denom;

    return new ComplexNumber(real1 + "+" + imag1 + "i");

  }

  /**
   * gets operand for this complex number.
   * @return the operand
   */
  public char getOperand()
  {
    System.out.println(operator);
    return operator;
  }
  
  /**
   * gets the real number.
   * @return the real number
   */
  public float getReal()
  {
    return realNum;
  }
  /**
   * Converts the equation to polar form.
   * 
   * @return polar answer
   */
  public String toPolar()
  {
    String returnString = "";
    double r, theta;
    r = Math.pow(this.getReal(), 2) + Math.pow(this.getImag(), 2);
    r = Math.sqrt(r);
    if (realNum == 0F)
      return String.format("%.2f(cos(%.2f) + <i>i</i>isin(%.2f))", r, 0F, 0F);
    theta = Math.atan(this.getImag() / this.getReal());
    returnString = String.format("%.2f(cos(%.2f) + <i>i</i>isin(%.2f))", r, theta, theta);

    return returnString;

  }

  /**
   * returns a formatted string.
   * 
   * @return the toString
   */
  public String toString()
  {
    if (!hasImag)
    {
      return String.format("%.2f", realNum);
    }
    if (operator == '+' || operator == '-')
    {
      return String.format("(%.2f %s %.2f<i>i</i>)", realNum, operator, imagNum);
    }
    else
      return String.format("<i>%.2fi</i>", imagNum);
  }
  /**
   * handles real only addition.
   * 
   * @param incNum incoming number
   */
  private void realAdd(final float incNum)
  {
    System.out.println("A");
    realNum = realNum + incNum;
  }
  /**
   * handles real only division.
   * 
   * @param incNum incoming number
   */
  private void realDiv(final float incNum)
  {
    System.out.println("D");
    realNum = realNum / incNum;
  }
  /**
   * handles real only multiplication.
   * 
   * @param incNum incoming number
   */
  private void realMult(final float incNum)
  {
    System.out.println("M");
    realNum = realNum * incNum;
  }
 /**
  * Parses the string searching for only real Numbers.
  * @param eq the incoming string equation
  * @return the new CN with real nums
  */
  public ComplexNumber realNumber(final String eq)
  {
    float numOne = 0.0F;
    int j = 0;
    ArrayList<Character> prev = new ArrayList<Character>();
    for (int i = 0; i < eq.length(); i++)
    {
      if (58 > (int) eq.charAt(i) && (int) eq.charAt(i) > 47)
      {
        numOne += Float.parseFloat(Character.toString(eq.charAt(i)));
        System.out.printf("%.2f\n", numOne);
      }
      if (48 > (int) eq.charAt(i) && (int) eq.charAt(i) > 41 || prev.size() >= j + 1)
      {
        prev.add(eq.charAt(i));
        if (!hasFirst)
        {
          realNum += numOne;
          numOne = 0.0F;
          hasFirst = true;
        }
        else
        {
          System.out.println("Switch");
          System.out.println(prev.get(j));
          switch (prev.get(j))
          {
            case ('*'):
              realMult(numOne);
              numOne = 0;
              break;
            case ('+'):
              realAdd(numOne);
              numOne = 0;
              break;
            case ('-'):
              realSub(numOne);
              numOne = 0;
              break;
            case ('/'):
              realDiv(numOne);
              numOne = 0;
              break;
          }
          j++;
        }
      }
    }
    ComplexNumber cnR = new ComplexNumber(realNum);
    return cnR;
  }
  /**
   * handles real only subtraction.
   * 
   * @param incNum incoming number
   */
  private void realSub(final float incNum)
  {
    System.out.println("S");
    realNum = realNum - incNum;
  }
}
