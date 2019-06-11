drop trigger if exists chargeCard;
drop trigger if exists checkCharge;
drop trigger if exists checkActive;
drop trigger if exists checkCardOwner;

CREATE TRIGGER chargeCard
AFTER INSERT ON Reservations
FOR EACH ROW
   UPDATE CreditCard
      SET CreditCard.balance = (DATEDIFF(toDate(NEW.CheckOut), toDate(NEW.CheckIn))
                * NEW.rate) + CreditCard.balance
WHERE EXISTS (SELECT * FROM Payment, Customers 
			   WHERE Payment.cardNum = CreditCard.cardNum
			   and Payment.cID = Customers.username
			   and NEW.code = Payment.resID);
      
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

DELIMITER $    
CREATE TRIGGER checkActive
BEFORE UPDATE ON CreditCard
FOR EACH ROW
BEGIN
   IF !NEW.active THEN
      SIGNAL SQLSTATE '12345'
      SET MESSAGE_TEXT = 'check constraint on active failed';
   END IF;
END$
DELIMITER ;

DELIMITER $    
CREATE TRIGGER checkCardOwner
AFTER INSERT ON Reservations
FOR EACH ROW
BEGIN
   IF !(EXISTS (SELECT * FROM Payment, Customers 
			   WHERE Payment.cardNum = CreditCard.cardNum
			   and Payment.cID = Customers.username
			   and NEW.code = Payment.resID
               and CreditCard.customerID = Customers.username)) THEN
      SIGNAL SQLSTATE '12345'
      SET MESSAGE_TEXT = 'check constraint on card ownership failed';
   END IF;
END$
DELIMITER ;
