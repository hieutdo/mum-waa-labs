package edu.mum.cs545.domain;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class FriendlyMatch extends Match {
    @Enumerated
    private AwardType award;

    public AwardType getAward() {
        return award;
    }

    public void setAward(AwardType award) {
        this.award = award;
    }
}
