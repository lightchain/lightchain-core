package commandline;

import java.util.HashMap;

public class CommandFactory {
    static HashMap<String, Command> map = new HashMap<>();

    public static void setFactory() {
        map.put("start", new start());
        map.put("?", new printHelp());
        map.put("help", new printHelp());
        map.put("gen", new generateAddress());
        map.put("port", new setPort());
        map.put("connect", new connect());
        map.put("listen", new listen());
        map.put("bind", new bindToPort());
        map.put("add", new add());
        map.put("exit", new exit());
    }

    public static Command getCommand(String type){
             map.get(type).execute();
        return null;
    }

}
