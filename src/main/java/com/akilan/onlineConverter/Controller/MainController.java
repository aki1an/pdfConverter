package com.akilan.onlineConverter.Controller;

import com.akilan.onlineConverter.Model.OutputFile;
import com.akilan.onlineConverter.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    FileService fileService;

    @GetMapping("/words-to-pdfs")
    public ResponseEntity<List<OutputFile>> wordFilesToPdfFiles(@RequestParam("file") List<MultipartFile> wordFiles) {
        return ResponseEntity.ok(fileService.getListOfWordAndConvertToListOfPDF(wordFiles));
    }

    @GetMapping("/words-to-pdf-merged")
    public ResponseEntity<OutputFile> wordFilesToMergedPdf(@RequestParam("file") List<MultipartFile> wordFiles) {
        return ResponseEntity.ok(fileService.getListOfWordsAndConvertToSinglePdf(wordFiles));
    }
}