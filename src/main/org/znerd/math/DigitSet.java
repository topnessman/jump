/*
 * $Id: DigitSet.java,v 1.11 2002/08/16 21:54:40 znerd Exp $
 */
package org.znerd.math;

/**
 * A set of digits, having a radix and an exponent. Objects of this type are
 * used to implement rounding.
 *
 * <p />The value of a <code>DigitSet</code> number is the sum of:
 *
 * <blockquote>
 *    <em>digits</em>[0] * <em>radix</em><sup><em>exponent</em></sup><br>
 *    <em>digits</em>[1] * <em>radix</em><sup><em>exponent</em> - 1</sup><br>
 *    <em>digits</em>[2] * <em>radix</em><sup><em>exponent</em> - 2</sup><br>
 *    ...<br>
 *    <em>digits</em>[<em>precision</em> - 1] * <em>radix</em><sup><em>exponent</em> - (<em>precision</em> - 1)</sup><br>
 * </blockquote>
 *
 * @version $Revision: 1.11 $ $Date: 2002/08/16 21:54:40 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public abstract class DigitSet extends RationalNumber {

   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Creates a new <code>DigitSet</code> instance.
    *
    * @param parts
    *    an array containing the numerator and denonimator, not
    *    <code>null</code>, having at least 2 elements and not having a
    *    <code>null</code> at index 0 or 1.
    *
    * @param asString
    *    textual presentation of this number, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>asString == null</code>.
    */
   protected DigitSet(IntegerNumber[] parts, String asString)
   throws IllegalArgumentException {
      super(parts, asString);
   }


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Returns the radix or base. The radix is for example 10 for a decimal
    * number and 16 for a hexadecimal number. A binary number has the smallest
    * radix, 2.
    *
    * @return
    *    the radix, always &gt; 1.
    */
   public abstract int getRadix();

   /**
    * Returns the exponent.
    *
    * @return
    *    the exponent.
    */
   public abstract int getExponent();

   /**
    * Returns a new array containing all the digits. Every digit in the
    * returned array is an <code>int</code> value between 0 (inclusive) and
    * the radix (exclusive).
    *
    * @return
    *    a new array containing all the digits;
    *    <code><em>r</em> != null             &amp;&amp;
    *          <em>r</em>[<em>i</em>] &gt;= 0 &amp;&amp;
    *          <em>r</em>[<em>i</em>] &lt; getRadix()</code>,
    *    where <em>r</em> is the return value and <em>i</em> is an
    *    <code>int</code> between 0 and <code><em>r.</em>length</code>.
    */
   public abstract int[] getDigits();

   /**
    * Returns the precision, the total number of digits.
    *
    * @return
    *    the precision, always &gt;= 0.
    */
   public abstract int getPrecision();

   /**
    * Returns a digit set with the specified precision. If the specified
    * precision is larger than or equal to the current precision, then this
    * digit set (<code>this</code>) will be returned.
    *
    * <p />No rounding will be performed, only truncation. If the specified
    * precision is smaller than the precision of this <code>DigitSet</code>,
    * then some of the digits will just be removed.
    *
    * <p />This method calls {@link #toPrecisionImpl(int)} after checking the
    * preconditions. If that method returns an invalid value, then an
    * {@link InternalError} is thrown.
    *
    * @param precision
    *    the precision, &gt;= 1.
    *
    * @return
    *    a <code>DigitSet</code> that is equal to this number, truncated to
    *    the specified precision, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>precision &lt; 1</code>.
    */
   public final DigitSet toPrecision(int precision)
   throws IllegalArgumentException {

      // Check preconditions
      if (precision < 1) {
         throw new IllegalArgumentException("precision < 1");
      }
      if (precision >= getPrecision()) {
         return this;
      }

      // Call subclass
      DigitSet d = toPrecisionImpl(precision);

      // Check postconditions
      if (d == null) {
         throw new InternalError(getClass().getName() + ".toPrecisionImpl(int) returned null.");
      } else if (d == this) {
         throw new InternalError(getClass().getName() + ".toPrecisionImpl(int) returned this while specified precision is less than the current precision.");
      } else if (d.getPrecision() != precision) {
         throw new InternalError(getClass().getName() + ".toPrecisionImpl(int) returned a digit set with precision " + d.getPrecision() + " while the precision " + precision + " was requested.");
      } else {
         return d;
      }
   }

   /**
    * Returns a digit set with the specified precision, actual implementation.
    * This method is called from {@link #toPrecision(int)}.
    *
    * @param precision
    *    the precision, guaranteed to be &gt;= 1 and &lt;
    *    {@link #getPrecision()}.
    *
    * @return
    *    a <code>DigitSet</code> that is equal to this number, truncated to
    *    the specified precision.
    */
   protected abstract DigitSet toPrecisionImpl(int precision);
}
