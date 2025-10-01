package com.restock.platform.profile.domain.services;

import com.restock.platform.profile.domain.model.commands.DeleteProfileCommand;
import com.restock.platform.profile.domain.model.commands.UpdateBusinessInformationCommand;
import com.restock.platform.profile.domain.model.commands.UpdatePasswordCommand;
import com.restock.platform.profile.domain.model.commands.UpdatePersonalInformationCommand;
import com.restock.platform.profile.domain.model.entities.Profile;

import java.util.Optional;

public interface ProfileCommandService {
    Optional<Profile> handle(UpdatePersonalInformationCommand command);
    Optional<Profile> handle(UpdateBusinessInformationCommand command);
    boolean handle(UpdatePasswordCommand command);
    boolean handle(DeleteProfileCommand command);
}
