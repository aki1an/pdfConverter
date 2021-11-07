package com.akilan.onlineConverter.Controller;

import com.akilan.onlineConverter.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    FileService fileService;

    @PostMapping(value = "/word-to-pdf", produces = "application/zip")
    @CrossOrigin
    public ResponseEntity wordFilesToPdfFiles(@RequestParam("file") List<MultipartFile> wordFiles) {
        return ResponseEntity.ok(fileService.getListOfWordAndConvertToListOfPDF(wordFiles));
    }

    @PostMapping(value = "/words-to-pdf-merged", produces = "application/pdf")
    @CrossOrigin
    public ResponseEntity wordFilesToMergedPdf(@RequestParam("file") List<MultipartFile> wordFiles) {
        return ResponseEntity.ok(fileService.getListOfWordsAndConvertToSinglePdf(wordFiles).getOutputFile());
    }
}
