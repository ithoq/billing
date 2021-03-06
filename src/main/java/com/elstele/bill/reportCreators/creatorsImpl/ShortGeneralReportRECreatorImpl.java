package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import com.elstele.bill.reportCreators.reportsStringCreator.ReportStringCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.List;

public class ShortGeneralReportRECreatorImpl implements ReportCreator {

    private CallForCSVDataService callForCSVDataService;
    final public static Logger LOGGER = LogManager.getLogger(ShortGeneralReportRECreatorImpl.class);

    public ShortGeneralReportRECreatorImpl(CallForCSVDataService callForCSVDataService) {
        this.callForCSVDataService = callForCSVDataService;
    }

    public void create(ReportDetails reportDetails) {
        Float costTotalForPeriod = 0.0f;
        Date startTime = DateReportParser.parseStartTime(reportDetails);
        Date endTime = DateReportParser.parseEndTime(reportDetails);

        List<String> listWithNumberA = callForCSVDataService.getUniqueNumberA(startTime, endTime);
        PrintStream ps = FileCreator.createFileForWriting(reportDetails);
        int i = 0;
        for (String numberA : listWithNumberA) {
            List<CallForCSV> callsListByNumberA = callForCSVDataService.getCallForCSVByNumberA(numberA, startTime, endTime);
            ReportStringCreator stringCreator = new ReportStringCreator();
            List<String> stringList = stringCreator.createCSVStringsShort(numberA, callsListByNumberA, i);

            ReportStringsWriter.write(stringList, ps);
            costTotalForPeriod += CostTotalCounter.countForCSV(callsListByNumberA);
            i++;
        }
        String footerString = "Общая стоимость переговоров -  " + ReportStringCreator.round(costTotalForPeriod, 2) + " грн";
        if (ps != null) {
            ps.println(footerString);
            ps.close();
        }
        LOGGER.info("Report generating is Done");

    }
}
