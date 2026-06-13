package entidades;

import lombok.experimental.SuperBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
public class Categoria extends Base {
    @ToString.Include
    @Column(nullable = false)
    private String nombre;
    @ToString.Include
    private String descripcion;

    @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
    private Set<Producto> productos = new HashSet<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }
}
