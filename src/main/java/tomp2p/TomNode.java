package tomp2p;

import core.Node;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;

import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.FuturePut;
import net.tomp2p.dht.FutureRemove;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.storage.Data;
import net.tomp2p.utils.Utils;

/**
 * Created by uwe on 12/6/14.
 */
public class TomNode extends Network implements ITomNode {

    private KeyPair keyPair;
    private PeerAddress storedPeerAddress;
    private PeerDHT peerDHT;


    public FutureGet getDomainData(Number160 nodeHash, PublicKey publicKey) {

        return peerDHT.get(nodeHash).start();
    }

    public FutureGet getData(Number160 nodeHash) {

        return peerDHT.get(nodeHash).start();
    }

    public FutureGet getDataMap(Number160 nodeHash) {

        return peerDHT.get(nodeHash).all().start();
    }

    public Node getAddress() {

        PeerAddress peerAddress = peerDHT.peerBean().serverPeerAddress();
        return Node.store(
                peerDHT.peerID().toString(),
                peerAddress.inetAddress().getHostAddress(),
                peerAddress.peerSocketAddress().tcpPort());
    }


    public FuturePut putData(Number160 nodeHash, Data data) {

        return peerDHT.put(nodeHash).data(data).start();
    }

    public FuturePut addProtectedData(Number160 nodeHash, Data data) {

        return peerDHT.add(nodeHash).data(data).start();
    }

    public FutureRemove removeFromDataMap(Number160 nodeHash, Data data) {

        Number160 contentKey = data.hash();
        return peerDHT.remove(nodeHash).contentKey(contentKey).start();
    }

    public FutureDirect sendData(PeerAddress peerAddress, Object payLoad) {

        FutureDirect futureDirect = peerDHT.peer().sendDirect(peerAddress).object(payLoad).start();
        return futureDirect;

    }

     public void storeAddress() throws IOException {
        try {
            FuturePut futurePut = save();

        } catch (IOException e) {

            throw new IOException("Exception at storeAddress.", e);

        }
    }

    private FuturePut save() throws IOException {

        Number160 locationKey = Utils.makeSHAHash(keyPair.getPublic().getEncoded());
        Data data = new Data(new TomPeer(peerDHT.peerAddress()));
        return putDomainData(locationKey, data);
    }

    public FuturePut putDomainData(Number160 locationKey, Data data) {

        return peerDHT.put(locationKey).data(data).start();
    }

    public void shutDown() {

        if (peerDHT != null)
            peerDHT.shutdown();
    }

}
