package ExercicioAvaliativo;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static ArrayList<Robo> robos = new ArrayList<>();

    public static void main(String[] args) {
        ArrayList<Robo> robos = new ArrayList<>();
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
            System.out.println("Opção 5: Excluir robô."); 
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
                    System.out.println("Id:");
                    int id = entrada.nextInt();
                    if(id < 0){
                        System.out.println("O id deve ser positivo.");
                        break;
                    }else{

                        for(Robo p: robos){
                            if(p.id == id){
                                System.out.println("Já existe um robô com este código");
                                break;
                            }
                        }
                    }
                    
                    System.out.println("Nome:");
                    String nome = entrada.next();

                    System.out.println("Ataque:");
                    int ataque = entrada.nextInt();
                    if(ataque < 10 ||ataque > 30){
                        System.out.println("Valor de ataque inválido");
                        break;
                    }else{
                        System.out.println("Ataque cadastrado");
                    }

                    System.out.println("Defesa:");
                    int defesa = entrada.nextInt();
                    if(defesa < 0 ||defesa > 20){
                        System.out.println("Valor de defesa inválido");
                        break;
                    }else{
                        System.out.println("Defesa cadastrada");
                    }
                    
                    Robo Robo = new Robo(
                        id,
                        nome,
                        ataque,
                        defesa
                    );

                    robos.add(Robo);
                    break;

                case 2: 
                    System.out.println("Informe o id do robô que você deseja consultar: ");
                        int idConsulta = entrada.nextInt(); 
                        Robo encontrado = null;
                        for(Robo p: robos){
                            if(p.id == idConsulta){
                            encontrado = p;
                            break;
                        }
                        
                        }
                        if (encontrado == null) 
                        {
                            System.out.println("Nenhum robô encontrado");
                            break;                 
                        }
                        else
                        {
                            encontrado.exibirDados();
                        }
                        break;

                
                case 3:
                    System.out.println("Robôs cadastrados:");
                        
                        if(robos.isEmpty()){
                            System.out.print("Nenhum robô cadastrado");
                            break;
                        }else{
                            for(Robo p : robos){
                                p.exibirDados();
                                System.out.print("-----------------------------\n");
                            }
                        }


                case 4:
                    System.out.println("Informe o id do primeiro robô do combate:");
                    int idCombatente = entrada.nextInt();
                    Robo robo1 = null;
                    for(Robo p: robos){
                        if(p.id == idCombatente){
                            robo1 = p;
                        }
                        }
                        if (robo1 == null) {
                        System.out.println("Código informado não existe!");
                        continue;
                        }
                            if(robo1.energiaAtual > 30){
                                System.out.println("Robô " + robo1.nome + " selecionado.");
                            }else{
                                System.out.println("Robô " + robo1.nome + " indisponivel para combate.");
                                continue;
                            }
                       
                    


                    System.out.println("Informe o id do segundo robô do combate:");
                    int idCombatente2 = entrada.nextInt();
                    Robo robo2 = null;
                    for(Robo p: robos){
                        if(p.id == idCombatente2){
                            robo2 = p;
                        }
                        }
                        if (robo2 == null) {
                            System.out.println("Código informado não existe!");
                            continue;
                        }   
                            if(robo2.energiaAtual > 30){
                                System.out.println("Robô " + robo2.nome + " selecionado.");
                            }else{
                                System.out.println("Robô " + robo2.nome + " indisponivel para combate.");
                                continue;
                            }
                        

                        if(idCombatente == idCombatente2){
                            System.out.println("Escolha dois robos diferentes");
                            continue;
                        }
                    
                    

                    System.out.println("===============================");
                    System.out.println("---------LUTA DE ROBOS---------");
                    System.out.println("===============================");

                    Robo primeiro;
                    Robo segundo;

                    if(robo1.pontos < robo2.pontos){
                        primeiro = robo1;
                        segundo = robo2;
                    }else if(robo2.pontos < robo1.pontos){

                        primeiro = robo2;
                        segundo = robo1;
                    }else{
                        if(robo1.id < robo2.id){
                            primeiro = robo1;
                            segundo = robo2;
                        }else{
                            primeiro = robo2;
                            segundo = robo1;
                        }
                    }

                    for(int i = 1; i <= 5; i++){
                        System.out.println("===============================");
                        System.out.println("---------" + i + "º ROUND---------");
                        System.out.println("===============================");
                        
                        int dano = primeiro.calcularDano(segundo);
                        if(i % 2 == 0){
                            dano += 5;
                        }
                        segundo.receberDano(dano);
                        System.out.println(primeiro.nome + " causou " + dano + " de dano. Energia de " + segundo.nome + ": " + segundo.energiaAtual );
                        if(segundo.energiaAtual == 0){
                            System.out.println(segundo.nome + "está fora de combate");
                            break;
                        }

                        dano = segundo.calcularDano(primeiro);
                        if(i % 2 == 0){
                            dano += 5;
                        }
                        primeiro.receberDano(dano);
                        System.out.println(segundo.nome + " causou " + dano + " de dano. Energia de " + primeiro.nome + ": " + primeiro.energiaAtual );
                        if(primeiro.energiaAtual == 0){
                            System.out.println(primeiro.nome + "está fora de combate");
                            break;
                        }
                        }
                        if(robo1.energiaAtual > robo2.energiaAtual){
                            System.out.println("O robô vencedor é: " + robo1.nome);
                            robo1.registrarVitoria();
                            robo2.registrarDerrota();
                            
                        }else if(robo2.energiaAtual > robo1.energiaAtual){
                            System.out.println("O robô vencedor é: " + robo2.nome);
                            robo2.registrarVitoria();
                            robo1.registrarDerrota();
                        }else{
                            System.out.println("Empate");
                            robo1.registrarEmpate();
                            robo2.registrarEmpate();
                        }
                        break;
                    
                case 5:
                        System.out.println("Informe o id do robô que você deseja recuperar: ");
                        idConsulta = entrada.nextInt(); 
                        encontrado = null;
                        for(Robo p: robos){
                            if(p.id == idConsulta){
                            encontrado = p;
                            break;
                        }
                        }
                        if (encontrado == null) 
                        {
                            System.out.println("Nenhum robô encontrado");                       
                        }
                        else
                        {
                            System.out.println("Quanto de energia que você deseja recuperar?");
                            int quantidadeRecuperar = entrada.nextInt();
                            int custoEnergia = quantidadeRecuperar/10;

                            if (quantidadeRecuperar % 10 != 0 && quantidadeRecuperar >= 0){
                                    System.out.println("A quantidade de energia informada precisa ser positiva e múltipla do 10");
                                }else if(encontrado.pontos < custoEnergia){
                                    System.out.println("Pontos insuficientes! Custo: " + custoEnergia + " de energia");
                                }else if(encontrado.energiaAtual + quantidadeRecuperar > 100){
                                    System.out.println("A energia final não pode ser maior que 100");
                                }else{
                                    encontrado.recuperarEnergia(quantidadeRecuperar);
                                    System.out.println("Energia recuperada! Energia atual: " + encontrado.energiaAtual);
                                }
                        }
                        break;
                case 6:
                        System.out.println("Informe o id do robô que vocÊ deseja excluir:");
                        idConsulta = entrada.nextInt(); 
                        encontrado = null;
                        for(Robo p: robos){
                            if(p.id == idConsulta){
                            encontrado = p;
                            break;
                        }
                        }
                        if (encontrado == null) {
                            System.out.println("Nenhum robô encontrado");  
                        }else{
                            if(encontrado.pontos <= 0 && encontrado.vitorias <= 0 && encontrado.derrotas <= 0){
                                robos.remove(encontrado);
                            }
                        }


                    
            }
            
        }
        while(opcao != 10);
        {
            System.out.println("Programa encerrado.");
        }
    }
        
    }



