package cs545.bank.jsf.converters;

import cs545.bank.domain.Account;
import cs545.bank.service.IAccountService;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AccountConverter implements Converter {
    @Inject
    private IAccountService accountService;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        try {
            Long accountNumber = Long.valueOf(input);
            Account account = accountService.getAccount(accountNumber);
            if (account == null) {
                throw new ConverterException(new FacesMessage("Bad Request! Cannot found account with number " + accountNumber));
            }
            return account;
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage("Bad Request! " + input + " is not a valid account number"), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Account) {
            long number = ((Account) value).getAccountnumber();
            return String.valueOf(number);
        } else {
            throw new ConverterException("The value is not a valid Account instance: " + value);
        }
    }
}
