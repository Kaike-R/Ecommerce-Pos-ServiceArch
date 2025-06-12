package br.com.kaikedev.orderservice.Entity.Enum;

public enum OrderEnum {
    CREATED,
    PAID,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    PAYMENT_FAILED,
    REFUNDED,
    PROCESSING_FAILED;


    // Método para verificar transições válidas
    public boolean canTransitionTo(OrderEnum newStatus) {
        switch (this) {
            case CREATED:
                return newStatus == PAID || newStatus == CANCELLED || newStatus == PAYMENT_FAILED;
            case PAID:
                return newStatus == PROCESSING || newStatus == CANCELLED || newStatus == REFUNDED;
            case PROCESSING:
                return newStatus == SHIPPED || newStatus == CANCELLED || newStatus == PROCESSING_FAILED;
            case PROCESSING_FAILED:
                return newStatus == REFUNDED || newStatus == PROCESSING_FAILED || newStatus == PROCESSING || newStatus == SHIPPED;
            case SHIPPED:
                return newStatus == DELIVERED;
            case DELIVERED:
            case CANCELLED:
            case PAYMENT_FAILED:
                return newStatus == PAID || newStatus == CANCELLED;
            case REFUNDED:
                return false; // Status finais não podem mudar
            default:
                throw new IllegalStateException("Status desconhecido: " + this);
        }
    }
}
