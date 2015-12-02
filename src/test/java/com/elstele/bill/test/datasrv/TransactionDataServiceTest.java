package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.TransactionDAOImpl;
import com.elstele.bill.datasrv.TransactionDataServiceImpl;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.test.builder.ObjectBuilder;
import com.elstele.bill.utils.Constants.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionDataServiceTest {
    private List<Transaction> transactionListSample = new ArrayList<Transaction>();
    private ObjectBuilder objectBuilder = new ObjectBuilder();

    @Mock
    private TransactionDAOImpl transactionDAO;
    @InjectMocks
    private TransactionDataServiceImpl transactionDataService;

    @Before
    public void setUp() {
        Account account = objectBuilder.createAccount(1,"account1",33F, Constants.AccountType.PRIVATE);
        Transaction trans1 = objectBuilder.createTransaction(1,Constants.TransactionDirection.CREDIT,50F, Constants.TransactionSource.BANK, account);
        Transaction trans2 = objectBuilder.createTransaction(2,Constants.TransactionDirection.DEBIT,88F, Constants.TransactionSource.HANDMADE, account);
        transactionListSample.add(trans1);
        transactionListSample.add(trans2);
    }

    @After
    public void tearDown() {
        transactionDAO = null;
        transactionDataService = null;
    }

    @Test
    @Ignore
    public void getTransactionListTest(){
        when(transactionDAO.getTransactionList(1)).thenReturn(transactionListSample);

        AccountForm accountForm = objectBuilder.createAccountForm(1,"account1",33F, Constants.AccountType.PRIVATE);
        TransactionForm transForm1 = objectBuilder.createTransactionForm(1, Constants.TransactionDirection.CREDIT, 50F, Constants.TransactionSource.BANK, accountForm);
        TransactionForm transForm2 = objectBuilder.createTransactionForm(2, Constants.TransactionDirection.DEBIT, 88F, Constants.TransactionSource.HANDMADE, accountForm);

        Transaction trans1 = transactionListSample.get(0);
        Transaction trans2 = transactionListSample.get(1);
        transForm1.setDate(trans1.getDate());
        transForm2.setDate(trans2.getDate());

        List<TransactionForm> transactionFormList = transactionDataService.getTransactionList(1);
        assertTrue(transactionFormList.contains(transForm1));
        assertTrue(transactionFormList.contains(transForm2));

        TransactionForm transToCompare = transactionDataService.getTransactionForm(1);
        assertTrue(transToCompare.getAccount().getId().equals(accountForm.getId()));
    }
}
