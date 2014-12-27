package tomp2p.listeners;

import tomp2p.TomPeer;

/**
 * Created by uwe on 12/13/14.
 */
public interface PeerAddressListener {
    void onResult(TomPeer peer);

    void onResult();

    void onFailed();
}
