package com.elstele.bill.reportCreators.reportsStringCreator;

import com.elstele.bill.domain.Call;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.CallTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportStringCreator {
    private Double costTotalForThisNumber = 0.0;
    private Double callDurationForThisNumber = 0.0;
    private List<String> stringList = new ArrayList<>();
    final static Logger LOGGER = LogManager.getLogger(ReportStringCreator.class);

    public final static String lineSeparator = System.getProperty("line.separator");

    public List<String> createCallTOStrings(String numberA, List<CallTO> callListByNumberA) {
        header(numberA);
        callTOData(callListByNumberA);
        footerCreate();
        return stringList;
    }

    public List<String> createCSVStrings(String numberA, List<CallForCSV> callForCSVListByNumberA) {
        header(numberA);
        callCSVData(callForCSVListByNumberA);
        footerCreate();
        return stringList;
    }

    public List<String> createTOStringsShort(String numberA, List<CallTO> callForCSVListByNumberA, int i) {
        callTODataShort(numberA, callForCSVListByNumberA, i);
        return stringList;
    }

    public List<String> createCSVStringsShort(String numberA, List<CallForCSV> callForCSVListByNumberA, int i) {
        callCSVDataShort(numberA, callForCSVListByNumberA, i);
        return stringList;
    }

    public List<String> createCallStrings(String numberA, List<Call> callList) {
        header(numberA);
        callData(callList);
        callFooterCreate();
        return stringList;
    }

    public List<String> createCallStringsShort(String numberA, List<Call> callList, Integer strIndex) {
        callDataShort(numberA, callList, strIndex);
        return stringList;
    }

    public List<String> createCallsCostStringsShort(String numberA, List<Call> callList, Integer strIndex) {
        callCostDataShort(numberA, callList, strIndex);
        return stringList;
    }

    public void header(String numberA) {
        String numberAShort = numberA.substring(1, numberA.length());
        String str;
        str = "Номер телефона, с которого звонили: " + numberAShort + lineSeparator;
        stringList.add(str);
        str = "--------------------------------------------------------------------------------" + lineSeparator;
        stringList.add(str);
        str = "          |             |             Кому звонили                 |       |    " + lineSeparator;
        stringList.add(str);
        str = "   Дата   |  Время |Длит|------------------------------------------| Сумма |Зак." + lineSeparator;
        stringList.add(str);
        str = "          |             |  Код  | Телефон   | Город или страна     |       |    " + lineSeparator;
        stringList.add(str);
        str = "----------|--------|----|-------|-----------|----------------------|-------|----" + lineSeparator;
        stringList.add(str);
    }

    public void callTOData(List<CallTO> callListByNumberA) {
        try {
            for (CallTO callTO : callListByNumberA) {
                String numberB = callTO.getNumberb();
                Integer duration = callTO.getDuration().intValue();
                String dirPrefix = callTO.getPrefix();
                String descrOrg = callTO.getDescription();
                Float costTotal = callTO.getCosttotal();
                Date startTimeVal = callTO.getStarttime();
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String reportDate = df.format(startTimeVal);
                String shortNumberB = callTO.getNumberb().substring(dirPrefix.length(), numberB.length());
                String result = String.format("%-18s|%-4d|%-7s|%-11s|%-22s|%7.2f|", reportDate, duration, dirPrefix, shortNumberB, descrOrg, costTotal);
                stringList.add(result + lineSeparator);
                costTotalForThisNumber += costTotal;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void callTODataShort(String numberA, List<CallTO> callListByNumberA, int i) {
        try {
            for (CallTO callTO : callListByNumberA) {
                Double costTotal = (double) callTO.getCosttotal();
                costTotalForThisNumber += costTotal;
            }
            String result = i + " " + numberA.substring(1, numberA.length()) + " " + round(costTotalForThisNumber, 2) + lineSeparator;
            stringList.add(result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void callCSVData(List<CallForCSV> callForCSVListByNumberA) {
        try {
            for (CallForCSV callForCSVByNumberA : callForCSVListByNumberA) {
                String numberB = callForCSVByNumberA.getNumberB();
                Integer duration = callForCSVByNumberA.getDuration();
                String dirPrefix = callForCSVByNumberA.getDirPrefix();
                String descrOrg = callForCSVByNumberA.getDirDescrpOrg();
                Double costTotal = Double.parseDouble(callForCSVByNumberA.getCostCallTotal());
                Date startTimeVal = callForCSVByNumberA.getStartTime();
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String reportDate = df.format(startTimeVal);
                String shortNumberB = callForCSVByNumberA.getNumberB().substring(dirPrefix.length(), numberB.length());
                String result = String.format("%-18s|%-4d|%-7s|%-11s|%-22s|%7.2f|", reportDate, duration, dirPrefix, shortNumberB, descrOrg, costTotal);
                stringList.add(result + lineSeparator);
                costTotalForThisNumber += costTotal;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void callCSVDataShort(String numberA, List<CallForCSV> callListByNumberA, int i) {
        try {
            for (CallForCSV callForCSV : callListByNumberA) {
                Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
                costTotalForThisNumber += costTotal;
            }
            String result = i + " " + numberA.substring(1, numberA.length()) + " " + round(costTotalForThisNumber, 2) + lineSeparator;
            stringList.add(result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void callData(List<Call> callListByNumberA) {
        try {
            for (Call call : callListByNumberA) {
                String numberB = call.getNumberB();
                Integer duration = call.getDuration();
                String dirPrefix = "";
                String descrOrg = "";
                Double costTotal = 0.0;
                Date startTimeVal = call.getStartTime();
                DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                String reportDate = df.format(startTimeVal);
                String shortNumberB = numberB;
                if (numberB.startsWith("97") && numberB.length() == 8) {
                    shortNumberB = numberB.substring(1, 8);
                }
                if (numberB.startsWith("92") || numberB.startsWith("93") || numberB.startsWith("94") || numberB.startsWith("95") || numberB.startsWith("96") && numberB.length() == 7) {
                    shortNumberB = numberB.substring(1, 7);
                }
                String result = String.format("%-18s|%-4d|%-7s|%-11s|%-22s|%7.2f|", reportDate, duration, dirPrefix, shortNumberB, descrOrg, costTotal);
                stringList.add(result + lineSeparator);
                costTotalForThisNumber += duration;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void callDataShort(String numberA, List<Call> callListByNumberA, Integer strIndex) {
        try {
            for (Call call : callListByNumberA) {
                callDurationForThisNumber += call.getDuration();
            }
            String result = strIndex + " " + numberA.substring(1, numberA.length()) + " " + round(callDurationForThisNumber, 2) + " секунд" + lineSeparator;
            stringList.add(result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void callCostDataShort(String numberA, List<Call> callListByNumberA, Integer strIndex) {
        try {
            Double currentNumberTotalCost = 0.0;
            for (Call call : callListByNumberA) {
                callDurationForThisNumber += call.getDuration();
            }
            if (callDurationForThisNumber > 36000) {
                currentNumberTotalCost = (callDurationForThisNumber - 36000) * 0.0009;
            }
            String result = strIndex + " " + numberA.substring(1, numberA.length()) + " " + round(currentNumberTotalCost, 2) + " UAH";
            stringList.add(result);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void footerCreate() {
        String str;
        str = "----------|--------|----|-------|-----------|----------------------|-------|----" + lineSeparator;
        stringList.add(str);

        str = " Всего " + round(costTotalForThisNumber, 2) + lineSeparator;
        stringList.add(str);

        str = " В том числе НДС " + round(costTotalForThisNumber * 0.2, 2) + lineSeparator;
        stringList.add(str);

        str = "--------------------------------------------------------------------------------" + lineSeparator;
        stringList.add(str);
        stringList.add(lineSeparator);
        stringList.add(lineSeparator);
    }

    private void callFooterCreate() {
        String str;
        str = "----------|--------|----|-------|-----------|----------------------|-------|----" + lineSeparator;
        stringList.add(str);

        str = " Всего " + round(costTotalForThisNumber, 2) + lineSeparator;
        stringList.add(str);

        str = "--------------------------------------------------------------------------------" + lineSeparator;
        stringList.add(str);
        stringList.add(lineSeparator);
        stringList.add(lineSeparator);
    }

    public static double round(double value, int places) {
        try {
            if (places < 0) throw new IllegalArgumentException();
            long factor = (long) Math.pow(10, places);
            value = value * factor;
            long tmp = Math.round(value);
            return (double) tmp / factor;
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return 0.0;
        }
    }
}
