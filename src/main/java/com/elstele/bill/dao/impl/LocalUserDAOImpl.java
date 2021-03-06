package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.LocalUserDAO;
import com.elstele.bill.domain.LocalUser;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalUserDAOImpl extends CommonDAOImpl<LocalUser> implements LocalUserDAO {

    @Override
    public LocalUser getLocalUserByNameAndPass(String name, String pass) {
        String hql = "from LocalUser where username =:name and password =:pass";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql)
                .setParameter("name", name)
                .setParameter("pass", pass);
        if (!query.list().isEmpty()){
            return (LocalUser)query.list().get(0);
        }
        return null;
    }

    @Override
    public List listLocalUser(){
        String hql = "from LocalUser user where user.status <> 'DELETED' or user.status is null ";
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query query = session.createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }

    @Override
    public LocalUser getByName(String name) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from LocalUser user where user.username =:name ");
        query.setParameter("name", name);
        return (LocalUser)query.uniqueResult();
    }

}
