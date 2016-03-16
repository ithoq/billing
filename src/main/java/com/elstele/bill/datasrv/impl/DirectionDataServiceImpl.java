package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.DirectionAssembler;
import com.elstele.bill.dao.interfaces.DirectionDAO;
import com.elstele.bill.dao.interfaces.TariffZoneDAO;
import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectionDataServiceImpl implements DirectionDataService {
    @Autowired
    DirectionDAO directionDAO;
    @Autowired
    TariffZoneDAO tariffZoneDAO;

    private DirectionAssembler assembler;

    final static Logger LOGGER = LogManager.getLogger(DirectionDataServiceImpl.class);

    @Override
    @Transactional
    public List<DirectionForm> getDirectionList(int page, int rows, String prefix) {
        page = page - 1;
        assembler = new DirectionAssembler(tariffZoneDAO);
        int offset = page * rows;
        List<Direction> beansList = directionDAO.getDirectionList(offset, rows, prefix);
        List<DirectionForm> formList = new ArrayList<>();
        for (Direction direction : beansList) {
            formList.add(assembler.fromBeanToForm(direction));
        }
        return formList;
    }

    @Override
    @Transactional
    public int getPagesCount(int pagesCount, String prefix) {
        return calculatePagesCount(directionDAO.getPagesCount(prefix), pagesCount);
    }

    @Override
    @Transactional
    public String deleteDirection(int id) {
        try {
            directionDAO.setStatusDelete(id);
            LOGGER.info("Direction " +id + " deleted successfully");
            return Constants.DIRECTION_DELETE_SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return Constants.DIRECTION_DELETE_ERROR;
        }
    }

    @Override
    @Transactional
    public void createDirection(DirectionForm directionForm) {
        try {
            assembler = new DirectionAssembler(tariffZoneDAO);
            Direction direction = assembler.fromFormToBean(directionForm);
            TariffZone tariffZone = tariffZoneDAO.getById(directionForm.getZoneId());
            direction.setTarifZone(tariffZone.getZoneId());
            directionDAO.create(direction);
            LOGGER.info("Direction " + direction + "successfully added");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public DirectionForm getDirectionById(int id) {
        assembler = new DirectionAssembler(tariffZoneDAO);
        Direction direction = directionDAO.getById(id);
        return assembler.fromBeanToForm(direction);
    }

    @Override
    @Transactional
    public void updateDirection(DirectionForm form) {
        assembler = new DirectionAssembler(tariffZoneDAO);
        Direction direction = assembler.fromFormToBean(form);
        directionDAO.update(direction);
    }

    @Override
    @Transactional
    public ResponseToAjax checkForFree(int id, String prefix) {
        Direction direction = directionDAO.getByPrefix(prefix);
        if(direction != null) {
            if (id > 0) {
                if (direction.getId() == id) {
                    return ResponseToAjax.FREE;
                }
                return ResponseToAjax.BUSY;
            }
            return ResponseToAjax.BUSY;
        }
        return ResponseToAjax.FREE;
    }

    @Override
    @Transactional
    public Direction getByPrefixMainPart(String prefixMainPart) {
        return directionDAO.getByPrefixMainPart(prefixMainPart);
    }


    private int calculatePagesCount(int callsCount, int containedCount) {
        if (callsCount % containedCount == 0)
            return callsCount / containedCount;
        else
            return (callsCount / containedCount) + 1;
    }
}
