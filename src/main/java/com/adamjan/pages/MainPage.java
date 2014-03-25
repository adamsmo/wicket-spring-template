package com.adamjan.pages;

import com.adamjan.business.AccountBusiness;
import com.adamjan.dto.AccountDto;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

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
public class MainPage extends TemplatePage {

    private String accName;
    @SpringBean
    private AccountBusiness accountBusiness;

    public MainPage() {
        add(new Label("msg", "Hello"));

        LoadableDetachableModel<List<AccountDto>> model = new LoadableDetachableModel<List<AccountDto>>() {
            @Override
            protected List<AccountDto> load() {
                return accountBusiness.getAllAccounts();
            }
        };

        ListView listView = new ListView<AccountDto>("repeater", model) {
            @Override
            protected void populateItem(ListItem item) {
                AccountDto dto = getModelObject().get(item.getIndex());
                item.add(new Label("label", dto.getName()));
            }
        };
        add(listView);

        Form form = new Form<String>("form") {
            @Override
            protected void onSubmit() {
                accountBusiness.addAccount(accName);
                accName = null;
            }
        };

        add(form);

        form.add(new TextField<>("acc_name", new PropertyModel<>(this, "accName")));
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public AccountBusiness getAccountBusiness() {
        return accountBusiness;
    }

    public void setAccountBusiness(AccountBusiness accountBusiness) {
        this.accountBusiness = accountBusiness;
    }
}
