package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.utils.Constants;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeDAOImpl extends CommonDAOImpl<ServiceType> implements ServiceTypeDAO {

    @Override
    public List listServiceType(){
        String hql = "from ServiceType service where service.status <> 'DELETED' or service.status is null ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }

    public List<ServiceType> listServiceTypeByBussType(Constants.AccountType bussTyp) {
        String hql = "from ServiceType service where bussType = :busstype AND " +
                "(service.status <> 'DELETED' or service.status is null)";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("busstype", bussTyp);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }
}
