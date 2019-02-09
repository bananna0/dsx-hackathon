package uk.dsxt.matchingengine;

import org.awaitility.Awaitility;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    public static final String eurusd = "EURUSD";
    public static final String addressClient1 = "address1";
    public static final String addressClient2 = "address2";
    public static final String signClient1 = "sign1";
    public static final String signClient2 = "sign2";
    public SmartContractInterfaceMockImpl smartContractInterfaceMock = new SmartContractInterfaceMockImpl();
    public Exchange testExchange = new Exchange(smartContractInterfaceMock);

    public void waitForTradesCount(int expectedCount, int timeout, int pollInterval) {
        Awaitility.await("Looking for a " + expectedCount + " trades").timeout(timeout, TimeUnit.SECONDS)
                .pollInterval(pollInterval, TimeUnit.MILLISECONDS)
                .pollDelay(0, TimeUnit.SECONDS)
                .until(() -> smartContractInterfaceMock.trades.size() == expectedCount);
    }
}
