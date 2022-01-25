package ru.itis.pdfclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.pdfclient.dto.PdfDataDto;
import ru.itis.pdfclient.service.PdfService;

@RestController
@RequiredArgsConstructor
public class PdfController {

    private final PdfService pdfService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/generate/sell")
    public ResponseEntity<byte[]> sellGenerate(@RequestBody PdfDataDto pdfDataDto) {
        byte[] data = pdfService.generateDocument(pdfDataDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/generate/buy")
    public ResponseEntity<byte[]> buyGenerate(@RequestBody PdfDataDto pdfDataDto) {
        byte[] data = pdfService.generateDocument(pdfDataDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
