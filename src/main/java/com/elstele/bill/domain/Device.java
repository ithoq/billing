package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;


@Entity
@Table(name="Devices")
public class Device extends CommonDomainBean {
    private String name;

    public DeviceTypes getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(DeviceTypes deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="deviceType_id")
    private DeviceTypes deviceTypes;


    private String description;
    private String community;
    private String ip;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
