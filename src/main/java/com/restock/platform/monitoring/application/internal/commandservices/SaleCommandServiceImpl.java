package com.restock.platform.monitoring.application.internal.commandservices;

import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.commands.CreateSaleCommand;
import com.restock.platform.monitoring.domain.model.commands.DeleteSaleCommand;
import com.restock.platform.monitoring.domain.services.SaleCommandService;
import com.restock.platform.monitoring.infrastructure.persistence.mongodb.repositories.SaleRepository;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleCommandServiceImpl implements SaleCommandService {

    private final SaleRepository saleRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    public SaleCommandServiceImpl(
            SaleRepository saleRepository,
            SequenceGeneratorService sequenceGeneratorService) {
        this.saleRepository = saleRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    @Transactional
    public Long handle(CreateSaleCommand command) {
        // Generar el siguiente número de secuencia para la venta
        Long sequence = sequenceGeneratorService.generateSequence("sales_sequence");

        // Crear la venta con el número de secuencia
        Sale sale = new Sale(command, sequence);
        sale.setId(sequence);

        try {
            saleRepository.save(sale);
            return sale.getId();
        } catch (Exception e) {
            throw new RuntimeException("Error creating sale: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void handle(DeleteSaleCommand command) {
        Sale sale = saleRepository.findById(command.saleId())
            .orElseThrow(() -> new IllegalArgumentException("Sale not found with id: " + command.saleId()));

        // Cancelar la venta en lugar de eliminarla físicamente
        sale.cancel();

        try {
            saleRepository.save(sale);
        } catch (Exception e) {
            throw new RuntimeException("Error cancelling sale: " + e.getMessage(), e);
        }
    }
}
