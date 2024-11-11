package desafio.Inc.desafioHotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String nome;

    @NonNull
    @Column(nullable = false)
    private String localizacao;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    private List<Quarto> quartos;

    public Hotel(String nome, String localizacao) {
        this.nome = nome;
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return "Nome do Hotel: " + nome + "\n" +
                "Localização: " + localizacao;
    }
}
