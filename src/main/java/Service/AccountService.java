package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) return null;
        if (account.getPassword() == null || account.getPassword().length() < 4) return null;
        if (accountDAO.getAccountByUsername(account.getUsername()) != null) return null;

        return accountDAO.insertAccount(account);
    }

    public Account login(Account account) {
    return accountDAO.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
}

}
