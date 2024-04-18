package com.esprit.backend.Services;


import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;



@Service
public class CvParsingService {


  public String parsePdf(byte[] pdfData) throws IOException {
    // Directly load the PDF from the byte array
    try (PDDocument document = Loader.loadPDF(pdfData)) {
      PDFTextStripper stripper = new PDFTextStripper();
      return stripper.getText(document);
    }
  }
}
