package cs545.bank.dao;

import cs545.bank.domain.Account;

import java.util.Collection;


public interface IAccountDAO {
    public void saveAccount(Account account);

    public void updateAccount(Account account);

    public Account loadAccount(long accountnumber);

    public Collection<Account> getAccounts();
}
