package com.elstele.bill.assembler;

import com.elstele.bill.dao.interfaces.DeviceTypesDAO;
import com.elstele.bill.dao.interfaces.IpDAO;
import com.elstele.bill.domain.Address;
import com.elstele.bill.domain.Device;
import com.elstele.bill.form.AddressForm;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.Enums.Status;

import static org.springframework.beans.BeanUtils.copyProperties;

public class DeviceAssembler {

    public DeviceAssembler(DeviceTypesDAO dao, IpDAO ipDao) {
        setDeviceTypesDAO(dao);
        setIpDAO(ipDao);
    }

    private DeviceTypesDAO deviceTypesDAO;
    private IpDAO ipDAO;

    public DeviceForm fromBeanToForm(Device bean) {
        DeviceTypesForm deviceTypesForm = new DeviceTypesForm();
        IpForm ipForm = new IpForm();
        AddressForm addressForm = new AddressForm();
        if (bean.getDeviceType() != null) {
            DeviceTypesAssembler deviceTypesAssembler = new DeviceTypesAssembler();
            deviceTypesForm = deviceTypesAssembler.fromBeanToForm(bean.getDeviceType());
        }
        if (bean.getIpAdd() != null) {
            IpAssembler ipAssembler = new IpAssembler();
            ipForm = ipAssembler.fromBeanToForm(bean.getIpAdd());
        }
        if (bean.getDeviceAddress() != null) {
            AddressAssembler addressAssembler = new AddressAssembler();
            addressForm = addressAssembler.addressAssembleFromBeanToForm(bean.getDeviceAddress(), null);
        }
        DeviceForm form = new DeviceForm();
        form.setDevType(deviceTypesForm);
        form.setIpForm(ipForm);
        form.setDeviceAddressForm(addressForm);
        copyProperties(bean, form);
        return form;
    }


    public Device fromFormToBean(DeviceForm form) {
        Device bean = new Device();
        if (form.getDevType() != null) {
            bean.setDeviceType(deviceTypesDAO.getById(form.getDevType().getId()));
        }
        if (form.getIpForm() != null && form.getIpForm().getId() != null) {
            bean.setIpAdd(ipDAO.getById(form.getIpForm().getId()));
        }
        if(form.getDeviceAddressForm() != null){
            AddressAssembler addressAssembler = new AddressAssembler();
            Address address = addressAssembler.addressAssembleFromFormToBean(form.getDeviceAddressForm(), null);
            bean.setDeviceAddress(address);
        }
        copyProperties(form, bean);
        return bean;
    }

    public DeviceTypesDAO getDeviceTypesDAO() {
        return deviceTypesDAO;
    }

    public void setDeviceTypesDAO(DeviceTypesDAO deviceTypesDAO) {
        this.deviceTypesDAO = deviceTypesDAO;
    }

    public IpDAO getIpDAO() {
        return ipDAO;
    }

    public void setIpDAO(IpDAO ipDAO) {
        this.ipDAO = ipDAO;
    }
}
