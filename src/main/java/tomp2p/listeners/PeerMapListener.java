package tomp2p.listeners;


import core.Node;

import java.util.List;

/**
 * Created by uwe on 12/11/14.
 */
public interface PeerMapListener {

    void onPeerFound();

    void onPeerNotFound();

    void onPeerUp();

    void onPeerDown();

    void onPeerRemoved();

    void onMessage(Message message, Node sender);

}
