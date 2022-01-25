package ru.itis.pdfserver.service;

import com.itextpdf.html2pdf.HtmlConverter;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import ru.itis.pdfserver.dto.PdfDataDto;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class PdfServiceImpl implements PdfService {

    @Override
    public byte[] generatePdf(PdfDataDto pdfDataDto) {
        try {
            ByteArrayOutputStream htmlOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(htmlOutputStream);

            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            FileTemplateLoader templateLoader = new FileTemplateLoader(
                    new File("templates"));
            cfg.setTemplateLoader(templateLoader);

            Map<String, Object> data = new HashMap<>();

            data.put("firstname", pdfDataDto.getFirstname());
            data.put("lastname", pdfDataDto.getLastname());
            data.put("type", pdfDataDto.getType());

            Template template = cfg.getTemplate("pdf.ftlh");
            template.process(data, outputStreamWriter);

            ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

            HtmlConverter.convertToPdf(new ByteArrayInputStream(htmlOutputStream.toByteArray()),
                    pdfOutputStream);

            return pdfOutputStream.toByteArray();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    /*@Override
    public byte[] generatePdf(PdfDataDto pdfDataDto) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream content = new PDPageContentStream(document, page);
            content.beginText();

            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.setLeading(14.5f);
            content.newLineAtOffset(25, 700);

            content.showText("Данным сертификатом подтверждаю ");
            content.newLine();

            switch (pdfDataDto.getType()) {
                case "sell":
                    content.showText("продажу\nПродавец: ");
                    break;
                case "buy":
                    content.showText("покупку\nПокупатель: ");
                    break;
                case "rent":
                    content.showText("аренду\nАрендатор: ");
                    break;
            }
            content.showText(pdfDataDto.getFirstname() + " " + pdfDataDto.getLastname());

            content.endText();
            content.close();
            document.save(byteArrayOutputStream);
            document.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new IllegalStateException("Error create pdf");
        }
    }*/

    /*private final static String PDF_FOLDER = "/Users/shevanton/pdf/";

    @Override
    public String generatePdf(PdfDataDto pdfDataDto) {
        String filename = PDF_FOLDER + pdfDataDto.getType() + "_" + pdfDataDto.getFirstname()
                + "_" + pdfDataDto.getLastname() + "_" + UUID.randomUUID() + ".pdf";
        try {
            OutputStream fos = new FileOutputStream(filename);
            Document document = new Document();
            PdfWriter.getInstance(document, fos);
            document.open();
            BaseFont bf = BaseFont.createFont("src/main/resources/fonts/times-ro.ttf", BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED);
            Font font = new Font(bf, 20, Font.NORMAL);
            Paragraph paragraph = new Paragraph("Данным сертификатом подтверждаю ", font);

            switch (pdfDataDto.getType()) {
                case "sell":
                    paragraph.add("продажу\nПродавец: ");
                    break;
                case "buy":
                    paragraph.add("покупку\nПокупатель: ");
                    break;
                case "rent":
                    paragraph.add("аренду\nАрендатор: ");
                    break;
            }

            paragraph.add(pdfDataDto.getFirstname() + " " + pdfDataDto.getLastname());
            document.add(paragraph);
            document.close();
            fos.close();
            return "Yor pdf in " + filename;
        } catch (DocumentException | IOException e) {
            throw new IllegalStateException(e);
        }
    }*/
}


