package com.upload.controller;

import com.upload.model.UploadFile;
import com.upload.model.UploadFileForm;
import com.upload.service.upload.UploadFileService;
import com.upload.utils.StorageUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
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

    @GetMapping("/")
    public ModelAndView showIndex() {
        Iterable<UploadFile> uploadFiles = uploadFileService.findAll();
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("uploadFiles", uploadFiles);
        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        UploadFile uploadFile = uploadFileService.findById(id);
        UploadFileForm uploadFileForm = new UploadFileForm();

        uploadFileForm.setId(uploadFile.getId());
        uploadFileForm.setName(uploadFile.getName());
        uploadFileForm.setDescription(uploadFile.getDescription());
        uploadFileForm.setPublic(uploadFile.isPublic());
        uploadFileForm.setUploadedFileUrl(uploadFile.getUploadedFile());

        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("uploadFileForm", uploadFileForm);
        return modelAndView;
    }

    @PostMapping("{id}/edit")
    public ModelAndView updateFileInfo(@PathVariable("id") Long id, @ModelAttribute("uploadFile") UploadFileForm uploadFileForm) {
        UploadFile uploadFile = uploadFileService.findById(id);
        try {
            if (!uploadFileForm.getUploadedFile().isEmpty()) {
                StorageUtils.removeFile(uploadFile.getUploadedFile());
                String randomCode = UUID.randomUUID().toString();
                String originFileName = uploadFileForm.getUploadedFile().getOriginalFilename();
                String randomName = randomCode + StorageUtils.getFileExtension(originFileName);
                uploadFileForm.getUploadedFile().transferTo(new File(StorageUtils.FEATURE_LOCATION + "/" + randomName));
                uploadFile.setUploadedFile(randomName);
                uploadFileForm.setUploadedFileUrl(randomName);
            }
            uploadFile.setId(uploadFileForm.getId());
            uploadFile.setName(uploadFileForm.getName());
            uploadFile.setDescription(uploadFileForm.getDescription());
            uploadFile.setPublic(uploadFileForm.isPublic());
            uploadFileService.save(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();

        }
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("uploadFileForm", uploadFileForm);
        modelAndView.addObject("message", "File infomation is updated");
        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        UploadFile uploadFile = uploadFileService.findById(id);
        ModelAndView modelAndView = new ModelAndView("/delete");
        modelAndView.addObject("uploadFile", uploadFile);
        return modelAndView;
    }

    @PostMapping("/{id}/delete")
    public String deleteFile(@PathVariable("id") Long id) {
        UploadFile uploadFile = uploadFileService.findById(id);
        StorageUtils.removeFile(uploadFile.getUploadedFile());
        uploadFileService.delete(id);
        return "redirect:/";
    }

    @Autowired
    JavaMailSender mailSender;

    @GetMapping("/{id}/share")
    public ModelAndView shareByEmailForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/share");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @PostMapping("/{id}/share.do")
    public ModelAndView shareFileViaEmail(@PathVariable("id") Long id, @RequestParam("email") String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject("Link download File");
        mailMessage.setText("http://192.168.2.56:8080/" + id + "/download");
        mailSender.send(mailMessage);

        ModelAndView modelAndView = new ModelAndView("/share");
        modelAndView.addObject("message", "Download link has been sent to" + email);
        return modelAndView;
    }

    @GetMapping("/{id}/download")
    public ModelAndView showDownloadForm(@PathVariable("id") Long id) {
        UploadFile uploadFile = uploadFileService.findById(id);
        File file = new File(StorageUtils.FEATURE_LOCATION + "/" + uploadFile.getUploadedFile());
        String size = FileUtils.byteCountToDisplaySize(file.getTotalSpace());

        UploadFileForm uploadFileForm = new UploadFileForm();
        uploadFileForm.setId(uploadFile.getId());
        uploadFileForm.setName(uploadFile.getName());
        uploadFileForm.setDescription(uploadFile.getDescription());
        uploadFileForm.setPublic(uploadFile.isPublic());

        ModelAndView modelAndView = new ModelAndView("/download");
        modelAndView.addObject("uploadFileForm", uploadFileForm);
        modelAndView.addObject("size", size);

        return modelAndView;
    }

    @PostMapping("/{id}/downloading")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") Long id) throws Exception {
        UploadFile uploadFile = uploadFileService.findById(id);
        System.out.println(uploadFile.toString());
        File file = new File(StorageUtils.FEATURE_LOCATION + "/" + uploadFile.getUploadedFile());
        System.out.println("ok");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + file.getName())
                .contentType(MediaType.MULTIPART_FORM_DATA).contentLength(file.length())
                .body(resource);
    }
}
