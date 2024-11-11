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
    CommandLineRunner initDatabase(HotelRepository hotelRepository, QuartoRepository quartoRepository) {
        return args -> {
            System.out.println("Inicializando dados de hotéis e quartos...");

            if (hotelRepository.count() == 0) {
                List<Hotel> hoteis = Arrays.asList(
                        new Hotel("Hotel A", "Endereço A"),
                        new Hotel("Hotel B", "Endereço B"),
                        new Hotel("Hotel C", "Endereço C"),
                        new Hotel("Hotel D", "Endereço D"),
                        new Hotel("Hotel E", "Endereço E")
                );

                hotelRepository.saveAll(hoteis);

                for (Hotel hotel : hoteis) {
                    for (int i = 0; i < 5; i++) {
                        Quarto quartoNormal = new Quarto(TiposQuarto.NORMAL, Disponibilidade.DISPONIVEL, hotel);
                        Quarto quartoPremium = new Quarto(TiposQuarto.PREMIUM, Disponibilidade.DISPONIVEL, hotel);
                        quartoRepository.save(quartoNormal);
                        quartoRepository.save(quartoPremium);
                    }
                }

                System.out.println("Dados iniciais de hotéis e quartos carregados com sucesso.");
            }
        };
    }
}
