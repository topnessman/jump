/*
 * $Id: IntegerNumber.java,v 1.13 2004/04/15 21:50:12 znerd Exp $
 */
package org.znerd.math;

import java.math.*;

/**
 * An immutable integer number. The base for all integer numbers. It extends
 * {@link RationalNumber} by offering narrowed numeric computations for
 * integer numbers.
 *
 * <p />Some <code>IntegerNumber</code> numeric computations are narrowed
 * compared to their more general {@link RationalNumber} equivalents.
 * For instance, adding two integer numbers will result in a integer number.
 * The computations concerned are:
 *
 * <dl>
 *    <dt><code>add(IntegerNumber)</code></dt>
 *    <dd>adding two integer numbers will result in another integer
 *    number</dd>
 *
 *    <dt><code>subtract(RationalNumber)</code></dt>
 *    <dd>subtracting one rational number from another will result in an
 *    integer number</dd>
 *
 *    <dt><code>multiply(RationalNumber)</code></dt>
 *    <dd>multiplying two integer numbers will result in another integer
 *    number</dd>
 *
 * </dl>
 *
 * <p />Concrete subclasses must at least provide implementations for the
 * following methods:
 *
 * <dl>
 *    <dt><code>getSign()</code></dt>
 *    <dd>Determines the sign of this number</dd>
 *
 *    <dt><code>negate()</code></dt>
 *    <dd>Computes -this</dd>
 *
 *    <dt><code>add(IntegerNumber)</code></dt>
 *    <dd>Computes this+<em>n</em>, where <em>n</em> is an integer number</dd>
 *
 *    <dt><code>multiply(IntegerNumber)</code></dt>
 *    <dd>Computes this*<em>n</em>, where <em>n</em> is an integer number</dd>
 *
 *    <dt><code>integerDivide(IntegerNumber)</code></dt>
 *    <dd>Computes the integer result of a division</dd>
 *
 *    <dt><code>longValue()</code></dt>
 *    <dd>Converts the value of this number to a <code>long</code></dd>
 *
 *    <dt><code>doubleValue()</code></dt>
 *    <dd>Converts the value of this number to a <code>double</code></dd>
 *
 *    <dt><code>toByteArray()</code></dt>
 *    <dd>Converts the value of this number to a set of bytes that represent
 *    the two's complement of this number</dd>
 * </dl>
 *
 * <p />Subclasses are encouraged to override as many methods as they can in
 * order to optimize the implementation. Those methods marked as
 * <code>final</code> need no further optimization.
 *
 * @version $Revision: 1.13 $ $Date: 2004/04/15 21:50:12 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public abstract class IntegerNumber extends RationalNumber {

   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Creates a new <code>IntegerNumber</code> with the specified sign and the
    * specified textual representation. This constructor can only be called
    * from subclass constructors, since this class is abstract.
    *
    * @param sign
    *    the sign of this number, either -1 if this number is negative, 0 if
    *    this number is 0, or 1 is this number is positive.
    *
    * @param asString
    *    textual presentation of this number, not <code>null</code>.
    */
   protected IntegerNumber(int sign, String asString) {
      super(sign, asString);
   }


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   protected int compareToImpl(RationalNumber n)
   throws CanNotCompareException {

      if (n instanceof IntegerNumber) {
         return compareTo((IntegerNumber) n);
      }

      return super.compareTo(n);
   }

   /**
    * Compares this number with the specified integer number, first level.
    *
    * <p />This method calls {@link #compareToImpl(IntegerNumber)}. If that
    * method throws a {@link CanNotCompareException}, then it will attempt to
    * use a fallback comparison algorithm based on {@link #toByteArray()}.
    *
    * @param n
    *    the number to compare to, guaranteed to be not <code>null</code>.
    *
    * @return
    *    -1 if this &lt; <em>n</em>,
    *     0 if this ==   <em>n</em>,
    *     1 if this &gt; <em>n</em>.
    *
    * @throws CanNotCompareException
    *    if the comparison failed.
    */
   protected final int compareTo(IntegerNumber n)
   throws CanNotCompareException {

      try {
         return compareToImpl(n);
      } catch (CanNotCompareException exception) {

         // XXX: Optimize this
         byte[] a = toByteArray();
         byte[] b = n.toByteArray();

         return new BigInteger(a).compareTo(new BigInteger(b));
      }
   }

   /**
    * Compares this number with the specified integer number, second level.
    *
    * <p />This method is called from {@link #compareTo(IntegerNumber)}. The
    * implementation of this method in class {@link IntegerNumber} just throws
    * a {@link CanNotCompareException} to indicate it does not provide an
    * optimized algorithm for comparing this integer number with the argument
    * integer number. Subclasses are encouraged to override this method.
    *
    * @param n
    *    the number to compare to, guaranteed to be not <code>null</code>.
    *
    * @return
    *    -1 if this &lt; <em>n</em>,
    *     0 if this ==   <em>n</em>,
    *     1 if this &gt; <em>n</em>.
    *
    * @throws CanNotCompareException
    *    if the comparison failed.
    */
   protected int compareToImpl(IntegerNumber n)
   throws CanNotCompareException {
      throw new CanNotCompareException(this, n);
   }

   public final RealNumber abs() {
      return absInteger();
   }

   /**
    * Computes |this| and returns an <code>IntegerNumber</code>. The returned
    * value is always &gt;= 0.
    *
    * <p />The only difference between this method and {@link #abs()} is that
    * this method is explicitly defined to return an
    * <code>IntegerNumber</code>. In fact {@link #abs()} calls this method.
    *
    * @return
    *    the absolute of this, not <code>null</code> and always with a value
    *    &gt;= 0.
    */
   public final IntegerNumber absInteger() {
      if (getSign() < 0) {
         return negateInteger();
      } else {
         return this;
      }
   }

   public final RealNumber negate() {
      return negateInteger();
   }

   /**
    * Computes -this and returns an <code>IntegerNumber</code>.
    *
    * <p />The only difference between this method and {@link #negate()} is
    * that this method is explicitly defined to return an
    * <code>IntegerNumber</code>. In fact {@link #negate()} calls this method.
    *
    * @return
    *    the negative of this, not <code>null</code>.
    */
   public IntegerNumber negateInteger() {
      return multiply(SmallIntegerNumber.MINUS_ONE);
   }

   public RealNumber invert() throws ArithmeticException {
      return NumberCentral.createFraction(SmallIntegerNumber.ONE, this);
   }

   public RationalNumber add(RationalNumber n)
   throws IllegalArgumentException {

      // Filter IntegerNumbers out
      if (n instanceof IntegerNumber) {
         return ((IntegerNumber) this).add((IntegerNumber) n);
      }

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // Compute fraction
      IntegerNumber denominator = n.getDenominator();
      IntegerNumber numerator   = ((IntegerNumber) this).multiply(denominator).add(n.getNumerator());

      return NumberCentral.createFraction(numerator, denominator);
   }

   /**
    * Computes this + <em>n</em>, where <em>n</em> is an integer number.
    *
    * @param n
    *    the number to add to this, not <code>null</code>.
    *
    * @return
    *    the sum of this and <em>n</em>, never <code>null</code>.
    *
    * @throws IllegalArgumentException if the argument is
    *    <code>null</code>.
    */
   public abstract IntegerNumber add(IntegerNumber n)
   throws IllegalArgumentException;

   public RationalNumber subtract(RationalNumber n)
   throws IllegalArgumentException {

      // Filter IntegerNumbers out
      if (n instanceof IntegerNumber) {
         return subtract((IntegerNumber) n);
      }

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // Compute fraction
      IntegerNumber denominator = n.getDenominator();
      IntegerNumber numerator   = ((IntegerNumber) this).multiply(denominator).subtract(n.getNumerator());

      return NumberCentral.createFraction(numerator, denominator);
   }

   /**
    * Computes this - <em>n</em>, where <em>n</em> is an integer number.
    *
    * @param n
    *    the number to subtract from this, not <code>null</code>.
    *
    * @return
    *    this minus <em>n</em>, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   public IntegerNumber subtract(IntegerNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // Call add()
      IntegerNumber negated = (IntegerNumber) n.negate();
      return ((IntegerNumber) this).add(negated);
   }

   public RationalNumber multiply(RationalNumber n)
   throws IllegalArgumentException {

      // Filter IntegerNumbers out
      if (n instanceof IntegerNumber) {
         return ((IntegerNumber) this).multiply((IntegerNumber) n);
      }

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // Compute fraction
      IntegerNumber numerator   = ((IntegerNumber) this).multiply(n.getNumerator());
      IntegerNumber denominator = n.getDenominator();

      return NumberCentral.createFraction(numerator, denominator);
   }

   /**
    * Computes this * <em>n</em>, where <em>n</em> is an integer number.
    *
    * @param n
    *    the number to multiply this by, not <code>null</code>.
    *
    * @return
    *    the product of this and <em>n</em>, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   public abstract IntegerNumber multiply(IntegerNumber n)
   throws IllegalArgumentException;

   public RationalNumber divide(RationalNumber n)
   throws IllegalArgumentException, ArithmeticException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // Compute fraction
      IntegerNumber numerator = ((IntegerNumber) this).multiply(n.getDenominator());
      IntegerNumber denominator = n.getNumerator();

      return NumberCentral.createFraction(numerator, denominator);
   }

   public RationalNumber powImpl(IntegerNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      if (n.getSign() == 1) {
         IntegerNumber result = this;
         for (n=n.dec(); n.getSign()==1; n=n.dec()) {
            result = ((IntegerNumber) this).multiply(result);
         }
         return result;
      }

      // x**0 is always 1
      if (n.equals(SmallIntegerNumber.ZERO)) {
         return SmallIntegerNumber.ONE;
      }

      // n must be < 0

      // x ** n  (n<0) equals 1/(x ** -n)
      IntegerNumber absolute = (IntegerNumber) n.negate();
      RationalNumber invertedResult = pow(absolute);
      return (RationalNumber) (invertedResult.invert());
   }

   /**
    * Computes this-1.
    *
    * @return this decreased by 1.
    */
   public IntegerNumber dec() {
      return subtract(SmallIntegerNumber.ONE);
   }

   /**
    * Computes this+1.
    *
    * @return this increased by 1.
    */
   public IntegerNumber inc() {
      return ((IntegerNumber) this).add(SmallIntegerNumber.ONE);
   }

   /**
    * Computes this!. The value of this cannot be negative.
    *
    * @return
    *    the faculty of this, not <code>null</code>.
    *
    * @throws ArithmeticException
    *    if the value of this number is &lt; 0.
    */
   public IntegerNumber fac()
   throws ArithmeticException {

      // Check preconditions
      if (getSign() < 0) {
         throw new ArithmeticException("Cannot compute faculty of negative value.");
      }

      // fac(0) == 1
      if (getSign() == 0) {
         return SmallIntegerNumber.ZERO;
      }

      IntegerNumber one = SmallIntegerNumber.ONE;

      IntegerNumber result = this;
      IntegerNumber n = this.dec();
      while (n.compareTo(one) == 1) {
         result = result.multiply(n);
         n = n.dec();
      }
      return result;
   }

   /**
    * Computes the remainder of this/<em>n</em>, where <em>n</em> is an
    * integer number.
    *
    * @param n the number to divide this by.
    * @return the remainder.
    * @throws ArithmeticException if the value of the argument number is
    *    zero.
    * @throws IllegalArgumentException if the argument is
    *    <code>null</code>.
    */
   public IntegerNumber remainder(IntegerNumber n)
   throws ArithmeticException, IllegalArgumentException {
      return (IntegerNumber) subtract(integerDivide(n).multiply(n)).abs();
   }

   /**
    * Computes the greatest common divisor of this and <em>n</em>.
    *
    * @param n
    *    the other integer number.
    *
    * @return
    *    the greatest common divisor.
    *
    * @throws IllegalArgumentException
    *    if <em>n</em> is <code>null</code>.
    */
   public IntegerNumber gcd(IntegerNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // u is initially |this|
      IntegerNumber u = (IntegerNumber) abs();

      // If n==0 then return |this|
      if (n.getSign() == 0) {
         return u;
      }

      // n = |n|
      n = (IntegerNumber) n.abs();

      // If this==0 then return |n|
      if (u.getSign() == 0) {
         return n;
      }

      while (u.getSign() == 1) {
         if (u.compareTo(n) == -1) {
            // Swap u and n
            IntegerNumber temp = u;
            u = n;
            n = temp;
         }
         u = u.remainder(n);
      }

      return n;
   }

   /**
    * Determines if this and <em>n</em> are relative primes.
    *
    * @param n
    *    the other integer number.
    *
    * @return
    *    <code>true</code> if this and <em>n</em> are relative primes,
    *    <code>false</code> otherwise.
    *
    * @throws IllegalArgumentException
    *    if <em>n</em> is <code>null</code>.
    */
   public boolean isRelativePrime(IntegerNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      return (gcd(n).compareTo(SmallIntegerNumber.ONE) == 0);
   }

   /**
    * Converts the value of this number to a <code>BigInteger</code>.
    *
    * @return
    *    a <code>BigInteger</code> with the value of this.
    */
   public BigInteger toBigInteger() {
      return new BigInteger(toByteArray());
   }

   /**
    * Converts the value of this number to a <code>BigDecimal</code>. A
    * precision nor a rounding mode need to be specified for integer numbers.
    *
    * @return
    *    a <code>BigDecimal</code> with the exact value of this.
    */
   public BigDecimal toBigDecimal() {
      return new BigDecimal(toBigInteger());
   }

   /**
    * Converts the value of this number to a <code>BigDecimal</code> with the
    * specified precision. The <em>precision</em> argument is ignored,
    * because no rounding is needed.
    *
    * @param precision
    *    the number of digits behind the decimal point.
    *
    * @return
    *    a <code>BigDecimal</code> with the exact value of this.
    *
    * @throws IllegalArgumentException
    *    if <em>precision</em> &lt; 0
    */
   public BigDecimal toBigDecimal(int precision)
   throws IllegalArgumentException {
      // XXX: Improve the implementation of this method toBigDecimal(int)
      return toBigDecimal();
   }

   /**
    * Converts the value of this number to a <code>BigDecimal</code> with the
    * specified precision and rounding mode. The <em>precision</em> and
    * <em>roundingMode</em> arguments are ignored, because no rounding is
    * needed.
    *
    * @param precision
    *    the number of digits behind the decimal point.
    *
    * @param roundingMode
    *    the rounding mode to use, one of the modes defined
    *    in class <code>BigDecimal</code>.
    *
    * @return
    *    a <code>BigDecimal</code> with the rounded value of this.
    *
    * @throws IllegalArgumentException
    *    if one of the following applies:
    *    <ol>
    *       <li><em>precision</em> &lt; 0
    *       <li><em>roundingMode</em> does not have a valid value
    *    </ol>
    */
   public BigDecimal toBigDecimal(int precision, int roundingMode) {

      // XXX: Look into this
      return toBigDecimal();
   }

   public final IntegerNumber trunc() {
      return this;
   }

   /**
    * Returns the two's-complement representation of this integer number.
    * The array is big-endian (i.e., the most significant byte is in the
    * [0] position). The array contains the minimum number of bytes required
    * to represent the number.
    *
    * @return a byte array containing the bits of this integer number.
    */
   public abstract byte[] toByteArray();

   /**
    * Computes the integer result of this/<em>n</em>, where <em>n</em> is an
    * integer number.
    *
    * @param n the number to divide this by.
    * @return the integer result of this divided by <em>n</em>.
    * @throws ArithmeticException if the value of the argument number is
    *    zero.
    * @throws IllegalArgumentException if the argument is
    *    <code>null</code>.
    */
   public abstract IntegerNumber integerDivide(IntegerNumber n)
   throws ArithmeticException, IllegalArgumentException;
}
