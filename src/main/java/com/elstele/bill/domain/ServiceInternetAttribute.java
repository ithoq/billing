package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;
import static com.elstele.bill.utils.Constants.SERVICE_INTERNET;

@Entity
@Table(name="pacattributes")
public class ServiceInternetAttribute  extends CommonDomainBean {

    @Column(name = "attribute")
    private String attribute;
    @Column(name = "op")
    private String operation;
    @Column(name = "value")
    private String value;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "packetid")
    private ServiceType serviceType;

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}