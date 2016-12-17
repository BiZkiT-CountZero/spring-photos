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
public class PhotoDAO {

    @Autowired
    private EntityManager entityManager;

    public void addPhoto(Photo photo) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(photo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public byte[] getPhoto(long id) {
        try {
            Photo photo = entityManager.find(Photo.class, id);
            return photo.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Photo> getWholeList() {
        Query query = entityManager.createQuery("SELECT c FROM Photo c");
        return (List<Photo>) query.getResultList();
    }

    public void delete(long[] id) {
        try {
            entityManager.getTransaction().begin();
            for (long a : id) {
                Photo photo = entityManager.find(Photo.class, a);
                entityManager.remove(photo);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
            ex.printStackTrace();
        }
    }
}
