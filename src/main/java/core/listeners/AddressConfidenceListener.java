package com.decentralbank.decentralj.core.listeners;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.TransactionConfidence;
/**
 * Created by Uwe on 10/18/14.
 */
public class AddressConfidenceListener {
    private final Address address;

    public AddressConfidenceListener(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void onTransactionConfidenceChanged(TransactionConfidence confidence) {
    }

}
