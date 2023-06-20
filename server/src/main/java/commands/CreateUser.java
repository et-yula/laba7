package commands;
import managers.UserManager;
import utility.Response;
import utility.User;

/**
 * команда для создания нового пользователя
 */
public class CreateUser extends Command {

    private final UserManager userManager;

    public CreateUser(UserManager userManager) {
        super("create_user login:password", "создать нового пользователя");
        this.userManager = userManager;
    }

    /**
     * выполняет команду
     * @return успешность выполнения команды
     */

    @Override
    public Response execute(String[] arguments, Object obj, User user) {
        if (arguments[1].isEmpty()) {
            return new Response(400, "Неправильное количество аргументов!\nИспользование: '" + getName() + "'");
        }
        try {
            if (userManager.addUser(arguments[1].split(":")[0],arguments[1].split(":")[1])) {
                return new Response("OK");
            } else {
                return new Response(500, "Not OK");
            }
        } catch (ArrayIndexOutOfBoundsException e){
            return new Response(400, "login:password not valid");
        }
    }
}