/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.jpa.controller;

import com.acme.jpa.Employee;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Yordan
 */
@Stateless
@LocalBean
public class EmployeeFacade extends AbstractFacade<Employee> {
    @PersistenceContext(unitName = "ACME_Banking_System-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }
    
}
