package com.restock.platform.monitoring.application.internal.commandservices;

import com.restock.platform.monitoring.application.internal.outboundedservices.acl.ExternalPlanningService;
import com.restock.platform.monitoring.domain.model.aggregate.Sale;
import com.restock.platform.monitoring.domain.model.commands.AddSaleItemToSaleCommand;
import com.restock.platform.monitoring.domain.model.commands.CreateSaleCommand;
import com.restock.platform.monitoring.domain.model.valueobjects.SaleItemId;
import com.restock.platform.monitoring.domain.services.SaleCommandService;
import com.restock.platform.monitoring.infrastructure.persistence.mongodb.repositories.SaleRepository;
import com.restock.platform.shared.infrastructure.persistence.mongodb.SequenceGeneratorService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SaleCommandServiceImpl implements SaleCommandService {

    // Add validations and error handling
    private final SaleRepository saleRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final ExternalPlanningService externalPlanningService;

    public SaleCommandServiceImpl(
            SaleRepository saleRepository,
            SequenceGeneratorService sequenceGeneratorService,
            ExternalPlanningService externalPlanningService) {
        this.saleRepository = saleRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.externalPlanningService = externalPlanningService;
    }

    @Override
    public Long handle(CreateSaleCommand command) {
        var sale = new Sale(command);
        var id = sequenceGeneratorService.generateSequence("sales_sequence");
        sale.setId(id);
        try {
            saleRepository.save(sale);
        }catch (Exception e) {
            throw new RuntimeException("Error saving sale: " + e.getMessage(), e);
        }
        return sale.getId();
    }

    @Override
    public Optional<Sale> handle(AddSaleItemToSaleCommand command) {
        var sale = saleRepository.findById(command.saleId())
                .orElseThrow(() -> new IllegalArgumentException("Sale not found with id: " + command.saleId()));

        double unitPrice = externalPlanningService.fetchRecipePriceByRecipeId(command.recipeId()); // ✅
        double subTotalPrice = unitPrice * command.quantity();

        sale.addItem(command.saleId(), command.recipeId(), command.quantity(), subTotalPrice);

        saleRepository.save(sale);
        return Optional.of(sale);
    }
}
