/*
 * $Id: CompositeNumber.java,v 1.3 2002/08/16 21:54:40 znerd Exp $
 */
package org.znerd.math;

/**
 * A composite real number. Examples of composite numbers include
 * sines, logarithms, powers, sums and products.
 *
 * @version $Revision: 1.3 $ $Date: 2002/08/16 21:54:40 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public abstract class CompositeNumber extends RealNumber {

   //-------------------------------------------------------------------------
   // Constructors
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>CompositeNumber</code> object.
    *
    * <p />The sign of the value needs to be specified. Any negative value is
    * interpreted as meaning that the value of this number is negative. Any
    * positive value is interpreted as meaning that the value of this number
    * is positive.
    *
    * @param sign
    *    the sign of this number, either a positive number if the number is
    *    greater than zero, 0 if the number equals zero or a negative number
    *    if the number is smaller than zero.
    *
    * @param asString
    *    a textual presentation of this number, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>asString == null</code>.
    */
   protected CompositeNumber(int sign, String asString)
   throws IllegalArgumentException {
      super(sign, asString);
   }


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Returns the operands.
    *
    * @return
    *    a new array, containing the operands.
    */
   public abstract RealNumber[] getElements();

   /**
    * Counts the number of operands.
    *
    * @return
    *    the operand count, &gt;= 0.
    */
   public abstract int getElementCount();

   /**
    * Returns the <em>n</em>th operand.
    *
    * @param n
    *    the index of the operand, &gt;= 0 and &lt;
    *    {@link #getElementCount()}.
    *
    * @return
    *    the <em>n</em>th operand, not <code>null</code>.
    *
    * @throws IndexOutOfBoundsException
    *    if one of the following applies:
    *    <ol>
    *       <li><code>n &lt; 0</code></li>
    *       <li><code>n &gt;= operandCount</code></li>
    *    </ol>
    */
   public abstract RealNumber getElement(int n)
   throws IndexOutOfBoundsException;
}
