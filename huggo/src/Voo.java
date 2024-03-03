import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Voo {

  private final UUID id;
  private LocalDate data;
  private LocalTime horarioSaida;
  private LocalTime horarioChegada;
  private BigDecimal valor;
  private String origem;
  private String destino;
  private Integer assentosDisponiveis;

  public Voo() {
    this.id = UUID.randomUUID();
  }

  public UUID getId() {
    return id;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public LocalTime getHorarioSaida() {
    return horarioSaida;
  }

  public void setHorarioSaida(LocalTime horarioSaida) {
    this.horarioSaida = horarioSaida;
  }

  public LocalTime getHorarioChegada() {
    return horarioChegada;
  }

  public void setHorarioChegada(LocalTime horarioChegada) {
    this.horarioChegada = horarioChegada;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public String getOrigem() {
    return origem;
  }

  public void setOrigem(String origem) {
    this.origem = origem;
  }

  public String getDestino() {
    return destino;
  }

  public void setDestino(String destino) {
    this.destino = destino;
  }

  public Integer getAssentosDisponiveis() {
    return assentosDisponiveis;
  }

  public void setAssentosDisponiveis(Integer assentosDisponiveis) {
    this.assentosDisponiveis = assentosDisponiveis;
  }

  public String detalhesVoo() {
    return "Voo " + id +
            "\nData: " + data + ",\n" +
            "Horario Saida: " + horarioSaida + ",\n" +
            "Horario Chegada: " + horarioChegada + ",\n" +
            "Valor: " + valor + ",\n" +
            "Origem: " + origem + '\'' + ",\n" +
            "Destino: " + destino + '\'' + ",\n" +
            "Assentos Disponiveis: " + assentosDisponiveis;
  }
}

