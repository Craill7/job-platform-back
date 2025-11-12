-- V2__make_company_name_nullable.sql
ALTER TABLE companies
    MODIFY company_name VARCHAR(255) NULL;
