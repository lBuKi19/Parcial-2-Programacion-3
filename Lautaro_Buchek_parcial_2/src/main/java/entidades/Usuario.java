package entidades;
import jakarta.persistence.*;
import enums.Rol;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)

@Entity
public class Usuario extends Base {
    @ToString.Include
    @Column(nullable = false)
    private String nombre;
    @ToString.Include
    @Column(nullable = false)
    private String apellido;
    @ToString.Include
    @Column(nullable = false, unique = true)
    private String mail;
    @Column(nullable = false)
    private String celular;
    @Column(nullable = false)
    private String contrasena;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Builder.Default
    @OneToMany(mappedBy = "usuario")
    private Set<Pedido> pedidos = new HashSet<>();

    private static List<Usuario> listaUsuarios= new ArrayList<>();

    public Usuario(String nombre, String apellido, String mail, String celular, String contrasena, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasena = contrasena;
        this.rol = rol;
        listaUsuarios.add(this);
    }

    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }


}

