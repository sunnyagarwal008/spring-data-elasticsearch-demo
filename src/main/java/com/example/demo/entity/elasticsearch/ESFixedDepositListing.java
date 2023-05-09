package com.example.demo.entity.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "fixed_deposit_listing")
public class ESFixedDepositListing {

    public enum Status {
        ACTIVE,
        INACTIVE,
        UPCOMING
    }

    @Id
    private String id;

    private String bank;

    private Status status;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<FixedDeposit> fixedDeposits;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<FixedDeposit> getFixedDeposits() {
        return fixedDeposits;
    }

    public void setFixedDeposits(List<FixedDeposit> fixedDeposits) {
        this.fixedDeposits = fixedDeposits;
    }

    public static class FixedDeposit {
        private String id;
        private String name;
        private double rate;
        private Integer minimumDeposit;
        private Integer maximumDeposit;
        private int tenureInDays;
        private int lockInPeriodInDays;

        public FixedDeposit() {
        }

        public FixedDeposit(String id, String name, double rate, Integer minimumDeposit, Integer maximumDeposit, int tenureInDays, int lockInPeriodInDays) {
            this.id = id;
            this.name = name;
            this.rate = rate;
            this.minimumDeposit = minimumDeposit;
            this.maximumDeposit = maximumDeposit;
            this.tenureInDays = tenureInDays;
            this.lockInPeriodInDays = lockInPeriodInDays;
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

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public Integer getMinimumDeposit() {
            return minimumDeposit;
        }

        public void setMinimumDeposit(Integer minimumDeposit) {
            this.minimumDeposit = minimumDeposit;
        }

        public Integer getMaximumDeposit() {
            return maximumDeposit;
        }

        public void setMaximumDeposit(Integer maximumDeposit) {
            this.maximumDeposit = maximumDeposit;
        }

        public int getTenureInDays() {
            return tenureInDays;
        }

        public void setTenureInDays(int tenureInDays) {
            this.tenureInDays = tenureInDays;
        }

        public int getLockInPeriodInDays() {
            return lockInPeriodInDays;
        }

        public void setLockInPeriodInDays(int lockInPeriodInDays) {
            this.lockInPeriodInDays = lockInPeriodInDays;
        }
    }
}
