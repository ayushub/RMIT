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
import javax.validation.constraints.Size;

/**
 *
 * @author Yordan
 */
@Entity
@Table(name = "SAVINGS_TRANSACTIONS", catalog = "", schema = "APP")
@NamedQueries({
    @NamedQuery(name = "SavingsTransactions.findAll", query = "SELECT s FROM SavingsTransactions s"),
    @NamedQuery(name = "SavingsTransactions.findById", query = "SELECT s FROM SavingsTransactions s WHERE s.id = :id"),
    @NamedQuery(name = "SavingsTransactions.findByDescription", query = "SELECT s FROM SavingsTransactions s WHERE s.description = :description"),
    @NamedQuery(name = "SavingsTransactions.findByAmount", query = "SELECT s FROM SavingsTransactions s WHERE s.amount = :amount")})
public class SavingsTransactions implements Serializable {
    @Size(max = 10)
    @Column(name = "CATEGORY")
    private String category;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 50)
    @Column(name = "DESCRIPTION")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @JoinColumn(name = "ACCOUNT_KEY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Account accountKey;

    public SavingsTransactions() {
    }

    public SavingsTransactions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        if (!(object instanceof SavingsTransactions)) {
            return false;
        }
        SavingsTransactions other = (SavingsTransactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acme.jpa.SavingsTransactions[ id=" + id + " ]";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
