package io.bandrefilipe.brewer.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
public final class BeerResponse {

    private Long id;
    private String sku;
}
