package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fixed_deposit")
public class FixedDeposit {
    private int dbId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getDbId() {
        return dbId;
    }
    public void setDbId(int id) {
        this.dbId = id;
    }

}
