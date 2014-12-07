/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import javax.ejb.Remote;

/**
 *
 * @author Yordan
 */
@Remote
public interface AccountSessionBeanRemote {

    String createSavingsAccount(String customerId, String accountNumber);
    String createCreditCardAccount(String customerId, String accountNumber, String creditcardnumber);

    String createHomeLoanAccount(String customerId, String accountNumber, double amountBorrowed);
    
}
