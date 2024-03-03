import java.util.UUID;

public class Reserva {

    private UUID idReserva;
    private String nome;
    private Integer quantidadePassageiros;
    private String contato;

    private Voo voo;

    public Reserva(String nome, Integer quantidadePassageiros, String contato, Voo voo) {
        this.idReserva = UUID.randomUUID();
        this.nome = nome;
        this.quantidadePassageiros = quantidadePassageiros;
        this.contato = contato;
        this.voo = voo;
    }

    public UUID getIdReserva() {
        return idReserva;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadePassageiros() {
        return quantidadePassageiros;
    }

    public void setQuantidadePassageiros(Integer quantidadePassageiros) {
        this.quantidadePassageiros = quantidadePassageiros;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public Voo getVoo() {
        return voo;
    }

    public void setVoo(Voo voo) {
        this.voo = voo;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", nome='" + nome + '\'' +
                ", quantidadePassageiros=" + quantidadePassageiros +
                ", contato='" + contato + '\'' +
                ", voo=" + voo +
                '}';
    }
}
