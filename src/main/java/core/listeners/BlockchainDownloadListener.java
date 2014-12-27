package com.decentralbank.decentralj.core.listeners;

import org.bitcoinj.core.BlockChain;
/**
 * Created by Uwe on 10/18/14.
 */
public class BlockchainDownloadListener {
    private final BlockChain blockChain;

    public BlockchainDownloadListener(BlockChain blockChain) {
        this.blockChain = blockChain;
    }

    public BlockChain getAddress() {
        return blockChain;
    }

}
