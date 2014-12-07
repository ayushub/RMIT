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
public interface HomeLoanSessionBeanRemote {

    String makeDeposit(String customerId, String savingsAccountNumber, String homeLoanAccountNumber, double amount);
}
