/*
 * $Id: RationalNumber.java,v 1.13 2002/10/01 18:56:55 znerd Exp $
 */
package org.znerd.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Rational number. It is a specialisation of {@link RealNumber} that offers
 * narrowed numeric computations for rational numbers.
 *
 * <p />All rational values can be written as a fraction
 * <em>x</em>/<em>y</em>, where both <em>x</em> and <em>y</em> are
 * integer numbers.
 *
 * <p /><code>RationalNumber</code> derivates must obey these rules:
 *
 * <blockquote><dl>
 *    <dt><em>they should be normalized</em></dt>
 *    <dd>the fraction properties should always return a fraction that
 *        cannot be simplified any further, thus 3/9 is illegal,
 *        while 1/3 is legal</dd>
 *
 *    <dt><em>the denominator should always be positive</em></dt>
 *    <dd>if the fraction is negative, the numerator should be negative
 *        and the denominator positive, otherwise both should be positive,
 *        thus both -1/-3 and 1/-3 are illegal, while 1/3 and -1/3
 *        are legal</dd>
 * </dl></blockquote>
 *
 * <p />Some <code>RationalNumber</code> numeric computations are narrowed
 * compared to their more general <code>RealNumber</code> equivalents. For
 * instance, adding two rational numbers will result in a rational number.
 * The computations concerned are:
 *
 * <blockquote><dl>
 *    <dt>{@link #add(RationalNumber)}</dt>
 *    <dd>adding two rational numbers
 *        will result in another rational number</dd>
 *
 *    <dt>{@link #subtract(RationalNumber)}</dt>
 *    <dd>subtracting one rational number from another
 *        will result in a rational number</dd>
 *
 *    <dt>{@link #multiply(RationalNumber)}</dt>
 *    <dd>multiplying two rational numbers
 *        will result in another rational number</dd>
 *
 *    <dt>{@link #divide(RationalNumber)}</dt>
 *    <dd>dividing one rational number by another
 *        will result in a rational number</dd>
 *
 *    <dt>{@link #powImpl(IntegerNumber)}</dt>
 *    <dd>raising a rational number to an integer power
 *        will result in a rational number</dd>
 * </dl></blockquote>
 *
 * <p />Concrete subclasses should at least provide an implementations for the
 * following methods:
 *
 * <ul>
 *    <li><code>getNumerator()</code> --
 *        Returns the numerator of this fraction</li>
 *
 *    <li><code>getDenominator()</code> --
 *        Returns the denominator of this fraction</li>
 * </ul>
 *
 * <p /><code>IntegerNumber</code> implementations based on this class must
 * override the following methods:
 *
 * <ul>
 *    <li><code>getSign()</code> --
 *        The implementation in this class returns
 *        the sign of the numerator, which is <code>this</code> in an
 *        <code>IntegerNumber</code> implementation</li>
 *
 *    <li><code>negate()</code> --
 *        The implementation in this class returns a
 *        fraction with a negated numerator, which is <code>this</code> in an
 *        <code>IntegerNumber</code> implementation</li>
 *
 *    <li><code>toString()</code> --
 *        The implementation in this class
 *        returns a string representation of the numerator and the string
 *        representation of the denominator; in an <code>IntegerNumber</code>
 *        implementation the numerator is this</li>
 *
 * </ul>
 *
 * @version $Revision: 1.13 $ $Date: 2002/10/01 18:56:55 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public abstract class RationalNumber extends RealNumber {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Checks the specified numerator and denominator parts of a rational
    * number.
    *
    * @param parts
    *    an array containing a numerator and the denominator, not
    *    <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>parts == null       ||
    *             parts.length &lt; 2 ||
    *             parts[0] == null    ||
    *             parts[1] == null</code>
    */
   private final static void checkParts(IntegerNumber[] parts)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("parts", parts);
      if (parts.length < 2) {
         throw new IllegalArgumentException("parts.length < 2");
      } else if (parts[0] == null && parts[1] == null) {
         throw new IllegalArgumentException("parts[0] == null && parts[1] == null");
      } else if (parts[0] == null) {
         throw new IllegalArgumentException("parts[0] == null");
      } else if (parts[1] == null) {
         throw new IllegalArgumentException("parts[1] == null");
      }
   }

   /**
    * Determines the sign for a rational number with the specified numerator
    * and denominator.
    *
    * @param parts
    *    an array containing the numerator and the denominator, not
    *    <code>null</code>.
    *
    * @return
    *    the sign for the number, eiter -1 for a number smaller than zero, 0
    *    for a number that equals 0 or 1 for a number greater than zero.
    *
    * @throws IllegalArgumentException
    *    if <code>parts == null       ||
    *             parts.length &lt; 2 ||
    *             parts[0] == null    ||
    *             parts[1] == null</code>
    */
   private final static int determineSign(IntegerNumber[] parts)
   throws IllegalArgumentException {

      // Check preconditions
      checkParts(parts);

      IntegerNumber numerator   = parts[0];
      IntegerNumber denominator = parts[1];

      return numerator.getSign() * denominator.getSign();
   }

   /**
    * Creates a textual presentation for a rational number with the specified
    * numerator and denominator.
    *
    * @param parts
    *    an array containing the numerator and the denominator, not
    *    <code>null</code>.
    *
    * @return
    *    a textual presentation for this rational number, never
    *    <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>parts == null       ||
    *             parts.length &lt; 2 ||
    *             parts[0] == null    ||
    *             parts[1] == null</code>
    */
   private final static String createString(IntegerNumber[] parts)
   throws IllegalArgumentException {

      // Check preconditions
      checkParts(parts);

      IntegerNumber numerator   = parts[0];
      IntegerNumber denominator = parts[1];

      return numerator.toString() + '/' + denominator.toString();
   }


   //-------------------------------------------------------------------------
   // Constructors
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>RationalNumber</code> with the specified sign.
    * This constructor should <em>only</em> be called by the
    * {@link IntegerNumber} subclass.
    *
    * <p />The sign of the value needs to be specified. Any negative value is
    * interpreted as meaning that the value of this number is negative. Any
    * positive value is interpreted as meaning that the value of this number
    * is positive.
    *
    * @param sign
    *    the sign of this number.
    *
    * @param asString
    *    textual representation of this number.
    *
    * @throws IllegalArgumentException
    *    if <code>asString == null</code>.
    */
   RationalNumber(int sign, String asString) {

      // Call RealNumber constructor
      super(sign, asString);

      // This must be an IntegerNumber, so set the numerator to this and the
      // denominator to 1
      _numerator   = (IntegerNumber) this;
      _denominator = SmallIntegerNumber.ONE;
   }

   /**
    * Constructs a new <code>RationalNumber</code> with the specified
    * numerator and denominator.
    *
    * @param parts
    *    an array containing the numerator and denonimator, not
    *    <code>null</code>, having at least 2 elements and not having a
    *    <code>null</code> at index 0 or 1.
    *
    * @throws IllegalArgumentException
    *    if <code>parts == null</code> or <code>parts.length &lt; 2</code> or
    *    <code>parts[0] == null</code> or <code>parts[1] == null</code>.
    */
   protected RationalNumber(IntegerNumber[] parts)
   throws IllegalArgumentException {

      // Call RealNumber constructor
      super(determineSign(parts), createString(parts));

      // Initialize the fields
      _numerator   = parts[0];
      _denominator = parts[1];
   }


   /**
    * Constructs a new <code>RationalNumber</code> with the specified
    * numerator, denominator and textual presentation.
    *
    * @param parts
    *    an array containing the numerator and denonimator, not
    *    <code>null</code>, having at least 2 elements and not having a
    *    <code>null</code> at index 0 or 1.
    *
    * @param asString
    *    textual presentation of the number, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>parts == null</code> or <code>parts.length &lt; 2</code> or
    *    <code>parts[0] == null</code> or <code>parts[1] == null</code>.
    */
   protected RationalNumber(IntegerNumber[] parts, String asString)
   throws IllegalArgumentException {

      // Call RealNumber constructor
      super(determineSign(parts), asString);

      // Initialize the fields
      _numerator   = parts[0];
      _denominator = parts[1];
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The numerator. This field is initialized by the constructor, it can
    * never be <code>null</code>.
    */
   private final IntegerNumber _numerator;

   /**
    * The denominator. This field is initialized by the constructor, it can
    * never be <code>null</code>.
    */
   private final IntegerNumber _denominator;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   // XXX: The following methods are actually narrowed, because the Java
   // XXX: language does not (yet) support narrowed return types in overloaded
   // XXX: methods:
   // XXX:
   // XXX:    public RationalNumber abs();
   // XXX:    public RationalNumber negate();
   // XXX:    public RationalNumber invert() throws ArithmeticException;

   /**
    * Compares this number with the specified number, second level.
    *
    * <p />The implementation of this method in class {@link RationalNumber}
    * first checks if <code>n instanceof RationalNumber</code>. If so, then it
    * calls {@link #compareTo(RationalNumber)}. Otherwise, it calls
    * {@link #compareToImpl2(RealNumber)}.
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
   protected final int compareToImpl(RealNumber n)
   throws CanNotCompareException {

      if (n instanceof RationalNumber) {
         return compareTo((RationalNumber) n);
      }
      return compareToImpl2(n);
   }

   /**
    * Compares this number with the specified number, third level.
    *
    * <p />The implementation of this method in class {@link RationalNumber}
    * just throws a {@link CanNotCompareException}.
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
   protected int compareToImpl2(RealNumber n)
   throws CanNotCompareException {
      throw new CanNotCompareException(this, n);
   }

   /**
    * Compares this number with the specified rational number, first level.
    *
    * <p />The implementation of this method in class {@link RationalNumber}
    * returns the result of
    * {@link #subtract(RationalNumber) subtract}<code>(n).</code>{@link RealNumber#getSign() getSign}<code>()</code>.
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
   protected final int compareTo(RationalNumber n)
   throws CanNotCompareException {
      try {
         return compareToImpl(n);
      } catch (CanNotCompareException exception) {
         return subtract(n).getSign();
      }
   }

   /**
    * Compares this number with the specified rational number, second level.
    *
    * <p />This method is called from {@link #compareTo(RationalNumber)}. The
    * implementation of this method in class {@link RationalNumber} just
    * throws a {@link CanNotCompareException} to indicate it does not provide
    * an optimized algorithm for comparing this integer number with the
    * argument integer number. Subclasses are encouraged to override this
    * method.
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
   protected int compareToImpl(RationalNumber n)
   throws CanNotCompareException {
      throw new CanNotCompareException(this, n);
   }

   public RealNumber negate() {
      return NumberCentral.createFraction(getNumerator().negateInteger(),
                                          getDenominator());
   }

   public RealNumber invert() {
      return NumberCentral.createFraction(getDenominator(), getNumerator());
   }

   public RealNumber add(RealNumber n) {
      if (n instanceof RationalNumber) {
         return add((RationalNumber) n);
      }
      return super.add(n);
   }

   /**
    * Computes this+<em>n</em>, where <em>n</em> is a rational number.
    *
    * @param n
    *    the number to add to this.
    *
    * @return
    *    the sum of this and <em>n</em>.
    *
    * @throws IllegalArgumentException
    *    if <em>n</em> == <code>null</code>.
    */
   public RationalNumber add(RationalNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // Disect the argument RationalNumber
      IntegerNumber nd = n.getDenominator();
      IntegerNumber nn = n.getNumerator();

      IntegerNumber a = getNumerator().multiply(nd);
      IntegerNumber b = getDenominator().multiply(nn);

      IntegerNumber numerator = a.add(b);
      IntegerNumber denominator = getDenominator().multiply(nd);

      return NumberCentral.createFraction(numerator, denominator);
   }

   public RealNumber subtract(RealNumber n)
   throws IllegalArgumentException {

      if (n instanceof RationalNumber) {
         return subtract((RationalNumber) n);
      }
      return super.subtract(n);
   }

   /**
    * Computes this-<em>n</em>, where <em>n</em> is a rational number.
    *
    * @param n
    *    the number to subtract from this.
    *
    * @return
    *    this minus <em>n</em>.
    *
    * @throws IllegalArgumentException
    *    if <em>n</em> == <code>null</code>.
    */
   public RationalNumber subtract(RationalNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      // Disect the argument RationalNumber
      IntegerNumber nd = n.getDenominator();
      IntegerNumber nn = n.getNumerator();

      IntegerNumber a = getNumerator().multiply(nd);
      IntegerNumber b = getDenominator().multiply(nn);

      IntegerNumber numerator = a.subtract(b);
      IntegerNumber denominator = getDenominator().multiply(nd);

      return NumberCentral.createFraction(numerator, denominator);
   }

   public RealNumber multiply(RealNumber n) {
      if (n instanceof RationalNumber) {
         return multiply((RationalNumber) n);
      }
      return super.multiply(n);
   }

   /**
    * Computes this*<em>n</em>, where <em>n</em> is a rational number.
    *
    * @param n
    *    the number to multiply this by.
    *
    * @return
    *    the product of this and <em>n</em>.
    *
    * @throws IllegalArgumentException
    *    if <em>n</em> == <code>null</code>.
    */
   public RationalNumber multiply(RationalNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      IntegerNumber numerator = getNumerator().multiply(n.getNumerator());
      IntegerNumber denominator = getDenominator().multiply(n.getDenominator());

      return NumberCentral.createFraction(numerator, denominator);
   }

   public RealNumber divide(RealNumber n)
   throws IllegalArgumentException, ArithmeticException {
      if (n instanceof RationalNumber) {
         return divide((RationalNumber) n);
      }
      return super.divide(n);
   }

   /**
    * Computes this/<em>n</em>, where <em>n</em> is a rational number.
    *
    * @param n
    *    the number to divide this by.
    *
    * @return
    *    this divided by <em>n</em>.
    *
    * @throws IllegalArgumentException
    *    if <em>n</em> == <code>null</code>.
    *
    * @throws ArithmeticException
    *    if the value of <em>n</em> is zero.
    */
   public RationalNumber divide(RationalNumber n)
   throws IllegalArgumentException, ArithmeticException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      IntegerNumber numerator = getNumerator().multiply(n.getDenominator());
      IntegerNumber denominator = getDenominator().multiply(n.getNumerator());

      return NumberCentral.createFraction(numerator, denominator);
   }

   public final RealNumber powImpl(RealNumber n)
   throws UnsupportedOperationException {

      if (n instanceof IntegerNumber) {
         return powImpl((IntegerNumber) n);
      }
      return super.powImpl(n);
   }

   public final RationalNumber pow(IntegerNumber n) {
      return powImpl(n);
   }

   protected RationalNumber powImpl(IntegerNumber n) {

      int sign = n.getSign();

      if (sign > 0) {
         IntegerNumber numerator   = (IntegerNumber) _numerator.powImpl(n);
         IntegerNumber denominator = (IntegerNumber) _denominator.powImpl(n);
         return NumberCentral.createFraction(numerator, denominator);

      // x**0 is always 1
      } else if (sign == 0) {
         return SmallIntegerNumber.ONE;

      // n must be < 0
      } else {

         // x ** n  (n<0) equals 1/(x ** -n)
         IntegerNumber absolute = (IntegerNumber) n.negate();
         RationalNumber invertedResult = powImpl(absolute);
         return (RationalNumber) (invertedResult.invert());
      }
   }

   public double doubleValue() {
      return getNumerator().doubleValue() / getDenominator().doubleValue();
   }

   public BigDecimal toBigDecimal(int precision)
   throws IllegalArgumentException {
      return toBigDecimal(precision, BigDecimal.ROUND_HALF_UP);
   }

   /**
    * Converts the value of this number to a <code>BigDecimal</code> with the
    * specified precision and rounding mode.
    *
    * <p />The implementation of this method in class {@link RationalNumber}
    * first converts both the numerator and the denominator to
    * {@link BigDecimal} objects using {@link IntegerNumber#toBigDecimal()}.
    * It then calls {@link BigDecimal#divide(BigDecimal,int,int)} and returns
    * the result of that call.
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
    *    if <code>precision &lt; 0</code>.
    */
   public BigDecimal toBigDecimal(int precision, int roundingMode)
   throws IllegalArgumentException {

      // XXX: This check should be done by
      // XXX: {@link BigDecimal#divide(BigDecimal,int,int)}
      if (precision < 0) {
         throw new IllegalArgumentException("precision < 0");
      }

      BigDecimal numerator   = getNumerator().toBigDecimal();
      BigDecimal denominator = getDenominator().toBigDecimal();

      return numerator.divide(denominator, precision, roundingMode);
   }

   public IntegerNumber trunc() {
      return getNumerator().integerDivide(getDenominator());
   }

   /**
    * Returns the numerator of this fraction. This numerator may be negative.
    *
    * @return
    *    the numerator, not <code>null</code>.
    */
   public final IntegerNumber getNumerator() {
      return _numerator;
   }

   /**
    * Returns the denominator of this fraction. The denominator will always
    * be a positive integer number.
    *
    * @return
    *    the denominator, not <code>null</code>.
    */
   public final IntegerNumber getDenominator() {
      return _denominator;
   }
}
