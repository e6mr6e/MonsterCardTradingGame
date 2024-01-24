package at.fhtw.sampleapp.model;

import lombok.Getter;
import lombok.Setter;

public class UserStats {

    @Setter @Getter
    private String name;
    @Setter @Getter
    private int mmr;
    @Setter @Getter
    private int wins;
    @Setter @Getter
    private int losses;
    @Setter @Getter
    private int coins;

}
