package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.AccountService;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceDAOImpl extends CommonDAOImpl<AccountService> implements AccountServiceDAO {


    public List listAccountServices(){
        String hql = "from AccountService service where service.status <> 'DELETED' or service.status is null ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }
}
