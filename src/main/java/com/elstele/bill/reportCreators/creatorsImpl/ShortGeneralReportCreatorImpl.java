package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import com.elstele.bill.reportCreators.reportsStringCreator.ReportStringCreator;
import com.elstele.bill.reportCreators.CallTO;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.List;

public class ShortGeneralReportCreatorImpl implements ReportCreator {

    private CallDataService callDataService;
    final public static Logger LOGGER = LogManager.getLogger(ShortGeneralReportCreatorImpl.class);

    public ShortGeneralReportCreatorImpl(CallDataService callDataService) {
        this.callDataService = callDataService;
    }

    public void create(ReportDetails reportDetails) {
        Float costTotalForPeriod = 0.0f;
        Date startTime = DateReportParser.parseStartTime(reportDetails);
        Date endTime = DateReportParser.parseEndTime(reportDetails);

        List<String> listWithNumberA = callDataService.getUniqueNumberAFromCalls(startTime, endTime);
        PrintStream ps = FileCreator.createFileForWriting(reportDetails);
        int i = 1;
        for (String numberA : listWithNumberA) {
            List<CallTO> callsListByNumberA = callDataService.getCallByNumberA(numberA, startTime, endTime);
            ReportStringCreator stringCreator = new ReportStringCreator();
            List<String> stringList = stringCreator.createTOStringsShort(numberA, callsListByNumberA, i);
            ReportStringsWriter.write(stringList, ps);
            costTotalForPeriod += CostTotalCounter.countForTO(callsListByNumberA);
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
