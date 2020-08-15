package io.bandrefilipe.brewer.web.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public final class BeerRegistrationModel {

    private static final int MAX_DESCRIPTION_LENGTH = 140;

    @NotBlank(message = "sku is required")
    private String sku;

    @NotBlank(message = "name is required")
    private String name;

    @Size(
        max = MAX_DESCRIPTION_LENGTH,
        message = "description must have up to " + MAX_DESCRIPTION_LENGTH + " characters"
    )
    private String description;

    @Setter(AccessLevel.PRIVATE)
    private int descriptionMaxLength = MAX_DESCRIPTION_LENGTH;
}
