package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.List;

@Service
public class ShortGeneralReportRECreatorImpl extends GeneralReportCreator implements ReportCreator {

    @Autowired
    CallForCSVDataService callForCSVDataService;

    public void create(ReportDetails reportDetails) {
        PrintStream bw = createFileForWriting(reportDetails);
        filePrintingCreate(bw, reportDetails.getYear(), reportDetails.getMonth());
    }

    public void filePrintingCreate(PrintStream bw, String year, String month) {
        try {
            Double costTotalForPeriod = 0.0;
            List<String> listWithNumberA = getUniqueNumbersA(year, month);
            for (String numberA : listWithNumberA) {
                List<CallForCSV> callsListByNumberA = getCallsFromDBByNumbersA(numberA, year, month);
                Double costTotalForThisNumber = 0.0;
                costTotalForThisNumber = costTotalForThisNumberOperation(callsListByNumberA);
                costTotalForPeriod += costTotalForThisNumber;
                String firstString = numberA.substring(1, numberA.length()) + " " + round(costTotalForThisNumber, 2) + "\r\n";
                bw.println(firstString);
            }
            String firstString = "Общая стоимость переговоров -  " + round(costTotalForPeriod, 2) + " грн";
            bw.println(firstString);
            bw.close();
            log.info("Report generating is Done");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public List<CallForCSV> getCallsFromDBByNumbersA(String numberA, String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<CallForCSV> result = callForCSVDataService.getCallForCSVByNumberA(numberA, startTime, endTime);
        return result;
    }

    public List<String> getUniqueNumbersA(String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<String> listWithNumberA = callForCSVDataService.getUniqueNumberA(startTime, endTime);
        return listWithNumberA;
    }

    public Double costTotalForThisNumberOperation(List<CallForCSV> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (CallForCSV callForCSV : callListByNumberA) {
            Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }
}