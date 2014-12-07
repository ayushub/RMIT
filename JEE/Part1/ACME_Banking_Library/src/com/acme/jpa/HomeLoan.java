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
@Table(name = "HOME_LOAN", catalog = "", schema = "APP")
@NamedQueries({
    @NamedQuery(name = "HomeLoan.findAll", query = "SELECT h FROM HomeLoan h"),
    @NamedQuery(name = "HomeLoan.findById", query = "SELECT h FROM HomeLoan h WHERE h.id = :id"),
    @NamedQuery(name = "HomeLoan.findByAmountBorrowed", query = "SELECT h FROM HomeLoan h WHERE h.amountBorrowed = :amountBorrowed"),
    @NamedQuery(name = "HomeLoan.findByAmountRepayed", query = "SELECT h FROM HomeLoan h WHERE h.amountRepayed = :amountRepayed")})
public class HomeLoan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "AMOUNT_BORROWED")
    private BigDecimal amountBorrowed;
    @Column(name = "AMOUNT_REPAYED")
    private BigDecimal amountRepayed;
    @JoinColumn(name = "ACCOUNT_KEY", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Account accountKey;

    public HomeLoan() {
    }

    public HomeLoan(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmountBorrowed() {
        return amountBorrowed;
    }

    public void setAmountBorrowed(BigDecimal amountBorrowed) {
        this.amountBorrowed = amountBorrowed;
    }

    public BigDecimal getAmountRepayed() {
        return amountRepayed;
    }

    public void setAmountRepayed(BigDecimal amountRepayed) {
        this.amountRepayed = amountRepayed;
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
        if (!(object instanceof HomeLoan)) {
            return false;
        }
        HomeLoan other = (HomeLoan) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acme.jpa.HomeLoan[ id=" + id + " ]";
    }
    
}
