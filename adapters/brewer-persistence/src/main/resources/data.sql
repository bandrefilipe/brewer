BEGIN TRANSACTION;
    INSERT INTO beer_type (type) VALUES
        ('Amber Lager'),
        ('Dark Lager'),
        ('Pale Lager'),
        ('Pilsner'),
        ('Wheat')
    ;

    INSERT INTO beer (sku, name, unit_price, alcohol_content, commission, origin, flavor, beer_type_id) VALUES
        ('AA0001', 'Hoegaarden', '7.99', '4.9', '20.0', 'I', 'S', 5)
    ;
COMMIT;
