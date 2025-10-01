package com.restock.platform.profile.domain.model.commands;

public record UpdatePasswordCommand(Long userId, String currentPassword, String newPassword) {
}
