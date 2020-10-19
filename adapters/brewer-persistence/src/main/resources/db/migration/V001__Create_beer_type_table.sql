DROP TABLE IF EXISTS beer_type CASCADE;

CREATE TABLE beer_type (
    beer_type IDENTITY PRIMARY KEY,
    type VARCHAR NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP WITH TIME ZONE ON UPDATE CURRENT_TIMESTAMP
);