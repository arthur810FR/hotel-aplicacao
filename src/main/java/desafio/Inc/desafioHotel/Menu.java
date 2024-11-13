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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
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
        boolean isRunning = true;

        while (isRunning) {
            try {
                exibirMenuPrincipal();

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1 -> exibirHoteisDisponiveis();
                    case 2 -> cadastrarNovoCliente(scanner);
                    case 3 -> realizarReserva(scanner);
                    case 4 -> exibirReservasDoCliente(scanner);
                    case 5 -> {
                        isRunning = false;
                        System.out.println("Saindo do sistema... Obrigado por utilizar nossos serviços!");
                    }
                    default -> System.out.println("Opção inválida. Por favor, escolha um número de 1 a 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Digite um número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n===========================================");
        System.out.println("======== SISTEMA DE GESTÃO DE HOTÉIS ======");
        System.out.println("===========================================");
        System.out.println("| 1. Ver Hotéis Disponíveis               |");
        System.out.println("| 2. Cadastrar Cliente                    |");
        System.out.println("| 3. Realizar Reserva                     |");
        System.out.println("| 4. Consultar Minhas Reservas            |");
        System.out.println("| 5. Sair                                 |");
        System.out.println("===========================================");
        System.out.print("Escolha uma opção: ");
    }

    private void exibirHoteisDisponiveis() {
        List<Hotel> hoteis = hotelRepository.findAll();
        if (hoteis.isEmpty()) {
            System.out.println("Nenhum hotel disponível no momento.");
        } else {
            System.out.println("Lista de Hotéis com Quartos Disponíveis:");
            hoteis.forEach(hotel -> {
                long quartosDisponiveis = quartoRepository.countByHotelAndDisponibilidade(hotel, Disponibilidade.DISPONIVEL);
                System.out.printf("Hotel: %s\nLocalização: %s\nQuartos disponíveis: %d\n",
                        hotel.getNome(), hotel.getLocalizacao(), quartosDisponiveis);
                System.out.println("------------------------------------------");
            });
        }
    }

    private void cadastrarNovoCliente(Scanner scanner) {
        String nome;
        while (true) {
            System.out.print("Digite o nome completo do cliente: ");
            nome = scanner.nextLine().trim();

            if (nome.matches("^[a-zA-Z\\s]+$") && nome.length() >= 3) {
                break;
            } else if (nome.length() < 3) {
                System.out.println("Erro: O nome deve ter no mínimo 3 caracteres.");
            } else {
                System.out.println("Erro: Nome inválido! Digite apenas letras e espaços.");
            }
        }

        String cpf;
        while (true) {
            System.out.print("Digite o CPF do cliente (apenas números): ");
            cpf = scanner.nextLine().trim();

            if (cpf.isEmpty()) {
                System.out.println("Erro: O CPF não pode estar vazio.");
            } else if (cpf.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("Erro: CPF inválido! Deve conter exatamente 11 dígitos.");
            }
        }

        Cliente novoCliente = new Cliente(nome, cpf);
        clienteRepository.save(novoCliente);
        System.out.printf("Cliente %s cadastrado com sucesso!\n", nome);
    }

    private void realizarReserva(Scanner scanner) {
        System.out.print("Digite o CPF do cliente: ");
        String cpfCliente = scanner.nextLine().trim();
        Optional<Cliente> clienteOpt = clienteRepository.findByCpf(cpfCliente);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();

            List<Hotel> listaHoteis = hotelRepository.findAll();
            if (listaHoteis.isEmpty()) {
                System.out.println("Nenhum hotel disponível para reserva no momento.");
                return;
            }

            System.out.println("Escolha o hotel pelo número correspondente:");
            for (int i = 0; i < listaHoteis.size(); i++) {
                System.out.printf("%d. %s - Localização: %s\n", i + 1, listaHoteis.get(i).getNome(), listaHoteis.get(i).getLocalizacao());
            }

            try {
                System.out.print("Digite o número do hotel desejado: ");
                int hotelNumero = scanner.nextInt();
                scanner.nextLine();

                if (hotelNumero < 1 || hotelNumero > listaHoteis.size()) {
                    System.out.println("Erro: Número inválido. Escolha um hotel da lista.");
                    return;
                }

                Hotel hotel = listaHoteis.get(hotelNumero - 1);

                System.out.println("Escolha o tipo de quarto:");
                System.out.println("1 - Quarto Normal");
                System.out.println("2 - Quarto Premium");
                System.out.print("Digite o número correspondente ao tipo de quarto: ");
                int tipoQuartoEscolhido = scanner.nextInt();
                scanner.nextLine();

                TiposQuarto tipoQuarto;
                if (tipoQuartoEscolhido == 1) {
                    tipoQuarto = TiposQuarto.NORMAL;
                } else if (tipoQuartoEscolhido == 2) {
                    tipoQuarto = TiposQuarto.PREMIUM;
                } else {
                    System.out.println("Erro: Tipo de quarto inválido. Escolha entre 1 e 2.");
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false);

                String dataInicio;
                String dataFim;

                while (true) {
                    try {
                        System.out.print("Digite a data de início da reserva (dd/MM/yyyy): ");
                        dataInicio = scanner.nextLine().trim();
                        dateFormat.parse(dataInicio);

                        System.out.print("Digite a data de fim da reserva (dd/MM/yyyy): ");
                        dataFim = scanner.nextLine().trim();
                        dateFormat.parse(dataFim);

                        break;
                    } catch (ParseException e) {
                        System.out.println("Erro: Formato de data inválido! Use dd/MM/yyyy.");
                    }
                }

                String resultadoReserva = reservaService.criarReserva(cliente.getId(), hotel.getId(), tipoQuarto, dataInicio, dataFim);
                System.out.println(resultadoReserva);

            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Verifique as opções e tente novamente.");
                scanner.nextLine();
            }

        } else {
            System.out.println("Erro: Cliente não encontrado. Cadastre o cliente antes de reservar.");
        }
    }

    private void exibirReservasDoCliente(Scanner scanner) {
        System.out.print("Digite o CPF do cliente para consultar reservas: ");
        String cpfConsulta = scanner.nextLine().trim();

        try {
            List<Reserva> reservas = reservaService.buscarReservasPorCliente(cpfConsulta);

            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva encontrada para este cliente.");
            } else {
                System.out.println("Reservas encontradas:");
                reservas.forEach(System.out::println);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar reservas: " + e.getMessage());
        }
    }
}
