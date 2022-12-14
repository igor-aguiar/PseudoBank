package com.desenvigor.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public abstract class  Account {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();
    private String number;
    private String agency;
    private BigDecimal balance;
    private Double tax;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTransactions(Transaction transactions) {
        transactions.setAccount(this);
        this.transactions.add(transactions);
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Account() {
    }

    public Account(Client client, String number, String agency, BigDecimal balance) {
        this.client = client;
        this.number = number;
        this.agency = agency;
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void withdraw(String value){
        BigDecimal tax1 = this.getBalance().subtract(new BigDecimal(tax.toString()));
        BigDecimal wd = new BigDecimal(value);
        this.setBalance(tax1.subtract(wd));
        Transaction trans = new Transaction(this, Operation.WITHDRAW, wd);
        this.setTransactions(trans);
    };

    public abstract void deposit(String value);

    @Override
    public String toString() {
        return client + " Normal Account";
    }
}
