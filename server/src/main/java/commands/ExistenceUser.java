package commands;

import managers.UserManager;
import utility.Response;
import utility.User;

/**
 * команда для проверки существования пользователя
 */
public class ExistenceUser extends Command{
    private final UserManager userManager;

    public ExistenceUser(UserManager userManager) {
        super("existence_user", "проверка существования пользователя");
        this.userManager = userManager;
    }
    /**
     * выполняет команду
     * @return успешность выполнения команды
     * */

    @Override
    public Response execute(String[] arguments, Object obj, User user) {
        return new Response("OK");
    }
}
