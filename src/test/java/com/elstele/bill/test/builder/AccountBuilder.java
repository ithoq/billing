package com.elstele.bill.test.builder;

import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Address;
import com.elstele.bill.utils.Constants.Constants;
import com.elstele.bill.utils.Enums.Status;

/**
 * Created by ivan on 15/11/30.
 */
public class AccountBuilder implements TestObjectCreator<AccountBuilder, Account>{
    private Account account;

    public AccountBuilder build() {
        account = new Account();
        account.setStatus(Status.ACTIVE);
        return this;
    }

    public Account getRes() {
        if (account == null){
            build();
        }
        return account;
    }

    public AccountBuilder withAccName(String name){
        account.setAccountName(name);
        return this;
    }

    public AccountBuilder withAccType(Constants.AccountType type){
        account.setAccountType(type);
        return this;
    }

    public AccountBuilder withBalance(Float balance){
        account.setCurrentBalance(balance);
        return this;
    }

    public AccountBuilder withFIO(String fio){
        account.setFio(fio);
        return this;
    }

    public AccountBuilder withRandomPhyAddress(){
        AddressBuilder ab = new AddressBuilder();
        Address addr = ab.createRandomAddress();
        account.setPhyAddress(addr);
        return this;
    }

    public AccountBuilder withRandomLegalAddress(){
        AddressBuilder ab = new AddressBuilder();
        Address addr = ab.createRandomAddress();
        account.setLegalAddress(addr);
        return this;
    }

    //TODO: continue with services

}
