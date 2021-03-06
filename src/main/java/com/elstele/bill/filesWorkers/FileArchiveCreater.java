package com.elstele.bill.filesWorkers;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


@Service
public class FileArchiveCreater {
    final static Logger LOGGER = LogManager.getLogger(FileArchiveCreater.class);
    private static final int BUFFER_SIZE = 4096;
    List<String> fileNameList = new ArrayList<String>();

    public void createArchive(String path, String directoryName) {
        String zipFilePath = path + File.separator + directoryName + ".zip";
        LOGGER.info("Zip file path is " + zipFilePath);
        String fileDirectoryPath = path + File.separator + directoryName;
        LOGGER.info("File directory path is " + fileDirectoryPath);
        createFileNameList(path, directoryName);
        zipItFiles(zipFilePath, fileDirectoryPath);

    }

    public void createFileNameList(String path, String directoryName) {
        File fileDirectory = new File(path + File.separator + directoryName);
        try {
            if (fileDirectory.isDirectory()) {
                LOGGER.info("File: " + fileDirectory.getName() + " is directory");
                File[] fileArray = fileDirectory.listFiles();
                assert fileArray != null;
                for (File file : fileArray) {
                    if (file.isFile()) {
                        LOGGER.info("File: " + file.getName() + " is file");
                        fileNameList.add(file.getName());
                    }
                }
            }
        }catch(SecurityException e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void zipItFiles(String zipFilePath, String fileDirectoryPath) {
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            LOGGER.info("Output to Zip : " + zipFilePath);
            for (String fileName : fileNameList) {
                LOGGER.info("File Added : " + fileName);
                ZipEntry ze = new ZipEntry(fileName);
                zos.putNextEntry(ze);
                String inPath = fileDirectoryPath + File.separator + fileName;
                FileInputStream in =
                        new FileInputStream(inPath);
                LOGGER.info("File directory path and file name is " + inPath);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                in.close();
            }
            zos.closeEntry();
            zos.close();
            LOGGER.info("File's zipping is Done");
        } catch (IOException e) {
            LOGGER.debug(e);
        }
    }
}
