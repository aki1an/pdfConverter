package com.akilan.onlineConverter.Controller;

import com.akilan.onlineConverter.Model.SystemArch;
import com.akilan.onlineConverter.Service.FileService;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.List;

@RestController
public class MainController {

    final FileService fileService;

    public MainController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/word-to-pdf", produces = "application/zip")
    @CrossOrigin
    public ResponseEntity<byte[]> wordFilesToPdfFiles(@RequestParam("file") List<MultipartFile> wordFiles) {
        return ResponseEntity.ok(fileService.getListOfWordAndConvertToListOfPDF(wordFiles));
    }

    @PostMapping(value = "/words-to-pdf-merged", produces = "application/pdf")
    @CrossOrigin
    public ResponseEntity<byte[]> wordFilesToMergedPdf(@RequestParam("file") List<MultipartFile> wordFiles) {
        return ResponseEntity.ok(fileService.getListOfWordsAndConvertToSinglePdf(wordFiles).getOutputFile());
    }

    @GetMapping(value = "/device-status")
    @CrossOrigin
    public ResponseEntity<SystemArch> wordFilesToMergedPdf() {
        SystemArch systemArch = new SystemArch();
        systemArch.setProcessors(Runtime.getRuntime().availableProcessors() + "  PROCESSORS");
        systemArch.setAvailableRam(Runtime.getRuntime().freeMemory() / (1024 * 1024) + " MB");
        systemArch.setAllocatedRam(Runtime.getRuntime().totalMemory() / (1024 * 1024) + " MB");
        systemArch.setTotalRam(((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalMemorySize() / (1024 * 1024) + " MB");
        systemArch.setTotalDiskVolume(new File("/").getTotalSpace() / (1024 * 1024 * 1024) + " GB");
        systemArch.setUsableDiskVolume(new File("/").getFreeSpace() / (1024 * 1024 * 1024) + " GB");
        return ResponseEntity.ok(systemArch);
    }

}
