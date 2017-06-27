/*
 * $Id: SimpleExample.java,v 1.9 2002/10/01 18:45:56 znerd Exp $
 */
package org.znerd.math.examples.simple;

import org.znerd.math.*;

/**
 * Simple example program that shows some basic JUMP functionalities.
 *
 * @version $Revision: 1.9 $ $Date: 2002/10/01 18:45:56 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD</a>)
 */
public class SimpleExample extends Object {

   /**
    * Main function.
    *
    * @param args
    *    the arguments to this function.
    */
   public final static void main(String[] args) {

      int i = 39509245;
      IntegerNumber number1 = valueOf(i);
      System.out.println("-- The int value " + i + " gets converted to an instance of " + number1.getClass().getName() + " with the value " + number1 + '.');

      int j = -4514223;
      IntegerNumber number2 = valueOf(j);
      System.out.println("-- The int value " + j + " gets converted to an instance of " + number2.getClass().getName() + " with the value " + number2 + '.');

      int compareResult = compare(number1, number2);

      System.out.print("-- Determined that ");
      System.out.print(number1);
      System.out.print(" is ");
      if (compareResult < 0) {
         System.out.print("smaller than");
      } else if (compareResult > 0) {
         System.out.print("greater than");
      } else {
         System.out.print("equal to");
      }
      System.out.print(' ');
      System.out.print(number2);
      System.out.println('.');

      round(number1, 4, RoundingModes.ROUND_FLOOR);
      round(number1, 4, RoundingModes.ROUND_CEILING);
      round(number1, 4, RoundingModes.ROUND_DOWN);
      round(number1, 4, RoundingModes.ROUND_UP);

      double d = 0.399;
      RationalNumber n = valueOf(d);
      System.out.println("-- The double value " + d + " gets converted to an instance of " + n.getClass().getName() + " with the value " + n + '.');

      int numerator   = -1;
      int denominator =  2;
      n = createFraction(numerator, denominator);
      System.out.println("-- The fraction parts " + numerator + " and " + denominator + " get converted to an instance of " + n.getClass().getName() + " with the value " + n + '.');

      numerator   =  1;
      denominator = -2;
      n = createFraction(numerator, denominator);
      System.out.println("-- The fraction parts " + numerator + " and " + denominator + " get converted to an instance of " + n.getClass().getName() + " with the value " + n + '.');

      System.out.println("-- The smallest double is: " + Double.MIN_VALUE);
   }

   private static IntegerNumber valueOf(int n) {

      System.out.print(">> Converting ");
      System.out.print(n);
      System.out.print("...");
      try {
         IntegerNumber obj = NumberCentral.valueOf(n);
         System.out.println(" [ DONE ]");
         return obj;
      } catch (Throwable t) {
         System.out.println(" [ FAILED ]");
         t.printStackTrace(System.out);
         System.exit(1);
         throw new InternalError(); // XXX: Keep the compiler happy
      }
   }

   private static RationalNumber valueOf(double n) {

      System.out.print(">> Converting ");
      System.out.print(n);
      System.out.print("...");
      try {
         RationalNumber obj = NumberCentral.valueOf(n);
         System.out.println(" [ DONE ]");
         return obj;
      } catch (Throwable t) {
         System.out.println(" [ FAILED ]");
         t.printStackTrace(System.out);
         System.exit(1);
         throw new InternalError(); // XXX: Keep the compiler happy
      }
   }

   private static RationalNumber createFraction(int numerator, int denominator) {

      System.out.print(">> Converting ");
      System.out.print(numerator);
      System.out.print('/');
      System.out.print(denominator);
      System.out.print("...");
      try {
         RationalNumber obj = NumberCentral.createFraction(numerator, denominator);
         System.out.println(" [ DONE ]");
         return obj;
      } catch (Throwable t) {
         System.out.println(" [ FAILED ]");
         t.printStackTrace(System.out);
         System.exit(1);
         throw new InternalError(); // XXX: Keep the compiler happy
      }
   }

   private static int compare(RealNumber number1, RealNumber number2) {
      System.out.print(">> Comparing ");
      System.out.print(number1);
      System.out.print(" and ");
      System.out.print(number2);
      System.out.print("...");
      try {
         int compareResult = number1.compareTo(number2);
         System.out.println(" [ DONE ]");
         return compareResult;
      } catch (Throwable t) {
         System.out.println(" [ FAILED ]");
         t.printStackTrace(System.out);
         System.exit(1);
         throw new InternalError(); // XXX: Keep the compiler happy
      }
   }

   private static void round(IntegerNumber n,
                             int           precision,
                             RoundingMode  roundingMode) {

      final int radix = 10;

      System.out.print(">> Rounding ");
      System.out.print(n);
      System.out.print(" (radix=");
      System.out.print(radix);
      System.out.print(", precision=");
      System.out.print(precision);
      System.out.print(", roundingMode=");
      System.out.print(roundingMode);
      System.out.print(")... ");

      try {
         DigitSet ds = n.round(radix, precision, roundingMode);
         System.out.println(" [ DONE ]");
         System.out.println("-- The value " + n + " gets rounded to an instance of " + ds.getClass().getName() + " with the value " + ds.toString() + '.');
      } catch (Throwable t) {
         System.out.println(" [ FAILED ]");
         t.printStackTrace(System.out);
         System.exit(1);
         throw new InternalError(); // XXX: Keep the compiler happy
      }
   }
}
