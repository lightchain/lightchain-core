package automata;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by uwe on 12/14/14.
 */
public class Automata {

    private String id;
    private String pubKeyAsHex;
    private String messagePubKeyAsHex;
    private String name;
    private ID_TYPE idType;
    private List<Locale> languages;
    private List<ID_VERIFICATION> idVerifications;
    private String webUrl;
    private String description;
    private Reputation reputation;

    public Automata() {
    }

    public Automata(String pubKeyAsHex,String name, String messagePubKeyAsHex,  ID_TYPE idType, List<Locale> languages,
                    Reputation reputation,
                    List<ID_VERIFICATION> idVerifications,
                    String webUrl,
                    String description) {
        this.pubKeyAsHex = pubKeyAsHex;
        this.messagePubKeyAsHex = messagePubKeyAsHex;
        this.name = name;
        this.idType = idType;
        this.languages = languages;
        this.reputation = reputation;
        this.idVerifications = idVerifications;
        this.webUrl = webUrl;
        this.description = description;
        //TODO:Generate well randomized hash for id
        this.id = name;
    }

    public void applyAutomata(Automata automata) {
        this.pubKeyAsHex = automata.getPubKeyAsHex();
        this.messagePubKeyAsHex = automata.getPubKeyAsHex();
        this.name = automata.getName();
        this.idType = automata.getIdType();
        this.languages = automata.getLanguages();
        this.reputation = automata.getReputation();
        this.idVerifications = automata.getIdVerifications();
        this.webUrl = automata.getWebUrl();
        this.description = automata.getDescription();

        id = name;
    }

    public String getId() {
        return id;
    }

    public String getPubKeyAsHex() {
        return pubKeyAsHex;
    }

    public String getMessagePubKeyAsHex() {
        return messagePubKeyAsHex;
    }

    /******************************************Getters**************************************/

    public String getName() {
        return name;
    }

    public ID_TYPE getIdType() {
        return idType;
    }

    public List<Locale> getLanguages() {
        return languages;
    }

    public Reputation getReputation() {
        return reputation;
    }

    public List<ID_VERIFICATION> getIdVerifications() {
        return idVerifications;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return Objects.hashCode(id);
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Automata)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Automata other = (Automata) obj;
        return id != null && id.equals(other.getId());
    }

}
