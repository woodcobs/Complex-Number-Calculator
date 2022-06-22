package rimplex;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ComplexNumberTest
{

  @Test
  void testGettersAndSetters()
  {
    // empty Constructor
    ComplexNumber cn1 = new ComplexNumber();
    // realNum only
    ComplexNumber cn2 = new ComplexNumber(1F);
    // full equation addition
    ComplexNumber cn3 = new ComplexNumber("1+2i");
    ComplexNumber cn3b = new ComplexNumber("2+1i");
    // full equation subtraction
    ComplexNumber cn4 = new ComplexNumber("1-2i");
    // full equation multiplication
    ComplexNumber cn5 = new ComplexNumber("1*2i");
    // full equation division
    ComplexNumber cn6 = new ComplexNumber("1/2i");
    // test others
    ComplexNumber other1 = new ComplexNumber("-(1/2i)");
    ComplexNumber other2 = new ComplexNumber("(1/2i)");
    ComplexNumber other3 = new ComplexNumber("(1/2)");
    ComplexNumber other4 = new ComplexNumber("-1+2i");
    ComplexNumber other5 = new ComplexNumber("-(1+i)");
    ComplexNumber other6 = new ComplexNumber("-(1-1i)");
    ComplexNumber other7 = new ComplexNumber("-(1-i)");
    ComplexNumber other8 = new ComplexNumber("-(1*1i)");
    ComplexNumber other9 = new ComplexNumber("-(1*i)");
    ComplexNumber other10 = new ComplexNumber("-(1/1i)");
    // i and not length 1
    ComplexNumber other11 = new ComplexNumber("i1.2");
    // i and length 1
    ComplexNumber other12 = new ComplexNumber("i");
    // not i and length 1
    ComplexNumber other13 = new ComplexNumber("1");
    // not i and not length 1
    ComplexNumber other14 = new ComplexNumber("1.2");
    // not i and length 0






    // test Evaluate()

    Assertions.assertTrue(cn1.Evaluate(cn1, '+').toString().equals("(0.00 + 0.00<i>i</i>)"));
    Assertions.assertTrue(cn1.Evaluate(cn1, '-').toString().equals("(0.00 + 0.00<i>i</i>)"));
    Assertions.assertTrue(cn1.Evaluate(cn1, '*').toString().equals("(0.00 + 0.00<i>i</i>)"));
    Assertions.assertTrue(cn3.Evaluate(cn3b, '/').toString().equals("(0.80 + 0.60<i>i</i>)"));
    ComplexNumber cnDiv = new ComplexNumber("1+1i");
    Assertions.assertTrue(cnDiv.Evaluate(cnDiv, '/').toString().equals("1.00"));
    Assertions.assertTrue(cnDiv.Evaluate(cnDiv, '3').toString().equals("(0.00 + 0.00<i>i</i>)"));

    // test getReal()
    Assertions.assertEquals(0F, cn1.getReal());
    Assertions.assertEquals(1F, cn2.getReal());
    Assertions.assertEquals(1F, cn3.getReal());
    Assertions.assertEquals(1F, cn4.getReal());
    Assertions.assertEquals(0F, cn5.getReal());
    Assertions.assertEquals(0F, cn6.getReal());

    // test getImag()
    Assertions.assertEquals(0F, cn1.getImag());
    Assertions.assertEquals(0F, cn2.getImag());
    Assertions.assertEquals(2F, cn3.getImag());
    Assertions.assertEquals(-2F, cn4.getImag());
    Assertions.assertEquals(2F, cn5.getImag());
    Assertions.assertEquals(.5F, cn6.getImag());

    // test getOperand()
    Assertions.assertEquals('+', cn1.getOperand());
    Assertions.assertEquals('+', cn2.getOperand());
    Assertions.assertEquals('+', cn3.getOperand());
    Assertions.assertEquals('+', cn4.getOperand());
    Assertions.assertEquals('*', cn5.getOperand());
    Assertions.assertEquals('/', cn6.getOperand());

    // test toString()
    String test = String.format("%.2f", cn1.getReal());
    Assertions.assertEquals(test, cn1.toString());
    test = String.format("%.2f", cn2.getReal());
    Assertions.assertEquals(test, cn2.toString());
    test = String.format("(%.2f %s %.2f<i>i</i>)", cn3.getReal(), cn3.getOperand(), cn3.getImag());
    Assertions.assertEquals(test, cn3.toString());
    test = String.format("(%.2f %s %.2f<i>i</i>)", cn4.getReal(), cn4.getOperand(), cn4.getImag());
    Assertions.assertEquals(test, cn4.toString());
    test = String.format("<i>%.2fi</i>", cn5.getImag());
    Assertions.assertEquals(test, cn5.toString());
    test = String.format("<i>%.2fi</i>", cn6.getImag());
    Assertions.assertEquals(test, cn6.toString());

    // test Evaluate()

    // test getInverse()
    Assertions.assertEquals(0F, cn1.getInverse().getReal());
    Assertions.assertEquals(0F, cn1.getInverse().getImag());

    Assertions.assertEquals(1F, cn2.getInverse().getReal());
    Assertions.assertEquals(0F, cn2.getInverse().getImag());

    Assertions.assertEquals(0.2F, cn3.getInverse().getReal());
    Assertions.assertEquals(-0.4F, cn3.getInverse().getImag());

    Assertions.assertEquals(0.2F, cn4.getInverse().getReal());
    Assertions.assertEquals(0.4F, cn4.getInverse().getImag());

    Assertions.assertEquals(0F, cn5.getInverse().getReal());
    Assertions.assertEquals(-0.5F, cn5.getInverse().getImag());

    Assertions.assertEquals(0F, cn6.getInverse().getReal());
    Assertions.assertEquals(-2.0F, cn6.getInverse().getImag());

    double r, theta;
    r = Math.pow(cn1.getReal(), 2) + Math.pow(cn1.getImag(), 2);
    r = Math.sqrt(r);
    test = String.format("%.2f(cos(%.2f) + <i>i</i>isin(%.2f))", r, 0F, 0F);
    Assertions.assertEquals(test, cn1.toPolar());

    r = Math.pow(cn2.getReal(), 2) + Math.pow(cn2.getImag(), 2);
    r = Math.sqrt(r);
    theta = Math.atan(cn2.getImag() / cn2.getReal());
    test = String.format("%.2f(cos(%.2f) + <i>i</i>isin(%.2f))", r, theta, theta);
    Assertions.assertEquals(test, cn2.toPolar());

    r = Math.pow(cn3.getReal(), 2) + Math.pow(cn3.getImag(), 2);
    r = Math.sqrt(r);
    theta = Math.atan(cn3.getImag() / cn3.getReal());
    test = String.format("%.2f(cos(%.2f) + <i>i</i>isin(%.2f))", r, theta, theta);
    Assertions.assertEquals(test, cn3.toPolar());

    r = Math.pow(cn4.getReal(), 2) + Math.pow(cn4.getImag(), 2);
    r = Math.sqrt(r);
    theta = Math.atan(cn4.getImag() / cn4.getReal());
    test = String.format("%.2f(cos(%.2f) + <i>i</i>isin(%.2f))", r, theta, theta);
    Assertions.assertEquals(test, cn4.toPolar());

    r = Math.pow(cn5.getReal(), 2) + Math.pow(cn5.getImag(), 2);
    r = Math.sqrt(r);
    test = String.format("%.2f(cos(%.2f) + <i>i</i>isin(%.2f))", r, 0F, 0F);
    Assertions.assertEquals(test, cn5.toPolar());

    r = Math.pow(cn6.getReal(), 2) + Math.pow(cn6.getImag(), 2);
    r = Math.sqrt(r);
    test = String.format("%.2f(cos(%.2f) + <i>i</i>isin(%.2f))", r, 0F, 0F);
    System.out.println(test);
    Assertions.assertEquals(test, cn6.toPolar());

    // test realNumber()

    // test Conjugate()
    Assertions.assertEquals(0F, cn1.Conjugate().getImag());
    Assertions.assertEquals(0F, cn2.Conjugate().getImag());
    Assertions.assertEquals(-2F, cn3.Conjugate().getImag());
    Assertions.assertEquals(2F, cn4.Conjugate().getImag());
    Assertions.assertEquals(-2F, cn5.Conjugate().getImag());
    Assertions.assertEquals(-.5F, cn6.Conjugate().getImag());

  }

}
