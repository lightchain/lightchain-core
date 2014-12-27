package automata;

import java.io.Serializable;

/**
 * Created by uwecerron on 12/26/14.
 */
public class Reputation implements Serializable {
    private long upTime;
    private String solvency;
    private String trust;




    public Reputation (long upTime, String solvency, String trust) {
        this.upTime = upTime;
        this.solvency = solvency;
        this.trust = trust;
    }

    //implement
    @Override
    public String toString() {
        return "upTime: "+ upTime + " solvency:" +solvency  + " trust"+trust ;
    }

}
