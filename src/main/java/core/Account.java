package core;

import java.math.BigInteger;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;

/**
 * A Node's Pool Account for currency or smart contracts
 */
public class Account{
    
    /* A value equal to the number of transactions or contracts */
	private BigInteger nonce;
    /* total balance of bitcoin*/
    private BigInteger balance;
	//private eckey
    private ECKey ecKey;
	//address
	private Address address;
	//set to testnet for now
	private final NetworkParameters netParams = NetworkParameters.testNet();
	
	public Account() {
		this(new ECKey(), BigInteger.ZERO, BigInteger.ZERO);
	}

	public Account(ECKey ecKey) {
		this.ecKey = ecKey;
	}

	public Account(ECKey ecKey, BigInteger nonce, BigInteger balance) {
		this.address = ecKey.toAddress(netParams);	   
		this.ecKey = ecKey;
		this.nonce = nonce;
		this.balance = balance;
		System.out.println(ecKey.toAddress(netParams));
	}	

	public BigInteger getNonce() {
        return nonce;
    }
	
    public ECKey getECKey() {
    	
        return ecKey;
    
    }

	public Address getAddress() {
		
		return address;
	
	}

	public void setAddress(Address address) {
		
		this.address = address;
	
	}
	
    public BigInteger getBalance() {
    	
		if(balance.equals(BigInteger.ZERO)){
			return BigInteger.ZERO;
		}
		
		return balance;
	
	}

	public void setBalance(BigInteger balance) {
		
		this.balance = balance;
	
	}
}