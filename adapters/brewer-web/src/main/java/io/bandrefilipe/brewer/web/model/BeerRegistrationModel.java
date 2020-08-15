package io.bandrefilipe.brewer.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public final class BeerRegistrationModel {

    @NotBlank
    private String sku;

    private String name;

    @Size(max = 140)
    private String description;
}
