package springbootwebsocket.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
//@Table(name = "User", )
public class User {

    @Id
    private BigDecimal id;

    private String name;

    private String surName;

    public User() {
    }

}
