/*
 * $Id: Sum.java,v 1.7 2002/08/16 21:54:40 znerd Exp $
 */
package org.znerd.math;

import java.math.*;

/**
 * A sum of two real numbers.
 *
 * <p />Instances of <code>Sum</code> must be obtained by using one of the
 * <code>createInstance()</code> factory methods.
 *
 * @version $Revision: 1.7 $ $Date: 2002/08/16 21:54:40 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public class Sum extends CompositeNumber {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns a <code>Sum</code> with the specified operands.
    *
    * @param a
    *    the first operand, not <code>null</code>.
    *
    * @param b
    *    the second operand, not <code>null</code>.
    *
    * @return
    *    the <code>Sum</code> instance, possibly newly constructed.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    *
    * @throws CanNotCompareException
    *    if the sign of this sum cannot be determined because the 2 arguments
    *    cannot be compared.
    */
   public static Sum createInstance(RealNumber a, RealNumber b)
   throws IllegalArgumentException, CanNotCompareException {
      return new Sum(a, b);
   }

   /**
    * Computes the sign of a sum with the specified operands.
    *
    * @param a
    *    the first operand, not <code>null</code>.
    *
    * @param b
    *    the second operand, not <code>null</code>.
    *
    * @return
    *    the sign for a sum with the specified operands, either -1 if the
    *    number is smaller than zero, 0 if the number is zero, or 1 if the
    *    number is greater than 0.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    *
    * @throws CanNotCompareException
    *    if a comparison was necessary but failed.
    */
   private static int determineSign(RealNumber a, RealNumber b)
   throws IllegalArgumentException, CanNotCompareException {

      // Check preconditions
      MandatoryArgumentChecker.check("a", a, "b", b);

      int signA = a.getSign();
      int signB = b.getSign();

      if (signA != signB) {

         if (signA==-1 && signB==1) {
            return a.compareTo(b.negate());
         } else if (signA==1 && signB==-1) {
            return b.compareTo(a.negate());
         } else if (signA==0) {
            return signB;
         } else { // if (signB==0)
            return signA;
         }
      }

      // implicit else (signA==signB)
      return signA;
   }

   /**
    * Creates a textual presentation of this number. This method is used by
    * the constructor.
    *
    * @param a
    *    the first operand for this sum, not <code>null</code>.
    *
    * @param b
    *    the second operand for this sum, not <code>null</code>.
    *
    * @return
    *    a textual presentation for this sum, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    */
   private final static String createString(RealNumber a, RealNumber b)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("a", a, "b", b);

      StringBuffer buffer = new StringBuffer(512);

      if (a instanceof CompositeNumber) {
         buffer.append('(');
         buffer.append(a.toString());
         buffer.append(")+");
      } else {
         buffer.append(a.toString());
         buffer.append('+');
      }

      if (b instanceof CompositeNumber) {
         buffer.append('(');
         buffer.append(b.toString());
         buffer.append(')');
      } else {
         buffer.append(b.toString());
      }

      return buffer.toString();
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a <code>Sum</code> based on the 2 specified operands.
    *
    * @param a
    *    the first operand for the sum, not <code>null</code>.
    *
    * @param b
    *    the second operand for the sum, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    *
    * @throws CanNotCompareException
    *    if the sign of this sum cannot be determined because the 2 arguments
    *    cannot be compared.
    */
   protected Sum(RealNumber a, RealNumber b)
   throws IllegalArgumentException, CanNotCompareException {

      // Call the CompositeNumber constructor
      super(determineSign(a, b), createString(a, b));

      // Store the arguments
      _elements = new RealNumber[2];
      _elements[0] = a;
      _elements[1] = b;
   }


   // XXX: add this method ?
/*
   public static Sum createInstance(RealNumber[] operands)
   throws IllegalArgumentException
   {
      ExceptionSupport.checkOperandsNotNull(operands);
      ExceptionSupport.checkOperandsLengthAtLeast2(operands);

      if (operands.length == 2)
      {
         return createInstance(operands[0], operands[1]);
      }

      RealNumber[] lastOperands = new RealNumber[operands.length - 1];
      System.arraycopy(lastOperands, 1, operands, 0, operands.length - 1);

      return createInstance(operands[0], createInstance(lastOperands));
   }
*/
   //---------------------------


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The operands for this sum. This field is never <code>null</code> and it
    * should never contain any <code>null</code> elements. It is initialized
    * by the constructor. After that the contents should never change anymore.
    */
   private final RealNumber[] _elements;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   protected int compareToImpl(RealNumber n)
   throws IllegalArgumentException, CanNotCompareException {

      int thatSign = n.getSign();
      int thisSign = getSign();

      if (thisSign > thatSign) {
         return  1;
      } else if (thisSign < thatSign) {
         return -1;
      } else if ((thisSign == 0) && (thatSign == 0)) {
         return  0;
      }

      //------------------------------
      // TODO: How do we do this ?!
      //------------------------------

      throw new CanNotCompareException(n, this);
   }

   /**
    * Converts the value of this number to a <code>BigDecimal</code> with the
    * specified precision and rounding mode.
    *
    * @param precision the number of digits behind the decimal point.
    * @param roundingMode the rounding mode to use, one of the modes defined
    *    in class <code>BigDecimal</code>.
    * @return a <code>BigDecimal</code> with the rounded value of this.
    * @throws IllegalArgumentException if <em>precision</em>&lt;0 or
    *    the rounding mode is not one of the valid rounding modes defined in
    *    class <code>BigDecimal</code>.
    */
   public BigDecimal toBigDecimal(int precision, int roundingMode)
   throws IllegalArgumentException {

      BigDecimal a = _elements[0].toBigDecimal(precision+1,
         BigDecimal.ROUND_HALF_UP);
      BigDecimal b = _elements[1].toBigDecimal(precision+1,
         BigDecimal.ROUND_HALF_UP);

      // compute BigDecimal sum
      BigDecimal result = a.add(b);

      // return correct precision
      return result.setScale(precision, roundingMode);
   }

   /**
    * Rounds to an integer number towards 0.
    *
    * @return this number truncated to an integer.
    */
   public IntegerNumber trunc() {

      // XXX: Look into this.
      BigDecimal bigDecimal = toBigDecimal(0, BigDecimal.ROUND_FLOOR);
      return NumberCentral.valueOf(bigDecimal).trunc();
   }

   public RealNumber[] getElements() {
      return (RealNumber[]) _elements.clone();
   }

   public int getElementCount() {
      return _elements.length;
   }

   public RealNumber getElement(int n)
   throws IndexOutOfBoundsException {
      return _elements[n];
   }
}
