package br.com.kaikedev.logisticservice.controller;

import br.com.kaikedev.logisticservice.Dto.LogisticRequest;
import br.com.kaikedev.logisticservice.Dto.LogisticResponse;
import br.com.kaikedev.logisticservice.service.LogisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LogisticController {

    private static final Logger log = LoggerFactory.getLogger(LogisticController.class);
    @Autowired
    private LogisticService logisticService;

    @PostMapping
    public ResponseEntity<?> processShipping(@RequestBody LogisticRequest logisticRequest)
    {
        try {
            LogisticResponse x = logisticService.processDeliveryRequest(logisticRequest);
            log.info(x.toString());
            return ResponseEntity.ok(x);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
