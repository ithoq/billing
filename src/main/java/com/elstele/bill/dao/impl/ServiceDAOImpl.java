package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;

import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.domain.OnlineStatistic;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.utils.Enums.IpStatus;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceDAOImpl extends CommonDAOImpl<Service> implements ServiceDAO {

    @Autowired
    private IpDataService ipDataService;

    @Override
    public List listServices(){
        String hql = "from Service service where service.status <> 'DELETED' or service.status is null ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }

    @Override
    public String saveService(Service service, boolean isNewObject) {
        //TODO message return here is not a good idea
        //need to be refactored
        String message = "Service was successfully ";
        if (isNewObject) {
            create(service);
            message += "added.";
        } else {
            update(service);
            message += "updated.";
        }
        return message;
    }

    @Override
    public void deleteService(Integer serviceId){
        setStatusDelete(serviceId);
        Service service = getById(serviceId);
        if (service instanceof ServiceInternet)
            ipDataService.setStatus(((ServiceInternet) service).getIpAddress().getId(), IpStatus.FREE);
    }

    public List<OnlineStatistic> getUserOnline() {

        Query query = getSessionFactory().getCurrentSession().createSQLQuery(
                "select o.username, a.user_fio, text(o.nasipaddress) as nasipaddress, " +
                        "o.nasportid, to_char(o.acctstarttime, 'YYYY-MM-DD HH24:MI:SS') as acctstarttime, o.acctsessiontime, " +
                        "text(o.framedipaddress) as framedipaddress, o.acctinputoctets, o.acctoutputoctets " +
                        "from pppoe_online as o, accounts as a, service as s " +
                        "where " +
                        "o.username = s.username and " +
                        "s.account_id = a.id " +
                        "order by o.username")
                .setResultTransformer(Transformers.aliasToBean(OnlineStatistic.class));
        List <OnlineStatistic> dbResult = query.list();
        return dbResult;
    }
}
