package commandline;

import core.Node;
import core.Wallet;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.InsufficientMoneyException;
import org.bitcoinj.store.BlockStoreException;

import java.util.concurrent.ExecutionException;

interface Command {
    public void execute(Object ... args);
}

//running serverThread and instantiating Node.
class start implements Command
{
    Node decentralNode = Node.getInstance();
    public void execute(Object ... args) {
        System.out.println("Decentral: Starting server...");
        //running thread alongside Node
        //ThreadPool  threadPool = new ThreadPool(8400);
        //threadPool.start();
        System.out.println("Decentral: Connecting to peers...");
        //decentralNode = new Node();
    }
}

class printHelp implements Command
{
    public void execute(Object ... args) {
        /* Decentral Network Server Commands*/
        System.out.println("Commands: ");
        System.out.println("    -start                   Starts Decentral sever listens for peers");
        System.out.println("    -testnet                 Use Bitcoin Testnet (Default)");
        System.out.println("    -gen                     Generate Deposit Multisig Addresses");
        System.out.println("    -connect                 Connect only to the specified node(s)");
        System.out.println("    -listen                  Accept connections from outside");
        System.out.println("    -bind                    Bind to given address and always listen on it.");
        System.out.println("    -bind                    Bind to given address and always listen on it.");
        System.out.println("    -exit                    Shutdowns Decentral Server");
        /* Decentral Wallet Commands */
        System.out.println("    -deposit                 Generate Deposit Guarantee Addresses");
        System.out.println("    -transactions            List History of Transactions");

    }
}

class listen implements Command
{
    //Node decentralNode = Node.getInstance();
    public void execute(Object ... args) {

    }
}

class setPort implements Command
{
    public void execute(Object ... args) {

    }
}

class connect implements Command
{
    public void execute(Object ... args) {

    }

}

class generateAddress implements Command
{
    Node singleton = Node.getInstance();
    //NodeWallet wallet = NodeWallet.getInstance();

    public void execute(Object ... args) {

        Wallet wallet = singleton.getWallet();
        ECKey lekey = new ECKey();
        ECKey lekey2 = new ECKey();
    }
}

class generateDeposit implements Command
{
    Node singleton = Node.getInstance();

    public void execute(Object ... args) {
        //Wallet wallet = singleton.getWallet();
        //wallet.addNewAddress();

    }
}

class bindToPort implements Command
{

    public void execute(Object ... args) {

    }
}


class exit implements Command
{

    public void execute(Object ... args) {
        Runtime.getRuntime().halt(0);
    }
}

class add implements Command
{

    public void execute(Object... args) {
        try {
            if(args.length == 2)
            {
                int a = (int)args[0];
                int b = (int)args[1];
                System.out.println(a + b);
            }
            else throw new Exception("incorrect num of inputs");
        }
        catch(Exception e){
            System.out.println(e);
        }

    }

}
