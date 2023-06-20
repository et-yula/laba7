import commands.*;
import managers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.*;
import utility.Runner;
import utility.StandardConsole;

public class Main {
    private static final int PORT=14151;
    private static final Logger LOGGER = (Logger) LogManager.getLogger("Main");
    public static void main(String[] args) {
        var console = new StandardConsole();
        var dataBaseManager= new DataBaseManager("jdbc:postgresql://127.0.0.1:5432/studs", "s367177", "****************", console); //вместо звездочек пароль от базы данных на гелиосе
        var dumpManager = new DumpManager(dataBaseManager, console);
        if (!dumpManager.initializeTables()) {
            System.exit(1); }
        var userManager = new UserManager(dumpManager);
        if (!userManager.initialize()) {
            System.exit(1);
        }
        var collectionManager = new CollectionManager(dumpManager, userManager);
        collectionManager.load();
        var commandManager = new CommandManager() {{
            register("create_user", new CreateUser(userManager));
            register("add_if_max", new AddIfMax(collectionManager));
            register("info", new Info(collectionManager));
            register("show", new Show(collectionManager));
            register("add", new Add(collectionManager));
            register("clear", new Clear(collectionManager));
            register("min_by_minimal_point", new MinByMinimalPoint(collectionManager));
            register("print_ascending", new PrintAscending(collectionManager));
            register("remove_greater", new RemoveGreater(collectionManager));
            register("remove_lower", new RemoveLower(collectionManager));
            register("remove_by_id", new RemoveById(collectionManager));
            register("update", new Update(collectionManager));
            register("existence_user", new ExistenceUser(userManager));
            register("sum_of_minimal_point", new SumOfMinimalPoint(collectionManager));
        }};
        var runner = new Runner(console, commandManager, userManager);

        var tcpserver = new TCPServer(PORT, runner::executeCommand);
        tcpserver.start();
    }
}


