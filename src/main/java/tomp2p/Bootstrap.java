package tomp2p;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.google.common.util.concurrent.SettableFuture;

import core.Node;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import net.tomp2p.connection.Bindings;
import net.tomp2p.connection.ChannelClientConfiguration;
import net.tomp2p.connection.ChannelServerConfiguration;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.futures.BaseFuture;
import net.tomp2p.futures.BaseFutureListener;
import net.tomp2p.futures.FutureBootstrap;
import net.tomp2p.futures.FutureDiscover;
import net.tomp2p.nat.FutureNAT;
import net.tomp2p.nat.PeerBuilderNAT;
import net.tomp2p.nat.PeerNAT;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;
import net.tomp2p.peers.PeerMapChangeListener;
import net.tomp2p.peers.PeerStatistic;


/**
 * Created by uwe on 12/5/14.
 */

public class Bootstrap extends Network {

    private KeyPair keyPair;
    private final int port;
    private boolean useManualPortForwarding;
    private final Node bootstrapNode;
    public static final String DOMAIN = "ec2-54-243-46-104.compute-1.amazonaws.com";
    public static final String IP = "10.107.133.43";
    public static final int PORT = 8888;
    public static final String NETWORK_INTERFACE = "eth0";

    private final SettableFuture<PeerDHT> settableFuture = SettableFuture.create();

    private Peer peer;
    private PeerDHT peerDHT;


    public Bootstrap(int port, boolean useManualPortForwarding) {

        this.port = port;
        this.useManualPortForwarding = useManualPortForwarding;
        bootstrapNode = new Node(DOMAIN, IP, PORT);
    }

    public void setKeyPair(KeyPair keyPair) {

        this.keyPair = keyPair;
    }

    public SettableFuture<PeerDHT> start() {

        try {

            DefaultEventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(150);

            ChannelClientConfiguration clientConf = PeerBuilder.createDefaultChannelClientConfiguration();
            clientConf.pipelineFilter(new PeerBuilder.EventExecutorGroupFilter(eventExecutorGroup));

            ChannelServerConfiguration serverConf = PeerBuilder.createDefaultChannelServerConfiguration();
            serverConf.pipelineFilter(new PeerBuilder.EventExecutorGroupFilter(eventExecutorGroup));
            serverConf.connectionTimeoutTCPMillis(5000);

            Bindings bindings = new Bindings();
            bindings.addInterface(NETWORK_INTERFACE);
            if (useManualPortForwarding) {
                System.out.println("port Forwarding");
                peer = new PeerBuilder(keyPair)
                        .channelClientConfiguration(clientConf)
                        .channelServerConfiguration(serverConf)
                        .ports(port)
                        .tcpPortForwarding(port)
                        .udpPortForwarding(port)
                        .start();
            }else {
                peer = new PeerBuilder(keyPair)
                        .channelClientConfiguration(clientConf)
                        .channelServerConfiguration(serverConf)
                        .ports(port)
                        .start();
            }


            peerDHT = new PeerBuilderDHT(peer).start();

            peer.peerBean().peerMap().addPeerMapChangeListener(new PeerMapChangeListener() {
                @Override
                public void peerInserted(PeerAddress peerAddress, boolean verified) {
                }

                @Override
                public void peerRemoved(PeerAddress peerAddress, PeerStatistic peerStatistics) {
                }

                @Override
                public void peerUpdated(PeerAddress peerAddress, PeerStatistic peerStatistics) {
                }
            });

            discoverOurAddress();
        } catch (IOException e) {
            System.out.println("Cannot create a peer with port: " +
                    port + ". Exception: " + e);
        }

        return settableFuture;
    }

    //discover our own address if inside NAT setup port forwarding
    private void discoverOurAddress() {

        FutureDiscover futureDiscover = peer.discover().peerAddress(getBootstrapAddress()).start();
        System.out.println("Starting discovery");
        PeerNAT peerNAT = new PeerBuilderNAT(peer).start();
        FutureNAT futureNAT = peerNAT.startSetupPortforwarding(futureDiscover);
        futureNAT.addListener(new BaseFutureListener<BaseFuture>() {
            @Override
            public void operationComplete(BaseFuture future) throws Exception {

                if (futureDiscover.isSuccess()) {
                        bootstrap();
                }
                else {
                    System.out.println("We are probably behind a NAT and not reachable to other peers. ");
                    System.out.println("We try to setup automatic port forwarding.");
                    if (futureNAT.isSuccess()) {
                        System.out.println("Discover with automatic port forwarding was successful.");
                        bootstrap();
                    }
                    else {
                        System.out.println("Automatic port forwarding " +"failed. "+ future.failedReason());
                    }
                }
            }

            @Override
            public void exceptionCaught(Throwable e) throws Exception {

                System.out.println("Discover exception: " + e.getMessage());
            }
        });
    }

    private void bootstrap() {

        FutureBootstrap futureBootstrap = peer.bootstrap().peerAddress(getBootstrapAddress()).start();
        futureBootstrap.addListener(new BaseFutureListener<BaseFuture>() {
            @Override
            public void operationComplete(BaseFuture future) throws Exception {
                if (futureBootstrap.isSuccess()) {
                    settableFuture.set(peerDHT);
                }
                else {
                    System.out.println("Bootstrapping failed. " + futureBootstrap.failedReason());
                }
            }

            @Override
            public void exceptionCaught(Throwable t) throws Exception {
                System.out.println("Exception at bootstrap: " + t.getMessage());
            }
        });
    }

    private PeerAddress getBootstrapAddress() {
        System.out.println("bootstrap address called");
        try {
            return new PeerAddress(Number160.createHash(bootstrapNode.getId()),
                    InetAddress.getByName(bootstrapNode.getIp()),bootstrapNode.getPort(),bootstrapNode.getPort());
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public Node getBootstrapNode() {

        return bootstrapNode;
    }

    public void shutDown() {

        if (peerDHT != null)
            peerDHT.shutdown();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Bootstrap leboot;;
        Random r = new Random();
        KeyPairGenerator gen = null;
        try {
            gen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        KeyPair pair1 = gen.generateKeyPair();
        leboot = new Bootstrap(8888, true);
        leboot.bootstrapNode.setID("23ssdf23342f");
        leboot.setKeyPair(pair1);
        leboot.start();
    }
}
