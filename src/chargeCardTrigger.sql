drop trigger if exists chargeCard;
drop trigger if exists checkCharge;

CREATE TRIGGER chargeCard
AFTER INSERT ON Reservations
FOR EACH ROW
   UPDATE CreditCard
      SET currBalance = (DATEDIFF(toDate(NEW.CheckOut), toDate(NEW.CheckIn))
                * NEW.rate) + currBalance
      WHERE (SELECT username FROM Customers WHERE CreditCard.custId = username);
      
DELIMITER $    
CREATE TRIGGER checkCharge
BEFORE UPDATE ON CreditCard
FOR EACH ROW
BEGIN
   IF NEW.balance >= NEW.creditLim THEN
      SIGNAL SQLSTATE '12345'
      SET MESSAGE_TEXT = 'check constraint on creditLimit failed';
   END IF;
END$
DELIMITER ;
