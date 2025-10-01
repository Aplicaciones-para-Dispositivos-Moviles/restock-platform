package com.restock.platform.profile.application.internal.queryservices;

import com.restock.platform.iam.infrastructure.persistence.mongodb.repositories.UserRepository;
import com.restock.platform.profile.domain.model.entities.Profile;
import com.restock.platform.profile.domain.model.queries.GetProfileByUserIdQuery;
import com.restock.platform.profile.domain.services.ProfileQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileQueryServiceImpl implements ProfileQueryService {

    private final UserRepository userRepository;

    public ProfileQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Profile> handle(GetProfileByUserIdQuery query) {
        return userRepository.findById(query.userId()).map(user -> {
            var profile = user.getProfile();
            if (profile == null) {
                profile = new Profile();
                user.setProfile(profile);
                userRepository.save(user);
            }
            return profile;
        });
    }
}
