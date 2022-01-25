package ru.itis.pdfserver.service;

import ru.itis.pdfserver.dto.PdfDataDto;

public interface PdfService {

    byte[] generatePdf(PdfDataDto pdfDataDto);
}
