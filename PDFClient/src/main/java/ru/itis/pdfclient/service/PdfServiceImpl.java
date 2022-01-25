package ru.itis.pdfclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.itis.pdfclient.configuration.RabbitMQConfiguration;
import ru.itis.pdfclient.dto.PdfDataDto;
import ru.itis.pdfclient.dto.PdfResponseDto;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public byte[] generateDocument(PdfDataDto pdfDataDto) {
        String queue;

        if (pdfDataDto.getType().equals("sell")) {
            queue = RabbitMQConfiguration.SELL_QUEUE_NAME;
        } else if (pdfDataDto.getType().equals("buy")) {
            queue = RabbitMQConfiguration.BUY_QUEUE_NAME;
        } else {
            throw new UnsupportedOperationException("unsupported class");
        }

        PdfResponseDto responseDto;
        try {
            responseDto = objectMapper.readValue((String) rabbitTemplate.convertSendAndReceive(
                    RabbitMQConfiguration.EXCHANGE_NAME, queue, pdfDataDto), PdfResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Error json read value");
        }

        if (responseDto != null) {
            return responseDto.getData();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
