use sec05group06;
drop trigger if exists checkIfFull;
drop trigger if exists checkCapacity;
drop trigger if exists checkIfFullUpdate;

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
      SIGNAL SQLSTATE '11110'
      SET MESSAGE_TEXT = 'That room is unavailable. Please try again';
   END IF;
END$
DELIMITER ;

DELIMITER $    
CREATE TRIGGER checkIfFullUpdate
BEFORE UPDATE ON Reservations
FOR EACH ROW
BEGIN
   IF (EXISTS (select * from Reservations
				where Reservations.room = NEW.room and OLD.code != Reservations.code
					and ((toDate(NEW.CheckOut) <= toDate(Reservations.CheckOut)
						and toDate(NEW.CheckOut) > toDate(Reservations.CheckIn))
						or (toDate(NEW.CheckIn) < toDate(Reservations.CheckOut)
							and toDate(NEW.CheckIn) >= toDate(Reservations.CheckIn))))) THEN
      SIGNAL SQLSTATE '11111'
      SET MESSAGE_TEXT = 'That room is unavailable. Please try again';
   END IF;
END$
DELIMITER ;

DELIMITER $    
CREATE TRIGGER checkCapacity
BEFORE INSERT ON Reservations
FOR EACH ROW
BEGIN 
   IF NEW.adults + NEW.kids > (SELECT maxOccupancy from Rooms where NEW.room = Rooms.RoomId) THEN
      SIGNAL SQLSTATE '11112'
      SET MESSAGE_TEXT = 'Max occupancy exceeded! Please try again';
   END IF;
END$
DELIMITER ;