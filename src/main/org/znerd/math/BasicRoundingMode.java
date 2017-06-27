/*
 * $Id: BasicRoundingMode.java,v 1.5 2002/08/16 21:54:40 znerd Exp $
 */
package org.znerd.math;

/**
 * Basic implementation of <code>RoundingMode</code>.
 *
 * <p />All instances of this class are consistent (see
 * {@link #isConsistent()}).
 *
 * @version $Revision: 1.5 $ $Date: 2002/08/16 21:54:40 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
final class BasicRoundingMode extends RoundingMode {

   //-------------------------------------------------------------------------
   // Class constants
   //-------------------------------------------------------------------------

   /**
    * Rounding mode that rounds towards positive infinity.
    */
   final static BasicRoundingMode CEILING = new BasicRoundingMode("ceiling", false, true);

   /**
    * Rounding mode that rounds towards negative infinity.
    */
   final static BasicRoundingMode FLOOR = new BasicRoundingMode("floor", true, false);

   /**
    * Rounding mode that rounds towards 0.
    */
   final static BasicRoundingMode DOWN = new BasicRoundingMode("down", false, false);

   /**
    * Rounding mode that rounds away from 0.
    */
   final static BasicRoundingMode UP = new BasicRoundingMode("up", true, true);


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
    * @param negativeUp
    *    <code>true</code> if negative values should be rounded towards
    *    positive infinity.
    *
    * @param positiveUp
    *    <code>true</code> if positive values should be rounded towards
    *    positive infinity.
    *
    * @throws IllegalArgumentException
    *    if <code>name == null</code>.
    */
   private BasicRoundingMode(String name, boolean negativeUp, boolean positiveUp)
   throws IllegalArgumentException {
      super(name, true);
      _negativeUp = negativeUp;
      _positiveUp = positiveUp;
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * Flag that determines if negative values should be rounded towards
    * positive infinity. If the value of this field is <code>true</code> then
    * they will.
    */
   private final boolean _negativeUp;

   /**
    * Flag that determines if positive values should be rounded towards
    * positive infinity. If the value of this field is <code>true</code> then
    * they will.
    */
   private final boolean _positiveUp;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   protected boolean roundImpl(RealNumber number, int sign, int radix, int digit)
   throws IllegalArgumentException {
      if (sign < 0) {
         return _negativeUp;
      }
      return _positiveUp;
   }
}
