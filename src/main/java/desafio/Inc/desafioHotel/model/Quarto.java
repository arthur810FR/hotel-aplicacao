package desafio.Inc.desafioHotel.model;

import desafio.Inc.desafioHotel.enums.Disponibilidade;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "quarto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String tipo;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Disponibilidade disponibilidade;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public Quarto(String tipo, Disponibilidade disponibilidade, Hotel hotel) {
        this.tipo = tipo;
        this.disponibilidade = disponibilidade;
        this.hotel = hotel;
    }

    public boolean isDisponivel() {
        return this.disponibilidade == Disponibilidade.DISPONIVEL;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponibilidade = disponivel ? Disponibilidade.DISPONIVEL : Disponibilidade.INDISPONIVEL;
    }

    @Override
    public String toString() {
        return "Número do Quarto: " + id + "\n" +
                "Tipo de Quarto: " + tipo;
    }
}
