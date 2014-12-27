package tomp2p;


import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import automata.Automata;
import tomp2p.listeners.DecentralPeerListener;
import tomp2p.listeners.Message;
import tomp2p.listeners.PeerAddressListener;
import tomp2p.listeners.PeerMapListener;
import net.tomp2p.dht.FutureGet;
import net.tomp2p.dht.FuturePut;
import net.tomp2p.dht.FutureRemove;
import net.tomp2p.futures.BaseFuture;
import net.tomp2p.futures.BaseFutureAdapter;
import net.tomp2p.futures.BaseFutureListener;
import net.tomp2p.futures.FutureDirect;
import net.tomp2p.peers.Number160;
import net.tomp2p.storage.Data;
import net.tomp2p.utils.Utils;

/**
 * Created by uwe on 12/13/14.
 */
public class TomService extends Network{

    private static final String ARBITRATORS_ROOT = "ArbitratorsRoot";

    private final TomNode p2pNode;
    private final List<DecentralPeerListener> peerListeners = new ArrayList<>();
    private final List<PeerMapListener> incomingMessageListeners = new ArrayList<>();


    public TomService(TomNode p2pNode) {
        this.p2pNode = p2pNode;
    }

    public void shutDown() {
        if (p2pNode != null)
            p2pNode.shutDown();
    }


    public void getPeerAddress(PublicKey publicKey, PeerAddressListener listener) {
        final Number160 locationKey = Utils.makeSHAHash(publicKey.getEncoded());
        FutureGet futureGet = p2pNode.getDomainData(locationKey, publicKey);

        futureGet.addListener(new BaseFutureAdapter<BaseFuture>() {
            @Override
            public void operationComplete(BaseFuture baseFuture) throws Exception {
                if (baseFuture.isSuccess() && futureGet.data() != null) {
                    final TomPeer peer = (TomPeer) futureGet.data().object();
                }
                else {
                    System.out.println("getPeerAddress failed. failedReason = " + baseFuture.failedReason());
                }
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    // Trade process
    ///////////////////////////////////////////////////////////////////////////////////////////

    public void sendMessage(TomPeer peer, Message message,
                            PeerAddressListener listener) {
        if (!(peer instanceof TomPeer)) {
            throw new IllegalArgumentException("peer must be of type TomP2PPeer");
        }
        FutureDirect futureDirect = p2pNode.sendData(((TomPeer) peer).getPeerAddress(), message);
        futureDirect.addListener(new BaseFutureListener<BaseFuture>() {
            @Override
            public void operationComplete(BaseFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.print(message);
                }
                else {
                    System.out.println("sendMessage failed with reason " + futureDirect.failedReason());
                }
            }
            @Override
            public void exceptionCaught(Throwable t) throws Exception {

            }
        });
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // Arbitrator Automata
    ///////////////////////////////////////////////////////////////////////////////////////////

    public void addArbitrator(Automata arbitrator) {
        Number160 locationKey = Number160.createHash(ARBITRATORS_ROOT);
        try {
            final Data arbitratorData = new Data(arbitrator);

            FuturePut addFuture = p2pNode.addProtectedData(locationKey, arbitratorData);
            addFuture.addListener(new BaseFutureAdapter<BaseFuture>() {
                @Override
                public void operationComplete(BaseFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("Add arbitrator to DHT was successful. Stored data: [key: " + locationKey + ", " +
                                "values: " + arbitratorData + "]");
                    }
                    else {
                        System.out.println("Add arbitrator to DHT failed with reason:" + addFuture.failedReason());
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeArbitrator(Automata arbitrator) throws IOException {
        Number160 locationKey = Number160.createHash(ARBITRATORS_ROOT);
        final Data arbitratorData = new Data(arbitrator);
        FutureRemove removeFuture = p2pNode.removeFromDataMap(locationKey, arbitratorData);
        removeFuture.addListener(new BaseFutureAdapter<BaseFuture>() {
            @Override
            public void operationComplete(BaseFuture future) throws Exception {
                System.out.println("Remove from DHT was successful. Stored data: [key: " + locationKey + ", " +
                        "values: " + arbitratorData + "]");
            }
        });
    }

    public void getArbitrators(Locale languageLocale) {
        Number160 locationKey = Number160.createHash(ARBITRATORS_ROOT);
        FutureGet futureGet = p2pNode.getDataMap(locationKey);
        futureGet.addListener(new BaseFutureAdapter<BaseFuture>() {
            @Override
            public void operationComplete(BaseFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("Get from DHT was successful. Stored data: [key: " + locationKey + ", " +
                            "values: " + futureGet.dataMap() + "]");
                }
                else {
                    System.out.println("Get from DHT failed with reason:" + future.failedReason());
                }
            }
        });
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // Event Listeners
    ///////////////////////////////////////////////////////////////////////////////////////////

    public void addArbitratorListener(DecentralPeerListener listener) {
        peerListeners.add(listener);
    }

    public void removeArbitratorListener(DecentralPeerListener listener) {
        peerListeners.remove(listener);
    }

    public void addIncomingMessageListener(PeerMapListener listener) {
        incomingMessageListeners.add(listener);
    }

    public void removeIncomingMessageListener(PeerMapListener listener) {
        incomingMessageListeners.remove(listener);
    }


    public void handleMessage(Object message, TomPeer sender) {

    }
}
