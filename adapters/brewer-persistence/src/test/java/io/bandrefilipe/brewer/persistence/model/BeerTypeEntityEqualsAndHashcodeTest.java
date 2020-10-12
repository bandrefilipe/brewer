package io.bandrefilipe.brewer.persistence.model;

class BeerTypeEntityEqualsAndHashcodeTest extends AbstractEqualsAndHashcodeTest<BeerTypeEntity> {

    @Override
    BeerTypeEntity newObject() {
        final var entity = new BeerTypeEntity();
        entity.setName("TEST_NAME");
        return entity;
    }
}
