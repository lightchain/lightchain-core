package core;

import java.net.InetAddress;
import java.net.SocketAddress;

//This is to be used by a Node to send and receive HELLO requests
public class Request {
	
	private String status;
	private String peerID;
	private String message;
    private InetAddress address;        //  Own address
    private InetAddress broadcast;      //  Broadcast address
    private SocketAddress sender;       //  Where last recv came from
    private String host;                //  Our own address as string
    private String from;                //  Sender address of last message
	
	public Request() {
		this("","","");
	}
	
	public Request(String status, String peerID) {
		this(status, peerID, "");
	}
	
	public Request(String status, String peerID, String responseMessage){
		this.status = status;
		this.peerID = peerID;
		this.message = responseMessage;
	}

    public Request(int pingPortNumber) {

    }



    public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getPeerID() {
		return peerID;
	}

	public void setPeerID(String peerID) {
		this.peerID = peerID;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		return this.getStatus()+" "+this.getPeerID()+" "+this.getMessage();	
	}

    public static void destroy() {
    }
}
