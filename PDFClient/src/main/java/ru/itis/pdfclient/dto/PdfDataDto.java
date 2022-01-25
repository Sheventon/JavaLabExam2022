package ru.itis.pdfclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PdfDataDto {
    private String firstname;
    private String lastname;
    private String type;
}
