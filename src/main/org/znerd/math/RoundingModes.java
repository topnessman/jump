/*
 * $Id: RoundingModes.java,v 1.1 2002/06/12 19:49:43 znerd Exp $
 */
package org.znerd.math;

/**
 * Set of standard rounding modes.
 *
 * @version $Revision: 1.1 $ $Date: 2002/06/12 19:49:43 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public interface RoundingModes {

   /**
    * Rounding mode that rounds towards positive infinity.
    */
   final static RoundingMode ROUND_CEILING = BasicRoundingMode.CEILING;

   /**
    * Rounding mode that rounds towards negative infinity.
    */
   final static RoundingMode ROUND_FLOOR = BasicRoundingMode.FLOOR;

   /**
    * Rounding mode that rounds towards 0.
    */
   final static RoundingMode ROUND_DOWN = BasicRoundingMode.DOWN;

   /**
    * Rounding mode that rounds away from 0.
    */
   final static RoundingMode ROUND_UP = BasicRoundingMode.UP;
}
