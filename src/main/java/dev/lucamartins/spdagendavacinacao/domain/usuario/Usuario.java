package dev.lucamartins.spdagendavacinacao.domain.usuario;

import dev.lucamartins.spdagendavacinacao.domain.alergia.Alergia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String nome;

    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    private String logradouro;

    private String numero;

    private String Setor;

    private String cidade;

    private String uf;

    @ManyToMany
    @JoinTable(
            name = "usuario_alergia",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "alergia_id")
    )
    private List<Alergia> alergias;
}