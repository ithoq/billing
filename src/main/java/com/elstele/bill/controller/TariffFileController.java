package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.tariffFileParser.TariffFileParser;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class TariffFileController {
    @Autowired
    TariffFileParser parser;
    @Autowired
    UploadedFileInfoDataService uploadedFileInfoDataService;

    @RequestMapping(value = "/uploaddirectionfile", method = RequestMethod.GET)
    public ModelAndView getModelAndViewForDirectionFile(HttpSession session){
        UserStateStorage.setProgressToObjectInMap(session, 0);
        ModelAndView mav = new ModelAndView("uploaddirectionfileModel");
        mav.addObject("fileInfoList", uploadedFileInfoDataService.getUploadedFileInfoList(Constants.DOCX_FILE_TYPE));
        return mav;
    }

    @RequestMapping(value = "/uploaddirectionfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax parseDOCX(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session) {
        return  parser.handleTariffFile(multiPartHTTPServletRequestFiles, session);
    }

    @RequestMapping(value = "uploaddirectionfile/reportCreatingProgress", method = RequestMethod.GET)
    @ResponseBody
    public Float getProcessingProgress(HttpSession session) {
        return UserStateStorage.getProgressBySession(session);
    }
}
