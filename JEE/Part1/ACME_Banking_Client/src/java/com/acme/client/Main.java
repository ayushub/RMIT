/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.client;

import com.acme.ejb.CustomerSessionBeanRemote;
import com.acme.ejb.EmployeeSessionBeanRemote;
import java.sql.Date;
import javax.ejb.EJB;

/**
 *
 * @author Yordan
 */
public class Main {
//    @EJB
//    private static UserSessionBeanRemote userSessionBean;

//    @EJB
//    private static HomeLoanSessionBeanRemote homeLoanSessionBean;
//    @EJB
//    private static UserSessionBeanRemote userSessionBean;
//
    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBean;
//    @EJB
//    private static CreditCardAccountSessionBeanRemote creditCardAccountSessionBean;
//    @EJB
//    private static SavingAccountSessionBeanRemote savingAccountSessionBean;
//    @EJB
//    private static AccountSessionBeanRemote accountSessionBean;
    @EJB
    private static CustomerSessionBeanRemote customerSessionBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {

        /*
         * Part 2:
         */
        /*
         * >> Major Functionality Use-Cases:
         */

        //1. Create a HomeLoan account

//        System.out.println("Creating HomeLoan Account... " + accountSessionBean.createHomeLoanAccount("C0001", "8080100", 11500.00));

//        System.out.println("Creating HomeLoan Account... " + accountSessionBean.createHomeLoanAccount("C0001", "8080102", 10500.00));


//
        //2. Make a repayment to a HomeLoan account

//        System.out.println("Deposit to homeloan account from savings account... " + homeLoanSessionBean.makeDeposit("C0001", "9080703", "8080100", 1500.00));
//
//        System.out.println("Deposit to homeloan account from savings account... " + homeLoanSessionBean.makeDeposit("C0001", "9080703", "8080100", 100.50));

//        System.out.println("Deposit to homeloan account from savings account... " + homeLoanSessionBean.makeDeposit("C0002", "401213", "8080103", 599.00));

        //
        //3. Perform a funds transfer to a customer from a different bank.
//
        //4. Customer login to Customer portal

        //userSessionBean.createUser("C01", "pass", "Yordan Bautista", "Customer");    

//        userSessionBean.createUser("C02", "pass", "Customer User Test", "Customer");    

        //System.out.println(userSessionBean.authenticateUser("C01", "pass")); 
//  
        //5. Employee login to Employee portal
//        
        //6. Transaction support for account-to-account transfers for the same customer.
//
        //7. Transaction support for the create customer wizard. 
//
        //8. Transaction support for Bank-to-Bank fund transfers.


        /*
         * Part 1:
         */

        /*
         * > Employee Major Functionality Use-Cases:
         */

        //1. Create two employees 	 
//        employeeSessionBean.createemployee("e0001", "Yordan", "Bautista", Date.valueOf("1981-11-07"), "330 Swanston St Melbourne");
//
//        employeeSessionBean.createemployee("e0002", "Ayush", "Agrawal", null, null);

        employeeSessionBean.createemployee("Employee", "Test", null, null);


        //2. Create three customers    	 
//        System.out.println(customerSessionBean.createcustomer("C0001", "John", "Smith", null, null));

//        System.out.println(customerSessionBean.createcustomer("C0002", "Carlo", "Graham", Date.valueOf("1981-12-07"), null));

//        System.out.println(customerSessionBean.createcustomer("C0003", "Vesta", "Taylor", null, null));


        // Stateful session bean method invocation
//       customerSessionBean.setPersonalDetails("James", "Morrison", Date.valueOf("1981-12-07"), "123 High St");
//       
//       Thread.sleep(5000);
//       
//       customerSessionBean.setEmploymentDetails("Developer", "BigSoft", 75.000);
//       
//       Thread.sleep(5000);
//       
//       customerSessionBean.setContactMethod("Phone");
//       
//       Thread.sleep(5000);
//       
//       customerSessionBean.createCustomer();

        //3. Create a Savings account
//        System.out.println("Creating Savings Account... " + accountSessionBean.createSavingsAccount("C0001", "9080701"));

//        System.out.println("Creating Savings Account... " + accountSessionBean.createSavingsAccount("C0001", "9080704"));

        // 4. Create a Credit account
//        System.out.println("Creating Credit Account... " + accountSessionBean.createCreditCardAccount("C0001", "9080702", "5217334426557799"));

        /*
         * > Customer Major Functionality Use-Cases:
         */
//    	1. Make deposit in Savings account

//        System.out.println("Making Deposit to Savings Account... " + savingAccountSessionBean.makeDeposit("C0001", "9080703", 9700.0));

//        System.out.println("Making Deposit to Savings Account... " + savingAccountSessionBean.makeDeposit("C0001", "9080703", 4300.0));

//        System.out.println("Making Deposit to Savings Account... " + savingAccountSessionBean.makeDeposit("C0001", "9080703", 4400.0));
//
//    	2. Make withdrawal from Savings account
//
//        System.out.println("Withdrawing from Savings Account... " + savingAccountSessionBean.makeWithdrawal("C0001", "9080703", 2000.0));
//
//    	3. View balance of Savings account
//
//        System.out.println("Balance:" + savingAccountSessionBean.viewBalance("C0001", "9080701"));

//    	4. Purchase on credit account
//        System.out.println("Purchase on credit account... " + creditCardAccountSessionBean.makePurchase("C0001", "9080702", "Fees Payment", 1000.0));

//    	5. Deposit to credit account from savings account
//    	System.out.println("Deposit to credit account from savings account... " + creditCardAccountSessionBean.makeDeposit("C0001", "9080701", "9080702", 50.0)) ;

//        6. Make credit limit increase request 
//        System.out.println("Request to increasse credit limit... " + creditCardAccountSessionBean.makeCreditRaiseRequest("C0001", "9080702", BigDecimal.valueOf(2500.0))) ;

//        6. Make credit limit increase request
//        System.out.println("Request to increasse credit limit... " + creditCardAccountSessionBean.makeCreditRaiseRequest("C0001", "9080702", BigDecimal.valueOf(2500.0)));



    }
}
