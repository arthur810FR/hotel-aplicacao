package desafio.Inc.desafioHotel.service;

import desafio.Inc.desafioHotel.enums.Disponibilidade;
import desafio.Inc.desafioHotel.enums.TiposQuarto;
import desafio.Inc.desafioHotel.exceptions.ClienteNaoEncontrado;
import desafio.Inc.desafioHotel.exceptions.HotelNaoEncontrado;
import desafio.Inc.desafioHotel.exceptions.QuartoIndisponivel;
import desafio.Inc.desafioHotel.model.Cliente;
import desafio.Inc.desafioHotel.model.Hotel;
import desafio.Inc.desafioHotel.model.Quarto;
import desafio.Inc.desafioHotel.model.Reserva;
import desafio.Inc.desafioHotel.repository.ClienteRepository;
import desafio.Inc.desafioHotel.repository.HotelRepository;
import desafio.Inc.desafioHotel.repository.QuartoRepository;
import desafio.Inc.desafioHotel.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    public String criarReserva(Long idCliente, Long idHotel, TiposQuarto tipoQuarto, String dataInicio, String dataFim) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNaoEncontrado("Não foi possível localizar o cliente com ID: " + idCliente));

        Hotel hotel = hotelRepository.findById(idHotel)
                .orElseThrow(() -> new HotelNaoEncontrado("Hotel com ID: " + idHotel + " não encontrado. Verifique e tente novamente."));

        Quarto quartoDisponivel = quartoRepository.findFirstByHotelAndDisponibilidadeAndTipo(hotel, Disponibilidade.DISPONIVEL, tipoQuarto)
                .orElseThrow(() -> new QuartoIndisponivel("Desculpe, todos os quartos do tipo " + tipoQuarto + " estão indisponíveis no hotel " + hotel.getNome() + "."));

        Reserva reserva = new Reserva(quartoDisponivel, cliente, dataInicio, dataFim);
        reservaRepository.save(reserva);

        quartoDisponivel.setDisponivel(false);
        quartoRepository.save(quartoDisponivel);

        return "Reserva realizada com sucesso no " + hotel.getNome() + ". Aproveite sua estadia!";
    }

    public List<Reserva> buscarReservasPorCliente(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontrado("Cliente com CPF: " + cpf + " não encontrado. Verifique o CPF e tente novamente."));

        return reservaRepository.findByClienteId(cliente.getId());
    }
}
