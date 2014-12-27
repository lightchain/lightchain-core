package com.decentralbank.decentralj.core.listeners;

import org.bitcoinj.core.TransactionConfidence;
/**
 * Created by tobi on 10/18/14.
 */
public class TxConfidenceListener {
    private String txHash;

    public TxConfidenceListener(String txID) {
        this.txHash = txID;
    }

    public String getTxID() {
        return txHash;
    }

    public void onTransactionConfidenceChanged(TransactionConfidence confidence) {
    }
}
