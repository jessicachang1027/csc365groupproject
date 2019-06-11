use sec05group06;
drop trigger if exists chargeCard;
drop trigger if exists updateCharge;
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
               
CREATE TRIGGER updateCharge
AFTER update ON Reservations
FOR EACH ROW
   UPDATE CreditCard
      SET CreditCard.balance = (DATEDIFF(toDate(NEW.CheckOut), toDate(NEW.CheckIn))
                * NEW.rate) + CreditCard.balance
                - (DATEDIFF(toDate(OLD.CheckOut), toDate(OLD.CheckIn)) * OLD.rate)
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
      SIGNAL SQLSTATE '11113'
      SET MESSAGE_TEXT = 'Your credit limit was exceeded. Please try again';
   END IF;
END$
DELIMITER ;

DELIMITER $    
CREATE TRIGGER checkActive
BEFORE UPDATE ON CreditCard
FOR EACH ROW
BEGIN
   IF !NEW.active THEN
      SIGNAL SQLSTATE '11114'
      SET MESSAGE_TEXT = 'Your card is not activated. Please try again';
   END IF;
END$
DELIMITER ;

DELIMITER $    
CREATE TRIGGER checkCardOwner
BEFORE INSERT ON Reservations
FOR EACH ROW
BEGIN
   IF !(EXISTS (SELECT * FROM Payment, Customers, CreditCard
			   WHERE Payment.cardNum = CreditCard.cardNum
			   and Payment.cID = Customers.username
			   and NEW.code = Payment.resID
               and CreditCard.customerID = Customers.username)) THEN
      SIGNAL SQLSTATE '11115'
      SET MESSAGE_TEXT = 'That card is not under your name. Please try again';
   END IF;
END$
DELIMITER ;
