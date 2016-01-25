package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.UploadedFileInfoForm;

import java.util.List;

public interface UploadedFileInfoDataService {
    public List<UploadedFileInfoForm> getUploadedFileInfoList();
    public Integer addUploadedFileInfo(UploadedFileInfoForm uploadedFileInfoForm);
    public UploadedFileInfoForm getById(Integer id);
    public void setUploadedFileInfoStatusDelete(Integer id);
    public void updateFile(UploadedFileInfoForm uploadedFileInfoForm);
    public void createOrUpdateFileInfo(UploadedFileInfoForm uploadedFileInfoForm);
}
