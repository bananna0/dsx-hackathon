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
    
    function deposit(string memory currency, uint amount, address userAddress) public {
        addressToWallet[userAddress][currency] += amount;
    }
    
    function createOrder(address userAddress, uint amount, uint rate, string memory orderId, string memory signature, string memory baseCur, string memory quotedCur, bool isBuy) public {
        currencyPair memory pair = currencyPair({
            baseCur: baseCur,
            quotedCur: quotedCur
        });
        if (isBuy) {
           uint currentAmount = addressToWallet[userAddress][pair.quotedCur];
           require(amount * rate <= currentAmount);
           addressToWallet[userAddress][pair.quotedCur] -= amount * rate;
        } else {
           uint currentAmount = addressToWallet[userAddress][pair.baseCur];
           require(amount <= currentAmount);
           addressToWallet[userAddress][pair.baseCur] -= amount;
        }
        
        order memory newOrder = order({amount: amount, rate: rate, pair: pair, isBuy: isBuy});
        orderList[userAddress][orderId] = newOrder;
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
    
    
    function getState() public view returns(order memory orderA, order memory orderB, uint balanceA1, uint balanceA2, uint balanceB1, uint balanceB2) {
        // orderA = orderList[0xCA35b7d915458EF540aDe6068dFe2F44E8fa733c];
        // orderB = orderList[0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C];
        balanceA1 = addressToWallet[0xCA35b7d915458EF540aDe6068dFe2F44E8fa733c]["USD"];
        balanceA2 = addressToWallet[0xCA35b7d915458EF540aDe6068dFe2F44E8fa733c]["EUR"];
        balanceB1 = addressToWallet[0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C]["USD"];
        balanceB2 = addressToWallet[0x14723A09ACff6D2A60DcdF7aA4AFf308FDDC160C]["EUR"];
    }
}
