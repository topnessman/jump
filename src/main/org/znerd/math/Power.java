/*
 * $Id: Power.java,v 1.3 2002/08/16 20:51:08 znerd Exp $
 */
package org.znerd.math;

import java.math.*;

/**
 * A power, consisting of a base and an exponent.
 *
 * @version $Revision: 1.3 $ $Date: 2002/08/16 20:51:08 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public class Power extends AbstractCompositeNumber {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns a <code>Power</code> with the specified operands.
    *
    * @param base
    *    the base for the power, not <code>null</code>.
    *
    * @param exponent
    *    the exponent for the power, not <code>null</code>.
    *
    * @return
    *    the <code>Power</code> instance, possibly newly constructed.
    *
    * @throws IllegalArgumentException
    *    if <code>base == null || exponent == null</code>.
    */
   public static Power createInstance(RealNumber base, RealNumber exponent)
   throws IllegalArgumentException {
      return new Power(base, exponent);
   }

   /**
    * Computes the sign of a power with the specified operands.
    *
    * @param base
    *    the base for the power, not <code>null</code>.
    *
    * @param exponent
    *    the exponent for the power, not <code>null</code>.
    *
    * @return
    *    the <code>Power</code> instance, possibly newly constructed.
    *
    * @throws IllegalArgumentException
    *    if <code>base == null || exponent == null</code>.
    */
   protected static int determineSign(RealNumber base, RealNumber exponent)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("base", base, "exponent", exponent);

      // TODO: Implement this method determineSign(RealNumber,RealNumber)

      throw new InternalError("Unable to determine sign of power.");
   }

   /**
    * Creates a textual representation of an instance of this class that has
    * the specified base and exponent.
    *
    * @param base
    *    the base for the power, not <code>null</code>.
    *
    * @param exponent
    *    the exponent for the power, not <code>null</code>.
    *
    * @return
    *    the textual presentation, never <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>base == null || exponent == null</code>.
    */
   private static String createString(RealNumber base, RealNumber exponent)
   throws IllegalArgumentException {

      // Check preconditions
      MandatoryArgumentChecker.check("base", base, "exponent", exponent);

      // Create a buffer to store the string in
      StringBuffer buffer = new StringBuffer();

      // If the base is composite, then put it between brackets
      if (base instanceof CompositeNumber) {
         buffer.append('(');
         buffer.append(base);
         buffer.append(')');
      } else {
         buffer.append(base);
      }

      buffer.append("**");

      // If the exponent is composite, then put it between brackets
      if (exponent instanceof CompositeNumber) {
         buffer.append('(');
         buffer.append(exponent);
         buffer.append(')');
      } else {
         buffer.append(exponent);
      }

      return buffer.toString();
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a <code>Power</code> with the specified base and exponent.
    *
    * @param base
    *    the base for the power, not <code>null</code>.
    *
    * @param exponent
    *    the exponent for the power, not <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>base == null || exponent == null</code>.
    */
   protected Power(RealNumber base, RealNumber exponent)
   throws IllegalArgumentException {
      super(determineSign(base, exponent),
            createString(base, exponent),
            new RealNumber[] { base, exponent });
   }


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

   /**
    * Returns the base of this power.
    *
    * @return
    *    the base, never <code>null</code>.
    */
   public RealNumber getBase() {
      return getElement(0);
   }

   /**
    * Returns the exponent of this power.
    *
    * @return
    *    the exponent, never <code>null</code>.
    */
   public RealNumber getExponent() {
      return getElement(1);
   }

   public BigDecimal toBigDecimal(int precision, int roundingMode)
   throws IllegalArgumentException {
      throw new InternalError("TODO");
   }

   public IntegerNumber trunc() {
      throw new InternalError("TODO");
   }
}
