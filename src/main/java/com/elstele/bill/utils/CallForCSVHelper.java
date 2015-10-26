package com.elstele.bill.utils;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CallForCSVHelper {


    public static CallForCSVForm arrayHandlingMethodCSV(String line) throws ParseException {
        final String DELIMITER = ";";
        String[] data = line.split(DELIMITER);

        String numberA = data[0].substring(5,12);
        String numberB = data[1];
        String duration = data[2];
        String call_start = data[3];
        String dir_prefix = data[4];
        String dir_descr_orig = data[5];
        String cost_without_nds = data[9];

        String dir_descr = dir_descr_orig.replace("'", "");
        dir_descr = dir_descr.replace("'", "\'");

        Date startTime = startTimeHandling(call_start);
        String costWithNDS = costWithNDS(cost_without_nds);


        CallForCSVForm callForCSVForm = new CallForCSVForm();
        callForCSVForm.setNumberA(numberA);
        callForCSVForm.setNumberB(numberB);
        callForCSVForm.setCostCallTotal(costWithNDS);
        callForCSVForm.setStartTime(startTime);
        callForCSVForm.setDuration(duration);
        callForCSVForm.setDirPrefix(dir_prefix);
        callForCSVForm.setDirDescrpOrg(dir_descr);
        callForCSVForm.setProvider("2");

        return callForCSVForm;
    }

    public static CallForCSVForm arrayHandlingMethodCSVUkrNet(String line) throws ParseException {
        final String DELIMITER = " ";
        Pattern pattern  = Pattern.compile("\\s{2,}");
        Matcher matcher = pattern.matcher(line);
        String result = matcher.replaceAll(" ");

        String[] data = result.split(DELIMITER);

        String numberA = data[0].substring(2,9);
        String numberB = data[3];
        String duration = data[7];
        String call_start = data[1];
        String dir_prefix = data[4];
        String dir_descr_orig = "no data";
        String cost_without_nds = data[9];
        if (checkWithRegExp(dir_prefix, "/^0.*/")) {
            dir_prefix = dir_prefix;
        }
        if (dir_prefix.startsWith("8")) {
            dir_prefix = "0" + dir_prefix;
        }
        if (checkWithRegExp(dir_prefix, "/^[1-7].*/")) {
            dir_prefix = "00" + dir_prefix;
        }

        //Comparing dir_prefix to detect direction value;
        //


        String dir_descr = dir_descr_orig.replace("'", "");
        dir_descr = dir_descr.replace("'", "\'");

        Date startTime = startTimeHandlingUkrNet(call_start);
        String costWithNDS = costWithNDS(cost_without_nds);


        CallForCSVForm callForCSVForm = new CallForCSVForm();
        callForCSVForm.setNumberA(numberA);
        callForCSVForm.setNumberB(numberB);
        callForCSVForm.setCostCallTotal(costWithNDS);
        callForCSVForm.setStartTime(startTime);
        callForCSVForm.setDuration(duration);
        callForCSVForm.setDirPrefix(dir_prefix);
        callForCSVForm.setDirDescrpOrg(dir_descr);
        callForCSVForm.setProvider("1");

        return callForCSVForm;
    }

    public static Date startTimeHandling(String call_start) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = call_start.substring(6,10);
        String month = call_start.substring(3,5);
        String day = call_start.substring(0,2);
        String time = call_start.substring(10,18);
        Date startTime = sdf.parse(year+"-"+month+"-"+day + " "+ time);
        return startTime;
    }

    public static Date startTimeHandlingUkrNet(String call_start) throws ParseException {
        String year = call_start.substring(0,4);
        String month = call_start.substring(4,6);
        String day = call_start.substring(6,8);
        String hour = call_start.substring(8,10);
        String min = call_start.substring(10,12);
        String sec = call_start.substring(12,14);
        String startTimeStr = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = sdf.parse(startTimeStr);
        return startTime;
    }


    public static String costWithNDS(String costWithoutNDS){
        if (costWithoutNDS.indexOf(",") == 0) {
            costWithoutNDS = "0" + costWithoutNDS;
        }
        return Double.toString(Double.parseDouble(costWithoutNDS) * 1.2);
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static boolean checkWithRegExp(String strToCheck, String strPattern){
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strToCheck);
        return m.matches();
    }
}