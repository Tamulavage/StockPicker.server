package com.dmt.stockpicker.model;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Balance")
    private Double balance;

    // @Column(name = "user_id")
    // private Integer userId;
    @OneToOne(cascade = PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name= "user_id")
    private UserProfile userProfile;

    public Account() {
        this.balance = 0.0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    // public Integer getUserId() {
    //     return userId;
    // }

    // public void setUserId(Integer userId) {
    //     this.userId = userId;
    // }

}
