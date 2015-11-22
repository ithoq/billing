package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.IpStatus;

import java.util.List;

public interface IpDataService {
    public List<IpForm> getIpAddressList();
    public void update(IpForm ipForm);
    public IpForm getById(Integer id);
    public void setStatus(Integer id, IpStatus ipStatus);
    public List<IpForm> getBySubnetId(Integer id);
    }