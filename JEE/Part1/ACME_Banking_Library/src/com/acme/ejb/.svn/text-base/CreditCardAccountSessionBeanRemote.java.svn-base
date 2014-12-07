/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author s3391854
 */
@Remote
public interface CreditCardAccountSessionBeanRemote {

    String makePurchase(String customerId, String accountNumber, String description, double amount);

    String makeDeposit(String customerId, String savingsAccountNumber, String creditAccountNumber, double amount);
    
    String makeCreditRaiseRequest(String customerId, String accountNumber, BigDecimal creditlimit);
}
