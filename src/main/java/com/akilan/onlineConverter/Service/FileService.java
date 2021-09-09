package com.akilan.onlineConverter.Service;

import com.akilan.onlineConverter.Model.OutputFile;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfReader;
import lombok.SneakyThrows;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    public List<OutputFile> getListOfWordAndConvertToListOfPDF(List<MultipartFile> wordFiles) {
        List<OutputFile> convertedPdfFiles = new ArrayList<>();
        wordFiles.forEach(wordFile -> {
            convertedPdfFiles.add(getWordAndConvertToPDF(wordFile));
        });
        return convertedPdfFiles;
    }

    private OutputFile getWordAndConvertToPDF(MultipartFile wordFile) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            InputStream in = wordFile.getInputStream();
            XWPFDocument document = new XWPFDocument(in);
            PdfConverter.getInstance().convert(document, out, null);
        } catch (IOException exception) {

        }
        return new OutputFile(out.toByteArray());
    }

    @SneakyThrows
    public OutputFile getListOfWordsAndConvertToSinglePdf(List<MultipartFile> listOfWords) {
        ByteArrayOutputStream singlePdf = new ByteArrayOutputStream();
        Document pdf = new Document();
        int currentPage = 1;
        for (MultipartFile wordFile : listOfWords) {
            PdfReader pdfReader = new PdfReader(new ByteArrayInputStream(wordFile.getBytes()));
            while (currentPage < pdfReader.getNumberOfPages()) {
                pdf.newPage();

            }

        }
    }

}
