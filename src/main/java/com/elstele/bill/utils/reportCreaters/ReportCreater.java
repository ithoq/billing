package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.domain.CallForCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ReportCreater {

    @Autowired
    CallForCSVDataService callForCSVDataService;

    @Autowired
    CallDataService callDataService;


    public PrintStream createFileForWriting(String path, String fileName) throws IOException {
        Date tempStartTime = callForCSVDataService.getDateInterval();
        File file = new File(path + File.separator + tempStartTime.toString().substring(0, 7) + "_" + fileName + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintStream bw = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, false)), true, "cp1251");
        return bw;
    }

    public void mainHeaderPrint(PrintStream bw, String numberA) {
        String numberAShort = numberA.substring(1, numberA.length());
        String firstString = "";

        firstString = "Номер телефона, с которого звонили: " + numberAShort;
        bw.println(firstString);

        firstString = "--------------------------------------------------------------------------------";
        bw.println(firstString);

        firstString = "          |             |             Кому звонили                 |       |    ";
        bw.println(firstString);

        firstString = "   Дата   |  Время |Длит|------------------------------------------| Сумма |Зак.";
        bw.println(firstString);

        firstString = "          |             |  Код  | Телефон   | Город или страна     |       |    ";
        bw.println(firstString);

        firstString = "----------|--------|----|-------|-----------|----------------------|-------|----";
        bw.println(firstString);
    }

    public Double endOfTheHeaderPrint(PrintStream bw, Double costTotalForPeriod, Double costTotalForThisNumber) {

        String firstString = "";
        firstString = "----------|--------|----|-------|-----------|----------------------|-------|----";
        bw.println(firstString);

        firstString = " Всего " + round(costTotalForThisNumber, 2);
        bw.println(firstString);

        firstString = " В том числе НДС " + round(costTotalForThisNumber * 0.2, 2);
        bw.println(firstString);

        firstString = "--------------------------------------------------------------------------------";
        bw.println(firstString);

        bw.println("\r\n");
        bw.println("\r\n");
        costTotalForPeriod += costTotalForThisNumber;
        return costTotalForPeriod;
    }

    public Date getTempStartTime() {
        Date tempStartTime = callForCSVDataService.getDateInterval();
        return tempStartTime;
    }

    public Date getEndTimeDate(Date tempStartTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(tempStartTime);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date endTime = c.getTime();
        return endTime;
    }

    public Date getStartTimeDate(Date tempStartTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(tempStartTime);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date startTime = c.getTime();
        return startTime;
    }

    public Double costTotalForThisNumberOperation(List<CallForCSV> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (CallForCSV callForCSV : callListByNumberA) {
            Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }

    public Double costTotalForThisCallNumberOperation(PrintStream bw, List<Call> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (Call call : callListByNumberA) {
            Double costTotal = (double)call.getCostTotal();
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }


    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}