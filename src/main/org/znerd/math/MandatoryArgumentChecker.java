/*
 * $Id: MandatoryArgumentChecker.java,v 1.2 2002/08/16 20:58:56 znerd Exp $
 */
package org.znerd.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Checks the arguments for a method.
 *
 * @version $Revision: 1.2 $ $Date: 2002/08/16 20:58:56 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
class MandatoryArgumentChecker extends Object {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Asserts that the argument with the specified name is not
    * <code>null</code>. If it is, then an
    * {@link IllegalArgumentException} is thrown. Otherwise nothing is done.
    *
    * @param argumentName
    *    the name of the argument to check, not <code>null</code>; this
    *    precondition is not checked unless
    *    <code>argumentValue == null</code>.
    *
    * @param argumentValue
    *    the value to compare with <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>argumentValue == null</code>.
    */
   public static void check(String argumentName, Object argumentValue)
   throws IllegalArgumentException {

      if (argumentValue == null) {
         throw new MissingArgumentException(argumentName);
      }
   }

   /**
    * Asserts that the 2 arguments with the specified names are not
    * <code>null</code>. If any of them is, then an
    * {@link IllegalArgumentException} is thrown. Otherwise nothing is done.
    *
    * @param argumentName1
    *    the name of the first argument to check, not <code>null</code>; this
    *    precondition is not checked unless
    *    <code>argumentValue == null</code>.
    *
    * @param argumentValue1
    *    the value of the first argument to compare with <code>null</code>.
    *
    * @param argumentName2
    *    the name of the second argument to check, not <code>null</code>; this
    *    precondition is not checked unless
    *    <code>argumentValue == null</code>.
    *
    * @param argumentValue2
    *    the value of the second argument to compare with <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>argumentValue1 == null || argumentValue2 == null</code>.
    */
   public static void check(String argumentName1, Object argumentValue1,
                            String argumentName2, Object argumentValue2)
   {
      if (argumentValue1 == null && argumentValue2 == null) {
         throw new MissingArgumentException(new String[]{argumentName1, argumentName2});
      } else if (argumentValue1 == null) {
         throw new MissingArgumentException(argumentName1);
      } else if (argumentValue2 == null) {
         throw new MissingArgumentException(argumentName2);
      }
   }

   /**
    * Asserts that the 3 arguments with the specified names are not
    * <code>null</code>. If any of them is, then an
    * {@link IllegalArgumentException} is thrown. Otherwise nothing is done.
    *
    * @param argumentName1
    *    the name of the first argument to check, not <code>null</code>; this
    *    precondition is not checked unless
    *    <code>argumentValue == null</code>.
    *
    * @param argumentValue1
    *    the value of the first argument to compare with <code>null</code>.
    *
    * @param argumentName2
    *    the name of the second argument to check, not <code>null</code>; this
    *    precondition is not checked unless
    *    <code>argumentValue == null</code>.
    *
    * @param argumentValue2
    *    the value of the second argument to compare with <code>null</code>.
    *
    * @param argumentName3
    *    the name of the third argument to check, not <code>null</code>; this
    *    precondition is not checked unless
    *    <code>argumentValue == null</code>.
    *
    * @param argumentValue3
    *    the value of the third argument to compare with <code>null</code>.
    *
    * @throws IllegalArgumentException
    *    if <code>argumentValue1 == null ||
    *             argumentValue2 == null ||
    *             argumentValue3 == null ||</code>.
    */
   public static void check(String argumentName1, Object argumentValue1,
                            String argumentName2, Object argumentValue2,
                            String argumentName3, Object argumentValue3)
   {
      if (argumentValue1==null && argumentValue2==null && argumentValue3==null) {
         throw new MissingArgumentException(new String[]{argumentName1, argumentName2, argumentName3});
      } else if (argumentValue1==null && argumentValue2==null) {
         throw new MissingArgumentException(new String[]{argumentName1, argumentName2});
      } else if (argumentValue1==null && argumentValue3==null) {
         throw new MissingArgumentException(new String[]{argumentName1, argumentName3});
      } else if (argumentValue2==null && argumentValue3==null) {
         throw new MissingArgumentException(new String[]{argumentName2, argumentName3});
      } else if (argumentValue1 == null) {
         throw new MissingArgumentException(argumentName1);
      } else if (argumentValue2 == null) {
         throw new MissingArgumentException(argumentName2);
      } else if (argumentValue3 == null) {
         throw new MissingArgumentException(argumentName3);
      }
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>MandatoryArgumentChecker</code> object. This
    * constructor is never called since this class is only used for its class
    * functions.
    */
   private MandatoryArgumentChecker() {
      // empty
   }
}
