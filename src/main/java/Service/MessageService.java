package Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import DAO.MessageDAO;
import Model.Message;
import Util.ConnectionUtil;

public class MessageService {

    MessageDAO messageDAO;

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) {
    if (message.getMessage_text() == null || message.getMessage_text().isBlank()) return null;
    if (message.getMessage_text().length() > 255) return null;
    if (!messageDAO.userExists(message.getPosted_by())) return null;

    return messageDAO.insertMessage(message);
}

public List<Message> getAllMessages() {
    return messageDAO.getAllMessages();
}
public Message getMessageById(int id) {
    return messageDAO.getMessageById(id);
}
public Message deleteMessageById(int id) {
    return messageDAO.deleteMessageById(id);
}
public Message updateMessage(int id, String newText) {
    if (newText == null || newText.isBlank() || newText.length() > 255) return null;
    return messageDAO.updateMessageById(id, newText);
}

public List<Message> getMessagesByAccountId(int accountId) {
    return messageDAO.getMessagesByAccountId(accountId);
}


}
