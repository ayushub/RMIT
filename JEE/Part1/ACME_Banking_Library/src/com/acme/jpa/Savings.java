/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Yordan
 */
@Entity
@Table(name = "SAVINGS", catalog = "", schema = "APP")
@NamedQueries({
    @NamedQuery(name = "Savings.findAll", query = "SELECT s FROM Savings s"),
    @NamedQuery(name = "Savings.findById", query = "SELECT s FROM Savings s WHERE s.id = :id"),
    @NamedQuery(name = "Savings.findByBalance", query = "SELECT s FROM Savings s WHERE s.balance = :balance")})
public class Savings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @JoinColumn(name = "ACCOUNT_KEY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Account accountKey;

    public Savings() {
    }

    public Savings(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(Account accountKey) {
        this.accountKey = accountKey;
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
        if (!(object instanceof Savings)) {
            return false;
        }
        Savings other = (Savings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acme.jpa.Savings[ id=" + id + " ]";
    }
    
}
