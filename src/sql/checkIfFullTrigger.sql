drop trigger if exists checkIfFull;

DELIMITER $    
CREATE TRIGGER checkIfFull
BEFORE INSERT ON Reservations
FOR EACH ROW
BEGIN
   IF (EXISTS (select * from Reservations
				where Reservations.room = NEW.room
					and ((toDate(NEW.CheckOut) <= toDate(Reservations.CheckOut)
						and toDate(NEW.CheckOut) > toDate(Reservations.CheckIn))
						or (toDate(NEW.CheckIn) < toDate(Reservations.CheckOut)
							and toDate(NEW.CheckIn) >= toDate(Reservations.CheckIn))))) THEN
      SIGNAL SQLSTATE '12345'
      SET MESSAGE_TEXT = 'check constraint on unbooked room failed';
   END IF;
END$
DELIMITER ;