DROP TABLE IF EXISTS beer_type;

CREATE TABLE beer_type (
    beer_type IDENTITY PRIMARY KEY,
    type VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP WITH TIME ZONE ON UPDATE CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS beer;

CREATE TABLE beer (
    beer IDENTITY PRIMARY KEY,
    sku VARCHAR NOT NULL UNIQUE,
    name VARCHAR NOT NULL,
    description VARCHAR,
    unit_price DECIMAL(8, 2),
    alcohol_content DECIMAL(5, 2),
    commission DECIMAL(5, 2),
    stock INTEGER,
    origin CHAR(1),
    flavor CHAR(1),
    beer_type_id BIGINT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP WITH TIME ZONE ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (beer_type_id) REFERENCES beer_type(beer_type)
);

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
