package cs545.bank.domain;

public class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public Customer() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
