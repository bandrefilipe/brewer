DROP TABLE IF EXISTS beer CASCADE;

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
