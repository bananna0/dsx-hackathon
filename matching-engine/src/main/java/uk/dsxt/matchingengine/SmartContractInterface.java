package uk.dsxt.matchingengine;

public interface SmartContractInterface {

    void dealCreated(long number, String currencyPair, long amount, long price,
                     long buyerAmount, long buyerClientOrderId, String buyerAddress, String buyerSign,
                     long sellerAmount, long sellerClientOrderId, String sellerAddress, String sellerSign,
                     String exchangeSing);

}
