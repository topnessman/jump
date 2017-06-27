/*
 * $Id: SmallIntegerNumber.java,v 1.23 2002/08/19 18:55:33 znerd Exp $
 */
package org.znerd.math;

import java.math.*;

/**
 * Implementation of an <code>IntegerNumber</code> based on a
 * <code>int</code> value.
 *
 * <p />Instances must be obtained by using one of the
 * <code>createInstance()</code> factory methods. Using this approach,
 * instances can be transparently cached by this class.
 *
 * @version $Revision: 1.23 $ $Date: 2002/08/19 18:55:33 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public final class SmallIntegerNumber extends IntegerNumber {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Creates an array of <code>SmallIntegerNumber</code> instances for use as
    * a cache. The size and contents of the cache will be determined by
    * looking at {@link #CACHE_SIZE} and {@link #MINIMUM_CACHE_VALUE}.
    *
    * @return
    *    the cache array;
    *    <code>r != null &amp;&amp;
    *          r.length == CACHE_SIZE &amp;&amp;
    *          r[i]        != null &amp;&amp;
    *          r[i]._value == MINIMUM_CACHE_VALUE + i</code>,
    *    where <code>r</code> is the return value and <code>i</code> is an
    *    <code>int</code> in the range 0..<code>CACHE_SIZE</code>.
    */
   private static SmallIntegerNumber[] createCache() {
      SmallIntegerNumber[] cache = new SmallIntegerNumber[CACHE_SIZE];
      for (int index=0; index < CACHE_SIZE; index++) {
         int value = MINIMUM_CACHE_VALUE + index;
         cache[index] = new SmallIntegerNumber(value);
      }
      return cache;
   }

   /**
    * Returns an instance of a <code>SmallIntegerNumber</code> based on a
    * <code>int</code> value.
    *
    * @param n
    *    the <code>int</code> to construct a <code>SmallIntegerNumber</code> from.
    *
    * @return
    *    the (possibly newly constructed) <code>SmallIntegerNumber</code>.
    */
   public static SmallIntegerNumber createInstance(int n) {
      if (n >= MINIMUM_CACHE_VALUE && n <= MAXIMUM_CACHE_VALUE) {
         int index = (int) (n - MINIMUM_CACHE_VALUE);
         return CACHE[index];
      } else {
         return new SmallIntegerNumber(n);
      }
   }

   /**
    * Determines the number of digits for an <code>int</code> value with the
    * specified radix.
    *
    * @param radix
    *    the radix, always &gt;= 2 and &lt;= {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @param value
    *    the value.
    *
    * @return
    *    the scale of the value in the specified radix, always &gt;= 0.
    *
    * @throws IllegalArgumentException
    *    if <code>radix &lt; 2
    *          || radix &gt; {@link RealNumber#MAXIMUM_RADIX}</code>.
    */
   private static int determineScale(int radix, int value)
   throws IllegalArgumentException {

      // Check preconditions
      checkRadix(radix);

      // Get |value|, the absolute of value
      if (value < 0) {
         value = -value;
      }

      // Compute the scale of |value|
      int scale;
      for (scale = 0; value > 0; scale++) {
         value /= radix;
      }
      return scale;
   }


   //-------------------------------------------------------------------------
   // Class fields
   //-------------------------------------------------------------------------

   /**
    * The minimum value that is in the cache.
    *
    * @see #CACHE
    */
   private final static int MINIMUM_CACHE_VALUE = -100;

   /**
    * The size of the cache.
    *
    * @see #CACHE
    */
   private final static int CACHE_SIZE = 201;

   /**
    * The maximum value that is in the cache. This value is computed using
    * {@link #MINIMUM_CACHE_VALUE} and {@link #CACHE_SIZE}.
    *
    * @see #CACHE
    */
   private final static int MAXIMUM_CACHE_VALUE = MINIMUM_CACHE_VALUE + (CACHE_SIZE - 1);

   /**
    * Cache with <code>SmallIntegerNumber</code> instances.
    */
   private final static SmallIntegerNumber[] CACHE = createCache();

   /**
    * The minimum <code>IntegerNumber</code> that can be represented by this
    * class.
    */
   public final static SmallIntegerNumber MINIMUM_VALUE = new SmallIntegerNumber(Integer.MIN_VALUE);

   /**
    * The maximum <code>IntegerNumber</code> that can be represented by this
    * class.
    */
   public final static SmallIntegerNumber MAXIMUM_VALUE = new SmallIntegerNumber(Integer.MAX_VALUE);

   /**
    * Cached instance that represents the number minus two, -2.
    */
   public final static SmallIntegerNumber MINUS_TWO = new SmallIntegerNumber(-2);

   /**
    * Cached instance that represents the number minus one, -1.
    */
   public final static SmallIntegerNumber MINUS_ONE = new SmallIntegerNumber(-1);

   /**
    * Cached instance that represents the number zero, 0.
    */
   public final static SmallIntegerNumber ZERO = new SmallIntegerNumber(0);

   /**
    * Cached instance that represents the number one, 1.
    */
   public final static SmallIntegerNumber ONE = new SmallIntegerNumber(1);

   /**
    * Cached instance that represents the number two, 2.
    */
   public final static SmallIntegerNumber TWO = new SmallIntegerNumber(2);


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a <code>SmallIntegerNumber</code> from an <code>int</code>.
    *
    * @param n
    *    the value for the new number.
    */
   private SmallIntegerNumber(int n) {
      super(n, String.valueOf(n));
      _value = n;
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The <code>int</code> this <code>IntegerNumber</code> implementation
    * is based on.
    */
   public final int _value;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   protected int compareToImpl(IntegerNumber n)
   throws CanNotCompareException {

      // Filter SmallIntegerNumbers out
      if (n instanceof SmallIntegerNumber) {
         return compareToImpl((SmallIntegerNumber) n);
      }

      // Otherwise let the superclasses figure it out
      throw new CanNotCompareException(this, n);
   }

   /**
    * Compares this number with the specified <code>SmallIntegerNumber</code>.
    *
    * <p />This method is called from {@link #compareToImpl(IntegerNumber)}.
    *
    * @param n
    *    the number to compare to, guaranteed to be not <code>null</code>.
    *
    * @return
    *    -1 if this &lt; <em>n</em>,
    *     0 if this ==   <em>n</em>,
    *     1 if this &gt; <em>n</em>.
    *
    * @throws NullPointerException
    *    if <code>n == null</code>.
    *
    * @throws CanNotCompareException
    *    if the comparison failed.
    */
   private int compareToImpl(SmallIntegerNumber n) {

      if (_value < n._value) {
         return -1;
      } else if (_value > n._value) {
         return 1;
      } else {
         return 0;
      }
   }

   public DigitSet round(int radix, int precision, RoundingMode roundingMode)
   throws IllegalArgumentException {

      // Check preconditions
      checkRadix(radix);
      if (precision < 1) {
         throw new IllegalArgumentException("The specified precision (" + precision + ") is smaller than the minimum precision, 1.");
      }
      MandatoryArgumentChecker.check("roundingMode", roundingMode);

      // Determine the number of digits the value actually has
      int scale = determineScale(radix, _value);

      // The result precision can never be greater than the scale
      if (precision > scale) {
         precision = scale;
      }

      // Determine the exponent
      int exponent = scale - precision;

      // Make value = |_value|
      // XXX: Make sure that Integer.MIN_VALUE is handled properly
      boolean positive = (_value > 0);
      int value = positive ? _value : (-1 * _value);

      // Determine if we need to round and the number of digits to compute
      boolean round = (precision < scale);
      int length = round ? (precision + 1) : scale;

      // Decrease the factor of the value according to the rounding
      int decreaseFactor = scale - length;
      for (int i = 0; i < decreaseFactor; i++) {
         value /= radix;
      }

      // Create an array to store the digits in
      int[] digits = new int[length];

      // Compute the digits (plus one extra if we're rounding)
      for (int i = (length - 1); i > 0; i--) {
         digits[i] = value % radix;
         value /= radix;
      }
      digits[0] = value;

      int maxDigit = radix - 1;
      if (round) {
         if (roundingMode.round(this, radix, digits[length - 1])) {
            boolean done = false;
            int i;
            for (i = (length - 2); (i > 0) && (done == false); i--) {
               if (digits[i] == maxDigit) {
                  digits[i] = 0;
               } else {
                  done = true;
               }
            }

            if (done) {
               digits[i + 1]++;
            } else {
               digits[0] = 1;
               exponent++;
            }
         }
      }

      if (round) {
         int[] orig = digits;
         digits = new int[precision];
         System.arraycopy(orig, 0, digits, 0, precision);
      }

      return new BasicDigitSet(positive, radix, digits, exponent);
   }

   public IntegerNumber negateInteger() {
      return createInstance(-1 * _value);
   }

   public IntegerNumber add(IntegerNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      if (n.fitsInt()) {

         int value2 = n.intValue();

         // both negative or both positive
         if ((_value>=0 || value2>=0 || _value>=(Integer.MIN_VALUE - value2)) &&
             (_value<=0 || value2<=0 || _value<=(Integer.MAX_VALUE - value2))) {
            return createInstance(_value + value2);
         }
      }

      IntegerNumber big = BigIntegerNumber.createInstance(BigInteger.valueOf(_value));
      return big.add(n);
   }

   public IntegerNumber multiply(IntegerNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      if (n.fitsInt()) {
         int value2 = n.intValue();

         // zero result, value is zero
         if (_value==0) {
            return this;

         // zero result, value2 is zero
         } else if (value2==0) {
            return n;

         // negative result
         } else if ((_value>0 && value2<0 && _value <= (Integer.MIN_VALUE / value2)) ||
             (_value<0 && value2>0 && value2 <= (Integer.MIN_VALUE / _value))) {
            return createInstance(_value * value2);
         }

         // positive result
         if (value2 <= (Integer.MAX_VALUE / _value)) {
            return createInstance(_value * value2);
         }

         // FALLTHROUGH
      }

      IntegerNumber big = BigIntegerNumber.createInstance(BigInteger.valueOf(_value));
      return big.multiply(n);
   }

   public IntegerNumber dec() {
      if (_value > Integer.MIN_VALUE) {
         return createInstance(_value - 1);
      }

      IntegerNumber big = BigIntegerNumber.createInstance(BigInteger.valueOf(_value));
      return big.dec();
   }

   public IntegerNumber inc() {
      if (_value < Integer.MAX_VALUE) {
         return createInstance(_value + 1);
      }

      IntegerNumber big = BigIntegerNumber.createInstance(BigInteger.valueOf(_value));
      return big.inc();
   }


   public IntegerNumber integerDivide(IntegerNumber n)
   throws ArithmeticException, IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      if (n.fitsInt()) {
         int value2 = n.intValue();

         if (value2==0) {
            throw new ArithmeticException("divide by zero");
         }

         return createInstance(_value / value2);
      }

      return ZERO;
   }

   public IntegerNumber remainder(IntegerNumber n)
   throws ArithmeticException, IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      if (n.fitsInt()) {
         int value2 = n.intValue();

         if (value2==0) {
            throw new ArithmeticException("divide by zero");
         }

         return (IntegerNumber) createInstance(_value % value2).abs();
      }

      return super.remainder(n);
   }

   public long longValue() {
      return _value;
   }

   public int intValue() {
      return _value;
   }

   public double doubleValue() {
      return (double) _value;
   }

   public byte[] toByteArray() {
      // XXX: Improve efficiency of this method toByteArray()
      return BigInteger.valueOf(_value).toByteArray();
   }

   public BigInteger toBigInteger() {
      return BigInteger.valueOf(_value);
   }
}
