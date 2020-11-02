BEGIN TRANSACTION;
    INSERT INTO beer_type (type) VALUES
        ('IPA'),
        ('Lager'),
        ('Pale Ale'),
        ('Pilsner'),
        ('Porter'),
        ('Stout'),
        ('Wheat')
    ;

    INSERT INTO beer (sku, name, unit_price, alcohol_content, commission, origin, flavor, beer_type_id) VALUES
        ('TEST001', 'Goose Island', '9.99', '5.9', '20.0', 'I', 'B', 1)
    ;
COMMIT;
