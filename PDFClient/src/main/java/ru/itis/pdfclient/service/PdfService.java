package ru.itis.pdfclient.service;

import ru.itis.pdfclient.dto.PdfDataDto;

public interface PdfService {

    byte[] generateDocument(PdfDataDto pdfDataDto);
}
