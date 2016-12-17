package com.raveleen;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Святослав on 14.12.2016.
 */
@Entity
@Table(name = ("Dishes"))
public class Dish {
    @Id
    @GenericGenerator(name = "auto_inc", strategy = "increment")
    @GeneratedValue(generator = "auto_inc")
    private long id;
    @Column(name = ("Name"))
    private String name;
    @Column(name = ("Weight"))
    private int weight;
    @Column(name = ("Price"))
    private int price;
    @Column(name = ("Discount"), nullable = true)
    private int discount;

    public Dish() {
    }

    public Dish(String name, int weight, int price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Dish(String name, int weight, int price, int discount) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
