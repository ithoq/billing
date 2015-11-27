package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Street;
import com.elstele.bill.utils.Status;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDAOImpl extends CommonDAOImpl<Account> implements AccountDAO {
    @Override
    public List<Account> getAccountList(int limit, int offset) {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query q = session.
                createQuery("select a from Account a where status <> 'DELETED'" +
                        "order by a.accountName");
        q.setFirstResult(offset).setMaxResults(limit);

        return (List<Account>)q.list();
    }

    @Override
    public List<Account> getAccountList() {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        return (List<Account>)session.
                createCriteria(Account.class).add(Restrictions.ne("status", Status.DELETED))
                .list();
    }

    @Override
    public Integer getActiveAccountsCount() {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Account a where status <> 'DELETED'");
        Long res = (Long)q.uniqueResult();
        return res.intValue();
    }

    @Override
    public List<Street> getListOfStreets(String query) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("from Street");//s where s.name like :target");
        //q.setString("target", query);
        return q.list();
    }
}
