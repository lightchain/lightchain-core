package tomp2p;

import net.tomp2p.peers.PeerAddress;

import java.io.Serializable;

/**
 * Created by uwe on 12/6/14.
 */
public class TomPeer implements  Serializable {

    private final PeerAddress peerAddress;

    public TomPeer(PeerAddress peerAddress) {
        this.peerAddress = peerAddress;
    }

    public PeerAddress getPeerAddress() {
        return peerAddress;
    }

}
