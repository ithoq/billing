package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.TransactionAssembler;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.TransactionDAO;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.to.TransactionTO;
import com.elstele.bill.utils.Constants;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionDataServiceImpl implements TransactionDataService {
    @Autowired
    private TransactionDAO transactionDAO;
    @Autowired
    private AccountDAO accountDAO;

    @Override
    @Transactional
    public List<TransactionForm> getTransactionList(Integer accountId){
        List<Transaction> beans = transactionDAO.getTransactionList(accountId);
        return makeTransactionFormList(beans);
    }

    @Override
    @Transactional
    public List<TransactionForm> getTransactionList(Integer accountId, Integer displayLimit){
        List<Transaction> beans = transactionDAO.getTransactionList(accountId, displayLimit);
        return makeTransactionFormList(beans);
    }

    @Transactional
    public TransactionForm getTransactionById(Integer transactionId) {
        Transaction tr = transactionDAO.getById(transactionId);
        TransactionAssembler assembler = new TransactionAssembler();
        return assembler.fromBeanToForm(tr);
    }
    @Override
    @Transactional
    public List<TransactionForm> searchTransactionList(String account, Date dateStart, Date dateEnd){
        List<Transaction> beans = transactionDAO.searchTransactionList(account, dateStart, dateEnd);
        return makeTransactionFormList(beans);
    }

    @Transactional
    public String saveTransaction(TransactionForm transactionForm) {
        TransactionAssembler assembler = new TransactionAssembler();
        Transaction transaction = assembler.fromFormToBean(transactionForm);
        transactionDAO.create(transaction);
        Account account = accountDAO.getAccountForUpgradeById(transaction.getAccount().getId());
        Float currentBalance = account.getCurrentBalance();
        Float newBalance = currentBalance; //by default we stay balance as is
        if (transaction.getDirection().equals(Constants.TransactionDirection.DEBET)){
            newBalance = currentBalance + transaction.getPrice();
        }
        if (transaction.getDirection().equals(Constants.TransactionDirection.CREDIT)){
            newBalance = currentBalance - transaction.getPrice();
        }
        account.setCurrentBalance(newBalance);
        accountDAO.save(account);

        return "Transaction was successfully created";
    }

    @Override
    public TransactionForm getTransactionForm(Integer accountId){
        TransactionForm transactionForm = new TransactionForm();
        if(accountId > 0) {
            AccountForm accountForm = new AccountForm();
            accountForm.setId(accountId);
            transactionForm.setAccount(accountForm);
        }
        return transactionForm;
    }

    private List<TransactionForm> makeTransactionFormList(List<Transaction> beans){
        List<TransactionForm> result = new ArrayList<TransactionForm>();
        TransactionAssembler assembler = new TransactionAssembler();
        for (Transaction curBean : beans){
            TransactionForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    //TODO need to be covered by test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<TransactionTO> getTransactionForAccount(Integer accountId) {
        List<TransactionTO> result = new ArrayList<>();
        TransactionAssembler assembler = new TransactionAssembler();
        List<Transaction> beans =  transactionDAO.getTransactionList(accountId);
        for (Transaction curBean : beans){
            TransactionTO curTO = assembler.fromBeanToTO(curBean);
            result.add(curTO);
        }
        return result;
    }


    //TODO need to be covered by test
    @Transactional
    public Float getBalanceOnDateForAccount(Integer accountId, Date date){
        return transactionDAO.getBalanceOnDateForAccount(accountId, date);
    }

    @Transactional
    public Float calcSumOfDebetAccountTransactionForPeriod(Integer accountId, Date from, Date to) {
        Float sum = 0f;
        List<Transaction> tempList = transactionDAO.getDebetTransactionsByAccountForPeriod(accountId, from, to);
        for (Transaction curTransaction : tempList){
            sum = sum + curTransaction.getPrice();
        }
        return sum;
    }

    @Transactional
     public Float calcSumOfKreditAccountTransactionForPeriod(Integer accountId, Date from, Date to){
        Float sum = 0f;
        List<Transaction> tempList = transactionDAO.getKreditTransactionsByAccountForPeriod(accountId, from, to);
        for (Transaction curTransaction : tempList){
            sum = sum + curTransaction.getPrice();
        }
        return sum;
    }

    @Transactional
    public List<Transaction> getKreditAccountTransactionForPeriod(Integer accountId, Date from, Date to){
        List<Transaction> tempList = transactionDAO.getKreditTransactionsByAccountForPeriod(accountId, from, to);
        return tempList;
    }

}
