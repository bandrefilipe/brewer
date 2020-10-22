package io.bandrefilipe.brewer.application.core.domain.entities;

import io.bandrefilipe.brewer.application.core.domain.vo.Id;
import io.bandrefilipe.brewer.application.core.domain.vo.SKU;
import io.bandrefilipe.brewer.commons.identifier.Identifiable;
import lombok.Getter;

@Getter
public final class Beer implements Identifiable<Id> {

    private Id id;
    private SKU sku;
}
