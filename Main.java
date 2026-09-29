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
            System.out.println("Opção 5: Encerrar"); 
            System.out.println("============================================");

            try
            {
                opcao = entrada.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Opção inválida! Digite um número de 1 a 5.");
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
                            System.out.println("Robô " + robo1.nome + " selecionado.");
                        }
                    }
                    if (robo1 == null) {
                        System.out.println("Código informado não existe!");
                        continue;
                    }

                    System.out.println("Informe o id do segundo robô do combate:");
                    int idCombatente2 = entrada.nextInt();
                    Robo robo2 = null;
                    for(Robo p: robos){
                        if(p.id == idCombatente2){
                            robo2 = p;
                            System.out.println("Robô " + robo2.nome + " selecionado.");
                        }
                    }
                    if (robo2 == null) {
                        System.out.println("Código informado não existe!");
                        continue;
                    }

            
            }
            
        }
        while(opcao != 10);
        {
            System.out.println("Programa encerrado.");
        }
    }
        
    }



