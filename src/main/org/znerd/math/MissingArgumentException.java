/*
 * $Id: MissingArgumentException.java,v 1.1 2002/06/05 22:23:15 znerd Exp $
 */
package org.znerd.math;

import java.util.Map;
import java.util.HashMap;

/**
 * Exception thrown when one or more mandatory arguments for an object method
 * were found missing (<code>null</code>).
 *
 * @version $Revision: 1.1 $ $Date: 2002/06/05 22:23:15 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">ernst@FreeBSD.org</a>)
 */
class MissingArgumentException extends IllegalArgumentException {

   //-------------------------------------------------------------------------
   // Constructors
   //-------------------------------------------------------------------------

   /**
    * Constructs a <code>MissingArgumentException</code> for a single
    * mandatory argument. The name of the argument must be specified.
    *
    * @param argumentName
    *    the name of the argument that is mandatory but missing.
    *
    * @throws IllegalArgumentException
    *    if <code>argumentName == null</code>.
    */
   public MissingArgumentException(String argumentName)
   throws IllegalArgumentException {

      // Check the precondition
      if (argumentName == null) {
         throw new MissingArgumentException("argumentName");
      }

      // Set the internal field for retrieval by the getArgumentNames method
      _argumentNames = new String[1];
      _argumentNames[0] = argumentName;
      _argumentCount = 1;

      _message = argumentName + " == null";
   }

   /**
    * Constructs a new <code>MissingArgumentException</code> instance
    * for one or more mandatory arguments. The names of the arguments must
    * be specified and no two names can be identical.
    *
    * @param argumentNames
    *    the names of the arguments that are mandatory but missing.
    *
    * @throws MissingArgumentException
    *    if <code>argumentNames == null</code> or if
    *    <code>argumentNames</code> contains one or more <code>null</code>
    *    values.
    *
    * @throws IllegalArgumentException
    *    if either <code>argumentNames.length == 0</code> or
    *    <code>argumentNames</code> contains a duplicate argument name.
    */
   public MissingArgumentException(String[] argumentNames) {

      if (argumentNames == null)
         throw new MissingArgumentException("argumentNames");
      if (argumentNames.length == 0)
         throw new IllegalArgumentException("argumentNames.length == 0");

      // Count the number of arguments and save it in a cache field
      _argumentCount = argumentNames.length;

      // Create the array field that will contain the argument names
      _argumentNames = new String[_argumentCount];

      // Create a table that will contain the names we have had and the
      // index these names were found at
      Map table = new HashMap();

      // Check and save every argument name
      StringBuffer message = new StringBuffer();
      for (int i=0; i<_argumentCount; i++) {

         // Get the argument name from the array
         String name = argumentNames[i];

         // Check that the array element is not null
         if (name == null)
            throw new MissingArgumentException("argumentNames[" + i + ']');

         // Check that the argument name is new in this context
         Integer nameIndex = (Integer) table.get(name);
         if (nameIndex != null)
            throw new IllegalArgumentException("argumentNames[] contains a duplicate argument name: `" + name + "' is both at index " + nameIndex.intValue() + " and " + i);

         // Save the name of this argument with its index
         table.put(name, new Integer(i));

         // Save the name to the internal list of argument names
         _argumentNames[i] = name;

         // Extend the message string
         if (i > 0) {
            message.append(" and ");
         }
         message.append(name);
         message.append(" == null");
      }

      _message = message.toString();
   }


   //-------------------------------------------------------------------------
   // Fields
   //-------------------------------------------------------------------------

   /**
    * The number of arguments that were found missing.
    */
   protected final int _argumentCount;

   /**
    * The names of the arguments that were found missing.
    */
   protected final String[] _argumentNames;

   /**
    * The message that will be returned by <code>getMessage()</code>.
    */
   protected final String _message;


   //-------------------------------------------------------------------------
   // Getters
   //-------------------------------------------------------------------------

   /**
    * Gets the names of the arguments missing. The returned array can be
    * modified by the caller.
    *
    * @return
    *    the names of the missing arguments in an array, never
    *    <code>null</code>.
    */
   public String[] getArgumentNames() {
      String[] array = new String[_argumentCount];
      System.arraycopy(_argumentNames, 0, array, 0, _argumentCount);
      return array;
   }

   public String getMessage() {
      return _message;
   }
}
