/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Yordan
 */
@Entity
@Table(name = "ACCOUNTS", catalog = "", schema = "APP")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findById", query = "SELECT a FROM Account a WHERE a.id = :id"),
    @NamedQuery(name = "Account.findByCustomerKey", query = "SELECT a FROM Account a WHERE a.customerKey = :customerKey"),
    @NamedQuery(name = "Account.findByAccountNumber", query = "SELECT a FROM Account a WHERE a.accountNumber = :accountNumber"),
    @NamedQuery(name = "Account.findByAccountType", query = "SELECT a FROM Account a WHERE a.accountType = :accountType")})
public class Account implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountKey")
    private Collection<SavingsTransactions> savingsTransactionsCollection;
    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    private char status;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;
    @Size(max = 15)
    @Column(name = "ACCOUNT_TYPE")
    private String accountType;
    @JoinColumn(name = "CUSTOMER_KEY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Customer customerKey;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountKey")
    private Collection<CreditCard> creditCardCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountKey")
    private Collection<Savings> savingsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accountKey")
    private Collection<HomeLoan> homeLoanCollection;

    public Account() {
    }

    public Account(Integer id) {
        this.id = id;
    }

    public Account(Integer id, String accountNumber) {
        this.id = id;
        this.accountNumber = accountNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Customer getCustomerKey() {
        return customerKey;
    }

    public void setCustomerKey(Customer customerKey) {
        this.customerKey = customerKey;
    }

    public Collection<CreditCard> getCreditCardCollection() {
        return creditCardCollection;
    }

    public void setCreditCardCollection(Collection<CreditCard> creditCardCollection) {
        this.creditCardCollection = creditCardCollection;
    }

    public Collection<Savings> getSavingsCollection() {
        return savingsCollection;
    }

    public void setSavingsCollection(Collection<Savings> savingsCollection) {
        this.savingsCollection = savingsCollection;
    }

    public Collection<HomeLoan> getHomeLoanCollection() {
        return homeLoanCollection;
    }

    public void setHomeLoanCollection(Collection<HomeLoan> homeLoanCollection) {
        this.homeLoanCollection = homeLoanCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acme.jpa.Account[ id=" + id + " ]";
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public Collection<SavingsTransactions> getSavingsTransactionsCollection() {
        return savingsTransactionsCollection;
    }

    public void setSavingsTransactionsCollection(Collection<SavingsTransactions> savingsTransactionsCollection) {
        this.savingsTransactionsCollection = savingsTransactionsCollection;
    }
    
}
