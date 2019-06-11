drop function if exists toDate;

CREATE FUNCTION toDate (str VARCHAR(50))
        RETURNS DATE
        RETURN STR_TO_DATE(str, '%d-%b-%y');