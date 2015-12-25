package com.elstele.bill.datasrv.interfaces;


import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Street;
import com.elstele.bill.form.AccountForm;

import java.util.List;
import java.util.Set;

public interface AccountDataService {
    public List<AccountForm> getAccountsList();
    public List<Account> getAccountBeansList();
    public List<AccountForm> getAccountsList(int rows, int page);
    public List<AccountForm> getAccountsLiteFormList(int rows, int page);
    public void saveAccount(AccountForm form);
    public void updateAccount(AccountForm form);
    public AccountForm getAccountById(int id);
    public Account getAccountBeanById(int id);
    public void softDeleteAccount(int id);
    public int getActiveAccountsCount();
    public Set<AccountForm> searchAccounts(String value);
}
