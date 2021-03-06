package ru.kata.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.model.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;
import java.util.List;

@Repository
@Transactional
public class UsersDaoImpl implements UsersDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Users user) {
        entityManager.persist(user);
    }

    @Override
    public List<Users> listUsers() {
        TypedQuery<Users> query = entityManager.createQuery("SELECT u FROM Users u", Users.class);
        return query.getResultList();
    }

    @Override
    public Users show(long id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    public void update(long id, @Valid Users updatedUser) {
        Users user = entityManager.find(Users.class, id);
        user.setName(updatedUser.getName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        entityManager.merge(user);
    }

    @Override
    public void delete(long id) {
        Users user = show(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
}
