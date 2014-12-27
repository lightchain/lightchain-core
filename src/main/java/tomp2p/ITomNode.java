package tomp2p;

import core.Node;
import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.FuturePut;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.storage.Data;

import java.io.IOException;

/**
 * Created by uwe on 12/11/14.
 */
public interface ITomNode {

    public Node getAddress();

    public void storeAddress() throws IOException;

    public FutureDirect sendData(PeerAddress peerAddress, Object payLoad);

    public FuturePut putData(Number160 nodeHash, Data data);

    public FutureGet getData(Number160 nodeHash);

}
