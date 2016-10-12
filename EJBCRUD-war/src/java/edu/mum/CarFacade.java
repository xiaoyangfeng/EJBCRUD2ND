/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.mum;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Blue
 */
@Stateless
public class CarFacade extends AbstractFacade<Car> {

    @PersistenceContext(unitName = "EJBCRUD-warPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarFacade() {
        super(Car.class);
    }
    
    public void updateYear(){
        List<Car> cars=findAll();
        for(Car car:cars){
            car.setYear(car.getYear()+1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @Schedule(minute = "*/1")
    public void carsTotalNumber(){
        System.err.println("Total numbers: "+ count());
    }
    

    @PostConstruct
    @Interceptors(LoggingInterceptor.class)
    public void Called() {
        System.out.println("ejb was called");
    }

    @PreDestroy
    @Interceptors(LoggingInterceptor.class)
    public void finished() {
        System.out.println("ejb was finished");
    }

}
