package dev.lucamartins.spdagendavacinacao.domain.alergia;

import dev.lucamartins.spdagendavacinacao.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "alergias")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Alergia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String nome;

    @ManyToMany(mappedBy = "alergias")
    private List<Usuario> usuarios = List.of();
}
