package br.com.kaikedev.logisticservice.controller;

import br.com.kaikedev.logisticservice.Dto.LogisticRequest;
import br.com.kaikedev.logisticservice.Dto.LogisticResponse;
import br.com.kaikedev.logisticservice.service.LogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LogisticController {

    @Autowired
    private LogisticService logisticService;

    @PostMapping
    public ResponseEntity<?> processShipping(LogisticRequest logisticRequest)
    {

        try {
            LogisticResponse x = logisticService.processDeliveryRequest(logisticRequest);
            return ResponseEntity.ok(x);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
