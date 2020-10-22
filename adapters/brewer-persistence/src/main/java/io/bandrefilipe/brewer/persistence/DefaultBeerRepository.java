package io.bandrefilipe.brewer.persistence;

import io.bandrefilipe.brewer.application.core.domain.entities.Beer;
import io.bandrefilipe.brewer.application.core.domain.vo.Id;
import io.bandrefilipe.brewer.application.core.domain.vo.SKU;
import io.bandrefilipe.brewer.application.ports.out.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
class DefaultBeerRepository implements BeerRepository {

    private DefaultBeerRepository() {
        log.debug("Creating component for class {}", DefaultBeerRepository.class);
    }

    @Override
    public Optional<Beer> retrieveBeer(final Id id) {
        log.debug("input: {}", id);
        return Optional.empty();
    }

    @Override
    public Optional<Beer> retrieveBeer(final SKU sku) {
        log.debug("input: {}", sku);
        return Optional.empty();
    }
}
