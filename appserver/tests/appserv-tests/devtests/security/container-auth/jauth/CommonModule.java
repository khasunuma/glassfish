/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

import java.util.*;

import com.sun.enterprise.security.jauth.*;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

public class CommonModule implements ClientAuthModule, ServerAuthModule {

    protected AuthPolicy requestPolicy;
    protected AuthPolicy responsePolicy;
    protected CallbackHandler handler;
    protected Map options;

    protected TestCredential cred;

    protected CommonModule() { }

    public void initialize(AuthPolicy requestPolicy,
			AuthPolicy responsePolicy,
			CallbackHandler handler,
			Map options) {
	this.requestPolicy = requestPolicy;
	this.responsePolicy = responsePolicy;
	this.handler = handler;
	this.options = options;
    }

    public void secureRequest(AuthParam param,
				Subject subject,
				Map sharedState)
		throws AuthException {
	if (cred == null) {
	    cred = new TestCredential(this.getClass().getName(),
				options,
				requestPolicy,
				responsePolicy);
	}
	subject.getPublicCredentials().add(cred);
    }

    public void validateResponse(AuthParam param,
				Subject subject,
				Map sharedState)
		throws AuthException {
	if (cred == null) {
	    cred = new TestCredential(this.getClass().getName(),
				options,
				requestPolicy,
				responsePolicy);
	}
	subject.getPublicCredentials().add(cred);
    }

    public void validateRequest(AuthParam param,
				Subject subject,
				Map sharedState)
		throws AuthException {
	if (cred == null) {
	    cred = new TestCredential(this.getClass().getName(),
				options,
				requestPolicy,
				responsePolicy);
	}
	subject.getPublicCredentials().add(cred);
    }

    public void secureResponse(AuthParam param,
				Subject subject,
				Map sharedState)
		throws AuthException {
	if (cred == null) {
	    cred = new TestCredential(this.getClass().getName(),
				options,
				requestPolicy,
				responsePolicy);
	}
	subject.getPublicCredentials().add(cred);
    }

    public void disposeSubject(Subject subject,
				Map sharedState)
		throws AuthException {
	if (cred != null) {
	    subject.getPublicCredentials().remove(cred);
	}
    }
}

