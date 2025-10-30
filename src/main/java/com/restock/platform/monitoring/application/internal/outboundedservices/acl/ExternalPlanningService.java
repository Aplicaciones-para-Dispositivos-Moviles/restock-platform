package com.restock.platform.monitoring.application.internal.outboundedservices.acl;

import com.restock.platform.planning.domain.model.valueobjects.RecipeId;
import com.restock.platform.planning.interfaces.acl.PlanningContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalPlanningService {
    private final PlanningContextFacade planningContextFacade;

    public ExternalPlanningService(PlanningContextFacade planningContextFacade) {
        this.planningContextFacade = planningContextFacade;
    }

    public Optional<RecipeId> fetchRecipeByRecipeId (Long recipeId){
        var fetchedRecipeId = planningContextFacade.fetchRecipeByRecipeId(recipeId);
        return fetchedRecipeId == 0L ? Optional.empty() : Optional.of(new RecipeId(fetchedRecipeId));
    }

    public Double fetchRecipePriceByRecipeId (Long recipeId){
        return planningContextFacade.fetchRecipePriceByRecipeId(recipeId);
    }

}
