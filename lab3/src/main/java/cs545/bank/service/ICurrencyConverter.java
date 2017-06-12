package cs545.bank.service;

public interface ICurrencyConverter {
    public double euroToDollars(double amount);

    public double dollarsToEuros(double amount);
}
