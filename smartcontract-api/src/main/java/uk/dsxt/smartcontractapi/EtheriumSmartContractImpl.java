package uk.dsxt.smartcontractapi;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.http.HttpService;
import uk.dsxt.common.SmartContractInterface;

public class EtheriumSmartContractImpl implements SmartContractInterface {

    private final Web3j web3j;

    public EtheriumSmartContractImpl() {
        this.web3j = Admin.build(new HttpService());
        //YourSmartContract contract = YourSmartContract.load(
        //        "0x<address>|<ensName>", <web3j>, <credentials>, GAS_PRICE, GAS_LIMIT);
    }

    @Override
    public void dealCreated(long number, String currencyPair, long amount, long price, long buyerAmount, long buyerClientOrderId, String buyerAddress, String buyerSign, long sellerAmount, long sellerClientOrderId, String sellerAddress, String sellerSign, String exchangeSing) {

    }

    @Override
    public boolean lockMoney(long amount, String address, String signature) {
        return false;
    }


}
