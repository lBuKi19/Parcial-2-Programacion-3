package repository;

import entidades.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoRepository extends BaseRepository<Producto>{

    public ProductoRepository() {
        super(Producto.class);
    }

    // Devuelve la lista de productos activos que pertenecen a la categoria especificada
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Producto> prodXCategoria = em.createQuery(
                            "SELECT p FROM Producto p WHERE p.categoria.id = :categoriaId AND p.eliminado = false", Producto.class)
                    .setParameter("categoriaId", categoriaId)
                    .getResultList();
            return prodXCategoria;
        } finally {
            em.close();
        }
    }
}
