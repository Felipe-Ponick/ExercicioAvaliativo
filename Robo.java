package ExercicioAvaliativo;

// HERANÇA: Robo é a superclasse ABSTRATA. Guarda tudo o que é comum aos três tipos
// de robô. Não pode ser instanciada (não existe "new Robo"), só as subclasses.
public  abstract class Robo {

    public static final int energiaMaxima = 100;
    public static final int energiaMinina = 30;
    public static final int danoMinimo = 5;
    public static final int bonusPar = 5;

    // ENCAPSULAMENTO: todos os atributos são private. Nem as subclasses acessam
    // direto; tudo passa pelos getters e pelos métodos com regras abaixo.
    private final int id;
    private final String nome;
    private final int ataque;
    private final int defesa;
    private int energiaAtual;
    private int vitorias;
    private int derrotas;
    private int pontos;

    private String memoriaDoUltimoGolpe = "";

    // HERANÇA: este construtor é chamado pelas subclasses através do super(...).
    // ENCAPSULAMENTO: as validações ficam concentradas aqui, uma vez só para os três tipos.
    public Robo(int id, String nome, int ataque, int defesa){

        if( !idValido(id)){
            throw new IllegalArgumentException("O id deve ser positivo");
        }
        if( nome == null || nome.isBlank()){
            throw new IllegalArgumentException("O nome não pode ser vazio");
        }
        if(!ataqueValido(ataque)){
            throw new IllegalArgumentException("Valor de ataque inválido. (10 a 30)");
        }
        if(!defesaValida(defesa)){
            throw new IllegalArgumentException("Valor de defesa inválida. (0 a 20)");
        }

        this.id = id;
        this.nome = nome;
        this.ataque = ataque;
        this.defesa = defesa;
        this.energiaAtual = energiaMaxima;
        this.vitorias = 0;
        this.derrotas = 0;
        this.pontos = 0;
    }

    public static boolean idValido( int id ) {
        return id >= 0;
    }
    public static boolean ataqueValido( int ataque ) {
        return ataque >= 10 && ataque <= 30;
    }
    public static boolean defesaValida( int defesa ) {
        return defesa >= 0 && defesa <= 20;
    }

    // POLIMORFISMO: métodos abstratos. Cada subclasse é obrigada a sobrescrever
    // (@Override) com o seu próprio comportamento.
    public abstract String getTipo();

    protected abstract int calcularAtaqueDoGolpe();

    protected abstract int ajustarDanoRecebido(int dano);

    public int calcularDano(Robo adversario, int rodada) {
        // POLIMORFISMO: o Java executa a versão de calcularAtaqueDoGolpe() do objeto real
        // (Ofensivo soma +5, os outros não), sem nenhum if sobre o tipo.
        int ataqueDoGolpe = this.calcularAtaqueDoGolpe();
        int dano = ataqueDoGolpe - adversario.getDefesa();

        String passos = "ataque do golpe " + ataqueDoGolpe + " ("+ this.getTipo() + ", ataque base " + this.ataque + ")" + " - defesa " + adversario.getDefesa() + " = " + dano;
        if( dano < danoMinimo ) {
            dano = danoMinimo;
            passos += " -> mínimo " + danoMinimo;
        }

        if( rodada % 2 == 0) {
            dano += bonusPar;
            passos += " -> +" + bonusPar + " (rodada par) = " + dano;
        }

        // POLIMORFISMO: se o adversário for Defensivo, roda a versão que reduz 3;
        // se não, roda a versão que devolve o dano sem mudar.
        int danoFinal = adversario.ajustarDanoRecebido(dano);
        if( danoFinal != dano) {
            passos += " -> alvo " + adversario.getTipo() + " reduz para " + danoFinal;
        }

        memoriaDoUltimoGolpe = passos + " => Dano Final: " + danoFinal;
        return danoFinal;
    }

    public int atacar(Robo adversario, int rodada) {
        int dano = calcularDano(adversario, rodada);
        adversario.receberDano(dano);
        return dano;
    }

    // ENCAPSULAMENTO: private. A energia só diminui por meio de um ataque (atacar).
    private void receberDano(int dano) {
        if( dano < 0) {
            throw new IllegalArgumentException("Dano não pode ser negativo");
        }
        energiaAtual -= dano;
        if(energiaAtual < 0) {
            energiaAtual = 0;
        }
    }

    public boolean estaForaDeCombate() {
        return energiaAtual == 0;
    }

    public boolean podeCombater() {
        return energiaAtual >= energiaMinina;
    }

    // ENCAPSULAMENTO: pontos e resultados só mudam por estes métodos, seguindo as regras
    // (vitória +3, empate +1). Não existe setPontos().
    public void registrarVitoria(){
        vitorias++;
        pontos += 3;
    }
    public void registrarDerrota(){
        derrotas++;
    }
    public void registrarEmpate(){
        pontos++;
    }

    public static int custoRecuperacao(int quantidade) {
        return quantidade / 10;
    }

    // ENCAPSULAMENTO: a energia só aumenta por aqui, e só se passar em todas as regras.
    public void recuperarEnergia(int quantidadeEnergia) {
        if(quantidadeEnergia <= 0 || quantidadeEnergia % 10 != 0) {
            throw new IllegalArgumentException("A quantidade de energia precisa ser positiva e múltipla de 10");
        }
        int custoEnergia = custoRecuperacao(quantidadeEnergia);
        if(pontos < custoEnergia) {
            throw new IllegalArgumentException("Pontos insuficientes! Custo: " + custoEnergia + " pontos");
        }
        if(energiaAtual + quantidadeEnergia > energiaMaxima) {
            throw new IllegalArgumentException("A energia final não pode ser maior do que " + energiaMaxima);
        }
        energiaAtual += quantidadeEnergia;
        pontos -= custoEnergia;
    }

    public boolean podeSerExcluido() {
        return pontos <= 0 && vitorias <= 0  && derrotas <= 0;
    }

    public void exibirDados() {
        System.out.println("Id do Robô: " + id);
        System.out.println("Nome do robô: " + nome);
        // POLIMORFISMO: cada subclasse responde o seu próprio tipo.
        System.out.println("Tipo do robô: " + getTipo());
        System.out.println("Pontos de ataque: " + ataque);
        System.out.println("Pontos de defesa: " + defesa);
        System.out.println("Energia atual: " + energiaAtual);
        System.out.println("Quantidade de Vitórias: " + vitorias);
        System.out.println("Quantidade de Derrotas: " + derrotas);
        System.out.println("Pontuação atual: " + pontos);
        if(podeCombater()) {
            System.out.println("O robô está disponivel para combate");
        }else {
            System.out.println("O robô está se recuperando para o próximo combate");
        }
    }

    // ENCAPSULAMENTO: só getters (leitura). Nenhum setter para energia, pontos ou resultados.
    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getAtaque() { return ataque; }
    public int getDefesa() { return defesa; }
    public int getEnergiaAtual() { return energiaAtual; }
    public int getVitorias() { return vitorias; }
    public int getDerrotas() { return derrotas; }
    public int getPontos() { return pontos; }
    public String getMemoriaDoUltimoGolpe() { return memoriaDoUltimoGolpe; }
}