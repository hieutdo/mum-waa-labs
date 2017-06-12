package cs545.bank.jsf.beans;

import cs545.bank.domain.Account;
import cs545.bank.service.IAccountService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@ManagedBean
@SessionScoped
public class ViewAccounts implements Serializable {
    @Inject
    private IAccountService accountService;

    @PostConstruct
    public void init() {
        accountService.createAccount(123, "User 1");
        accountService.createAccount(456, "User 2");
        // use account 1;
        accountService.deposit(123, 1000);
        accountService.deposit(123, 2000);
        accountService.withdraw(123, 500);
        // use account 2;
        accountService.deposit(456, 2000);
        accountService.depositEuros(456, 1000);
        accountService.transferFunds(456, 123, 100, "payment of invoice 10232");
    }

    public Collection<Account> getAccounts() {
        return accountService.getAllAccounts().stream()
                .sorted(Comparator.comparingLong(Account::getAccountnumber))
                .collect(Collectors.toList());
    }

    public String getReturnValue() {
        return "/index";
    }
}
