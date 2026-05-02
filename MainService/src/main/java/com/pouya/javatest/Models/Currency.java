package com.pouya.javatest.Models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cn_id")
    private Long id;

    @Column(name = "cn_name")
    private String name;

    @Column(name = "cn_symbol")
    private String symbol;


    @Column(name = "cn_rate")
    private BigDecimal rate;

    public Currency(){}
    public Currency(String name, String symbol, Long rate) {
        this.name = name;
        this.symbol = symbol;
        this.rate = BigDecimal.valueOf(rate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
