package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.OnlineStatistic;
import com.elstele.bill.domain.Service;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceInternetAttributeForm;

import java.util.List;

public interface ServiceDataService {

    public String saveService(ServiceForm form, String changerName);
    public void deleteService(Integer id, String changerName);
    public ServiceForm getServiceFormById(Integer serviceId);
    public Integer getCurrentIpAddress (ServiceForm serviceForm);
    public Integer getCurrentIpAddressByServiceFormId(Integer serviceFormId);
    public List<Integer> addCurrentDevicePortToList(List<Integer> deviceFreePortList, Integer serviceId, Integer deviceId);
    public void changeSoftBlockStatus(Integer serviceId, String changedBy);
    public List<OnlineStatistic> getUsersOnline();
    public List<Integer> listActiveServicesIds();
}
