package desafio.Inc.desafioHotel.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quarto_id", nullable = false)
    @NonNull
    private Quarto quarto;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NonNull
    @Column(nullable = false)
    private String dataInicio;

    @NonNull
    @Column(nullable = false)
    private String dataFim;

    public Reserva(Quarto quarto, Cliente cliente, String dataInicio, String dataFim) {
        this.quarto = quarto;
        this.cliente = cliente;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    @Override
    public String toString() {
        return "Reserva:\n" +
                "Número do Quarto: " + quarto.getId() + "\n" +
                "Tipo de Quarto: " + quarto.getTipo() + "\n" +
                "Cliente:\n" + cliente.toString() + "\n" +
                "Data de Início: " + dataInicio + "\n" +
                "Data de Fim: " + dataFim;
    }
}
