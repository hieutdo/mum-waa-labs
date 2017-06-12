package cs545.bank.jsf.beans;

import cs545.bank.domain.Account;
import cs545.bank.domain.Customer;
import cs545.bank.service.IAccountService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class CreateAccount implements Serializable {
    @Inject
    private IAccountService accountService;

    private Account account;
    private Customer customer;

    @PostConstruct
    public void init() {
        this.account = new Account();
        this.customer = new Customer();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String save() {
        accountService.createAccount(account.getAccountnumber(), customer.getName());
        return "/index?faces-redirect=true";
    }
}
