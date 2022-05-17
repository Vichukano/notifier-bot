package ru.vichukano.notifier.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vichukano.notifier.bot.dto.NotificationRequest;
import ru.vichukano.notifier.bot.telegram.BotService;

@Slf4j
@RestController
public class NotificationController {
    private final BotService<NotificationRequest> service;

    public NotificationController(BotService<NotificationRequest> service) {
        this.service = service;
    }

    @PostMapping(value = "/api/v1/notificate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> notificate(@RequestBody NotificationRequest request) {
        log.info("Receive request with uuid: {}", request.uuid());
        log.debug("Receive request: {}", request);
        try {
            service.process(request);
        } catch (Exception e) {
            log.error("Failed to process request with uuid: {}, cause: ", request.uuid(), e);
        }
        log.info("Finish processing for request with uuid: {}", request.uuid());
        return ResponseEntity.ok().build();
    }
}