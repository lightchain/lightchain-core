package com.decentralbank.decentralj.core.listeners;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Coin;

/**
 * Created by Uwe on 10/18/14.
 */
public class BalanceListener {

    private Address address;

    public BalanceListener() {
    }

    public BalanceListener(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void onBalanceChanged(Coin balance) {
    }

}
