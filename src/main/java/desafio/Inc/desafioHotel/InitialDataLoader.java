package desafio.Inc.desafioHotel;

import desafio.Inc.desafioHotel.enums.Disponibilidade;
import desafio.Inc.desafioHotel.enums.TiposQuarto;
import desafio.Inc.desafioHotel.model.Hotel;
import desafio.Inc.desafioHotel.model.Quarto;
import desafio.Inc.desafioHotel.repository.HotelRepository;
import desafio.Inc.desafioHotel.repository.QuartoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class InitialDataLoader {

    @Bean
    CommandLineRunner initializeDatabase(HotelRepository hotelRepository, QuartoRepository quartoRepository) {
        return args -> {

            if (hotelRepository.count() == 0) {
                List<Hotel> listaHoteis = Arrays.asList(
                        new Hotel("Hotel A", "Endereço A"),
                        new Hotel("Hotel B", "Endereço B"),
                        new Hotel("Hotel C", "Endereço C"),
                        new Hotel("Hotel D", "Endereço D"),
                        new Hotel("Hotel E", "Endereço E")
                );

                hotelRepository.saveAll(listaHoteis);
                System.out.println("Hotéis salvos no banco de dados.");

                for (Hotel hotel : listaHoteis) {
                    for (int i = 0; i < 5; i++) {
                        Quarto quartoNormal = new Quarto(TiposQuarto.NORMAL, Disponibilidade.DISPONIVEL, hotel);
                        Quarto quartoPremium = new Quarto(TiposQuarto.PREMIUM, Disponibilidade.DISPONIVEL, hotel);
                        quartoRepository.save(quartoNormal);
                        quartoRepository.save(quartoPremium);
                    }
                }
            } else {
                System.out.println("Dados já existem no banco de dados. Nenhuma ação necessária.");
            }
        };
    }
}
