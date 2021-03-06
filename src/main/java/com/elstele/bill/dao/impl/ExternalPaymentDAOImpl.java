package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.ExternalPaymentDAO;
import com.elstele.bill.domain.ExternalPaymentTransaction;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExternalPaymentDAOImpl extends CommonDAOImpl<ExternalPaymentTransaction> implements ExternalPaymentDAO {

    public List<ExternalPaymentTransaction> getExtPaymentList() {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query q = session.
                createQuery("select tr from ExternalPaymentTransaction tr " +
                        "order by tr.timestamp DESC");

        return (List<ExternalPaymentTransaction>)q.list();
    }

    public List<ExternalPaymentTransaction> getLastNOfExtPaymentList(Integer num) {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query q = session.
                createQuery("select tr from ExternalPaymentTransaction tr " +
                        "order by tr.timestamp DESC")
                .setMaxResults(num);

        return (List<ExternalPaymentTransaction>)q.list();
    }
}
