/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.devtest.admin.synchronization.api.security.client;

import javax.naming.*;
import jakarta.jms.*;
import jakarta.ejb.*;
import javax.rmi.PortableRemoteObject;
import java.util.Properties;

import com.sun.devtest.admin.synchronization.api.security.shopping.*;

//import com.sun.ejte.ccl.reporter.SimpleReporterAdapter;

public class RpaClient {

    // consts
    public static String kTestNotRun    = "TEST NOT RUN";
    public static String kTestPassed    = "TEST PASSED";
    public static String kTestFailed    = "TEST FAILED";
    
    //private static SimpleReporterAdapter stat = 
     //   new SimpleReporterAdapter("appserv-tests");


    private RpaRemote hr = null;

    public static void main (String[] args) {

       //stat.addDescription("synchronization");
       if ( args.length != 3) {
            //stat.addStatus("client initSFSB-- wrong number of arguments", stat.FAIL);
            System.out.println("Usage: SyncClientTest provider-url ctxFactory jndi-name");
             return;
        } 

        RpaClient client = new RpaClient(args);
        System.out.println("[apiClient] doTest()...");
        client.doTest(args);
        System.out.println("[apiClient] DONE doTest()...");
        //stat.printSummary("synchronization");
    }  
    
    public RpaClient (String[] args) {
    }
    
    public void doTest(String[] args) {

        initSFSB(args);
        testSynch(args);
    }

    private void initSFSB(String[] args) {

        System.out.println("[apiClient] Inside init....");
        try {
            Context ic = null;
            Object objref = null;
            if ((args[0] == null) || (args[1] == null)) { 
                ic = new InitialContext();
                System.out.println("[apiClient] Lookingup Bean apiClient ");
                objref = ic.lookup("java:comp/env/ejb/apiSecurity");
            } else {
                Properties env = new Properties();
                env.put("java.naming.provider.url", args[0]);
                env.put("java.naming.factory.initial", args[1]);
                ic = new InitialContext(env);
                objref = ic.lookup(args[2]);
            }

            RpaHome home = (RpaHome)
                PortableRemoteObject.narrow (objref, RpaHome.class);

            hr = home.create("LizHurley");

            System.out.println("[passivateactivate] Initalization done");
            //stat.addStatus("init apiClient", stat.PASS);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("[apiClient] Exception in init....");
            e.printStackTrace();
            //stat.addStatus("client initSFSB", stat.FAIL);
        }
    }

    public void testSynch(String[] args) {
    		// invoke 3 overloaded methods on the EJB
        try{
		System.out.println ("Calling authorized method - addItem");
		hr.addItem("lipstick", 30);
		hr.addItem("mascara", 40);
                hr.addItem("lipstick2", 50);
                hr.addItem("sandals",  200);
                System.out.println(hr.getTotalCost());
                hr.deleteItem("lipstick2");
                java.lang.String[] shoppingList = hr.getItems();
                System.out.println("Shopping list for LizHurley");
                for (int i=0; i<shoppingList.length; i++){
                    System.out.println(shoppingList[i]);
                }

                System.out.println("Total Cost for Ms Hurley = "+
                    hr.getTotalCost());

        } catch(Exception re){
                re.printStackTrace();
		System.out.println("RealmPerApp:RpaLoginBean Test Failed");
                System.exit(-1);
	}
            System.out.println("RealmPerApp:RpaLoginBean Test Passed");

    }

} //Client{}
