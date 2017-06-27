/*
 * $Id: NumberCentral.java,v 1.17 2002/10/01 18:47:57 znerd Exp $
 */
package org.znerd.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * A static factory that produces <code>RealNumber</code> objects.
 *
 * @version $Revision: 1.17 $ $Date: 2002/10/01 18:47:57 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public final class NumberCentral extends Object {

   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   /**
    * The <code>Random</code> object used to obtain pseudo-random numbers.
    */
   private static Random RANDOM_GENERATOR = new Random();

   /**
    * The version of JUMP.
    */
   private static String VERSION = "%%JUMP_VERSION%%";


   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns the current version of JUMP.
    *
    * @return
    *    the version of JUMP, for example <code>"%%JUMP_VERSION%%"</code>,
    *    never <code>null</code>.
    */
   public static String getVersion() {
      return VERSION;
   }

   /**
    * Computes the sum of 2 <code>RealNumber</code>s.
    *
    * @param a
    *    the first operand, not <code>null</code>.
    *
    * @param b
    *    the second operand, not <code>null</code>.
    *
    * @return
    *    the sum of <em>a</em> and <em>b</em>, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    */
   static RealNumber add(RealNumber a, RealNumber b)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("a", a, "b", b);

      if (a instanceof RationalNumber && b instanceof RationalNumber) {
         return ((RationalNumber) a).add((RationalNumber) b);
      } else {
         return Sum.createInstance(a, b);
      }
   }

   /**
    * Computes the product of 2 <code>RealNumber</code>s.
    *
    * @param a
    *    the first operand, not <code>null</code>.
    *
    * @param b
    *    the second operand, not <code>null</code>.
    *
    * @return
    *    the product of <em>a</em> and <em>b</em>, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    */
   static RealNumber multiply(RealNumber a, RealNumber b)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("a", a, "b", b);

      if (a instanceof RationalNumber && b instanceof RationalNumber) {
         return ((RationalNumber) a).multiply((RationalNumber) b);
      } else {
         return Product.createInstance(a, b);
      }
   }

   /**
    * Returns a fraction with the given numerator and denominator, both
    * being an <code>int</code>.
    *
    * @param numerator
    *    the numerator for the fraction.
    *
    * @param denominator
    *    the denominator for the fraction, not 0.
    *
    * @return
    *    the rational number, never <code>null</code>.
    *
    * @throws ArithmeticException
    *    if <code>denominator == 0</em>.
    */
   public static RationalNumber createFraction(int numerator, int denominator)
   throws ArithmeticException {
      return createFraction(valueOf(numerator), valueOf(denominator));
   }

   /**
    * Returns a fraction with the given numerator and denominator.
    *
    * @param numerator
    *    the numerator for the fraction, not <code>null</code>.
    *
    * @param denominator
    *    the denominator for the fraction, not <code>null</code>.
    *
    * @return
    *    the fraction, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>numerator == null || denominator == null</code>.
    *
    * @throws ArithmeticException
    *    if the denominator is zero, i.e.
    *    <code>denominator.equals({@link SmallIntegerNumber#ZERO})</code>.
    */
   public static RationalNumber createFraction(IntegerNumber numerator,
                                               IntegerNumber denominator)
   throws ArithmeticException, IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("numerator", numerator,
                                     "denominator", denominator);
      checkDivideByZero(denominator);

      // Result of division may be integer...
      IntegerNumber remainder = numerator.remainder(denominator);
      if (remainder.getSign() == 0) {
         return numerator.integerDivide(denominator);

      // ...or not.
      } else {
         return BasicRationalNumber.createInstance(numerator, denominator);
      }
   }

   /**
    * Returns an integer number from a byte array in two's complement
    * notation.
    *
    * @param bytes
    *    the two's complement byte array, not <code>null</code>.
    *
    * @return
    *    an integer number that has the same value as represented by the
    *    argument, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>bytes == null</code>.
    */
   public static IntegerNumber createInteger(byte[] bytes)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("bytes", bytes);

      // TODO: Return a SmallIntegerNumber if bytes.length <= 4

      if (bytes.length == 0) {
         return SmallIntegerNumber.ZERO;
      } else {
         return BigIntegerNumber.createInstance(new BigInteger(bytes));
      }
   }

   /**
    * Converts a <code>double</code> to a <code>RationalNumber</code>.
    *
    * @param n
    *    the value to be converted, not an infinite number, nor NaN.
    *
    * @return
    *    the result of the conversion, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>Double.isInfinite(n) || Double.isNaN(n)</code>
    */
   public static RationalNumber valueOf(double n)
   throws IllegalArgumentException {

      // Check preconditions
      if (Double.isInfinite(n)) {
         throw new IllegalArgumentException("Double.isInfinite(n)");
      } else if (Double.isNaN(n)) {
         throw new IllegalArgumentException("Double.isNaN(n)");
      }

      // Convert the double to a long
      long bits = Double.doubleToLongBits(n);

      // Check if the value is +0 or -0
      if (bits == 0x0000000000000000L || bits == 0x8000000000000000L) {
         return SmallIntegerNumber.ZERO;
      }

      // Determine sign, exponent and mantissa
      long sign = bits >>> 63;
      long exp = ((bits >>> 52) & 0x7ff) - 1075;
      long mantissa = (bits & 0x000fffffffffffffL) + 0x0010000000000000L;
      IntegerNumber base = valueOf(mantissa);

      // Multiply by the exponent
      RationalNumber factor = valueOf(2).pow(valueOf(exp));
      RationalNumber result = base.multiply(factor);

      // Fix sign, if necessary
      if (sign == 0) {
         return result;
      } else {
         return (RationalNumber) (result.negate());
      }
   }

   /**
    * Converts a <code>float</code> to a <code>RationalNumber</code>.
    *
    * @param n
    *    the value to be converted.
    *
    * @return
    *    the result of the conversion, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>Float.isInfinite(n) || Float.isNaN(n)</code>
    */
   public static RationalNumber valueOf(float n)
   throws IllegalArgumentException {
      // TODO: Let this valueOf(float) have its own implementation
      return valueOf((double) n);
   }

   /**
    * Converts a <code>long</code> to an <code>IntegerNumber</code>.
    *
    * @param n
    *    the value to be converted.
    *
    * @return
    *    the result of the conversion, never <code>null</code>.
    */
   public static IntegerNumber valueOf(long n) {
      // TODO: Let this valueOf(long) have its own implementation
      return valueOf(BigInteger.valueOf(n));
   }

   /**
    * Converts an <code>int</code> to an <code>IntegerNumber</code>.
    *
    * @param n
    *    the value to be converted.
    *
    * @return
    *    the result of the conversion, never <code>null</code>.
    */
   public static IntegerNumber valueOf(int n) {
      return SmallIntegerNumber.createInstance(n);
   }

   /**
    * Converts a <code>short</code> to an <code>IntegerNumber</code>.
    *
    * @param n
    *    the value to be converted.
    *
    * @return
    *    the result of the conversion, never <code>null</code>.
    */
   public static IntegerNumber valueOf(short n) {
      return SmallIntegerNumber.createInstance((int) n);
   }

   /**
    * Converts a <code>byte</code> to an <code>IntegerNumber</code>.
    *
    * @param n
    *    the value to be converted.
    *
    * @return
    *    the result of the conversion, never <code>null</code>.
    */
   public static IntegerNumber valueOf(byte n) {
      return SmallIntegerNumber.createInstance((int) n);
   }

   /**
    * Converts a <code>BigInteger</code> to an <code>IntegerNumber</code>.
    *
    * @param n
    *    the value to be converted, not <code>null</code>.
    *
    * @return
    *    the result of the conversion, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   public static IntegerNumber valueOf(BigInteger n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      return BigIntegerNumber.createInstance(n);
   }

   /**
    * Converts a <code>BigDecimal</code> to a <code>RationalNumber</code>.
    *
    * @param n
    *    the value to be converted, not <code>null</code>.
    *
    * @return
    *    the result of the conversion, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   public static RationalNumber valueOf(BigDecimal n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // XXX: Sun should provide read access to the BigInteger.intVal field,
      //      we are working around this

      // Compute the numerator
      int intScale = n.scale();
      IntegerNumber numerator = valueOf(n.movePointRight(intScale).toBigInteger());

      // Compute the denominator
      IntegerNumber value10 = valueOf(10);
      IntegerNumber scale   = valueOf(intScale);
      IntegerNumber denominator = (IntegerNumber) value10.pow(scale);

      return createFraction(numerator, denominator);
   }

   /**
    * Returns an <code>IntegerNumber</code> with a random value. The maximum
    * number of bytes for the integer number must be specified.
    *
    * @param numBytes
    *    the maximum number of bytes for the constructed number, at least 1.
    *
    * @return
    *    a random integer, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>numBytes &lt; 1</code>.
    */
   public static IntegerNumber createRandomInteger(int numBytes)
   throws IllegalArgumentException {

      // Check preconditions
      if (numBytes < 1) {
         throw new IllegalArgumentException("numBytes < 1");
      }

      // Create a byte array with random content
      int n = (RANDOM_GENERATOR.nextInt() % numBytes) + 1;
      n = n<0 ? -n : n;
      n %= numBytes;
      n++;
      byte[] array = new byte[n];
      RANDOM_GENERATOR.nextBytes(array);

      // Create a BigInteger and from that an IntegerNumber
      BigInteger bigInteger = new BigInteger(array);
      return BigIntegerNumber.createInstance(bigInteger);
   }

   /**
    * Checks if dividing by the specified number would result in a division by
    * zero. If the argument indeed represents zero, then an
    * {@link ArithmeticException} is thrown.
    *
    * @param n
    *    the number to compare with zero, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    *
    * @throws ArithmeticException
    *    if <code>n.{@link RealNumber#getSign() getSign}() == 0</code>.
    */
   public static void checkDivideByZero(RealNumber n)
   throws IllegalArgumentException, ArithmeticException {
      MandatoryArgumentChecker.check("n", n);
      if (n.getSign() == 0) {
         throw new ArithmeticException("divide by zero");
      }
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>NumberCentral</code>. This constructor is marked
    * as private, because no instances of this class should be created.
    */
   private NumberCentral() {
      // empty
   }
}
