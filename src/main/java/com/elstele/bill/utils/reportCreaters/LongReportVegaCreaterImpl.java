package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.utils.CallTransformerDir;
import com.elstele.bill.utils.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LongReportVegaCreaterImpl extends ReportCreater implements ReportCreaterInterface {

    @Autowired
    CallDataService callDataService;

    public void reportCreateMain(String path, String fileName){
        PrintStream bw = createFileForWriting(path, fileName);
        filePrintingCreate(bw);
    }

    public void filePrintingCreate(PrintStream bw){
        Double costTotalForPeriod = 0.0;
        List<String> listWithNumberA = getUniqueNumbersA();
        for (String numberA : listWithNumberA) {
            List<CallTransformerDir> callsListByNumberA = getCallsFromDBByNumbersA(numberA);
            mainHeaderPrint(bw, numberA);
            Double costTotalForThisNumber = 0.0;
            costTotalForThisNumber = callDataPrint(bw, callsListByNumberA);
            costTotalForPeriod = endOfTheHeaderPrint(bw, costTotalForPeriod, costTotalForThisNumber);
        }
        String firstString = " Итого " + round(costTotalForPeriod, 2);
        bw.println(firstString);
        bw.close();
    }

    public List<String> getUniqueNumbersA() {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<String> listWithNumberA = new ArrayList<String>();
        listWithNumberA = callDataService.getUniqueNumberAFromCallsWithTrunk(startTime, endTime, "05");
        return listWithNumberA;
    }

    public List<CallTransformerDir> getCallsFromDBByNumbersA(String numberA) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<CallTransformerDir> result = callDataService.getCallByNumberAWithTrunk(numberA, startTime, endTime, "05");
        return result;
    }
    public Double callDataPrint(PrintStream bw, List<CallTransformerDir> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;

        for (CallTransformerDir call : callListByNumberA) {
            String numberB = call.getNumberb();
            String duration = call.getDuration().toString();
            String dirPrefix = call.getPrefix();
            String descrOrg = call.getDescription();
            Double costTotal = (double)call.getCosttotal();
            Date startTimeVal = call.getStarttime();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(startTimeVal);
            String shortNumberB = call.getNumberb().substring(dirPrefix.length(), numberB.length());
            bw.printf("%-18s|%-4s|%-7s|%-11s|%-22s|%7.2f|\r\n",
                    reportDate,
                    duration,
                    dirPrefix,
                    shortNumberB,
                    descrOrg,
                    costTotal
            );

            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }


}
