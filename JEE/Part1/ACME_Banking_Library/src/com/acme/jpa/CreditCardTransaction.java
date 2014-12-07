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
@Table(name = "CREDIT_CARD_TRANSACTIONS", catalog = "", schema = "APP")
@NamedQueries({
    @NamedQuery(name = "CreditCardTransaction.findAll", query = "SELECT c FROM CreditCardTransaction c"),
    @NamedQuery(name = "CreditCardTransaction.findById", query = "SELECT c FROM CreditCardTransaction c WHERE c.id = :id"),
    @NamedQuery(name = "CreditCardTransaction.findByDescription", query = "SELECT c FROM CreditCardTransaction c WHERE c.description = :description"),
    @NamedQuery(name = "CreditCardTransaction.findByAmount", query = "SELECT c FROM CreditCardTransaction c WHERE c.amount = :amount")})
public class CreditCardTransaction implements Serializable {
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
    @JoinColumn(name = "CREDIT_CARD_KEY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private CreditCard creditCardKey;

    public CreditCardTransaction() {
    }

    public CreditCardTransaction(Integer id) {
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

    public CreditCard getCreditCardKey() {
        return creditCardKey;
    }

    public void setCreditCardKey(CreditCard creditCardKey) {
        this.creditCardKey = creditCardKey;
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
        if (!(object instanceof CreditCardTransaction)) {
            return false;
        }
        CreditCardTransaction other = (CreditCardTransaction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acme.jpa.CreditCardTransaction[ id=" + id + " ]";
    }
    
}
