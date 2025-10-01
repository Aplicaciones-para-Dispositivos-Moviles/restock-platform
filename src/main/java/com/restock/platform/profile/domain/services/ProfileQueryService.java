package com.restock.platform.profile.domain.services;

import com.restock.platform.profile.domain.model.entities.Profile;
import com.restock.platform.profile.domain.model.queries.GetProfileByUserIdQuery;

import java.util.Optional;

public interface ProfileQueryService {
    Optional<Profile> handle(GetProfileByUserIdQuery query);
}
