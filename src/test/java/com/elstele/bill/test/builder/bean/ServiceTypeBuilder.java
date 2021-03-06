package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;

import java.util.HashSet;
import java.util.Set;

public class ServiceTypeBuilder {
    private ServiceType serviceType;

    public ServiceTypeBuilder build() {
        serviceType = new ServiceType();
        serviceType.setStatus(Status.ACTIVE);
        return this;
    }

    public ServiceType getRes(){
        if (serviceType == null){
            build();
        }
        return serviceType;
    }

    public ServiceTypeBuilder withId(Integer id){
        serviceType.setId(id);
        return this;
    }

    public ServiceTypeBuilder withName(String name){
        serviceType.setName(name);
        return this;
    }

    public ServiceTypeBuilder withPrice(Float price){
        serviceType.setPrice(price);
        return this;
    }

    public ServiceTypeBuilder withDescription(String description){
        serviceType.setDescription(description);
        return this;
    }

    public ServiceTypeBuilder withServiceType(String type){
        serviceType.setServiceType(type);
        return this;
    }

    public ServiceTypeBuilder withBussType(Constants.AccountType bussType){
        serviceType.setBussType(bussType);
        return this;
    }

    public ServiceTypeBuilder withAttributes(Set<ServiceInternetAttribute> attributeList){
        serviceType.setServiceInternetAttributes(attributeList);
        return this;
    }

    public ServiceTypeBuilder withRandomAttribute(){
        //TODO null pointer here
        if(this.serviceType.getServiceType().equals(Constants.SERVICE_INTERNET)) {
            ServiceAttributeBuilder sb = new ServiceAttributeBuilder();
            ServiceInternetAttribute attribute = sb.build().randomServiceAttribute().getRes();
            Set<ServiceInternetAttribute> attributeList = new HashSet<ServiceInternetAttribute>();
            attributeList.add(attribute);
            serviceType.setServiceInternetAttributes(attributeList);
        }
        return this;
    }
}
