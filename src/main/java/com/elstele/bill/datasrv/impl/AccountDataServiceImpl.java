package com.elstele.bill.datasrv.impl;


import com.elstele.bill.assembler.AccountAssembler;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.dao.interfaces.StreetDAO;
import com.elstele.bill.datasrv.interfaces.AccountDataService;
import com.elstele.bill.datasrv.interfaces.StreetDataService;
import com.elstele.bill.domain.*;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Enums.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@org.springframework.stereotype.Service
public class AccountDataServiceImpl implements AccountDataService {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private ServiceDAO serviceDAO;
    @Autowired
    private StreetDAO streetDAO;
    @Autowired
    StreetDataService streetDataService;

    final static Logger LOGGER = LogManager.getLogger(AccountDataServiceImpl.class);

    @Override
    @Transactional
    public List<AccountForm> getAccountsList() {
        List<AccountForm> result = new ArrayList<AccountForm>();
        AccountAssembler assembler = new AccountAssembler();

        List<Account> beans = accountDAO.getAccountList();
        if (beans != null) {
            for (Account curBean : beans) {
                AccountForm curForm = assembler.fromBeanToForm(curBean);
                result.add(curForm);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public List<Account> getAccountBeansList() {
        return accountDAO.getAccountList();
    }


    @Override
    @Transactional
    public List<AccountForm> getAccountsList(int rows, int page) {
        List<AccountForm> result = new ArrayList<AccountForm>();
        AccountAssembler assembler = new AccountAssembler();
        page = page - 1; //this is correction for User Interfase (for user page starts from 1, but we use 0 as first number)
        int offset = page * rows;
        List<Account> beans = accountDAO.getAccountList(rows, offset);
        for (Account curBean : beans) {
            AccountForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    @Transactional
    public List<AccountForm> getAccountsLiteFormList(int rows, int page) {
        List<AccountForm> result = new ArrayList<AccountForm>();
        AccountAssembler assembler = new AccountAssembler();
        page = page - 1; //this is correction for User Interfase (for user page starts from 1, but we use 0 as first number)
        int offset = page * rows;
        List<Account> beans = accountDAO.getAccountList(rows, offset);
        for (Account curBean : beans) {
            AccountForm curForm = assembler.fromBeanToShortForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    @Override
    @Transactional
    //TODO rename to create
    public void saveAccount(AccountForm form, String changerName) {
        AccountAssembler assembler = new AccountAssembler();
        if (form.getId() == null) {
            form.setCurrentBalance(0F);
            form.setStatus(Status.ACTIVE);
        }
        Account account = assembler.fromShortFormToBean(form);
        int creatingId = accountDAO.create(account, changerName);
    }

    @Override
    @Transactional
    public void updateAccount(AccountForm form, String changerName) {
        AccountAssembler assembler = new AccountAssembler();
        Account account;
        if (isFormAddressEmpty(form)) {
            //Editing on account list page
            account = accountDAO.getById(form.getId());
            account.setAccountName(form.getAccountName());
            account.setAccountType(form.getAccountType());
        } else {
            //Editing on Account full page with more concert information
            gettingCorrectIDForCurrentFormAndStreet(form);
            account = assembler.fromFormToBean(form);
            updateStreetListAfterInsert(form);
        }
        accountDAO.update(account, changerName);
    }

    private boolean isFormAddressEmpty(AccountForm form) {
        AddressForm phyAddress = form.getPhyAddress();
        AddressForm legalAddress = form.getLegalAddress();
        return (phyAddress == null || phyAddress.getStreet() == null && phyAddress.getStreetId() == null && phyAddress.getId() == null
                && phyAddress.getBuilding() == null && phyAddress.getFlat() == null) &&
                (legalAddress == null || legalAddress.getStreet() == null && legalAddress.getStreetId() == null && legalAddress.getId() == null
                        && legalAddress.getBuilding() == null && legalAddress.getFlat() == null);
    }

    public void gettingCorrectIDForCurrentFormAndStreet(AccountForm form) {
        if (form.getLegalAddress().getStreetId() == null || form.getPhyAddress().getStreetId() == null) {
            String streetNamePhyAddress = form.getPhyAddress().getStreet();
            String streetNameLegalAddress = form.getLegalAddress().getStreet();
            if (!streetNamePhyAddress.equals("")) {
                Integer streetIdFromDB = streetDAO.getStreetIDByStreetName(streetNamePhyAddress);
                if (streetIdFromDB != null) {
                    form.getPhyAddress().setStreetId(streetIdFromDB);
                }
            }
            if (!streetNameLegalAddress.equals("")) {
                Integer streetIdFromDB = streetDAO.getStreetIDByStreetName(streetNameLegalAddress);
                if (streetIdFromDB != null) {
                    form.getLegalAddress().setStreetId(streetIdFromDB);
                }
            }
        }
    }

    public void updateStreetListAfterInsert(AccountForm form) {
        Integer phyId = form.getPhyAddress().getStreetId();
        String phyStreet = form.getPhyAddress().getStreet();

        Integer legalId = form.getLegalAddress().getStreetId();
        String legalStreet = form.getLegalAddress().getStreet();

        if ((phyId == null && !phyStreet.isEmpty()) || (legalId == null && !legalStreet.isEmpty())) {
            streetDataService.clearStreetsList();
        }
    }

    @Override
    @Transactional
    public AccountForm getAccountById(int id) {
        AccountAssembler assembler = new AccountAssembler();
        AccountForm result = null;
        Account bean = accountDAO.getById(id);
        if (bean != null) {
            AccountForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }

    @Override
    @Transactional
    public AccountForm getAccountWithAllServicesById(int id){
        AccountAssembler assembler = new AccountAssembler();
        AccountForm result = null;
        Account bean = accountDAO.getAllById(id);
        if (bean != null) {
            AccountForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }

    @Override
    @Transactional
    public Account getAccountBeanById(int id) {
        Account result = null;
        Account bean = accountDAO.getById(id);
        if (bean != null) {
            result = bean;
        }
        return result;
    }

    @Override
    @Transactional
    public void softDeleteAccount(int id, String changerName) {
        Account account = accountDAO.getById(id);
        account.setStatus(Status.DELETED);
        Set<Service> serviceSet = account.getAccountServices();
        for (Service service : serviceSet) {
            service.setStatus(Status.DELETED);
            if(service instanceof ServiceInternet){
                ((ServiceInternet) service).getIpAddress().setIpStatus(IpStatus.FREE);
            }
        }
        account.setAccountServices(serviceSet);
        accountDAO.update(account, changerName);
    }

    @Override
    @Transactional
    public int getActiveAccountsCount() {
        return accountDAO.getActiveAccountsCount();
    }

    @Override
    @Transactional
    public Set<AccountForm> searchAccounts(String value) {
        try {
            List<Service> serviceListByLogin = serviceDAO.getServiceByLogin(value);
            List<Service> serviceListByPhoNumber = serviceDAO.getServiceByPhone(value);
            List<Account> accountListByFIOAndName = accountDAO.getAccountByFIOAndName(value);
            Set<AccountForm> result = new HashSet<>();

            addFormWithLoginToList(result, serviceListByLogin);
            addFormWithPhoneNumberToList(result, serviceListByPhoNumber);
            addFormToListWithFIO(result, accountListByFIOAndName);

            LOGGER.info("Getting from DB by Search Value: " + value + " successfully finished. Method searchAccounts");

            return result;
        } catch (HibernateException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptySet();
        }
    }

    public void addFormWithLoginToList(Set<AccountForm> result, List<Service> serviceListByLogin) {
        AccountAssembler accountAssembler = new AccountAssembler();
        for (Service service : serviceListByLogin) {
            AccountForm form = accountAssembler.fromBeanToForm(service.getAccount());
            if (service instanceof ServiceInternet) {
                String login = ((ServiceInternet) service).getUsername();
                form.setSearchCompares("Login: " + login);
            }
            result.add(form);
        }
    }

    public void addFormWithPhoneNumberToList(Set<AccountForm> result, List<Service> serviceListByPhoNumber) {
        AccountAssembler accountAssembler = new AccountAssembler();
        for (Service service : serviceListByPhoNumber) {
            AccountForm form = accountAssembler.fromBeanToForm(service.getAccount());
            if (service instanceof ServicePhone) {
                String phoneNumber = ((ServicePhone) service).getPhoneNumber();
                form.setSearchCompares("Phone Number: " + phoneNumber);
            }
            result.add(form);
        }
    }

    public void addFormToListWithFIO(Set<AccountForm> result, List<Account> accountListByFIOAndName) {
        AccountAssembler accountAssembler = new AccountAssembler();
        for (Account account : accountListByFIOAndName) {
            result.add(accountAssembler.fromBeanToForm(account));
        }
    }

}
