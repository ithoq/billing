package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Ip;

import java.util.List;

public interface IpDAO extends CommonDAO<Ip> {
    public List<Ip> getIpAddressList();
    public void setStatusById(Integer id);
    public List<Ip> getIpAddressListBySubnetId(Integer id);

}
