/*
 * $Id: AllTests.java,v 1.2 2002/08/12 21:06:48 znerd Exp $
 */
package org.znerd.math.tests;

import org.znerd.math.*;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Combination of all tests.
 *
 * @version $Revision: 1.2 $ $Date: 2002/08/12 21:06:48 $
 * @author Ernst de Haan (<a href="mailto:znerd@FreeBSD.org">znerd@FreeBSD.org</a>)
 */
public class AllTests extends TestSuite {

   //-------------------------------------------------------------------------
   // Class functions
   //-------------------------------------------------------------------------

   /**
    * Returns a test suite with all test cases.
    *
    * @return
    *    the test suite, never <code>null</code>.
    */
   public static Test suite() {
      TestSuite suite = new TestSuite();
      suite.addTestSuite(NumberCentralTests.class);
      return suite;
   }


   //-------------------------------------------------------------------------
   // Constructor
   //-------------------------------------------------------------------------

   /**
    * Constructs a new <code>AllTests</code> object with the specified name.
    * The name will be passed to the superconstructor.
    *
    * @param name
    *    the name for this test case.
    */
   public AllTests(String name) {
      super(name);
   }
}
