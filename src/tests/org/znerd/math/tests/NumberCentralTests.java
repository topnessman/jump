/*
 * $Id: NumberCentralTests.java,v 1.25 2004/04/15 21:48:42 znerd Exp $
 */
package org.znerd.math.tests;

import org.znerd.math.BasicRationalNumber;
import org.znerd.math.IntegerNumber;
import org.znerd.math.NumberCentral;
import org.znerd.math.RationalNumber;
import org.znerd.math.RealNumber;
import org.znerd.math.SmallIntegerNumber;
import java.math.BigDecimal;
import java.math.BigInteger;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests on the <code>NumberCentral</code> class.
 *
 * @version $Revision: 1.25 $ $Date: 2004/04/15 21:48:42 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public class NumberCentralTests extends TestCase {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns a test suite with all test cases defined by this class.
    *
    * @return
    *    the test suite, never <code>null</code>.
    */
   public static Test suite() {
      return new TestSuite(NumberCentralTests.class);
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>NumberCentralTests</code> test suite with the
    * specified name. The name will be passed to the superconstructor.
    *
    * @param name
    *    the name for this test suite.
    */
   public NumberCentralTests(String name) {
      super(name);
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   private IntegerNumber MINUS_TWO;
   private IntegerNumber MINUS_ONE;
   private IntegerNumber ZERO;
   private IntegerNumber ONE;
   private IntegerNumber TWO;
   private RationalNumber ONE_HALF;
   private RationalNumber MINUS_ONE_HALF;
   private BigInteger POSITIVE_BIG_INTEGER;
   private BigInteger NEGATIVE_BIG_INTEGER;
   private BigDecimal MINUS_ONE_HALF_BIG_DECIMAL;
   private BigDecimal ZERO_BIG_DECIMAL;
   private BigDecimal ONE_HALF_BIG_DECIMAL;
   private BigDecimal ONE_BIG_DECIMAL;
   private BigDecimal TWO_BIG_DECIMAL;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Performs setup for the tests.
    */
   protected void setUp() {
      MINUS_TWO = SmallIntegerNumber.MINUS_TWO;
      MINUS_ONE = SmallIntegerNumber.MINUS_ONE;
      ZERO = SmallIntegerNumber.ZERO;
      ZERO = SmallIntegerNumber.ZERO;
      ONE = SmallIntegerNumber.ONE;
      TWO = SmallIntegerNumber.TWO;
      ONE_HALF = BasicRationalNumber.ONE_HALF;
      MINUS_ONE_HALF = BasicRationalNumber.MINUS_ONE_HALF;

      POSITIVE_BIG_INTEGER = BigInteger.ONE.shiftLeft(10000);
      NEGATIVE_BIG_INTEGER = POSITIVE_BIG_INTEGER.negate();

      MINUS_ONE_HALF_BIG_DECIMAL = new BigDecimal("-0.5");
      ZERO_BIG_DECIMAL           = new BigDecimal("0.0");
      ONE_HALF_BIG_DECIMAL       = new BigDecimal("0.5");
      ONE_BIG_DECIMAL            = new BigDecimal("1.0");
      TWO_BIG_DECIMAL            = new BigDecimal("2.0");

      assertNotNull(MINUS_TWO);
      assertNotNull(MINUS_ONE);
      assertNotNull(ZERO);
      assertNotNull(ONE);
      assertNotNull(TWO);
      assertNotNull(ONE_HALF);
      assertNotNull(MINUS_ONE_HALF);
   }

   /**
    * Tests {@link NumberCentral#getVersion()}.
    */
   public void testGetVersion() {
      String version = NumberCentral.getVersion();
      assertNotNull(version);
   }

   /**
    * Tests {@link NumberCentral#createFraction(int,int)}.
    */
   public void testCreateFraction_int() {

      // Denominator should not be 0
      try {
         NumberCentral.createFraction(1, 0);
      } catch (ArithmeticException e) { /* as expected */ }

      assertEquals(ZERO,           NumberCentral.createFraction( 0, -1));
      assertEquals(ZERO,           NumberCentral.createFraction( 0,  1));
      assertEquals(ONE,            NumberCentral.createFraction( 1,  1));
      assertEquals(TWO,            NumberCentral.createFraction( 2,  1));
      assertEquals(ONE_HALF,       NumberCentral.createFraction( 1,  2));
      assertEquals(MINUS_ONE_HALF, NumberCentral.createFraction(-1,  2));
      assertEquals(MINUS_ONE_HALF, NumberCentral.createFraction( 1, -2));
      assertEquals(ONE_HALF,       NumberCentral.createFraction(-1, -2));
      assertEquals(ONE,            NumberCentral.createFraction(-1, -1));
      assertEquals(TWO,            NumberCentral.createFraction(-2, -1));
   }

   /**
    * Tests {@link NumberCentral#createFraction(IntegerNumber,IntegerNumber)}.
    */
   public void testCreateFraction_IntegerNumber() {

      final String message = "An IllegalArgumentException should be thrown if one of the arguments to " + NumberCentral.class.getName() + ".createFraction(" + IntegerNumber.class.getName() + ", " + IntegerNumber.class.getName() + " is null.";

      // First argument is null
      try {
         NumberCentral.createFraction(null, ONE);
         fail(message);
      } catch (IllegalArgumentException e) { /* as expected  */ }

      // Second argument is null
      try {
         NumberCentral.createFraction(ONE, null);
         fail(message);
      } catch (IllegalArgumentException e) { /* as expected */ }

      // Both arguments are null
      try {
         NumberCentral.createFraction(null, null);
         fail(message);
      } catch (IllegalArgumentException e) { /* as expected */ }

      // Denominator should not be zero
      try {
         NumberCentral.createFraction(ONE, ZERO);
      } catch (ArithmeticException e) { /* as expected */ }

      assertEquals(ZERO,           NumberCentral.createFraction(ZERO,      MINUS_ONE));
      assertEquals(ZERO,           NumberCentral.createFraction(ZERO,      ONE));
      assertEquals(ONE,            NumberCentral.createFraction(ONE,       ONE));
      assertEquals(TWO,            NumberCentral.createFraction(TWO,       ONE));
      assertEquals(ONE_HALF,       NumberCentral.createFraction(ONE,       TWO));
      assertEquals(MINUS_ONE_HALF, NumberCentral.createFraction(MINUS_ONE, TWO));
      assertEquals(MINUS_ONE_HALF, NumberCentral.createFraction(ONE,       MINUS_TWO));
      assertEquals(ONE_HALF,       NumberCentral.createFraction(MINUS_ONE, MINUS_TWO));
      assertEquals(ONE,            NumberCentral.createFraction(MINUS_ONE, MINUS_ONE));
      assertEquals(TWO,            NumberCentral.createFraction(MINUS_TWO, MINUS_ONE));
   }

   /**
    * Tests {@link NumberCentral#createInteger(byte[])}.
    */
   public void testCreateInteger() {
      // TODO
   }

   /**
    * Tests {@link NumberCentral#valueOf(double)}.
    */
   public void testValueOf_double() {

      final String messageStart = "An IllegalArgumentException should be thrown if the argument to " + NumberCentral.class.getName() + ".valueOf(double) is ";

      // Argument is Inf
      try {
         NumberCentral.valueOf(Double.POSITIVE_INFINITY);
         fail(messageStart + "positive infinity.");
      } catch (IllegalArgumentException e) { /* as expected  */ }

      // Argument is -Inf
      try {
         NumberCentral.valueOf(Double.NEGATIVE_INFINITY);
         fail(messageStart + "negative infinity.");
      } catch (IllegalArgumentException e) { /* as expected  */ }

      // Argument is NaN
      try {
         NumberCentral.valueOf(Double.NaN);
         fail(messageStart + "not a number (NaN).");
      } catch (IllegalArgumentException e) { /* as expected  */ }

      assertEquals(MINUS_TWO,        NumberCentral.valueOf(-2.0));
      assertEquals(MINUS_ONE,        NumberCentral.valueOf(-1.0));
      assertEquals(MINUS_ONE_HALF,   NumberCentral.valueOf(-0.5));
      assertEquals(ZERO,             NumberCentral.valueOf( 0.0));
      assertEquals(ONE_HALF,         NumberCentral.valueOf( 0.5));
      assertEquals(ONE,              NumberCentral.valueOf( 1.0));
      assertEquals(TWO,              NumberCentral.valueOf( 2.0));
      assertEquals(Double.MIN_VALUE, NumberCentral.valueOf(Double.MIN_VALUE).doubleValue(), 0.0);
      assertEquals(Double.MIN_VALUE, NumberCentral.valueOf(Double.MAX_VALUE).doubleValue(), 0.0);
   }

   /**
    * Tests {@link NumberCentral#valueOf(float)}.
    */
   public void testValueOf_float() {
      
      final String messageStart = "An IllegalArgumentException should be thrown if the argument to " + NumberCentral.class.getName() + ".valueOf(double) is ";

      // Argument is Inf
      try {
         NumberCentral.valueOf(Float.POSITIVE_INFINITY);
         fail(messageStart + "positive infinity.");
      } catch (IllegalArgumentException e) { /* as expected  */ }

      // Argument is -Inf
      try {
         NumberCentral.valueOf(Float.NEGATIVE_INFINITY);
         fail(messageStart + "negative infinity.");
      } catch (IllegalArgumentException e) { /* as expected  */ }

      // Argument is NaN
      try {
         NumberCentral.valueOf(Float.NaN);
         fail(messageStart + "not a number (NaN).");
      } catch (IllegalArgumentException e) { /* as expected  */ }
   }

   /**
    * Tests {@link NumberCentral#valueOf(long)}.
    */
   public void testValueOf_long() {
      assertEquals(Long.MIN_VALUE, NumberCentral.valueOf(Long.MIN_VALUE).longValue());
      assertEquals(MINUS_TWO,      NumberCentral.valueOf(-2l));
      assertEquals(MINUS_ONE,      NumberCentral.valueOf(-1l));
      assertEquals(ZERO,           NumberCentral.valueOf( 0l));
      assertEquals(ONE,            NumberCentral.valueOf( 1l));
      assertEquals(TWO,            NumberCentral.valueOf( 2l));
      assertEquals(Long.MAX_VALUE, NumberCentral.valueOf(Long.MAX_VALUE).longValue());
   }

   /**
    * Tests {@link NumberCentral#valueOf(int)}.
    */
   public void testValueOf_int() {
      assertEquals(Integer.MIN_VALUE, NumberCentral.valueOf(Integer.MIN_VALUE).intValue());
      assertEquals(MINUS_TWO,         NumberCentral.valueOf(-2));
      assertEquals(MINUS_ONE,         NumberCentral.valueOf(-1));
      assertEquals(ZERO,              NumberCentral.valueOf( 0));
      assertEquals(ONE,               NumberCentral.valueOf( 1));
      assertEquals(TWO,               NumberCentral.valueOf( 2));
      assertEquals(Integer.MAX_VALUE, NumberCentral.valueOf(Integer.MAX_VALUE).intValue(), Integer.MAX_VALUE);
   }

   /**
    * Tests {@link NumberCentral#valueOf(short)}.
    */
   public void testValueOf_short() {
      assertEquals(Short.MIN_VALUE, NumberCentral.valueOf(Short.MIN_VALUE).shortValue());
      assertEquals(MINUS_TWO,       NumberCentral.valueOf((short) -2));
      assertEquals(MINUS_ONE,       NumberCentral.valueOf((short) -1));
      assertEquals(ZERO,            NumberCentral.valueOf((short)  0));
      assertEquals(ONE,             NumberCentral.valueOf((short)  1));
      assertEquals(TWO,             NumberCentral.valueOf((short)  2));
      assertEquals(Short.MAX_VALUE, NumberCentral.valueOf(Short.MAX_VALUE).shortValue());
   }

   /**
    * Tests {@link NumberCentral#valueOf(byte)}.
    */
   public void testValueOf_byte() {
      assertEquals(Byte.MIN_VALUE, NumberCentral.valueOf(Byte.MIN_VALUE).byteValue());
      assertEquals(MINUS_TWO,      NumberCentral.valueOf((byte) -2));
      assertEquals(MINUS_ONE,      NumberCentral.valueOf((byte) -1));
      assertEquals(ZERO,           NumberCentral.valueOf((byte)  0));
      assertEquals(ONE,            NumberCentral.valueOf((byte)  1));
      assertEquals(TWO,            NumberCentral.valueOf((byte)  2));
      assertEquals(Byte.MAX_VALUE, NumberCentral.valueOf(Byte.MAX_VALUE).byteValue());
   }

   /**
    * Tests {@link NumberCentral#valueOf(BigInteger)}.
    */
   public void testValueOf_BigInteger() {

      final String message = "An IllegalArgumentException should be thrown if the argument to " + NumberCentral.class.getName() + ".valueOf(BigInteger) is null.";

      // Argument is null
      try {
         NumberCentral.valueOf((BigInteger) null);
         fail(message);
      } catch (IllegalArgumentException e) { /* as expected  */ }

      assertEquals(NEGATIVE_BIG_INTEGER, NumberCentral.valueOf(NEGATIVE_BIG_INTEGER).toBigInteger());
      assertEquals(ZERO,                 NumberCentral.valueOf(BigInteger.ZERO));
      assertEquals(POSITIVE_BIG_INTEGER, NumberCentral.valueOf(POSITIVE_BIG_INTEGER).toBigInteger());
   }

   /**
    * Tests {@link NumberCentral#valueOf(BigDecimal)}.
    */
   public void testValueOf_BigDecimal() {

      final String message = "An IllegalArgumentException should be thrown if the argument to " + NumberCentral.class.getName() + ".valueOf(BigDecimal) is null.";

      // Argument is null
      try {
         NumberCentral.valueOf((BigDecimal) null);
         fail(message);
      } catch (IllegalArgumentException e) { /* as expected  */ }

      assertEquals(MINUS_ONE_HALF, NumberCentral.valueOf(MINUS_ONE_HALF_BIG_DECIMAL));
      assertEquals(ZERO,           NumberCentral.valueOf(ZERO_BIG_DECIMAL));
      assertEquals(ONE_HALF,       NumberCentral.valueOf(ONE_HALF_BIG_DECIMAL));
      assertEquals(ONE,            NumberCentral.valueOf(ONE_BIG_DECIMAL));
      assertEquals(TWO,            NumberCentral.valueOf(TWO_BIG_DECIMAL));
   }

   /**
    * Tests {@link NumberCentral#createRandomInteger(int)}.
    */
   public void testCreateRandomInteger() {

      final String message = "An IllegalArgumentException should be thrown if the argument to " + NumberCentral.class.getName() + ".valueOf(BigDecimal) is less than 1.";

      // Argument is negative
      try {
         NumberCentral.createRandomInteger(-1);
         fail(message);
      } catch (IllegalArgumentException e) { /* as expected  */ }

      // Argument is zero
      try {
         NumberCentral.createRandomInteger(0);
         fail(message);
      } catch (IllegalArgumentException e) { /* as expected  */ }

      // Argument is positive
      for (int i=1; i < 300; i++) {
         doTestCreateRandomInteger(i);
      }
   }

   private void doTestCreateRandomInteger(int numBytes) {

      IntegerNumber n = NumberCentral.createRandomInteger(numBytes);
      assertNotNull("NumberCentral.createRandomInteger(int) returned null.",
                    n);

      byte[] byteArray = n.toByteArray();
      assertNotNull("The byte array returned by " + n.getClass().getName() + " returned null while the IntegerNumber contract prohibits this.",
                    byteArray);

      assertTrue("The byte array returned by " + n.getClass().getName() + " has length " + byteArray.length + " while it should not have been smaller than 1.",
                 byteArray.length >= 1);
      assertTrue("The byte array returned by " + n.getClass().getName() + " has length " + byteArray.length + " while it should not have been larger than " + numBytes + '.',
                 byteArray.length <= numBytes);
   }

   /**
    * Tests {@link NumberCentral#checkDivideByZero(RealNumber)}.
    */
   public void testCheckDivideByZero() {
      
      final String message = "An IllegalArgumentException should be thrown if the argument to " + NumberCentral.class.getName() + ".checkDivideByZero(RealNumber) is null.";

      // Argument is null
      try {
         NumberCentral.checkDivideByZero(null);
         fail(message);
      } catch (IllegalArgumentException e) { /* as expected  */ }

      // Argument represents the number ZERO
      try {
         NumberCentral.checkDivideByZero(ZERO);
         fail(message);
      } catch (ArithmeticException e) { /* as expected  */ }

      // Argument is non-null and not zero
      NumberCentral.checkDivideByZero(MINUS_TWO);
      NumberCentral.checkDivideByZero(MINUS_ONE);
      NumberCentral.checkDivideByZero(MINUS_ONE_HALF);
      NumberCentral.checkDivideByZero(ONE_HALF);
      NumberCentral.checkDivideByZero(ONE);
      NumberCentral.checkDivideByZero(TWO);
   }
}
