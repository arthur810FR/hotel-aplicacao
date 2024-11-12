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
        boolean running = true;

        while (running) {
            try {
                System.out.println("\n===========================================");
                System.out.println("======== MENU DE GESTÃO DE HOTEIS =========");
                System.out.println("===========================================");
                System.out.println("| 1. Ver Hotéis Disponíveis               |");
                System.out.println("| 2. Cadastrar Cliente                    |");
                System.out.println("| 3. Fazer Reserva                        |");
                System.out.println("| 4. Ver Minhas Reservas                  |");
                System.out.println("| 5. Sair                                 |");
                System.out.println("===========================================");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        mostrarHoteisDisponiveis();
                        break;

                    case 2:
                        cadastrarCliente(scanner);
                        break;

                    case 3:
                        fazerReserva(scanner);
                        break;

                    case 4:
                        verReservasDoCliente(scanner);
                        break;

                    case 5:
                        running = false;
                        System.out.println("Obrigado por usar o sistema! Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida. Escolha um número entre 1 e 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número válido.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void mostrarHoteisDisponiveis() {
        List<Hotel> hoteisDisponiveis = hotelRepository.findAll();
        if (hoteisDisponiveis.isEmpty()) {
            System.out.println("Nenhum hotel disponível no momento.");
        } else {
            System.out.println("Hotéis disponíveis com quartos para reserva:");
            hoteisDisponiveis.forEach(hotel -> {
                long quartosDisponiveis = quartoRepository.countByHotelAndDisponibilidade(hotel, Disponibilidade.DISPONIVEL);
                System.out.printf("Hotel: %s\nLocalização: %s\nQuartos disponíveis: %d\n",
                        hotel.getNome(), hotel.getLocalizacao(), quartosDisponiveis);
                System.out.println("------------------------------------------");
            });
        }
    }

    private void cadastrarCliente(Scanner scanner) {
        String nome;
        while (true) {
            System.out.print("Digite o nome do cliente(Completo): ");
            nome = scanner.nextLine().trim();

            if (nome.matches("^[a-zA-Z\\s]+$") && nome.length() >= 3) {
                break;
            } else if (nome.length() < 3) {
                System.out.println("Erro: O nome deve ter pelo menos 3 caracteres.");
            } else {
                System.out.println("Nome inválido! Digite o nome apenas com letras e espaços.");
            }
        }

        String cpf;
        while (true) {
            System.out.print("Digite o CPF do cliente: ");
            cpf = scanner.nextLine().trim();

            if (cpf.isEmpty()) {
                System.out.println("Erro: O CPF não pode estar vazio.");
            } else if (cpf.matches("\\d{11}")) {
                break;
            } else {
                System.out.println("Erro: CPF inválido! Certifique-se de que contém exatamente 11 dígitos numéricos.");
            }
        }

        Cliente novoCliente = new Cliente(nome, cpf);
        clienteRepository.save(novoCliente);
        System.out.printf("Cliente '%s' cadastrado com sucesso!\n", nome);
    }



    private void fazerReserva(Scanner scanner) {
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

            System.out.println("Selecione o hotel pelo número:");
            for (int i = 0; i < listaHoteis.size(); i++) {
                System.out.printf("%d. %s - Localização: %s\n", i + 1, listaHoteis.get(i).getNome(), listaHoteis.get(i).getLocalizacao());
            }

            try {
                System.out.print("Digite o número do hotel desejado: ");
                int hotelNumero = scanner.nextInt();
                scanner.nextLine();

                if (hotelNumero < 1 || hotelNumero > listaHoteis.size()) {
                    System.out.println("Opção inválida. Por favor, insira um número válido da lista.");
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
                    System.out.println("Opção inválida para o tipo de quarto. Tente novamente.");
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
                        System.out.println("Formato de data inválido! Por favor, insira a data no formato dd/MM/yyyy.");
                    }
                }

                String resultadoReserva = reservaService.fazerReserva(cliente.getId(), hotel.getId(), tipoQuarto, dataInicio, dataFim);
                System.out.println(resultadoReserva);

            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada inválida. Por favor, insira valores corretos.");
                scanner.nextLine();
            }

        } else {
            System.out.println("Cliente não encontrado. Por favor, cadastre o cliente antes de fazer a reserva.");
        }
    }

    private void verReservasDoCliente(Scanner scanner) {
        System.out.print("Digite o CPF do cliente para ver suas reservas: ");
        String cpfConsulta = scanner.nextLine().trim();

        try {
            List<Reserva> reservas = reservaService.verReservasDoCliente(cpfConsulta);

            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva encontrada para este cliente.");
            } else {
                reservas.forEach(System.out::println);
            }
        } catch (RuntimeException e) {
            System.out.println("Erro ao buscar reservas: " + e.getMessage());
        }
    }
}
