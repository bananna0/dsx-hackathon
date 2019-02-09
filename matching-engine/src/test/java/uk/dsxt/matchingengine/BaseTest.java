package uk.dsxt.matchingengine;

public class BaseTest {
    public static final String eurusd = "EURUSD";
    public static final String addressClient1 = "address1";
    public static final String addressClient2 = "address2";
    public static final String signClient1 = "sign1";
    public static final String signClient2 = "sign2";
    public SmartContractInterfaceMockImpl smartContractInterfaceMock = new SmartContractInterfaceMockImpl();
    public Exchange testExchange = new Exchange(smartContractInterfaceMock);
}
