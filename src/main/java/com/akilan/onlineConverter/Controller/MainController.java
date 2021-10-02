package com.akilan.onlineConverter.Controller;

import com.akilan.onlineConverter.Model.OutputFile;
import com.akilan.onlineConverter.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MainController {

    @Autowired
    FileService fileService;

    @PostMapping("/word-to-pdf")
    public ResponseEntity<List<OutputFile>> wordFilesToPdfFiles(@RequestParam("file") List<MultipartFile> wordFiles) {
        return ResponseEntity.ok(fileService.getListOfWordAndConvertToListOfPDF(wordFiles));
    }

    @PostMapping(value = "/words-to-pdf-merged",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<OutputFile> wordFilesToMergedPdf(@RequestParam("file") List<MultipartFile> wordFiles) {
        return ResponseEntity.ok(fileService.getListOfWordsAndConvertToSinglePdf(wordFiles).getOutputFile());
    }
}
