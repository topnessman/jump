/*
 * $Id: RoundingMode.java,v 1.11 2002/08/16 21:54:40 znerd Exp $
 */
package org.znerd.math;

/**
 * Rounding mode. This is an abstract base class for actual rounding mode
 * implementations.
 *
 * <p />Concrete subclasses must implement
 * {@link #roundImpl(RealNumber,int,int,int)}.
 *
 * @version $Revision: 1.11 $ $Date: 2002/08/16 21:54:40 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public abstract class RoundingMode extends Object {

   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>RoundingMode</code> with the specified name. The
    * name will be returned by {@link #getName()}.
    *
    * @param name
    *    the name for the rounding mode, not <code>null</code>.
    *
    * @param consistent
    *    <code>true</code> if this rounding mode always rounds in the same
    *    direction when passed the same arguments.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null</code>.
    */
   protected RoundingMode(String name, boolean consistent)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("name", name);

      // Store information
      _name       = name;
      _consistent = consistent;
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The name of this rounding mode. This field is initialized by the
    * constructor and it can never be set to <code>null</code>.
    */
   private final String _name;

   /**
    * Flag that indicates if this rounding mode is consistent. This field is
    * <code>true</code> if this rounding mode always rounds the same way when
    * passed the same arguments.
    *
    * <p />The value of this field is returned by {@link #isConsistent()}.
    */
   private final boolean _consistent;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Rounds the specified digit for a number in the specified base. If the
    * number to be rounded is zero, then <code>false</code> is returned.
    *
    * @param number
    *    the number that is being rounded, not <code>null</code>.
    *
    * @param radix
    *    the radix (or base) for the digit, at least 2, at most
    *    {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @param digit
    *    the digit of which should be determined whether it should be rounded
    *    up or down, it should be at least 0, and less than the radix.
    *
    * @return
    *    <code>true</code> if the concerned number should be rounded up, away
    *    from zero; <code>false</code> if it should be rounded down, towards
    *    zero.
    *
    * @throws IllegalArgumentException
    *    if <code>number ==    null
    *          || radix  &lt;  2
    *          || radix  &gt;  {@link RealNumber#MAXIMUM_RADIX}
    *          || digit  &lt;  0
    *          || digit  &gt;= radix</code>.
    */
   public final boolean round(RealNumber number, int radix, int digit)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("number", number);
      RealNumber.checkRadix(radix);
      if (digit < 0) {
         throw new IllegalArgumentException("digit (" + digit + ") < 0");
      } else if (digit >= radix) {
         throw new IllegalArgumentException("digit (" + digit + ") >= radix (" + radix + ')');
      }

      // Short-circuit if the number is zero
      int sign = number.getSign();
      if (sign == 0) {
         return false;
      }

      // Perform the actual rounding
      return roundImpl(number, sign, radix, digit);
   }

   /**
    * Actually rounds the specified digit for a number in the specified base.
    * This method is called from {@link #round(RealNumber,int,int)}.
    *
    * @param number
    *    the number that is being rounded, guaranteed not to be
    *    <code>null</code> and not to be zero.
    *
    * @param sign
    *    the sign, either -1 or 1.
    *
    * @param radix
    *    the radix (or base) for the digit, guaranteed to be at least 2 and at
    *    most {@link RealNumber#MAXIMUM_RADIX}.
    *
    * @param digit
    *    the digit of which should be determined whether it should be rounded
    *    up or down, guaranteed to be at least 0, and less than the radix.
    *
    * @return
    *    <code>true</code> if the concerned number should be rounded up, away
    *    from zero; <code>false</code> if it should be rounded down, towards
    *    zero.
    */
   protected abstract boolean roundImpl(RealNumber number, int sign, int radix, int digit);

   /**
    * Returns a short, human-presentable, description of this rounding mode.
    *
    * @return
    *    the name, never <code>null</code>.
    */
   public final String getName() {
      return _name;
   }

   /**
    * Returns a textual presentation of this object.
    *
    * @return
    *    a textual presentation of this object, never <code>null</code>.
    */
   public final String toString() {
      return _name;
   }

   /**
    * Indicates if this rounding mode is consistent.
    *
    * @return
    *    <code>true</code> if this rounding mode always rounds the same way
    *    when passed the same arguments.
    */
   public final boolean isConsistent() {
      return _consistent;
   }
}
