package com.restock.platform.profile.domain.model.commands;

import java.util.List;

public record UpdateBusinessInformationCommand(Long userId,
                                                String businessName,
                                                String businessAddress,
                                                String description,
                                                List<String> businessCategoryIds) {
}
