package com.elstele.bill.datasrv;


import com.elstele.bill.form.AccountForm;

import java.util.List;

public interface AccountDataService {
    public List<AccountForm> getAccountsList();
    public void saveAccount(AccountForm form);
    public void updateAccount(AccountForm form);
    public AccountForm getAccountById(int id);
}