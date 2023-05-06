import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import managers.TCPServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.*;
import utility.Runner;
import utility.StandardConsole;

public class Main {
    private static final int PORT=14151;
    private static final Logger LOGGER = (Logger) LogManager.getLogger("Main");

    public static void main(String[] args) {
        var console = new StandardConsole();

        var dumpManager = new DumpManager("test.csv", console);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.loadCollection()) { System.out.println("Коллекция не загружена!"); }

        var commandManager = new CommandManager() {{
            register("add_if_max", new AddIfMax(collectionManager));
            register("info", new Info(collectionManager));
            register("show", new Show(collectionManager));
            register("add", new Add(collectionManager));
            register("clear", new Clear(collectionManager));
            register("save", new Save(collectionManager));
            register("min_by_minimal_point", new MinByMinimalPoint(collectionManager));
            register("print_ascending", new PrintAscending(collectionManager));
            register("remove_greater", new RemoveGreater(collectionManager));
            register("remove_lower", new RemoveLower(collectionManager));
            register("remove_by_id", new RemoveById(collectionManager));
            register("update", new Update(collectionManager));
            register("sum_of_minimal_point", new SumOfMinimalPoint(collectionManager));
        }};

        var runner = new Runner(console, commandManager);

        var tcpserver = new TCPServer(PORT, runner::executeCommand);
        tcpserver.start();
    }
}


