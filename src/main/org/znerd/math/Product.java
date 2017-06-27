/*
 * $Id: Product.java,v 1.5 2002/08/16 21:35:50 znerd Exp $
 */
package org.znerd.math;

import java.math.*;

/**
 * A product of two real numbers.
 *
 * @version $Revision: 1.5 $ $Date: 2002/08/16 21:35:50 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public class Product extends AbstractCompositeNumber {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns a <code>Product</code> with the specified operands.
    *
    * @param a
    *    the first operand, not <code>null</code>.
    *
    * @param b
    *    the second operand, not <code>null</code>.
    *
    * @return
    *    the <code>Product</code> instance, possibly newly constructed, never
    *    <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    */
   public static Product createInstance(RealNumber a, RealNumber b)
   throws IllegalArgumentException {
      return new Product(a, b);
   }

   /**
    * Creates a textual representation for a product with the specified
    * operands.
    *
    * @param a
    *    the first operand, not <code>null</code>.
    *
    * @param b
    *    the second operand, not <code>null</code>.
    *
    * @return
    *    a textual presentation for a product with the specified operands,
    *    never <code>null</code>.
    */
   private static String createString(RealNumber a, RealNumber b) {

      StringBuffer buffer = new StringBuffer();

      if (a instanceof CompositeNumber) {
         buffer.append('(');
         buffer.append(a);
         buffer.append(")*");
      } else {
         buffer.append(a);
         buffer.append('*');
      }

      if (b instanceof CompositeNumber) {
         buffer.append('(');
         buffer.append(b);
         buffer.append(')');
      } else {
         buffer.append(b);
      }

      return buffer.toString();
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a <code>Product</code> based on the 2 specified operands.
    *
    * @param a
    *    the first operand for the product, not <code>null</code>.
    *
    * @param b
    *    the second operand for the product, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    */
   protected Product(RealNumber a, RealNumber b)
   throws IllegalArgumentException {
      super(determineSign(a, b),
            createString(a, b),
            new RealNumber[] { a, b });
   }


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Computes the sign of a product with the specified operands.
    *
    * @param a
    *    the first operand, not <code>null</code>.
    *
    * @param b
    *    the second operand, not <code>null</code>.
    *
    * @return
    *    the sign for the product of <code>a</code> and <code>b</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    */
   protected static int determineSign(RealNumber a, RealNumber b)
   throws IllegalArgumentException {

      MandatoryArgumentChecker.check("a", a, "b", b);
      return a.getSign() * b.getSign();
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
    *    {@link BigDecimal}.
    *
    * @return
    *    a <code>BigDecimal</code> with the rounded value of this number,
    *    never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>precision &lt; 0</code> or if the rounding mode is not one
    *    of the valid rounding modes defined in {@link BigDecimal}.
    */
   public BigDecimal toBigDecimal(int precision, int roundingMode)
   throws IllegalArgumentException {

      BigDecimal a = getElement(0).toBigDecimal(precision+1, roundingMode);
      BigDecimal b = getElement(1).toBigDecimal(precision+1, roundingMode);

      // compute BigDecimal product
      BigDecimal result = a.multiply(b);

      // return correct precision
      return result.setScale(precision, roundingMode);
   }

   /**
    * Rounds to an integer number towards 0.
    *
    * @return
    *    this number truncated to an integer, not <code>null</code>.
    */
   public IntegerNumber trunc() {

      //-------------------------------------
      // PENDING: Reconsider this
      //          This is pretty safe for now
      //-------------------------------------

      return NumberCentral.valueOf(toBigDecimal(0)).trunc();
   }
}
