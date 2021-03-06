package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.IpAssembler;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Enums.SubnetPurpose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IpDataServiceImpl implements IpDataService {

    @Autowired
    IpDAO ipDAO;

    @Override
    @Transactional
    public List<IpForm> getIpAddressList() {
        List<IpForm> result = new ArrayList<IpForm>();
        IpAssembler assembler = new IpAssembler();

        List<Ip> beans = ipDAO.getIpAddressList();
        for (Ip curBean : beans) {
            IpForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    @Override
    @Transactional
    public HashMap<Integer, String> getIpAddressAsMap() {
        List<IpForm> ipForms = getIpAddressList();
        HashMap<Integer, String> ipMap = new HashMap<>();
        for (IpForm ipForm : ipForms) {
            if (ipForm.getIpStatus() != IpStatus.USED && ipForm.getIpSubnet().getSubnetPurpose() == SubnetPurpose.MGMT)
                ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
        return ipMap;
    }

    public void update(IpForm ipForm) {
        IpAssembler assembler = new IpAssembler();
        Ip bean = assembler.fromFormToBean(ipForm);
        ipDAO.update(bean);
    }

    public IpForm getById(Integer id) {
        IpAssembler assembler = new IpAssembler();
        Ip bean = ipDAO.getById(id);
        IpForm result = assembler.fromBeanToForm(bean);
        return result;
    }

    @Override
    @Transactional
    public void setStatus(Integer id, IpStatus ipStatus){
        if(id != null) {
            ipDAO.setStatusById(ipStatus, id);
        }
    }

    @Override
    @Transactional
    public List<IpForm> getBySubnetId(Integer id){
        List<IpForm> result = new ArrayList<IpForm>();
        IpAssembler assembler = new IpAssembler();

        List<Ip> beans = ipDAO.getIpAddressListBySubnetId(id);
        for (Ip curBean : beans) {
            IpForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }

    @Override
    @Transactional
    public HashMap<Integer, String> getIpMapBySubnets(String subnetId) {
        Integer id = Integer.parseInt(subnetId);
        List<IpForm> list = getBySubnetId(id);
        HashMap<Integer, String> ipMap = new HashMap<>();
        for (IpForm ipForm : list) {
            if (ipForm.getIpStatus() != IpStatus.USED)
                ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
        return ipMap;
    }


}
