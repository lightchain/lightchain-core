package tomp2p.listeners;

import core.Node;

import java.util.List;

/**
 * Created by uwe on 12/12/14.
 */
public interface DecentralPeerListener {

    void onPeerAdded(Node node);

    void onPeerReceived(List<Node> nodes);

    void onPeerRemoved(Node arbitrator);

}
