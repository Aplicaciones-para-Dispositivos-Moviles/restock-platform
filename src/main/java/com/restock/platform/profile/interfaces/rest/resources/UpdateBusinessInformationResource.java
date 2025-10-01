package com.restock.platform.profile.interfaces.rest.resources;

import java.util.List;

public record UpdateBusinessInformationResource(String businessName,
                                                String businessAddress,
                                                String description,
                                                List<String> businessCategoryIds) {
}
