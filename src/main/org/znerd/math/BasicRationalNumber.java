/*
 * $Id: BasicRationalNumber.java,v 1.8 2002/08/16 22:07:32 znerd Exp $
 */
package org.znerd.math;

/**
 * Basic implementation of a rational number.
 *
 * <p />This implementation simply encapsulates a <em>numerator</em> and
 * a <em>denominator</em> field. Both are <code>IntegerNumber</code>
 * objects.
 *
 * @version $Revision: 1.8 $ $Date: 2002/08/16 22:07:32 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public class BasicRationalNumber extends RationalNumber {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Translates a number with the specified characteristics to an array with
    * the numerator and denominator. The constructor delegates this to this
    * class function because this array needs to be passed up to the
    * superconstructor in the <code>super(...)</code> call.
    *
    * @param numerator
    *    the numerator for the fraction, not <code>null</code>.
    *
    * @param denominator
    *    the denominator for the fraction, not <code>null</code>.
    *
    * @return
    *    the created array;
    *    <code><em>r</em> != null &amp;&amp;
    *          <em>r</em>.length == 2 &amp;&amp;
    *          <em>r</em>[0] == numerator &amp;&amp;
    *          <em>r</em>[1] == denominator</code>,
    *    where <em>r</em> is the returned value.
    *
    * @throws IllegalArgumentException
    *    if <code>numerator == null || denominator == null</code>.
    *
    * @throws ArithmeticException
    *    if <code>denominator.getSign() == 0</code>.
    */
   private final static IntegerNumber[] createParts(IntegerNumber numerator,
                                                    IntegerNumber denominator)
   throws IllegalArgumentException, ArithmeticException {

      // Check preconditions
      MandatoryArgumentChecker.check("numerator", numerator,
                                     "denominator", denominator);
      NumberCentral.checkDivideByZero(denominator);

      IntegerNumber divisor = numerator.gcd(denominator);

      numerator   = numerator.integerDivide(divisor);
      denominator = denominator.integerDivide(divisor);

      if (denominator.getSign() == -1) {
         numerator   = (IntegerNumber) numerator.negate();
         denominator = (IntegerNumber) denominator.negate();
      }

      IntegerNumber[] parts = new IntegerNumber[2];
      parts[0] = numerator;
      parts[1] = denominator;

      return parts;
   }

   /**
    * Returns an instance of a <code>BasicRationalNumber</code> with the
    * given numerator and denominator.
    *
    * @param numerator
    *    the numerator for the fraction.
    *
    * @param denominator
    *    the denominator for the fraction.
    *
    * @return
    *    the (possibly newly constructed) instance.
    *
    * @throws IllegalArgumentException
    *    if one of the following applies:
    *    <ol>
    *       <li><em>numerator</em>   == <code>null</code></li>
    *       <li><em>denominator</em> == <code>null</code></li>
    *    </ol>
    *
    * @throws ArithmeticException
    *    if the value of <code>denominator</code> is zero.
    */
   public static BasicRationalNumber createInstance(IntegerNumber numerator,
                                                    IntegerNumber denominator)
   throws IllegalArgumentException, ArithmeticException {

      // Check preconditions
      MandatoryArgumentChecker.check("numerator",   numerator,
                                     "denominator", denominator);

      if (denominator.compareTo(SmallIntegerNumber.TWO) == 0) {
         if (numerator.compareTo(SmallIntegerNumber.ONE) == 0) {
            return ONE_HALF;
         } else if (numerator.compareTo(SmallIntegerNumber.MINUS_ONE) == 0) {
            return MINUS_ONE_HALF;
         }
      }

      //------------------------------------------------
      // NOTE: constructor may throw ArithmeticException
      //------------------------------------------------

      return new BasicRationalNumber(numerator, denominator);
   }


   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   /**
    * Cached instance that represents the number one half, 1/2.
    */
   public static final BasicRationalNumber ONE_HALF =
      new BasicRationalNumber(SmallIntegerNumber.ONE,
                              SmallIntegerNumber.TWO);

   /**
    * Cached instance that represents the number minus one half, -1/2.
    */
   public static final BasicRationalNumber MINUS_ONE_HALF =
      new BasicRationalNumber(SmallIntegerNumber.MINUS_ONE,
                              SmallIntegerNumber.TWO);


   //-------------------------------------------------------------------------
   // Constructors
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>BasicRationalNumber</code> from a numerator and
    * a denominator.
    *
    * @param numerator
    *    the numerator for the fraction.
    *
    * @param denominator
    *    the denominator for the fraction.
    *
    * @throws IllegalArgumentException
    *    if one of the following applies:
    *    <ol>
    *       <li><em>numerator</em>   == <code>null</code></li>
    *       <li><em>denominator</em> == <code>null</code></li>
    *    </ol>
    *
    * @throws ArithmeticException
    *    if the value of <em>denominator</em> is zero.
    */
   protected BasicRationalNumber(IntegerNumber numerator,
                                 IntegerNumber denominator)
   throws IllegalArgumentException, ArithmeticException {

      super(createParts(numerator, denominator));
   }
}
