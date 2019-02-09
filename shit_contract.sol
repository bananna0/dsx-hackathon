pragma solidity ^0.5.1;
pragma experimental ABIEncoderV2;

contract StandardToken {
    
    struct currencyPair {
        string baseCur;
        string quotedCur;
    }
    
    struct order {
        uint amount;
        uint rate;
        currencyPair pair;
        bool isBuy;
    }
    mapping (address => mapping(string => uint)) addressToWallet;
    mapping (address => mapping(string => order)) orderList;
    
    function deposit(string memory currency, uint amount) public {
        addressToWallet[msg.sender][currency] += amount;
    }
    
    function createOrder(uint amount, uint rate, string memory orderId, string memory signature, string memory baseCur, string memory quotedCur, bool isBuy) public {
        currencyPair memory pair = currencyPair({
            baseCur: baseCur,
            quotedCur: quotedCur
        });
        if (isBuy) {
           uint currentAmount = addressToWallet[msg.sender][pair.quotedCur];
           require(amount * rate <= currentAmount);
           addressToWallet[msg.sender][pair.quotedCur] -= amount * rate;
        } else {
           uint currentAmount = addressToWallet[msg.sender][pair.baseCur];
           require(amount <= currentAmount);
           addressToWallet[msg.sender][pair.baseCur] -= amount;
        }
        
        order memory newOrder = order({amount: amount, rate: rate, pair: pair, isBuy: isBuy});
        orderList[msg.sender][orderId] = newOrder;
    }
    
    function deal(string memory orderIdA, string memory orderIdB, address addressA, address addressB, uint rate, uint amount) public {
        order memory orderA = orderList[addressA][orderIdA];
        order memory orderB = orderList[addressB][orderIdB];
        
        require(orderA.rate == rate || orderB.rate == rate);
        require(orderA.amount <= amount && orderB.amount <= amount);
        
        orderA.amount -= amount;
        orderB.amount -= amount;
        
        if (orderA.isBuy) {
            addressToWallet[addressA][orderA.pair.baseCur] += amount;
        } else {
            addressToWallet[addressB][orderB.pair.quotedCur] += amount * rate;
        }
        
        if (orderA.amount == 0) {
            delete orderList[addressA][orderIdA];
        }
        
        if (orderB.amount == 0) {
            delete orderList[addressB][orderIdB];
        }
    }
}
