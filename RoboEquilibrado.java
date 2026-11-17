package ExercicioAvaliativo;

public class RoboEquilibrado extends Robo {

    public RoboEquilibrado(int id, String nome, int ataque, int defesa) {
        super(id, nome, ataque, defesa);
    }

    @Override
    public String getTipo() {
        return "Equilibrado";
    }

    @Override
    public int calcularAtaqueDoGolpe() {
        return getAtaque();
    }

    @Override
    protected int ajustarDanoRecebido(int dano) {
        return dano;
    }
}