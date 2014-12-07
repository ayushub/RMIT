/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CREDIT_CARD", catalog = "", schema = "APP")
@NamedQueries({
    @NamedQuery(name = "CreditCard.findAll", query = "SELECT c FROM CreditCard c"),
    @NamedQuery(name = "CreditCard.findById", query = "SELECT c FROM CreditCard c WHERE c.id = :id"),
    @NamedQuery(name = "CreditCard.findByCreditCardNumber", query = "SELECT c FROM CreditCard c WHERE c.creditCardNumber = :creditCardNumber"),
    @NamedQuery(name = "CreditCard.findByCreditLimit", query = "SELECT c FROM CreditCard c WHERE c.creditLimit = :creditLimit"),
    @NamedQuery(name = "CreditCard.findByCredit", query = "SELECT c FROM CreditCard c WHERE c.credit = :credit")})
public class CreditCard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "CREDIT_CARD_NUMBER")
    private String creditCardNumber;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CREDIT_LIMIT")
    private BigDecimal creditLimit;
    @Column(name = "CREDIT")
    private BigDecimal credit;
    @JoinColumn(name = "ACCOUNT_KEY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Account accountKey;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creditCardKey")
    private Collection<CreditCardTransaction> creditCardTransactionCollection;

    public CreditCard() {
    }

    public CreditCard(Integer id) {
        this.id = id;
    }

    public CreditCard(Integer id, String creditCardNumber) {
        this.id = id;
        this.creditCardNumber = creditCardNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public Account getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(Account accountKey) {
        this.accountKey = accountKey;
    }

    public Collection<CreditCardTransaction> getCreditCardTransactionCollection() {
        return creditCardTransactionCollection;
    }

    public void setCreditCardTransactionCollection(Collection<CreditCardTransaction> creditCardTransactionCollection) {
        this.creditCardTransactionCollection = creditCardTransactionCollection;
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
        if (!(object instanceof CreditCard)) {
            return false;
        }
        CreditCard other = (CreditCard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acme.jpa.CreditCard[ id=" + id + " ]";
    }
    
}
