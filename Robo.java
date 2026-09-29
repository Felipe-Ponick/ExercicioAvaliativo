package ExercicioAvaliativo;

public class Robo {
    public int id;
    public String nome;
    public int ataque = 0;
    public int defesa = 0;
    public int energiaAtual = 100;
    public int vitorias = 0;
    public int derrotas = 0;
    public int pontos = 0;

    public Robo(int id, String nome, int ataque, int defesa){
        this.id = id;
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
    }

    public void exibirDados(){
        System.out.println("Id do Robô: " + id);
        System.out.println("Nome do robô: " + nome);
        System.out.println("Pontos de ataque: " + ataque);
        System.out.println("Pontos de defesa" + defesa);
        System.out.println("Energia atual: " + energiaAtual);
        System.out.println("Qunatidade de Vitórias: " + vitorias);
        System.out.println("Quantiadae de Derrotas: " + derrotas);
        System.out.println("Pontuação atual: " + pontos);
        if(energiaAtual >= 30){
            System.out.println("O robô está disponivel para combate");
        }else if(energiaAtual <= 29){
            System.out.println("O robô está se recuperando para o próximo combate");
        }
    }


} 
