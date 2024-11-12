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

    public String fazerReserva(Long clienteId, Long hotelId, TiposQuarto tipoQuarto, String dataInicio, String dataFim) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontrado("Cliente com ID " + clienteId + " não encontrado."));

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new HotelNaoEncontrado("Hotel com ID " + hotelId + " não encontrado."));

        Quarto quarto = quartoRepository.findFirstByHotelAndDisponibilidadeAndTipo(hotel, Disponibilidade.DISPONIVEL, tipoQuarto)
                .orElseThrow(() -> new QuartoIndisponivel("Nenhum quarto do tipo " + tipoQuarto + " disponível no hotel " + hotel.getNome() + "."));

        Reserva reserva = new Reserva(quarto, cliente, dataInicio, dataFim);
        reservaRepository.save(reserva);

        quarto.setDisponivel(false);
        quartoRepository.save(quarto);

        return "Reserva realizada com sucesso no " + hotel.getNome();
    }

    public List<Reserva> verReservasDoCliente(String cpf) {
        Cliente cliente = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ClienteNaoEncontrado("Cliente com CPF " + cpf + " não encontrado."));

        return reservaRepository.findByClienteId(cliente.getId());
    }
}
