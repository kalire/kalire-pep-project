package Controller;

import Service.AccountService;
import DAO.AccountDAO;
import Model.Account;
import io.javalin.Javalin;
import io.javalin.http.Context;
import DAO.MessageDAO;
import Service.MessageService;
import Model.Message;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService = new AccountService(new AccountDAO());
    MessageService messageService = new MessageService(new MessageDAO());

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUserHandler);



        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerHandler(Context ctx) {
        Account inputAccount = ctx.bodyAsClass(Account.class);
        Account registeredAccount = accountService.registerAccount(inputAccount);

        if (registeredAccount != null) {
            ctx.json(registeredAccount);
        } else {
            ctx.status(400);
        }
    }

    private void loginHandler(Context ctx) {
    Account credentials = ctx.bodyAsClass(Account.class);
    Account authenticated = accountService.login(credentials);
    if (authenticated != null) {
        ctx.json(authenticated);
    } else {
        ctx.status(401);
    }
}

    private void createMessageHandler(Context ctx) {
    Message message = ctx.bodyAsClass(Message.class);
    Message created = messageService.createMessage(message);

    if (created != null) {
        ctx.json(created);
    } else {
        ctx.status(400);
    }
}
private void getAllMessagesHandler(Context ctx) {
    ctx.json(messageService.getAllMessages());  // returns empty list if none
}
private void getMessageByIdHandler(Context ctx) {
    int id = Integer.parseInt(ctx.pathParam("message_id"));
    Message message = messageService.getMessageById(id);

    if (message != null) {
        ctx.json(message);
    } else {
        ctx.result(""); // empty body
    }
}
private void deleteMessageHandler(Context ctx) {
    int id = Integer.parseInt(ctx.pathParam("message_id"));
    Message deleted = messageService.deleteMessageById(id);

    if (deleted != null) {
        ctx.json(deleted);
    } else {
        ctx.result(""); // empty body
    }
}
private void updateMessageHandler(Context ctx) {
    int id = Integer.parseInt(ctx.pathParam("message_id"));
    Message input = ctx.bodyAsClass(Message.class);
    String newText = input.getMessage_text();

    Message updated = messageService.updateMessage(id, newText);
    if (updated != null) {
        ctx.json(updated);
    } else {
        ctx.status(400);
    }
}

private void getMessagesByUserHandler(Context ctx) {
    int accountId = Integer.parseInt(ctx.pathParam("account_id"));
    ctx.json(messageService.getMessagesByAccountId(accountId));
}




}