import commands.*;
import managers.CommandManager;
import managers.TCPClient;
import managers.TCPManager;
import utility.Runner;
import utility.StandardConsole;
import utility.UserUtility;

public class Main {
    private static final int PORT = 14151;
    private static final String SERVER_ADRESS = "127.0.0.1";
    public static void main(String[] args) {
        var console = new StandardConsole();
        var tcpclient = new TCPClient(SERVER_ADRESS, PORT);
        tcpclient.start();
        var tcpmanager = new TCPManager(tcpclient);
        var userUtility = new UserUtility(tcpmanager, console);
        if (!userUtility.tryLogin()) {
            System.exit(1);
        }
        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("add_if_max", new AddIfMax(console, tcpmanager));
            register("info", new Info(console, tcpmanager));
            register("show", new Show(console, tcpmanager));
            register("add", new Add(console, tcpmanager));
            register("update", new Update(console, tcpmanager));
            register("remove_by_id", new RemoveById(console, tcpmanager));
            register("clear", new Clear(console, tcpmanager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_greater", new RemoveGreater(console, tcpmanager));
            register("remove_lower", new RemoveLower(console, tcpmanager));
            register("min_by_minimal_point", new MinByMinimalPoint(console, tcpmanager));
            register("sum_of_minimal_point", new SumOfMinimalPoint(console, tcpmanager));
            register("print_ascending", new PrintAscending(console, tcpmanager));
        }};

        new Runner(console, commandManager, tcpmanager).interactiveMode();
    }
}

