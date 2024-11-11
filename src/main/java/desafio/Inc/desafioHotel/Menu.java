package desafio.Inc.desafioHotel;

import desafio.Inc.desafioHotel.enums.Disponibilidade;
import desafio.Inc.desafioHotel.enums.TiposQuarto;
import desafio.Inc.desafioHotel.model.Cliente;
import desafio.Inc.desafioHotel.model.Hotel;
import desafio.Inc.desafioHotel.model.Reserva;
import desafio.Inc.desafioHotel.repository.ClienteRepository;
import desafio.Inc.desafioHotel.repository.HotelRepository;
import desafio.Inc.desafioHotel.repository.QuartoRepository;
import desafio.Inc.desafioHotel.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Menu implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private ReservaService reservaService;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===========================================");
            System.out.println("========MENU DE GESTÃO DE HOTEIS===========");
            System.out.println("===========================================");
            System.out.println("---------------------------");
            System.out.println("|1. Ver Hotéis Disponíveis|");
            System.out.println("|2. Cadastrar Cliente     |");
            System.out.println("|3. Fazer Reserva         |");
            System.out.println("|4. Ver Minhas Reservas   |");
            System.out.println("|5. Sair                  |");
            System.out.println("---------------------------");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    List<Hotel> hoteisDisponiveis = hotelRepository.findAll();
                    if (hoteisDisponiveis.isEmpty()) {
                        System.out.println("Nenhum hotel disponível no momento.");
                    } else {
                        System.out.println("Hotéis disponíveis e quartos disponíveis para reserva:");
                        hoteisDisponiveis.forEach(hotel -> {
                            long quartosDisponiveis = quartoRepository.countByHotelAndDisponibilidade(hotel, Disponibilidade.DISPONIVEL);
                            System.out.println("Hotel: " + hotel.getNome() + ", Localização: " + hotel.getLocalizacao() +
                                    " - Quartos disponíveis: " + quartosDisponiveis);
                        });
                    }
                    break;

                case 2:
                    System.out.print("Digite o nome do cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Digite o CPF do cliente: ");
                    String cpf = scanner.nextLine();

                    Cliente novoCliente = new Cliente(nome, cpf);
                    clienteRepository.save(novoCliente);
                    System.out.println("Cliente cadastrado com sucesso!");
                    break;

                case 3:
                    System.out.print("Digite o CPF do cliente: ");
                    String cpfCliente = scanner.nextLine();
                    Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpfCliente);

                    if (clienteOpt.isPresent()) {
                        Cliente cliente = clienteOpt.get();

                        System.out.print("Digite o nome do hotel: ");
                        String nomeHotel = scanner.nextLine();
                        Optional<Hotel> hotelOpt = hotelRepository.findByNome(nomeHotel);

                        if (hotelOpt.isPresent()) {
                            Hotel hotel = hotelOpt.get();

                            System.out.print("Escolha o tipo de quarto (1 para Normal, 2 para Premium): ");
                            int tipoQuartoEscolhido = scanner.nextInt();
                            scanner.nextLine();
                            TiposQuarto tipoQuarto = tipoQuartoEscolhido == 1 ? TiposQuarto.NORMAL : TiposQuarto.PREMIUM;

                            System.out.print("Digite a data de início da reserva (yyyy-MM-dd): ");
                            String dataInicio = scanner.nextLine();
                            System.out.print("Digite a data de fim da reserva (yyyy-MM-dd): ");
                            String dataFim = scanner.nextLine();

                            String resultadoReserva = reservaService.fazerReserva(cliente.getId(), hotel.getId(), tipoQuarto, dataInicio, dataFim);
                            System.out.println(resultadoReserva);

                        } else {
                            System.out.println("Hotel não encontrado. Verifique o nome e tente novamente.");
                        }

                    } else {
                        System.out.println("Cliente não encontrado. Por favor, cadastre o cliente primeiro.");
                    }
                    break;

                case 4:
                    System.out.print("Digite o CPF do cliente para ver suas reservas: ");
                    String cpfConsulta = scanner.nextLine();

                    try {
                        List<Reserva> reservas = reservaService.verReservasDoCliente(cpfConsulta);

                        if (reservas.isEmpty()) {
                            System.out.println("Você não possui reservas.");
                        } else {
                            System.out.println("Suas reservas:");
                            reservas.forEach(System.out::println);
                        }
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;


                case 5:
                    running = false;
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}
