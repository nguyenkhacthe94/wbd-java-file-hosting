package com.upload.controller;

import com.upload.model.UploadFile;
import com.upload.model.UploadFileForm;
import com.upload.service.UploadFileService;
import com.upload.utils.StorageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadFileController {
    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/upload")
    public ModelAndView showUploadForm() {
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("uploadFileForm", new UploadFileForm());
        return modelAndView;
    }

    @PostMapping("/upload")
    public ModelAndView saveFile(@ModelAttribute("uploadFileForm") UploadFileForm uploadFileForm) {
        try {
            String randomCode = UUID.randomUUID().toString();
            String originFileName = uploadFileForm.getUploadedFile().getOriginalFilename();
            String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
            uploadFileForm.getUploadedFile().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));

            UploadFile uploadFile = new UploadFile();
            uploadFile.setName(uploadFileForm.getName());
            uploadFile.setDescription(uploadFileForm.getDescription());
            uploadFile.setPublic(uploadFileForm.isPublic());
            uploadFile.setUploadedFile(randomName);

            uploadFileService.save(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("/upload");
        modelAndView.addObject("uploadFileForm", uploadFileForm);
        modelAndView.addObject("message", "new file is uploaded");
        return modelAndView;
    }
}
