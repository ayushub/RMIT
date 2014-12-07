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
public interface SavingAccountSessionBeanRemote {

    String makeDeposit(String customerId, String accountNumber, double amount);

    String makeWithdrawal(String customerId, String accountNumber, double amount);

    String viewBalance(String customerId, String accountNumber);
    
}
