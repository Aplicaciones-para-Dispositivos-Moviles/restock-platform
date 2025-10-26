package com.restock.platform.profile.application.acl;

import com.restock.platform.profile.domain.model.queries.GetProfileByUserIdQuery;
import com.restock.platform.profile.domain.services.ProfileCommandService;
import com.restock.platform.profile.domain.services.ProfileQueryService;
import com.restock.platform.profile.interfaces.acl.ProfileContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ProfileContextFacadeImpl implements ProfileContextFacade {
    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;

    public ProfileContextFacadeImpl(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }


    /*
    @Override
    public Long fetchProfileByUserIdQuery(Long userId) {
        var getProfileByUserIdQuery = new GetProfileByUserIdQuery(userId);
        var profile = profileQueryService.handle(getProfileByUserIdQuery);
        return profile.isEmpty() ? 0L: profile.get().getDescription()
    }

     */
}
