package ru.itis.pdfserver.rabbitmq.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.itis.pdfserver.configuration.RabbitMQConfiguration;
import ru.itis.pdfserver.dto.PdfDataDto;
import ru.itis.pdfserver.dto.PdfResponseDto;
import ru.itis.pdfserver.service.PdfService;

@Component
@RequiredArgsConstructor
public class PdfConsumer {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final PdfService pdfService;

    @RabbitListener(queues = RabbitMQConfiguration.SELL_QUEUE_NAME)
    public String receiveSellMessage(PdfDataDto pdfDataDto) throws Exception {
        PdfResponseDto responsePdf = PdfResponseDto.builder()
                .data(pdfService.generatePdf(pdfDataDto))
                .build();

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.REPLY_QUEUE_NAME,
                responsePdf);

        return objectMapper.writeValueAsString(responsePdf);
    }

    @RabbitListener(queues = RabbitMQConfiguration.BUY_QUEUE_NAME)
    public String receiveBuyMessage(PdfDataDto pdfDataDto) throws Exception {
        PdfResponseDto responsePdf = PdfResponseDto.builder()
                .data(pdfService.generatePdf(pdfDataDto))
                .build();

        rabbitTemplate.convertAndSend(RabbitMQConfiguration.REPLY_QUEUE_NAME,
                responsePdf);

        return objectMapper.writeValueAsString(responsePdf);
    }
}
