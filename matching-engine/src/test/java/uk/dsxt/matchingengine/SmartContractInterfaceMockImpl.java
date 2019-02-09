package uk.dsxt.matchingengine;

import uk.dsxt.matchingengine.datamodel.Trade;

import java.util.ArrayList;

public class SmartContractInterfaceMockImpl implements SmartContractInterface {
    public ArrayList<Trade> trades = new ArrayList<>();

    @Override
    public void dealCreated(long number, String currencyPair, long amount, long price, long buyerAmount, long buyerClientOrderId, String buyerAddress, String buyerSign, long sellerAmount, long sellerClientOrderId, String sellerAddress, String sellerSign, String exchangeSing) {
        Trade trade = new Trade(number, amount, price);
        trades.add(trade);
    }

    @Override
    public boolean lockMoney(long amount, String address, String signature) {
        return false;
    }
}
