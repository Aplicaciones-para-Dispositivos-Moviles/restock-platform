package com.restock.platform.profile.infrastructure.persistence.mongodb.seed;

import com.restock.platform.profile.domain.model.entities.BusinessCategory;
import com.restock.platform.profile.infrastructure.persistence.mongodb.repositories.BusinessCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BusinessCategorySeeder implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessCategorySeeder.class);

    private static final List<String> DEFAULT_CATEGORY_NAMES = List.of(
            "Fast Food",
            "Pizzeria",
            "Grill",
            "Vegetarian",
            "Bakery",
            "Seafood",
            "Coffee Shop",
            "Desserts",
            "Healthy",
            "Barbecue",
            "Mexican",
            "Burgers"
    );

    private final BusinessCategoryRepository businessCategoryRepository;

    public BusinessCategorySeeder(BusinessCategoryRepository businessCategoryRepository) {
        this.businessCategoryRepository = businessCategoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<BusinessCategory> existingCategories = businessCategoryRepository.findAll();

        Set<String> existingNames = existingCategories.stream()
                .map(BusinessCategory::getName)
                .filter(name -> name != null && !name.isBlank())
                .map(name -> name.toLowerCase(Locale.ROOT))
                .collect(Collectors.toCollection(HashSet::new));

        List<BusinessCategory> missingCategories = DEFAULT_CATEGORY_NAMES.stream()
                .filter(name -> !existingNames.contains(name.toLowerCase(Locale.ROOT)))
                .map(BusinessCategory::new)
                .toList();

        if (!missingCategories.isEmpty()) {
            businessCategoryRepository.saveAll(missingCategories);
            LOGGER.info("Seeded {} business categories", missingCategories.size());
        }
    }
}
