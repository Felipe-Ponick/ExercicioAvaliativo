package ExercicioAvaliativo;


public class RoboOfensivo  extends Robo{

    private static final int bonusAtaque = 5;


    public RoboOfensivo(int id, String nome, int ataque, int defesa) {
        super(id, nome, ataque, defesa);
    }

    @Override
    public String getTipo(){
        return "Ofensivo";
    }

    @Override
    public int calcularAtaqueDoGolpe(){
        return getAtaque() + bonusAtaque;
    }

    @Override
    protected int ajustarDanoRecebido(int dano) {
        return dano;
    }
    
    
}