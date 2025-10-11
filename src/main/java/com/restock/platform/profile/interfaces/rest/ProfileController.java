package com.restock.platform.profile.interfaces.rest;

import com.restock.platform.profile.domain.model.commands.DeleteProfileCommand;
import com.restock.platform.profile.domain.model.commands.UpdateBusinessInformationCommand;
import com.restock.platform.profile.domain.model.commands.UpdatePasswordCommand;
import com.restock.platform.profile.domain.model.commands.UpdatePersonalInformationCommand;
import com.restock.platform.profile.domain.model.queries.GetProfileByUserIdQuery;
import com.restock.platform.profile.domain.services.ProfileCommandService;
import com.restock.platform.profile.domain.services.ProfileQueryService;
import com.restock.platform.profile.interfaces.rest.resources.ProfileResource;
import com.restock.platform.profile.interfaces.rest.resources.UpdateBusinessInformationResource;
import com.restock.platform.profile.interfaces.rest.resources.UpdatePasswordResource;
import com.restock.platform.profile.interfaces.rest.resources.UpdatePersonalInformationResource;
import com.restock.platform.profile.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/profiles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Endpoints for managing user profiles")
public class ProfileController {

    private final ProfileQueryService profileQueryService;
    private final ProfileCommandService profileCommandService;

    public ProfileController(ProfileQueryService profileQueryService, ProfileCommandService profileCommandService) {
        this.profileQueryService = profileQueryService;
        this.profileCommandService = profileCommandService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get profile by user id")
    public ResponseEntity<ProfileResource> getProfileByUserId(@PathVariable Long userId) {
        var result = profileQueryService.handle(new GetProfileByUserIdQuery(userId));
        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var resource = ProfileResourceFromEntityAssembler.toResourceFromEntity(userId, result.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping(value = "/{userId}/personal", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update personal profile information")
    public ResponseEntity<ProfileResource> updatePersonalInformation(@PathVariable Long userId,
                                                                     @RequestBody UpdatePersonalInformationResource resource) {
        var command = new UpdatePersonalInformationCommand(userId,
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.phone(),
                resource.address(),
                resource.country(),
                resource.avatar());

        var result = profileCommandService.handle(command);
        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var response = ProfileResourceFromEntityAssembler.toResourceFromEntity(userId, result.get());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{userId}/business", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update business profile information")
    public ResponseEntity<ProfileResource> updateBusinessInformation(@PathVariable Long userId,
                                                                     @RequestBody UpdateBusinessInformationResource resource) {
        var command = new UpdateBusinessInformationCommand(userId,
                resource.businessName(),
                resource.businessAddress(),
                resource.description(),
                resource.businessCategoryIds());

        var result = profileCommandService.handle(command);
        if (result.isEmpty()) return ResponseEntity.notFound().build();

        var response = ProfileResourceFromEntityAssembler.toResourceFromEntity(userId, result.get());
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{userId}/password", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update user password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long userId,
                                               @RequestBody UpdatePasswordResource resource) {
        var command = new UpdatePasswordCommand(userId, resource.currentPassword(), resource.newPassword());
        var updated = profileCommandService.handle(command);
        if (!updated) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete profile information")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long userId) {
        var deleted = profileCommandService.handle(new DeleteProfileCommand(userId));
        if (!deleted) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }
}
