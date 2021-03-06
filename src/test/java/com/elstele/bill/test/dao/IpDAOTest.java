package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.IpDAOImpl;
import com.elstele.bill.dao.impl.IpSubnetDAOImpl;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.utils.Enums.IpStatus;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IpDAOTest {

    @Autowired
    IpDAOImpl ipDAO;
    @Autowired
    IpSubnetDAOImpl ipSubnetDAO;

    private Ip ip1;
    private int subnetId;
    private Ip ip;

    @Before
    public void setUp(){
        ip = new Ip();
        ip.setIpStatus(IpStatus.FREE);
        IpSubnet subnet = new IpSubnet();
        subnetId = ipSubnetDAO.create(subnet);
        ip.setIpSubnet(subnet);
        ipDAO.save(ip);

        ip1 = new Ip();
        ip1.setIpStatus(IpStatus.USED);
        ip1.setIpSubnet(subnet);
        ipDAO.save(ip1);
    }

    @After
    public void tearDown(){
        ip =null;
        ip1 = null;
    }

    @Test
    public void getIpAddressListTest(){
        List<Ip> actual = ipDAO.getIpAddressList();
        assertTrue(actual.contains(ip));
        assertTrue(actual.contains(ip1));
    }

    @Test
    public void getIpAddressListBySubnetIdTest(){
        List<Ip> actual = ipDAO.getIpAddressListBySubnetId(subnetId);
        assertTrue(actual.contains(ip));
    }
}
