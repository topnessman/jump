/*
 * $Id: BasicDigitSet.java,v 1.15 2002/08/16 19:49:03 znerd Exp $
 */
package org.znerd.math;

/**
 * Basic implementation of a <code>DigitSet</code>.
 *
 * @version $Revision: 1.15 $ $Date: 2002/08/16 19:49:03 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public final class BasicDigitSet extends DigitSet {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Determines the sign for a <code>BasicDigitSet</code> instance that is
    * initialized with the specified arguments.
    *
    * @param positive
    *    indication if the number is positive or not.
    *
    * @param radix
    *    the radix for the number, always &gt;= 2 and &lt;=
    *    {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @param digits
    *    the digits, not <code>null</code>.
    *
    * @param exponent
    *    the exponent for the number.
    *
    * @return
    *    the sign for the number as specified in the arguments, either -1 if
    *    it is determined to be negative, 0 if it is determined to be zero or
    *    1 if it is determined to be positive.
    *
    * @throws IllegalArgumentException
    *    if <code>radix &lt; 2
    *          || radix &gt; {@link RealNumber#MAXIMUM_RADIX}
    *          || digits == null
    *          || digits[<em>i</em>] &lt; 0 ||
    *          || digits[<em>i</em>] &gt;= radix</code>,
    *    where <code><em>i</em> &gt;= 0 &amp;&amp;
    *                <em>i</em> &lt;  digits.length</code>.
    */
   private static int determineSign(boolean positive, int radix, int[] digits, int exponent)
   throws IllegalArgumentException {

      // Determine the number of significant digits
      int significantDigits = determineSignificantDigits(radix, digits);

      if (significantDigits == 0) {
         return 0;
      } else if (positive) {
         return 1;
      } else {
         return -1;
      }
   }

   /**
    * Constructs a textual presentation for a <code>BasicDigitSet</code>
    * instance that is initialized with the specified arguments.
    *
    * @param positive
    *    indication if the number is positive or not.
    *
    * @param radix
    *    the radix for the number, always &gt;= 2 and &lt;=
    *    {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @param digits
    *    the digits, not <code>null</code>.
    *
    * @param exponent
    *    the exponent for the number.
    *
    * @return
    *    a textual presentation that can be used for a number with
    *    characteristics as specified in the arguments.
    *
    * @throws IllegalArgumentException
    *    if <code>radix &lt; 2
    *          || radix &gt; {@link RealNumber#MAXIMUM_RADIX}
    *          || digits == null
    *          || digits[<em>i</em>] &lt; 0 ||
    *          || digits[<em>i</em>] &gt;= radix</code>,
    *    where <code><em>i</em> &gt;= 0 &amp;&amp;
    *                <em>i</em> &lt;  digits.length</code>.
    */
   private static String createString(boolean positive, int radix, int[] digits, int exponent)
   throws IllegalArgumentException {

      // Determine the number of significant digits
      int significantDigits = determineSignificantDigits(radix, digits);

      StringBuffer buffer = new StringBuffer();
      if (positive) {
         buffer.append('+');
      } else {
         buffer.append('-');
      }

      buffer.append('{');

      int count = digits.length;
      for (int i = count - significantDigits; i < count; i++) {
         buffer.append(String.valueOf(digits[i]));
         if ((i + 1) < count) {
            buffer.append(',');
         }
      }

      buffer.append('}');
      buffer.append(String.valueOf(radix));
      buffer.append("**");
      buffer.append(String.valueOf(exponent));

      return buffer.toString();
   }

   /**
    * Determines the number of significant digits for the specified array of
    * digits.
    *
    * @param radix
    *    the radix for the number, always &gt;= 2 and &lt;=
    *    {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @return
    *    the number of significant digits;
    *    <code><em>r</em> &gt; 0 &amp;&amp;
    *          <em>r</em> &lt;= digits.length</code>,
    *    where <em>r</em> is the returned value.
    *
    * @param digits
    *    the digits, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>radix &lt; 2
    *          || radix &gt; {@link RealNumber#MAXIMUM_RADIX}
    *          || digits == null
    *          || digits[<em>i</em>] &lt; 0 ||
    *          || digits[<em>i</em>] &gt;= radix</code>,
    *    where <code><em>i</em> &gt;= 0 &amp;&amp;
    *                <em>i</em> &lt;  digits.length</code>.
    */
   private static int determineSignificantDigits(int radix, int[] digits)
   throws IllegalArgumentException {

      // Check preconditions
      checkRadix(radix);
      MandatoryArgumentChecker.check("digits", digits);

      // Loop through all the digits
      int digitCount = digits.length;
      int leadingZeroes = 0;
      boolean hadNonZero = false;
      for (int i=0; i < digitCount; i++) {
         int d = digits[i];
         if (d < 0) {
            throw new IllegalArgumentException("digits[" + i + "] == " + d);
         } else if (radix < 0) {
            throw new IllegalArgumentException("digits[" + i + "] == " + d + ", while radix == " + radix);
         } else if (hadNonZero == false && d == 0) {
            leadingZeroes++;
         } else {
            hadNonZero = true;
         }
      }

      return digitCount - leadingZeroes;
   }

   /**
    * Translates a number with the specified characteristics to an array with
    * the numerator and denominator. The constructor delegates this to this
    * class function because this array needs to be passed up to the
    * superconstructor in the <code>super(...)</code> call.
    *
    * @param positive
    *    <code>true</code> if the number is positive.
    *
    * @param radix
    *    the radix for the number, always &gt;= 2 and &lt;=
    *    {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @param digits
    *    the digits, not <code>null</code>.
    *
    * @param exponent
    *    the exponent for the number.
    *
    * @return
    *    the created array;
    *    <code>r != null &amp;&amp;
    *          r.length == 2 &amp;&amp;
    *          r[0] != null &amp;&amp;
    *          (r[0].getSign() == 0 || positive == (r[0].getSign() == 1)) &amp;&amp;
    *          r[1] != null &amp;&amp;
    *          r[1].getSign() == 1</code>,
    *    where <code>r</code> is the returned value.
    *
    * @throws IllegalArgumentException
    *    if <code>radix &lt; 2
    *          || radix &gt; {@link RealNumber#MAXIMUM_RADIX}
    *          || digits == null
    *          || digits[<em>i</em>] &lt; 0 ||
    *          || digits[<em>i</em>] &gt;= radix</code>,
    *    where <code><em>i</em> &gt;= 0 &amp;&amp;
    *                <em>i</em> &lt;  digits.length</code>.
    */
   private final static IntegerNumber[] createParts(boolean positive,
                                                    int     radix,
                                                    int     digits[],
                                                    int     exponent)
   throws IllegalArgumentException {
      return new IntegerNumber[] { SmallIntegerNumber.ONE,
                                   SmallIntegerNumber.TWO };
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Creates a new <code>BasicDigitSet</code> instance.
    *
    * @param positive
    *    indication if the number is positive or not.
    *
    * @param radix
    *    the radix for the number, always &gt;= 2 and &lt;=
    *    {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @param digits
    *    the digits, not <code>null</code>.
    *
    * @param exponent
    *    the exponent for the number.
    *
    * @throws IllegalArgumentException
    *    if <code>radix &lt; 2
    *          || radix &gt; {@link RealNumber#MAXIMUM_RADIX}
    *          || digits == null
    *          || digits[<em>i</em>] &lt; 0 ||
    *          || digits[<em>i</em>] &gt;= radix</code>,
    *    where <code><em>i</em> &gt;= 0 &amp;&amp;
    *                <em>i</em> &lt;  digits.length</code>.
    */
   protected BasicDigitSet(boolean positive, int radix, int digits[], int exponent)
   throws IllegalArgumentException {

      // Call the superconstructor and check the arguments at the same time
      super(createParts(positive, radix, digits, exponent),
            createString(positive, radix, digits, exponent));

      _radix    = radix;
      _digits   = digits;
      _exponent = exponent;
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The radix for this number. The value of this field is always &gt;= 2 and
    * &lt;= {@link RealNumber#MAXIMUM_RADIX}.
    */
   private final int _radix;

   /**
    * An array containing the digits. This field is initialised by the
    * constructor.
    */
   private final int[] _digits;

   /**
    * The exponent for this number.
    */
   private final int _exponent;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   public int getRadix() {
      return _radix;
   }

   public int getExponent() {
      return _exponent;
   }

   public int[] getDigits() {
      int[] copy = new int[_digits.length];
      System.arraycopy(_digits, 0, copy, 0, _digits.length);
      return copy;
   }

   public int getPrecision() {
      return _digits.length;
   }

   public DigitSet toPrecisionImpl(int precision) {
      int[] digits = new int[precision];
      System.arraycopy(_digits, 0, digits, 0, precision);
      return new BasicDigitSet(getSign() > 0, _radix, digits, _exponent);
   }
}
