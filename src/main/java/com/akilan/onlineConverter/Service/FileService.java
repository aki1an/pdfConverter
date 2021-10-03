package com.akilan.onlineConverter.Service;

import com.akilan.onlineConverter.Model.OutputFile;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {
    @SneakyThrows
    public byte[] getListOfWordAndConvertToListOfPDF(List<MultipartFile> wordFiles) {
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (MultipartFile srcFile : wordFiles) {
            InputStream fis = new ByteArrayInputStream(getWordAndConvertToPDF(srcFile).getOutputFile());
            ZipEntry zipEntry = new ZipEntry(srcFile.getOriginalFilename().substring(0,srcFile.getOriginalFilename().indexOf("."))+".pdf");
            System.out.println(srcFile.getOriginalFilename().substring(0,srcFile.getOriginalFilename().indexOf("."))+".pdf");
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();

        return fos.toByteArray();
    }

    @SneakyThrows(IOException.class)
    private OutputFile getWordAndConvertToPDF(MultipartFile wordFile) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfConverter.getInstance().convert(new XWPFDocument(wordFile.getInputStream()), out, null);
        return new OutputFile(out.toByteArray());
    }

    @SneakyThrows({DocumentException.class, IOException.class})
    public OutputFile getListOfWordsAndConvertToSinglePdf(List<MultipartFile> listOfWords) {
        long start = System.currentTimeMillis();
        Document document = new Document();
        ByteArrayOutputStream singlePdf = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, singlePdf);
        document.open();
        int currentPdfReaderPage = 1;

        for (MultipartFile wordFile : listOfWords) {
            PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(getWordAndConvertToPDF(wordFile).getOutputFile()));
            while (currentPdfReaderPage <= pdfReader.getNumberOfPages()) {
                document.newPage();
                writer.getDirectContent().addTemplate(writer.getImportedPage(pdfReader, currentPdfReaderPage), 0, 0);
                currentPdfReaderPage++;
            }
            currentPdfReaderPage = 1;
        }
        singlePdf.flush();
        document.close();
        singlePdf.close();
        System.out.println((System.currentTimeMillis() - start) / 1000.00);
        return new OutputFile(singlePdf.toByteArray());
    }
}
