/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.ejb;

import com.acme.jpa.Account;
import com.acme.jpa.CreditCard;
import com.acme.jpa.CreditCardTransaction;
import com.acme.jpa.Savings;
import com.acme.jpa.SavingsTransactions;
import com.acme.jpa.controller.AccountFacade;
import com.acme.jpa.controller.CreditCardFacade;
import com.acme.jpa.controller.CreditCardTransactionFacade;
import com.acme.jpa.controller.CustomerFacade;
import com.acme.jpa.controller.SavingsFacade;
import com.acme.jpa.controller.SavingsTransactionsFacade;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

// Add business logic below. (Right-click in editor and choose
// "Insert Code > Add Business Method")
/**
 *
 * @author s3391854
 */
@Stateless
public class CreditCardAccountSessionBean implements CreditCardAccountSessionBeanRemote {

    @EJB
    private SavingsTransactionsFacade savingsTransactionsFacade;
    @EJB
    private CustomerFacade customerFacade;
    @EJB
    private AccountFacade accountFacade;
    @EJB
    private SavingsFacade savingsFacade;
    @EJB
    private CreditCardTransactionFacade creditCardTransactionFacade;
    @EJB
    private CreditCardFacade creditCardFacade;

    @Override
    public String makePurchase(String customerId, String accountNumber, String description, double amount) {

        String message = null;

        /* Customer exists */
        if (customerFacade.getCustomer(customerId) != null) {

            System.out.println("Customer Ok");

            /* Check is Credit Card account 
             * Check getting account from Customer.
             */
            Account account = accountFacade.getAccount(accountNumber);

            /* Account exists */
            if (account != null) {

                for (CreditCard creditCard : account.getCreditCardCollection()) {

                    BigDecimal credit = creditCard.getCredit();

                    if (creditCard.getCreditLimit().compareTo(credit.add(BigDecimal.valueOf(amount))) >= 0) {

                        BigDecimal newCredit;
                        newCredit = credit.add((BigDecimal.valueOf(amount)));

                        creditCard.setCredit(newCredit);


                        /* Withdrawals (credit) should be a negative amount */
                        BigDecimal purchaseAmount;
                        purchaseAmount = BigDecimal.valueOf(-1 * amount);

                        /* Create Credit Card Transaction entity */
                        CreditCardTransaction creditCardTx = new CreditCardTransaction();

                        creditCardTx.setCreditCardKey(creditCard);
                        creditCardTx.setDescription(description);
                        creditCardTx.setAmount(purchaseAmount);

                        try {

                            creditCardTransactionFacade.create(creditCardTx);

                            creditCardFacade.edit(creditCard);

                            System.out.println(message = "Done!");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }

                    } else {
                        message = "Credit Card does not have enough credit.";
                    }
                }

            } else {
                message = "Account does not exist.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;

    }

    @Override
    public String makeDeposit(String customerId, String savingsAccountNumber, String creditAccountNumber, double amount) {

        String message = null;

        /* Customer exists */
        if (customerFacade.getCustomer(customerId) != null) {

            /* Check is Credit Card account 
             * Check getting account from Customer.
             */
            Account savingsAccount = accountFacade.getAccount(savingsAccountNumber);

            Account creditAccount = accountFacade.getAccount(creditAccountNumber);

            /* Account exists */
            if (savingsAccount != null && creditAccount != null) {

                /* Biz Logic for Savings Account  */
                Savings savingsRegister = null;

                for (Savings savings : savingsAccount.getSavingsCollection()) {
                    savingsRegister = savings;
                }

                BigDecimal balance = savingsRegister.getBalance();

                if (savingsRegister.getBalance().compareTo(BigDecimal.valueOf(amount)) >= 0) {

                    BigDecimal newBalance;
                    newBalance = balance.subtract((BigDecimal.valueOf(amount)));

                    savingsRegister.setBalance(newBalance);

                    /* Withdrawals should be a negative amount */
                    BigDecimal withdrawalAmount;
                    withdrawalAmount = BigDecimal.valueOf(-1 * amount);

                    /* Create Savings Transaction entity */
                    SavingsTransactions savingsTx = new SavingsTransactions();

                    savingsTx.setAccountKey(savingsAccount);
                    savingsTx.setCategory("Withdrawal");
                    savingsTx.setDescription("Deposit to Credit Account");
                    savingsTx.setAmount(withdrawalAmount);

                    /* Biz Logic for Credit Card */
                    CreditCard creditCardRegister = null;

                    for (CreditCard creditCard : creditAccount.getCreditCardCollection()) {
                        creditCardRegister = creditCard;
                    }

                    BigDecimal credit = creditCardRegister.getCredit();
                    BigDecimal newCredit;
                    newCredit = credit.subtract(BigDecimal.valueOf(amount));

                    creditCardRegister.setCredit(newCredit);

                    /* Deposits should be a positive amount */
                    BigDecimal depositAmount;
                    depositAmount = BigDecimal.valueOf(amount);

                    /* Create Credit Card Transaction entity */
                    CreditCardTransaction creditCardTx = new CreditCardTransaction();

                    creditCardTx.setCreditCardKey(creditCardRegister);
                    creditCardTx.setDescription("Deposit from Savings Account");
                    creditCardTx.setAmount(depositAmount);

                    try {

                        savingsFacade.edit(savingsRegister);

                        savingsTransactionsFacade.create(savingsTx);

                        creditCardFacade.edit(creditCardRegister);
                        
                        creditCardTransactionFacade.create(creditCardTx);

                        System.out.println(message = "Done!");

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }

                } else {
                    message = "Account does not have enough funds.";
                }

            } else {
                message = "Account(s) do(es) not exist.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;

    }

    @Override
    public String makeCreditRaiseRequest(String customerId, String accountNumber, BigDecimal creditlimit) {

        String message = null;

        /* Customer exists */
        if (customerFacade.getCustomer(customerId) != null) {

            /* Check is Credit Card account 
             * Check getting account from Customer.
             */
            Account account = accountFacade.getAccount(accountNumber);

            /* Account exists */
            if (account != null) {

                for (CreditCard creditCard : account.getCreditCardCollection()) {

                    BigDecimal credit = creditCard.getCredit();
                    // Checking the credit is zero
                    if (credit.compareTo((BigDecimal.valueOf(0))) == 0) {

                        int transactionCount = creditCard.getCreditCardTransactionCollection().size();

                        if (transactionCount >= 0) {
                            creditCard.setCreditLimit(creditlimit);

                            try {

                                creditCardFacade.edit(creditCard);

                                System.out.println(message = "Done!");

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                e.printStackTrace();
                            }

                        } else {
                            System.out.println("CreditCard should be used at least once.");
                        }
                    } else {
                        message = "Credit Card must have credit = $0.";
                    }
                }

            } else {
                message = "Account does not exist.";
            }

        } else {
            message = "Customer does not exist.";
        }

        return message;


    }
}
