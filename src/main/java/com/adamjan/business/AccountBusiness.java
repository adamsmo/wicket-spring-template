package com.adamjan.business;

import com.adamjan.dto.AccountDto;
import com.adamjan.model.AccountModel;
import com.gs.collections.api.block.function.Function;
import com.gs.collections.impl.list.mutable.FastList;
import org.dozer.Mapper;
import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
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

    @Autowired
    private Mapper mapper;

    public List<AccountDto> getAllAccounts() {
        Criteria criteria = getSession().createCriteria(AccountModel.class);
        FastList<AccountModel> list = FastList.<AccountModel>newList(criteria.list());

        Function<AccountModel, AccountDto> function = new Function<AccountModel, AccountDto>() {

            @Override
            public AccountDto valueOf(AccountModel object) {
                return mapper.map(object, AccountDto.class);
            }
        };

        Collection<AccountDto> collection = list.collect(function);
        return new ArrayList<>(collection);
    }
}
