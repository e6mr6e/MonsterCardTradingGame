package at.fhtw.sampleapp.model;

import com.fasterxml.jackson.annotation.JsonAlias;
//emre
public class Card {

    @JsonAlias({"id"})
    private String id;

    @JsonAlias({"name"})
    private String name;

    @JsonAlias({"damage"})
    private Integer damage;

    @JsonAlias({"type"})
    private int type;

    public Card() {
    }

    public Card(String id, String name, Integer damage, CardType type) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.type = type.ordinal();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
