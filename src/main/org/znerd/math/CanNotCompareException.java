/*
 * $Id: CanNotCompareException.java,v 1.4 2002/08/16 19:12:40 znerd Exp $
 */
package org.znerd.math;

/**
 * Exception thrown to indicate a compare operation failed. This exception
 * is thrown by the <code>compareTo(RealNumber)</code> method in
 * interface <code>RealNumber</code>.
 *
 * @version $Revision: 1.4 $ $Date: 2002/08/16 19:12:40 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 *
 * @see RealNumber#compareTo(RealNumber)
 */
public class CanNotCompareException extends RuntimeException {

   //-------------------------------------------------------------------------
   // Class constants
   //-------------------------------------------------------------------------

   /**
    * Creates a textual presentation for a <code>CanNotCompareException</code>
    * that gets the specified arguments.
    *
    * @param a
    *    the first number, not <code>null</code>.
    *
    * @param b
    *    the second number, not <code>null</code>.
    *
    * @return
    *    a textual presentation suitable for a
    *    <code>CanNotCompareException</code> with the specified arguments,
    *    never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    */
   private static String createString(RealNumber a, RealNumber b)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("a", a, "b", b);

      StringBuffer buffer = new StringBuffer(256);

      buffer.append("Unable to compare ");
      buffer.append(a.getClass().getName());
      buffer.append(" (");
      buffer.append(a.toString());
      buffer.append(") and ");
      buffer.append(b.getClass().getName());
      buffer.append(" (");
      buffer.append(b.toString());
      buffer.append(").");

      return buffer.toString();
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>CanNotCompareException</code> for a failed
    * comparison between the specified numbers.
    *
    * @param a
    *    the first number, not <code>null</code>.
    *
    * @param b
    *    the second number, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>a == null || b == null</code>.
    */
   public CanNotCompareException(RealNumber a, RealNumber b)
   throws IllegalArgumentException {

      super(createString(a, b));

      // Store the numbers
      _numbers = new RealNumber[2];
      _numbers[0] = a;
      _numbers[1] = b;
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The numbers that cannot be compared. This array is not <code>null</code>
    * and contains 2 non-<code>null</code> elements.
    */
   private final RealNumber[] _numbers;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Returns the numbers that could not be compared in a new array.
    *
    * @return
    *    the numbers that could not be compared in a new array with size 2,
    *    never <code>null</code>.
    */
   public RealNumber[] getNumbers() {
      RealNumber[] copy = new RealNumber[2];
      copy[0] = _numbers[0];
      copy[1] = _numbers[1];
      return copy;
   }
}
