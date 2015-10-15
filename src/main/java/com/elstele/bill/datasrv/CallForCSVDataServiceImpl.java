package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.CallForCSVAssembler;
import com.elstele.bill.dao.CallForCSVDAO;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CallForCSVDataServiceImpl implements CallForCSVDataService {
    @Autowired
    CallForCSVDAO callForCSVDAO;

    @Override
    @Transactional
    public void addReportData(CallForCSVForm callForCSVForm) {
        CallForCSVAssembler assembler = new CallForCSVAssembler();
        CallForCSV bean = assembler.fromFormToBean(callForCSVForm);
        callForCSVDAO.create(bean);
    }

    @Override
    @Transactional
    public void clearReportTable() {
        callForCSVDAO.clearReportDataTable();

    }
}
