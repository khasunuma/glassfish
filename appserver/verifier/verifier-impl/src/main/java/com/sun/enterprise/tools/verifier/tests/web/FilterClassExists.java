/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.enterprise.tools.verifier.tests.web;

import com.sun.enterprise.tools.verifier.Result;
import com.sun.enterprise.tools.verifier.tests.*;

/** 
 * Filter class exists tests.
 * Verify that the Filter class exists inside the .war file and is loadable.
 * 
 * @author Jerome Dochez
 * @version 1.0
 */
public class FilterClassExists extends FilterClass implements WebCheck {

    /**
     * <p>
     * Run the verifier test against a declared individual filter class
     * </p>
     *
     * @param result is used to put the test results in
     * @param filterClass is the individual filter class object to test
     * @return true if the test pass
     */    

    protected boolean runIndividualFilterTest(Result result, Class filterClass) {

        if (filterClass != null) {
	  
	    result.addGoodDetails(smh.getLocalString
		(getClass().getName() + ".passed",
		 "Filter class [ {0} ] resides in the WEB-INF/classes or WEB-INF/lib directory.",
		  new Object[] {filterClass.getName()}));    
            return true;
        } else
            return false;
    }    

}
