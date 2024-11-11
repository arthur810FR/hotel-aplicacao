package desafio.Inc.desafioHotel.service;

import desafio.Inc.desafioHotel.enums.Disponibilidade;
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

    public String fazerReserva(Long clienteId, Long hotelId, String dataInicio, String dataFim) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        Optional<Hotel> hotelOpt = hotelRepository.findById(hotelId);

        if (clienteOpt.isPresent() && hotelOpt.isPresent()) {
            Optional<Quarto> quartoOpt = quartoRepository.findFirstByHotelAndDisponibilidade(
                    hotelOpt.get(), Disponibilidade.DISPONIVEL);

            if (quartoOpt.isPresent()) {
                Quarto quarto = quartoOpt.get();
                Reserva reserva = new Reserva(quarto, clienteOpt.get(), dataInicio, dataFim);
                reservaRepository.save(reserva);
                quarto.setDisponivel(false);
                quartoRepository.save(quarto);
                return "Reserva realizada com sucesso no " + hotelOpt.get().getNome();
            } else {
                return "Nenhum quarto disponível no hotel " + hotelOpt.get().getNome();
            }
        }
        return "Cliente ou hotel não encontrado.";
    }

    public List<Reserva> verReservasDoCliente(String cpf) {
        Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpf);
        if (clienteOpt.isPresent()) {
            return reservaRepository.findByClienteId(clienteOpt.get().getId());
        } else {
            throw new RuntimeException("Cliente não encontrado com o CPF fornecido.");
        }
    }
}
