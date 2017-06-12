package cs545.bank.jms;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class JMSSender implements IJMSSender {

    public void sendJMSMessage(String text) {
        System.out.println("JMSSender: sending JMS message =" + text);
    }

}
