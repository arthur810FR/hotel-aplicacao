package desafio.Inc.desafioHotel.repository;

import desafio.Inc.desafioHotel.enums.Disponibilidade;
import desafio.Inc.desafioHotel.model.Hotel;
import desafio.Inc.desafioHotel.model.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    Optional<Quarto> findFirstByHotelAndDisponibilidade(Hotel hotel, Disponibilidade disponibilidade);
    long countByHotelAndDisponibilidade(Hotel hotel, Disponibilidade disponibilidade);
}

