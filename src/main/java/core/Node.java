package core;

import java.math.BigInteger;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.NetworkParameters;

public class Node {
	
	public static final int MAXCON=11;
	private String nextPeerID;
	private String id;
    private String status;
	private int port;
	private String poolId;
	private String[] poolMembers = new String[MAXCON];
	private String hostname;
	private String nextPeerHostName;
	private String nextPeerPort;
	private String redirectPort;
	private String redirectHostName;
	private Wallet wallet;
	private static Node instance = new Node();
    private String ip;
    private String domain;

    public static final int DEFAULT_PORT = 7380;

    public Node(){}

    public Node(String domain, String ip, int port) {
        this.domain = domain;
        this.ip = ip;
        this.port = port;
    }

    //get instance of node
	public static synchronized Node getInstance() {
	      return instance;
	}
	
	//override clone method
	@Override
	public Object clone() throws CloneNotSupportedException {

        throw new CloneNotSupportedException();
	}
	
	public String getId() {

        return id;
	}

    public String getIp() {

        return ip;
    }

    public void setIp(String ip) {

        this.ip = ip;
    }

	public void setID(String ID) {

        this.id = ID;
	}

	public int getPort() {

        return this.port;
	}

	public void setPort(int port) {

        this.port = port;
	}
	
	public String getPoolId() {

        return this.poolId;
	}
	
	public void setPoolId(String poolId) {

        this.poolId = poolId;
	}

	public String [] getPoolMembers() {

        return poolMembers;
	}
	
	public void setPoolMembers(String [] members) {

        poolMembers = members.clone();
	}

	public String getNextPeerID() {

		return nextPeerID;
	}

	public void setNextPeerID(String nextPeerID) {

        nextPeerID = nextPeerID;
	}

	public String getHostname() {

        return hostname;
	}

	public void setHostname(String hostname) {

        hostname = hostname;
	}

	public String getNextPeerHostName() {

        return nextPeerHostName;
	}

	public void setNextPeerHostName(String NextPeerHostName) {

		this.nextPeerHostName = NextPeerHostName;
	}

	public String getNextPeerPort() {

        return nextPeerPort;
	}

	public void setNextPeerPort(String NextPeerPort) {

        this.nextPeerPort = NextPeerPort;
	}

	public String getRedirectHostName() {

        return redirectHostName;
	}

	public void setRedirectHostName(String redirectHostName) {

        redirectHostName = redirectHostName;
	}

	public String getRedirectPort() {

        return redirectPort;
	}

	public void setRedirectPort(String redirectPort) {

        redirectPort = redirectPort;
	}
	
	public Wallet getWallet(){

        return wallet;
	}
	
	public void createWallet() {

        if (wallet != null) {
		   return;
		}	
		wallet = new Wallet();
	}

    public String getStatus() {

        return this.status;
    }
	
	 // return String of object
	@Override
	public String toString(){
		return "Hostname: "+this.hostname+" Port: "+this.port+" ID: "+this.id+" NextHostname: "+this.nextPeerHostName+" NextPort: "+
			this.nextPeerPort+" NextID: "+this.nextPeerID+" Maximum connections: "+this.MAXCON+" Routing Table: ";
	}
	
	//testing
	public static void main(String [] args) throws AddressFormatException {
		
		Node lenode = Node.getInstance();
		Wallet lewallet = lenode.getWallet();
		BigInteger leinteger = BigInteger.valueOf(20000000);
		System.out.print(leinteger);
		 NetworkParameters netParams = NetworkParameters.testNet();
		// leaddress.equals("mipcBbFg9gMiCh81Kj8tqqdgoZub1ZJRfn");
		 Address targetAddress = new Address(netParams, "mipcBbFg9gMiCh81Kj8tqqdgoZub1ZJRfn");
		//Address.getParametersFromAddress("mipcBbFg9gMiCh81Kj8tqqdgoZub1ZJRfn");
		//lewallet.setBalance(targetAddress, leinteger);
		//BigInteger result = lewallet.getBalance(leaddress);
		System.out.print(targetAddress.toString());
		
	}


    public static Node store(String s, String hostAddress, int i) {

        return null;
    }


    public static Node address(String domain, String ip) {
        return Node.address(domain, ip, DEFAULT_PORT);
    }

    public static Node address(String name, String ip, int port) {
        return new Node(name, ip, port);
    }

    public static Node address(String domain, String ip, String port) {
        return new Node(domain, ip, Integer.valueOf(port));
    }

}
