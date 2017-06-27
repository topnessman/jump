/*
 * $Id: BigIntegerNumber.java,v 1.8 2002/10/01 18:46:31 znerd Exp $
 */
package org.znerd.math;

import java.math.*;

/**
 * Basic implementation of an integer number. This implementation is based on
 * a <code>java.math.BigInteger</code>. It can hold all values from
 * -2**32 to (2**32 - 1).
 *
 * <p />Instances must be obtained by using the
 * <code>createInstance()</code> factory method.
 *
 * @version $Revision: 1.8 $ $Date: 2002/10/01 18:46:31 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 *
 * @see java.math.BigInteger
 */
public final class BigIntegerNumber extends IntegerNumber {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns an instance of a <code>BigIntegerNumber</code> based on a
    * <code>java.math.BigInteger</code>.
    *
    * @param n
    *    the {@link BigInteger} to construct a <code>BigIntegerNumber</code>
    *    from.
    *
    * @return
    *    the (possibly newly constructed) <code>BigIntegerNumber</code>
    *    instance.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   public static BigIntegerNumber createInstance(BigInteger n)
   throws IllegalArgumentException {
      return new BigIntegerNumber(n);
   }

   /**
    * Determines the sign for the specified <code>BigInteger</code>.
    *
    * @param n
    *    the {@link BigInteger} to determine the sign of, not
    *    <code>null</code>.
    *
    * @return
    *    the sign for the number, either -1 if it is determined to be
    *    negative, 0 if it is determined to be zero or 1 if it is determined
    *    to be positive.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   private static int determineSign(BigInteger n)
   throws IllegalArgumentException {
      MandatoryArgumentChecker.check("n", n);
      return n.signum();
   }

   /**
    * Returns a textual presentation of the specified <code>BigInteger</code>.
    *
    * @param n
    *    the {@link BigInteger}, not <code>null</code>.
    *
    * @return
    *    the result of <code>n.toString()</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   private static String createString(BigInteger n)
   throws IllegalArgumentException {
      MandatoryArgumentChecker.check("n", n);
      return n.toString();
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a <code>BigIntegerNumber</code> from a
    * <code>BigInteger</code>.
    *
    * @param n
    *    the value for the new number, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>n == null</code>.
    */
   protected BigIntegerNumber(BigInteger n)
   throws IllegalArgumentException {
      super(determineSign(n), createString(n));

      _bigInteger = n;
      _hash       = _bigInteger.hashCode();
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The <code>BigInteger</code> this
    * <code>IntegerNumber</code> implementation is based on.
    */
   private final BigInteger _bigInteger;

   /**
    * The hash code.
    */
   private final int _hash;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   public int hashCode() {
      return _hash;
   }

   public IntegerNumber add(IntegerNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      BigInteger bigInt;
      if (n instanceof BigIntegerNumber) {
         bigInt = ((BigIntegerNumber) n)._bigInteger;
      } else {
         bigInt = new BigInteger(n.toByteArray());
      }

      return createInstance(_bigInteger.add(bigInt));
   }

   public IntegerNumber multiply(IntegerNumber n)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);

      BigInteger bigInt;
      if (n instanceof BigIntegerNumber) {
         bigInt = ((BigIntegerNumber) n)._bigInteger;
      } else {
         bigInt = new BigInteger(n.toByteArray());
      }

      return createInstance(_bigInteger.multiply(bigInt));
   }

   public IntegerNumber integerDivide(IntegerNumber n)
   throws ArithmeticException, IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("n", n);
      NumberCentral.checkDivideByZero(n);

      BigInteger bigInt;
      if (n instanceof BigIntegerNumber) {
         bigInt = ((BigIntegerNumber) n)._bigInteger;
      } else {
         bigInt = new BigInteger(n.toByteArray());
      }

      return createInstance(_bigInteger.divide(bigInt));
   }

   protected RationalNumber powImpl(int exponent) {
      // TODO: Handle negative numbers
      return new BigIntegerNumber(_bigInteger.pow(exponent));
   }

   public long longValue() {
      return _bigInteger.longValue();
   }

   public double doubleValue() {
      return _bigInteger.doubleValue();
   }

   public byte[] toByteArray() {
      return _bigInteger.toByteArray();
   }

   public BigInteger toBigInteger() {
      return _bigInteger;
   }
}
