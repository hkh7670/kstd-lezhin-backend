package com.example.springbootpractice.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateProductRequest(
    @NotBlank
    String name,

    @NotBlank
    String code,

    @NotNull
    LocalDate releaseDate,

    @NotNull
    @Min(1)
    @Max(1_000_000_000)
    Integer price,

    @NotNull
    Long manufacturerSeq
) {


}
