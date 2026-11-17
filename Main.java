package ExercicioAvaliativo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // POLIMORFISMO: uma única lista do tipo da superclasse (Robo) guarda objetos
    // das três subclasses (Ofensivo, Defensivo e Equilibrado).
    static ArrayList<Robo> robos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int opcao;
        do
        {
            System.out.println("============================================");
            System.out.println("--------------Menu de escolhas--------------");
            System.out.println("============================================");
            System.out.println("-------Escolha uma das opções abaixo:-------");
            System.out.println("Opção 1: Cadastrar Robô.");
            System.out.println("Opção 2: Consultar Robô pelo id.");
            System.out.println("Opção 3: Listar Robôs.");
            System.out.println("Opção 4: Combate de robôs.");
            System.out.println("Opção 5: Recuperar energia de um robô.");
            System.out.println("Opção 6: Excluir robô.");
            System.out.println("============================================");

            try
            {
                opcao = entrada.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Opção inválida! Digite um número de 1 a 6.");
                entrada.next();
                opcao = 0;
                continue;
            }

            switch (opcao)
            {
                case 1 :
                    cadastrarRobo(entrada);
                    break;
                case 2:
                    consultarRobo(entrada);
                    break;
                case 3:
                    listarRobos();
                    break;
                case 4:
                    combate(entrada);
                    break;
                case 5:
                    recuperarEnergia(entrada);
                    break;
                case 6:
                    excluirRobo(entrada);
                    break;
            }

        }
        while(opcao != 10);

        System.out.println("Programa encerrado.");
    }

static void cadastrarRobo(Scanner entrada) {
    System.out.println("Id:");
    int id = entrada.nextInt();
    if(!Robo.idValido(id)) {
        System.out.println("O id deve ser positivo.");
        return;
    }
    if( buscarPorId(id) != null ) {
        System.out.println("Já existe um robô com este código");
        return;
    }

    System.out.println("Nome:");
    String nome = entrada.next();

    System.out.println("Ataque:");
    int ataque = entrada.nextInt();
    if(!Robo.ataqueValido(ataque)) {
        System.out.println("Valor de ataque inválido");
        return;
    }
    System.out.println("Ataque cadastrado");

    System.out.println("Defesa:");
    int defesa = entrada.nextInt();
    if(!Robo.defesaValida(defesa)) {
        System.out.println("Valor de defesa inválido");
        return;
    }
    System.out.println("Defesa cadastrada");

    System.out.println("Tipo do robô:");
    System.out.println("1 - Ofensivo (+5 de ataque em cada golpe)");
    System.out.println("2 - Defensivo (recebe 3 de dano a menos, mínimo 2)");
    System.out.println("3 - Equilibrado (sem modificadores)");
    int tipo = entrada.nextInt();

    // POLIMORFISMO: a variável é do tipo Robo, mas guarda um objeto de uma subclasse.
    // O tipo só é escolhido aqui, na criação do objeto, nunca no cálculo do dano.
    Robo robo;
        switch (tipo) {
            case 1:
                robo = new RoboOfensivo( id, nome, ataque, defesa);
                break;
            case 2:
                robo = new RoboDefensivo( id, nome, ataque, defesa);
                break;
            case 3:
                robo = new RoboEquilibrado( id, nome, ataque, defesa);
                break;
            default:
                System.out.println("Tipo inválido");
                return;
        }

    robos.add(robo);
    System.out.println("Robô " + robo.getNome() + " (" + robo.getTipo() + ") cadastrado");
}

static void consultarRobo(Scanner entrada) {
    System.out.println("Informe o id do robô que você deseja consultar: ");
    Robo encontrado = buscarPorId(entrada.nextInt());
        if(encontrado == null) {
            System.out.println("Nenhum robô encontrado");
        } else {
            encontrado.exibirDados();
        }
}

static void listarRobos() {
    System.out.println("Robôs cadastrados:");

    if(robos.isEmpty()){
        System.out.println("Nenhum robô cadastrado");
        return;
    }
    for(Robo p : robos){
        // POLIMORFISMO: exibirDados() mostra o getTipo() de cada objeto real da lista.
        p.exibirDados();
        System.out.print("-----------------------------\n");
    }
}

static void combate(Scanner entrada) {
    System.out.println("Informe o id do primeiro robô do combate:");
    Robo robo1 = selecionarCombatente(entrada.nextInt());
    if (robo1 == null) return;

    System.out.println("Informe o id do segundo robô do combate:");
    Robo robo2 = selecionarCombatente(entrada.nextInt());
    if (robo2 == null) return;

    if(robo1 == robo2) {
        System.out.println("Escolha dois robos diferentes");
        return;
    }

    System.out.println("===============================");
    System.out.println("---------LUTA DE ROBOS---------");
    System.out.println(robo1.getNome() + " (" + robo1.getTipo() + ")  x  "+ robo2.getNome() + " (" + robo2.getTipo() + ")");
    System.out.println("===============================");

    Robo primeiro;
    Robo segundo;

    // ENCAPSULAMENTO: o Main lê pontos e id pelos getters, sem acessar os atributos.
    if(robo1.getPontos() < robo2.getPontos()) {
        primeiro = robo1;
        segundo = robo2;
    }else if(robo2.getPontos() < robo1.getPontos()) {
        primeiro = robo2;
        segundo = robo1;
    }else if(robo1.getId() < robo2.getId()) {
        primeiro = robo1;
        segundo = robo2;
    }else {
        primeiro = robo2;
        segundo = robo1;
    }

    for(int rodada = 1; rodada <= 5; rodada++) {
        System.out.println("===============================");
        System.out.println("---------" + rodada + "º ROUND---------");
        System.out.println("===============================");

        if (golpe(primeiro, segundo, rodada)) break;
        if (golpe(segundo, primeiro, rodada)) break;
    }

    // ENCAPSULAMENTO: o resultado é registrado só pelos métodos que aplicam as regras de pontos.
    if(robo1.getEnergiaAtual() > robo2.getEnergiaAtual()) {
        System.out.println("O robô vencedor é: " + robo1.getNome());
        robo1.registrarVitoria();
        robo2.registrarDerrota();
    }else if(robo2.getEnergiaAtual() > robo1.getEnergiaAtual()) {
        System.out.println("O robô vencedor é: " + robo2.getNome());
        robo2.registrarVitoria();
        robo1.registrarDerrota();
    }else {
        System.out.println("Empate");
        robo1.registrarEmpate();
        robo2.registrarEmpate();
    }
}

static boolean golpe(Robo atacante, Robo adversario, int rodada) {
    // POLIMORFISMO: nenhum if sobre o tipo aqui. atacar() executa por dentro os
    // métodos sobrescritos do objeto real (Ofensivo, Defensivo ou Equilibrado).
    int dano = atacante.atacar(adversario, rodada);
    System.out.println(atacante.getNome() + " causou " + dano + " de dano. Energia de " + adversario.getNome() + ": " + adversario.getEnergiaAtual());
    System.out.println("   Cálculo: " + atacante.getMemoriaDoUltimoGolpe());
    if ( adversario.estaForaDeCombate()) {
        System.out.println(adversario.getNome() + " está fora de combate");
        return true;
    }
    return false;
}

static Robo selecionarCombatente( int id) {
    Robo p = buscarPorId(id);
    if(p == null) {
        System.out.println("Código informado não existe!");
        return null;
    }
    if(p.getEnergiaAtual() <= 0) {
        System.out.println("Robô " + p.getNome() + " indisponível para combate.");
        return null;
    }
    System.out.println("Robô " + p.getNome() + " selecionado");
    return p;
}

static void recuperarEnergia( Scanner entrada) {
    System.out.println("Informe o id do robô que você deseja recuperar: ");
    Robo encontrado = buscarPorId(entrada.nextInt());
    if(encontrado == null) {
        System.out.println("Nenhum robô encontrado");
        return;
    }
    System.out.println("Quanto de energia que você deseja recuperar?");
    int quantidadeEnergia = entrada.nextInt();
    // ENCAPSULAMENTO: o Main não mexe na energia nem nos pontos. Quem valida e altera é o Robo.
    try {
        encontrado.recuperarEnergia(quantidadeEnergia);
        System.out.println("Energia recuperada! Energia Atual: " + encontrado.getEnergiaAtual());
    } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
}

static void excluirRobo( Scanner entrada) {
    System.out.println("Informe o id do robô que você deseja excluir:");
    Robo encontrado = buscarPorId(entrada.nextInt());
    if( encontrado == null){
        System.out.println("Nenhum robô encontrado");
    } else if ( encontrado.podeSerExcluido()) {
        robos.remove(encontrado);
        System.out.println("Robô excluído");
    }else {
        System.out.println("Não é possível excluir um robô que ja participou de combates");
    }
}

static Robo buscarPorId(int id) {
    for (Robo p : robos) {
        if(p.getId() == id) {
            return p;
        }
    }
    return null;
}

}