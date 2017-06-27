/*
 * $Id: AbstractCompositeNumber.java,v 1.3 2002/08/16 21:54:40 znerd Exp $
 */
package org.znerd.math;

import java.math.*;

/**
 * Abstract base class for <code>CompositeNumber</code> implementations. This
 * implementation is based on an array. Concrete subclasses should initialize
 * the array upon construction.
 *
 * @version $Revision: 1.3 $ $Date: 2002/08/16 21:54:40 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public abstract class AbstractCompositeNumber extends CompositeNumber {

   //-------------------------------------------------------------------------
   // Constructors
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>AbstractCompositeNumber</code> object.
    *
    * <p />The sign of the value needs to be specified. Any negative value is
    * interpreted as meaning that the value of this number is negative. Any
    * positive value is interpreted as meaning that the value of this number
    * is positive.
    *
    * @param sign
    *    the sign of this number.
    *
    * @param asString
    *    a textual presentation for this number, not <code>null</code>
    *
    * @param elements
    *    the elements for this composite number, not <code>null</code>, not
    *    empty and not containing any <code>null</code> values.
    */
   protected AbstractCompositeNumber(int          sign,
                                     String       asString,
                                     RealNumber[] elements) {
      super(sign, asString);
      _elements = elements; // TODO
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The elements of this operation.
    */
   private final RealNumber[] _elements;


   //-------------------------------------------------------------------------
   // Methods
   //-------------------------------------------------------------------------

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
