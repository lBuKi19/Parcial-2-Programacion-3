package repository;

import entidades.Base;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import util.JPAUtil;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T> {

    protected EntityManagerFactory emf;
    private Class<T> entityClass;

    public BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.emf = JPAUtil.getEntityManagerFactory();

    }

    //GUARDAR
    public T guardar(T entity) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.getTransaction().commit();
            return entity;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                System.out.println("Transaccion revertida por error");
            }
            throw e;
        } finally {
            em.close();
        }
    }

    //Busqueda por id
    public Optional<T> buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            T entity = em.find(entityClass, id);
            return Optional.ofNullable(entity);
        } finally {
            em.close();

        }
    }

    //listado de activos
    public List<T> listarActivos() {
        EntityManager em = emf.createEntityManager();
        try {
            List<T> activos = em.createQuery(
                            "SELECT u FROM " +entityClass.getSimpleName() + " u WHERE u.eliminado = false", entityClass)
                    .getResultList();
            return activos;
        }finally {
            em.close();
        }
    }

    //eliminado logico
    public boolean eliminarLogico(Long id) {
        EntityManager em = emf.createEntityManager();

        try {
            T entity = em.find(entityClass, id);

            if (entity == null) return false;
            em.getTransaction().begin();
            ((Base)entity).setEliminado(true);
            em.merge(entity);
            em.getTransaction().commit();
            return true;
        } finally {
            em.close();
        }
    }
}
