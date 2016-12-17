package com.raveleen;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Святослав on 14.12.2016.
 */
public class DishDAO {

    @Autowired
    private EntityManager entityManager;

    public void addDish(Dish dish) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(dish);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Dish> getWholeList() {
        Query query = entityManager.createQuery("SELECT c FROM Dish c");
        return (List<Dish>) query.getResultList();
    }

    public List<Dish> getPriceList(int minPrice, int maxPrice) {
        Query query = entityManager.createQuery("SELECT c FROM Dish c WHERE (c.price >= :minPrice) AND (c.price <= :maxPrice)");
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        return (List<Dish>) query.getResultList();
    }

    public List<Dish> getDiscountList() {
        Query query = entityManager.createQuery("SELECT c FROM Dish c WHERE c.discount>0");
        return (List<Dish>) query.getResultList();
    }

    public List<Dish> getOnePortionList() {
        List<Dish> returnList = new ArrayList<Dish>();
        List<Dish> tempList;
        int temp = 0;
        int sum = 0;
        Query query = entityManager.createQuery("SELECT c FROM Dish c WHERE c.weight < 1000");
        tempList = query.getResultList();
        for (int i = 0; i < tempList.size(); i++) {
            for (Dish dish : tempList) {
                if (dish.getWeight() < (1000 - sum)) {
                    returnList.add(dish);
                }
                sum = sumWeight(returnList);
            }
        }
        return returnList;
    }

    private int sumWeight(List<Dish> list) {
        int sum = 0;
        for (Dish dish : list) {
            sum += dish.getPrice();
        }
        return sum;
    }
}
