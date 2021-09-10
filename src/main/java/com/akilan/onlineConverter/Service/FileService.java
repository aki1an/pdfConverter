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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public List<OutputFile> getListOfWordAndConvertToListOfPDF(List<MultipartFile> wordFiles) {
        List<OutputFile> convertedPdfFiles = new ArrayList<>();
        wordFiles.forEach(wordFile -> convertedPdfFiles.add(getWordAndConvertToPDF(wordFile)));
        return convertedPdfFiles;
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
