package com.adamjan;

import com.adamjan.pages.LoginPage;
import com.adamjan.pages.MainPage;
import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.stereotype.Component;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

/**
 * The MIT License
 * <p/>
 * Copyright (c) 2013 Adam Smolarek
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
@Component
public class RootApplication extends AuthenticatedWebApplication {

    @Override
    protected void init() {
        super.init();

        getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
        getRequestCycleSettings().setResponseRequestEncoding("UTF-8");

        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        //automatically mount all found pages
        new AnnotatedMountScanner().scanPackage("com.adamjan").mount(this);

        setRootRequestMapper(new CryptoMapper(getRootRequestMapper(), this));
        getSecuritySettings().setAuthorizationStrategy(new AnnotationsRoleAuthorizationStrategy(this));

        //mount login page at /login
        mountPage("/login", LoginPage.class);
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return AuthorizedSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return MainPage.class;
    }
}
