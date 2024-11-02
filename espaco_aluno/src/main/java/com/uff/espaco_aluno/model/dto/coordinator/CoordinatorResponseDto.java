package com.uff.espaco_aluno.model.dto.coordinator;

import com.uff.espaco_aluno.model.entity.Coordinator;

import java.util.UUID;

public record CoordinatorResponseDto(UUID id, String name, String email, String schoolName) {

    public static CoordinatorResponseDto mapToCoordinatorResponseDTO(Coordinator coordinator) {
        return new CoordinatorResponseDto(
                coordinator.getId(),
                coordinator.getName(),
                coordinator.getEmail(),
                coordinator.getSchool().getName()
        );
    }
}
