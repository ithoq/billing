package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Street;

import java.util.List;

public interface StreetDAO extends CommonDAO<Street>{
    public Integer getStreetIDByStreetName(String streetName);
    public List<Street> getListOfStreets();
}
