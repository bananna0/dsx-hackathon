package uk.dsxt.smartcontractapi;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.1.1.
 */
public class Exchange extends Contract {
    private static final String BINARY = "Bin file was not provided";

    public static final String FUNC_CREATEORDER = "createOrder";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_DEAL = "deal";

    @Deprecated
    protected Exchange(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Exchange(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Exchange(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Exchange(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> createOrder(String userAddress, BigInteger amount, BigInteger rate, String orderId, String signature, String baseCur, String quotedCur, Boolean isBuy) {
        final Function function = new Function(
                FUNC_CREATEORDER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(userAddress), 
                new org.web3j.abi.datatypes.generated.Uint256(amount), 
                new org.web3j.abi.datatypes.generated.Uint256(rate), 
                new org.web3j.abi.datatypes.Utf8String(orderId), 
                new org.web3j.abi.datatypes.Utf8String(signature), 
                new org.web3j.abi.datatypes.Utf8String(baseCur), 
                new org.web3j.abi.datatypes.Utf8String(quotedCur), 
                new org.web3j.abi.datatypes.Bool(isBuy)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deposit(String currency, BigInteger amount) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(currency), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deal(String orderIdA, String orderIdB, String addressA, String addressB, BigInteger rate, BigInteger amount) {
        final Function function = new Function(
                FUNC_DEAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(orderIdA), 
                new org.web3j.abi.datatypes.Utf8String(orderIdB), 
                new org.web3j.abi.datatypes.Address(addressA), 
                new org.web3j.abi.datatypes.Address(addressB), 
                new org.web3j.abi.datatypes.generated.Uint256(rate), 
                new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Exchange load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Exchange(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Exchange load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Exchange(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Exchange load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Exchange(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Exchange load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Exchange(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
