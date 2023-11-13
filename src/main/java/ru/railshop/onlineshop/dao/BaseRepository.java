package ru.railshop.onlineshop.dao;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
public class BaseRepository<K extends Serializable, E extends BaseEntity<K>>
        implements Repository<K,E> {

    private final SessionFactory sessionFactory;
    private final Class<E> clazz;


    @Override
    public E save(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.save(entity);
        return entity;
    }

    @Override
    public void delete(K id) {
        @Cleanup var session = sessionFactory.openSession();
        session.delete(session.find(clazz,id));
        session.flush();
    }

    @Override
    public Optional<E> findById(K id) {
        @Cleanup var session = sessionFactory.openSession();
        return Optional.ofNullable(session.find(clazz,id));
    }

    @Override
    public List<E> findAll() {
        @Cleanup var session = sessionFactory.openSession();
        var criteria = session.getCriteriaBuilder().createQuery(clazz);
        criteria.from(clazz);
        return session.createQuery(criteria).getResultList();
    }

    @Override
    public void update(E entity) {
        @Cleanup var session = sessionFactory.openSession();
        session.merge(entity);


    }
}
