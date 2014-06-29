package de.bigzee.siegertipp.model;

import de.bigzee.siegertipp.account.Account;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by lzimmerm on 19.06.2014.
 */
@SuppressWarnings("serial")
@Document(collection = "players")
public class Player implements Serializable {

    @Id
    private String id;

    @Indexed(unique=true)
    private String name;

    private Gender gender;

    private byte[] picture;

    @DBRef
    private Account createdBy;

    protected Player() {
    }

    public Player(String name, Account createdBy) {
        this.name = name;
        this.createdBy = createdBy;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Account getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Account createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player group = (Player) o;

        if (!name.equals(group.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
