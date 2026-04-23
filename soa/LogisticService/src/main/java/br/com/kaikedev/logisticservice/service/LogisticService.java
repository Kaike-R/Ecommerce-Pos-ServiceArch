package br.com.kaikedev.logisticservice.service;

import br.com.kaikedev.logisticservice.Dto.LogisticRequest;
import br.com.kaikedev.logisticservice.Dto.LogisticResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogisticService {

    private static final Logger log = LoggerFactory.getLogger(LogisticService.class);

    public LogisticResponse processDeliveryRequest(LogisticRequest logisticRequest) {

        String cep = logisticRequest.getZip();

        log.info(logisticRequest.toString());

        // Validações básicas
        if (cep == null || cep.length() != 8 || !cep.matches("^\\d{8}$")) {
            return new LogisticResponse("CEP inválido", "error", false, null);
        }

        // Simulação de regras de entrega
        boolean canDeliver = checkDeliveryAvailability(cep);
        String transactionId = "DLV" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        if (!canDeliver) {
            return new LogisticResponse("Entrega não disponível para esta região", "error", false, null);
        }

        return new LogisticResponse("Entrega disponível com custo de frete", "success", true, transactionId);
    }

    public LogisticResponse trackDelivery(String transactionId) {
        // Validação básica
        if (transactionId == null || !transactionId.startsWith("DLV")) {
            return new LogisticResponse("Código de entrega inválido", "error", false, null);
        }

        // Simulação de status de entrega (50% chance de estar entregue)
        boolean isDelivered = Math.random() > 0.5;
        String statusMessage = isDelivered
                ? "Pedido entregue com sucesso"
                : "Pedido em trânsito";

        return new LogisticResponse(statusMessage, "success", isDelivered, transactionId);
    }

    private boolean checkDeliveryAvailability(String cep) {
        // Simulação - não entrega em CEPs que começam com 9 (exemplo)
        return !cep.startsWith("9");
    }
}