package com.restock.platform.monitoring.domain.model.valueobjects;
import lombok.Getter;
@Getter
public class SaleNumber {
    private final String value;
    public SaleNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Sale number cannot be null or empty");
        }
        this.value = value;
    }
    public SaleNumber() {
        this.value = null;
    }
    public static SaleNumber generateFromSequence(Long sequence) {
        return new SaleNumber(String.format("SALE-%04d", sequence));
    }
    @Override
    public String toString() {
        return value;
    }
}
