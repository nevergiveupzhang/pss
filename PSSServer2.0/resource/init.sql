USE pss;
SELECT *FROM account;
SELECT *FROM accounttransferlist;
SELECT *FROM alarmorder;
SELECT *FROM category;
SELECT *FROM commodity;
SELECT *FROM customer;
SELECT *FROM giftorder;
SELECT *FROM inventory;
SELECT *FROM lossoverfloworder;
SELECT *FROM paymentitem;
SELECT *FROM paymentorder;
SELECT *FROM purchasingorder;
SELECT *FROM purchasingreturnorder;
SELECT *FROM receiptsorder;
SELECT *FROM salesorder;
SELECT *FROM salesreturnorder;
SELECT *FROM STORAGE;
SELECT *FROM STORAGEinlist;
SELECT *FROM STORAGEinreturnlist;
SELECT *FROM STORAGEoutlist;
SELECT *FROM STORAGEoutreturnlist;
SELECT *FROM USER;

-- account初始化
INSERT INTO account VALUES('account1','1000','2014-1-1');
INSERT INTO account VALUES('account2','1000','2014-1-1');
INSERT INTO account VALUES('account3','1000','2014-1-1');

-- category初始化
INSERT INTO category VALUES(1,0,'category1',0,0,'2014-1-1');
INSERT INTO category VALUES(2,0,'category2',0,0,'2014-1-1');

-- commodity初始化
INSERT INTO commodity VALUES();
INSERT INTO accounttransferlist

