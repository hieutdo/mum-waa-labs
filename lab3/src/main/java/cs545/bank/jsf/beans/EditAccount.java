package cs545.bank.jsf.beans;

import cs545.bank.domain.Account;
import cs545.bank.service.AccountService;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.io.Serializable;

@ManagedBean
@RequestScoped
public class EditAccount implements Serializable {
    @Inject
    private AccountService accountService;

    private Account account;
    private Double depositAmount;
    private Double withdrawAmount;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String deposit() {
        accountService.deposit(account.getAccountnumber(), depositAmount);
        return "/index?faces-redirect=true";
    }

    public String withdraw() {
        accountService.withdraw(account.getAccountnumber(), withdrawAmount);
        return "/index?faces-redirect=true";
    }
}
