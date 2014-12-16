package com.adamjan.business;

import com.adamjan.dto.AccountDto;
import com.adamjan.model.AccountModel;
import com.adamjan.model.QAccountModel;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.impl.list.mutable.FastList;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
@Component
@Transactional(isolation = Isolation.SERIALIZABLE)
public class AccountBusiness extends AbstractBusiness {

    public List<AccountDto> getAllAccounts() {
        List<AccountModel> list = getHibernateQuery()
                .from(QAccountModel.accountModel)
                .list(QAccountModel.accountModel);

        return FastList.newList(list).collect(new Function<AccountModel, AccountDto>() {
            @Override
            public AccountDto valueOf(AccountModel object) {
                AccountDto dto = new AccountDto();
                dto.setId(object.getId());
                dto.setName(object.getName());
                return dto;
            }
        });
    }

    public void addAccount(String accName) {
        Integer gratestId = getHibernateQuery()
                .from(QAccountModel.accountModel)
                .orderBy(QAccountModel.accountModel.id.desc())
                .limit(1)
                .uniqueResult(QAccountModel.accountModel.id);

        if (gratestId == null) {
            gratestId = 0;
        }

        AccountModel newModel = new AccountModel();
        newModel.setId(gratestId + 1);
        newModel.setName(accName);
    }
}
