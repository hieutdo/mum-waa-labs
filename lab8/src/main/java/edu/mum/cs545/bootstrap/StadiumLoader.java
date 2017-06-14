package edu.mum.cs545.bootstrap;

import edu.mum.cs545.domain.Stadium;
import edu.mum.cs545.repository.StadiumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StadiumLoader implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(StadiumLoader.class);

    @Autowired
    private StadiumRepository stadiumRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Stadium stadium1 = new Stadium("Stadium 1", "City 1", "State 1");
        Stadium stadium2 = new Stadium("Stadium 2", "City 2", "State 2");
        stadiumRepository.save(stadium1);
        stadiumRepository.save(stadium2);
    }
}
