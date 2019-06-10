drop function if exists getMonthRev;

CREATE FUNCTION getMonthRev (monthNum INT, RoomCodeIn VARCHAR(5), year INT)
        RETURNS INT
        RETURN    
            (SELECT SUM(rev)
            FROM 
                (SELECT MONTHName(toDate(CheckIn)) as month,
                        MONTH(toDate(CheckIn)) as monthN,
                    (DATEDIFF(toDate(CheckOut), toDate(CheckIn))
                        * Rate) as rev
                from Reservations, Rooms
                where Room = RoomId and Room = RoomCodeIn
					and year = YEAR(toDate(CheckIn))
                and MONTH(toDate(CheckIn)) = monthNum) as tX
            GROUP BY month, monthN);

drop function if exists getTotalRev;

CREATE FUNCTION getTotalRev (monthNum INT, year INT)
        RETURNS INT
        RETURN    
            (SELECT SUM(rev)
            FROM 
                (SELECT MONTHName(toDate(CheckIn)) as month,
                        MONTH(toDate(CheckIn)) as monthN,
                    (DATEDIFF(toDate(CheckOut), toDate(CheckIn))
                        * Rate) as rev
                from Reservations, Rooms
                where Room = RoomId and year = YEAR(toDate(CheckIn))
                and MONTH(toDate(CheckIn)) = monthNum) as tX
            GROUP BY month, monthN);