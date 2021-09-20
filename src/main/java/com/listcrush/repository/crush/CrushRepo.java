package com.listcrush.repository.crush;

import com.listcrush.model.Crush;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Transactional
public class CrushRepo implements ICrushRepo{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Crush> showAll() {
        String queryStr = "select  c from Crush as c";
        TypedQuery<Crush> cr = entityManager.createQuery(queryStr, Crush.class);
        return cr.getResultList();
    }

    @Override
    public void save(Crush crush) {
        if(crush!=null){
            entityManager.merge(crush);
        } else {
            entityManager.persist(crush);
        }
    }

    @Override
    public void delete(int id) {
        Crush crush = findById(id);
        if(crush != null){
            entityManager.remove(crush);
        }
    }

    @Override
    public Crush findById(int id) {
        TypedQuery<Crush> query = entityManager.createQuery("select c from Crush c where c.id=:id", Crush.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
