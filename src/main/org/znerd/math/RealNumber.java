/*
 * $Id: RealNumber.java,v 1.17 2002/10/01 18:53:42 znerd Exp $
 */
package org.znerd.math;

import java.math.*;

/**
 * An immutable real number. A number of basic numeric operations are defined,
 * like:
 *
 * <blockquote><dl>
 *    <dt>{@link #negate()}</dt>
 *    <dd>Computes <code>-this</code></dd>
 *
 *    <dt>{@link #invert()}</dt>
 *    <dd>Computes <code>1/this</code></dd>
 *
 *    <dt>{@link #add(RealNumber)}</dt>
 *    <dd>Computes <code>this + <em>n</em></code></dd>
 *
 *    <dt>{@link #subtract(RealNumber)}</dt>
 *    <dd>Computes <code>this - <em>n</em></code></dd>
 *
 *    <dt>{@link #multiply(RealNumber)}</dt>
 *    <dd>Computes <code>this * <em>n</em></code></dd>
 *
 *    <dt>{@link #divide(RealNumber)}</dt>
 *    <dd>Computes <code>this / <em>n</em></code></dd>
 *
 *    <dt>{@link #pow(RealNumber)}</dt>
 *    <dd>Computes <code>this<sup><em>n</em></sup></code></dd>
 * </dl></blockquote>
 *
 * <p />Examples of real numbers are 3, -1.68, the square root of 3, 2/3,
 * <em>pi</em>, 18.2<sup>2.3</sup> and <em>e</em>.
 *
 * <p />Real numbers can be cast to native Java numbers with these conversion
 * methods:
 *
 * <blockquote><dl>
 *    <dt>{@link #longValue()}</dt>
 *    <dd>Converts this number to a <code>long</code>.
 *    Use {@link #fitsLong()} to make sure the conversion will succeed</dd>
 *
 *    <dt>{@link #intValue()}</dt>
 *    <dd>Converts this number to an <code>int</code>.
 *    Use {@link #fitsInt()} to make sure the conversion will succeed</dd>
 *
 *    <dt>{@link #shortValue()}</dt>
 *    <dd>Converts this number to a <code>short</code>. Use
 *        {@link #fitsShort()} to make sure the conversion will succeed</dd>
 *
 *    <dt>{@link #byteValue()}</dt>
 *    <dd>Converts this number to a <code>byte</code>.
 *    Use {@link #fitsByte()} to make sure the conversion will succeed</dd>
 *
 *    <dt>{@link #doubleValue()}</dt>
 *    <dd>Converts this number to a <code>double</code>.
 *    Use {@link #fitsDouble()} to make sure the conversion will succeed</dd>
 *
 *    <dt>{@link #floatValue()}</dt>
 *    <dd>Converts this number to a <code>float</code>.
 *    Use {@link #fitsFloat()} to make sure the conversion will succeed</dd>
 * </dl></blockquote>
 *
 * <p />Use the {@link NumberCentral} to obtain <code>RealNumber</code>
 * instances. For instance:
 *
 * <blockquote><code>RealNumber n = {@link NumberCentral}.{@link NumberCentral#valueOf(double) valueOf}(0.399);</code></blockquote>
 *
 * <p />PENDING: The names of the following methods may be changed in the future
 * to reflect the expected change in the syntax of the Java language syntax to
 * include operator overloading based on method names (see the section
 * <a href="http://java.sun.com/people/jag/FP.html#overloading">Operator Overloading</a>
 * in <em>The Evolution of Numerical Computing in Java</em>
 * by James Gosling.)
 *
 * <ul>
 *    <li>{@link #negate()}             --&gt; <em>uminus</em></li>
 *    <li>{@link #add(RealNumber)}      --&gt; <em>plus</em></li>
 *    <li>{@link #subtract(RealNumber)} --&gt; <em>minus</em></li>
 *    <li>{@link #multiply(RealNumber)} --&gt; <em>times</em></li>
 * </ul>
 *
 * <p />Concrete subclasses should provide implementations for the following
 * methods:
 *
 * <blockquote><dl>
 *    <dt>{@link #toBigDecimal(int,int)}</dt>
 *    <dd>Converts the value of this
 *        number to a {@link BigDecimal} with the specified precision,
 *        using the specified rounding mode.</dd>
 *
 *    <dt>{@link #trunc()}</dt>
 *    <dd>Truncates the value of this number to an {@link IntegerNumber}.</dd>
 * </dl></blockquote>
 *
 * @version $Revision: 1.17 $ $Date: 2002/10/01 18:53:42 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 *
 * @see NumberCentral
 */
public abstract class RealNumber
extends Number
implements Comparable, RoundingModes {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Checks that the specified radix is valid. If the radix is smaller than
    * the minimum (2) or greater than the maximum (see {@link #MAXIMUM_RADIX},
    * then an {@link IllegalArgumentException} is thrown.
    *
    * @param radix
    *    the radix to check.
    *
    * @throws IllegalArgumentException
    *    if <code>radix &lt; 2 || radix &gt; </code>{@link #MAXIMUM_RADIX}.
    */
   static void checkRadix(int radix) throws IllegalArgumentException {
      if (radix < 2) {
         throw new IllegalArgumentException("The specified radix is " + radix + ", which is smaller than than the minimum value for a radix, 2.");
      } else if (radix > MAXIMUM_RADIX) {
         throw new IllegalArgumentException("The specified radix is " + radix + ", which is greater than the maximum value for a radix, " + MAXIMUM_RADIX + '.');
      }
   }


   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   /**
    * The maximum value for a radix. The value of this field is 255.
    */
   public final static int MAXIMUM_RADIX = 255;


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>RealNumber</code> object.
    *
    * <p />The sign of the value needs to be specified. Any negative value is
    * interpreted as meaning that the value of this number is negative. Any
    * positive value is interpreted as meaning that the value of this number
    * is positive.
    *
    * @param sign
    *    the sign of this number; -1 if this number is smaller than zero, 0 is
    *    this number is 0 or 1 if this number is greater than zero.
    *
    * @param asString
    *    textual presentation of this number, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>asString == null</code>.
    */
   protected RealNumber(int sign, String asString)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("asString", asString);

      // Normalize the sign to -1, 0 or 1
      if (sign > 0) {
         sign = 1;
      } else if (sign < 0) {
         sign = -1;
      }

      // Store the sign and the string
      _sign     = sign;
      _asString = asString;
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The sign of this number. This field is initialized by the constructor.
    * The value of this field is -1, 0, or 1 as this number is negative,
    * zero, or positive.
    *
    * <p />The value of this field is returned by {@link #getSign()}.
    */
   private final int _sign;

   /**
    * Textual presentation of this number.
    *
    * <p />The value of this field is returned by {@link #toString()}.
    */
   private final String _asString;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   public final String toString() {
      return _asString;
   }

   public final boolean equals(Object o) {
      if (o instanceof RealNumber) {
         RealNumber n = (RealNumber) o;
         return compareTo(n) == 0;
      }

      return false;
   }

   /**
    * Determines the sign of this number. This method returns -1, 0, or 1 as
    * this number is negative, zero, or positive.
    *
    * @return
    *    the sign of this number, either -1, 0 or 1.
    */
   public final int getSign() {
      return _sign;
   }

   /**
    * Compares this object with the specified object.
    *
    * <p />This method calls {@link #compareTo(RealNumber)}.
    *
    * @param o
    *    the object to compare to, not <code>null</code>.
    *
    * @return
    *    -1 if this object is considered less than <em>o</em>,
    *     0 if this object is considered equal     <em>n</em>,
    *     1 if this object is greater than         <em>n</em>.
    *
    * @throws NullPointerException
    *    if <code>n == null</code>.
    *
    * @throws ClassCastException
    *    if <code>(n instanceof RealNumber) == false</code>.
    */
   public final int compareTo(Object o)
   throws NullPointerException, ClassCastException {

      // Cast and risk a ClassCastException
      return compareTo((RealNumber) o);
   }

   /**
    * Compares this number with the specified number.
    *
    * <p />This method attempts to compare this number with the specified
    * number based on the sign. If the signs are equal,
    * {@link #compareToImpl(RealNumber)} is called. If that method throws a
    * {@link CanNotCompareException}, then it is attempted to return the
    * negated result of <code>n.</code>{@link #compareToImpl(RealNumber)}. If
    * that method throws an exception as well, then it is thrown up to the
    * caller of this method.
    *
    * @param n
    *    the number to compare to, not <code>null</code>.
    *
    * @return
    *    -1 if this &lt; <em>n</em>,
    *     0 if this ==   <em>n</em>,
    *     1 if this &gt; <em>n</em>.
    *
    * @throws NullPointerException
    *    if <code>n == null</code>.
    */
   public final int compareTo(RealNumber n)
   throws NullPointerException {

      // Check preconditions
      if (n == null) {
         throw new NullPointerException("n");
      }

      // Get the signs
      int thisSign = getSign();
      int thatSign = n.getSign();

      // Compare based on signs
      if (thisSign != thatSign) {
         if (thisSign > thatSign) return  1;
         if (thisSign < thatSign) return -1;
         return 0;
      }

      // Try this.compareToImpl() and n.compareToImpl()
      try {
         return compareToImpl(n);
      } catch (CanNotCompareException exception) {
         return -n.compareToImpl(this);
      }
   }

   /**
    * Compares this number with the specified number, second level.
    *
    * <p />The implementation of this method in class {@link RealNumber}
    * throws a {@link CanNotCompareException}. Concrete subclasses are
    * encouraged to improve this behaviour by overriding this method.
    *
    * <p />Note that this method does not check if <code>n == null</code>.
    * This is already done in {@link #compareTo(RealNumber)}.
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
   protected int compareToImpl(RealNumber n)
   throws CanNotCompareException {
      throw new CanNotCompareException(this, n);
   }

   /**
    * Rounds to the specified radix, using the specified precision and
    * rounding mode.
    *
    * <p />If rounding is not supported by this class, then an
    * {@link UnsupportedOperationException} is thrown. The implementation of
    * this method in class {@link RealNumber} throws such an exception.
    *
    * @param radix
    *    the radix, always &gt;= 2 and &lt;= {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @param precision
    *    the precision, always &gt;= 1.
    *
    * @param roundingMode
    *    the rounding mode, never <code>null</code>.
    *
    * @return
    *    the rounded number, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>radix &lt; 2
    *          || radix &gt; {@link RealNumber#MAXIMUM_RADIX}
    *          || precision &lt; 1
    *          || roundingMode == null</code>.
    *
    * @throws UnsupportedOperationException
    *    if rounding is not supported by this class.
    */
   public DigitSet round(int radix, int precision, RoundingMode roundingMode)
   throws IllegalArgumentException, UnsupportedOperationException {
      // TODO: Improve the postconditions for this class
      throw new UnsupportedOperationException();
   }

   /**
    * Computes |this|. The returned value is always &gt;= 0.
    *
    * @return
    *    the absolute of this, not <code>null</code> and always with a value
    *    &gt;= 0.
    */
   public RealNumber abs() {
      if (getSign() == -1) {
         return negate();
      } else {
         return this;
      }
   }

   /**
    * Computes -this.
    *
    * @return
    *    the negative of this, not <code>null</code>.
    */
   public RealNumber negate() {
      return multiply(SmallIntegerNumber.MINUS_ONE);
   }

   /**
    * Computes 1/this.
    *
    * @return
    *    the inverse of this, not <code>null</code>.
    *
    * @throws ArithmeticException
    *    if the value of this is zero.
    */
   public RealNumber invert() throws ArithmeticException {
      return pow(SmallIntegerNumber.MINUS_ONE);
   }

   /**
    * Computes this + <em>n</em>, where <em>n</em> is a real number.
    *
    * @param n
    *    the number to add to this, not <code>null</code>.
    *
    * @return
    *    the sum of this and <em>n</em>, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   public RealNumber add(RealNumber n) throws IllegalArgumentException {
      // TODO: Use an approach with an addImpl() method
      return NumberCentral.add(this, n);
   }

   /**
    * Computes this - <em>n</em>, where <em>n</em> is a real number.
    *
    * <p />The implementation of this method in class {@link RealNumber} calls
    * {@link #add(RealNumber)} with <code>n.negate()</code> as the
    * argument.
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
   public RealNumber subtract(RealNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      return add(n.negate());
   }

   /**
    * Computes this * <em>n</em>, where <em>n</em> is a real number.
    *
    * @param n
    *    the factor, the number to multiply this by, not <code>null</code>.
    *
    * @return
    *    the product of this and <em>n</em>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   public RealNumber multiply(RealNumber n)
   throws IllegalArgumentException {
      // TODO: Use an approach with a multiplyImpl() method
      return NumberCentral.multiply(this, n);
   }

   /**
    * Computes this/<em>n</em>, where <em>n</em> is a real number.
    *
    * <p />The implementation of this method in class {@link RealNumber} calls
    * {@link #multiply(RealNumber)} with <code>n.invert()</code> as the
    * argument.
    *
    * @param n
    *    the number to divide this by, not <code>null</code>.
    *
    * @return
    *    this divided by <em>n</em>, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    *
    * @throws ArithmeticException
    *    if the value of <em>n</em> is zero.
    */
   public RealNumber divide(RealNumber n)
   throws IllegalArgumentException, ArithmeticException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      return multiply(n.invert());
   }

   /**
    * Computes this<sup><em>n</em></sup>, where <em>n</em> is a real number.
    *
    * <p />This method first checks the precondition and then calls
    * {@link #powImpl(RealNumber)}. If that method throws an
    * {@link UnsupportedOperationException} then a {@link Power} instance is
    * returned.
    *
    * @param n
    *    the exponent, not <code>null</code>.
    *
    * @return
    *    this raised to the power of <em>n</em>, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   public RealNumber pow(RealNumber n)
   throws IllegalArgumentException {

      // Check precondition
      MandatoryArgumentChecker.check("n", n);

      // Allow subclass to perform this operation
      try {
         return powImpl(n);

      // ...or fallback to a default way
      } catch (UnsupportedOperationException exception) {
         return Power.createInstance(this, n);
      }
   }

   /**
    * Computes this<sup><em>n</em></sup>, where <em>n</em> is a real number,
    * second level.
    *
    * <p />This method may be implemented by subclasses. The implementation of
    * this method in class {@link RealNumber} throws an
    * {@link UnsupportedOperationException}.
    *
    * @param n
    *    the exponent, guaranteed not to be <code>null</code> if called by
    *    {@link RealNumber#pow(RealNumber)}.
    *
    * @return
    *    this raised to the power of <em>n</em>, never <code>null</code>.
    *
    * @throws UnsupportedOperationException
    *    if this operation is not supported by this class.
    */
   protected RealNumber powImpl(RealNumber n) {
      throw new UnsupportedOperationException();
   }

   /**
    * Determines if the truncated value of this number fits in a
    * <code>long</code>.
    *
    * @return
    *    <code>true</code> iff this value fits in a <code>long</code>.
    */
   public boolean fitsLong() {
      byte[] byteArray = trunc().toByteArray();
      return (byteArray.length <= 8);
   }

   /**
    * Determines if the truncated value of this number fits in an
    * <code>int</code>.
    *
    * @return
    *    <code>true</code> iff this value fits in an <code>int</code>.
    */
   public boolean fitsInt() {
      byte[] byteArray = trunc().toByteArray();
      return (byteArray.length <= 4);
   }

   /**
    * Determines if the truncated value of this number fits in a
    * <code>short</code>.
    *
    * @return
    *    <code>true</code> iff this value fits in a <code>short</code>.
    */
   public boolean fitsShort() {
      byte[] byteArray = trunc().toByteArray();
      return (byteArray.length <= 2);
   }

   /**
    * Determines if the truncated value of this number fits in a
    * <code>byte</code>.
    *
    * @return
    *    <code>true</code> iff this value fits in a <code>byte</code>.
    */
   public boolean fitsByte() {
      byte[] byteArray = trunc().toByteArray();
      return (byteArray.length <= 1);
   }

   /**
    * Determines if the rounded value of this number fits in a
    * <code>double</code>.
    *
    * @return
    *    <code>true</code> iff this value fits in a <code>double</code>.
    */
   public boolean fitsDouble() {
      // TODO: Implement this method fitsDouble()
      return true;
   }

   /**
    * Determines if the rounded value of this number fits in a
    * <code>float</code>.
    *
    * @return
    *    <code>true</code> iff this value fits in a <code>float</code>.
    */
   public boolean fitsFloat() {
      // TODO: Implement this method fitsDouble()
      return true;
   }

   /**
    * Returns the value of this number as a <code>long</code>. This may
    * involve rounding.
    *
    * @return
    *    the numeric value represented by this object after conversion
    *    to type <code>long</code>.
    */
   public long longValue() {
      return trunc().longValue();
   }

   /**
    * Returns the value of this number as an <code>int</code>. This may
    * involve rounding.
    *
    * @return
    *    the numeric value represented by this object after conversion
    *    to type <code>int</code>.
    */
   public int intValue() {
      return (int) longValue();
   }

   /**
    * Returns the value of this number as a <code>short</code>. This may
    * involve rounding.
    *
    * @return
    *    the numeric value represented by this object after conversion
    *    to type <code>short</code>.
    */
   public short shortValue() {
      return (short) longValue();
   }

   /**
    * Returns the value of this number as a <code>byte</code>. This may
    * involve rounding.
    *
    * @return
    *    the numeric value represented by this object after conversion
    *    to type <code>byte</code>.
    */
   public byte byteValue() {
      return (byte) longValue();
   }

   /**
    * Returns the value of this number as a <code>double</code>. This may
    * involve rounding.
    *
    * @return
    *    the numeric value represented by this object after conversion
    *    to type <code>double</code>.
    */
   public double doubleValue() {
      // XXX: What precision should be used for doubles?
      //      30 seems pretty safe for now
      return toBigDecimal(30).doubleValue();
   }

   /**
    * Returns the value of this number as a <code>float</code>. This may
    * involve rounding.
    *
    * @return
    *    the numeric value represented by this object after conversion
    *    to type <code>float</code>.
    */
   public float floatValue() {
      return (float) doubleValue();
   }

   /**
    * Converts the value of this number to a <code>BigDecimal</code> with the
    * specified precision. This method uses the <code>ROUND_HALF_UP</code>
    * rounding mode as defined in <code>BigDecimal</code>.
    *
    * @param precision
    *    the number of digits behind the decimal point.
    *
    * @return
    *    a <code>BigDecimal</code> with the rounded value of this.
    *
    * @throws IllegalArgumentException
    *    if <code>precision &lt; 0</code>.
    */
   public BigDecimal toBigDecimal(int precision)
   throws IllegalArgumentException {
      return toBigDecimal(precision, BigDecimal.ROUND_HALF_UP);
   }

   /**
    * Converts the value of this number to a <code>BigDecimal</code> with the
    * specified precision and rounding mode.
    *
    * @param precision
    *    the number of digits behind the decimal point, &gt;= 0.
    *
    * @param roundingMode
    *    the rounding mode to use, one of the modes defined in class
    *    <code>BigDecimal</code>.
    *
    * @return
    *    a <code>BigDecimal</code> with the rounded value of this, never
    *    <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if one of the following applies:
    *    <ol>
    *       <li><em>precision</em> &lt; 0</li>
    *       <li><em>roundingMode</em> is not one of the valid rounding modes
    *           defined in class <code>BigDecimal</code></li>
    *    </ol>
    */
   public abstract BigDecimal toBigDecimal(int precision, int roundingMode)
   throws IllegalArgumentException;

   /**
    * Converts the value of this number to a <code>BigInteger</code>. This
    * may involve rounding.
    *
    * @return
    *    the numeric value represented by this object after conversion
    *    to type <code>BigInteger</code>.
    */
   public BigInteger toBigInteger() {

      // XXX: Is this correct?
      return toBigDecimal(0).toBigInteger();
   }

   /**
    * Rounds to an integer number towards 0.
    *
    * @return
    *    this real number truncated to an integer, never <code>null</code>.
    */
   public IntegerNumber trunc() {
      BigDecimal bigDecimal = toBigDecimal(0, BigDecimal.ROUND_FLOOR);
      return NumberCentral.valueOf(bigDecimal).trunc();
   }
}
