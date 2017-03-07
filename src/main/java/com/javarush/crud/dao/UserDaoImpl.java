package com.javarush.crud.dao;

import com.javarush.crud.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;
    private static final int limitResultsPerPage = 5;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void removeUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        if (user != null)
            session.delete(user);
    }

    @Override
    public User getUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsers(Long page) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User");
        query.setFirstResult((int)(page - 1) * limitResultsPerPage);
        query.setMaxResults(limitResultsPerPage);
        List<User> users = query.list();
        return users;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsers(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User WHERE NAME = :name");
        query.setParameter("name", name);
        List<User> users = query.list();
        return users;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsers(int age) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM User WHERE AGE = :age");
        query.setParameter("age", age);
        List<User> users = query.list();
        return users;
    }
}
