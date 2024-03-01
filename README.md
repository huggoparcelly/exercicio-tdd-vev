# Exercicio Verificacao de Software

## Processador de Boletos
Deve-se implementar um processador de boletos. O objetivo desse processador é verificar todos os boletos e, caso o valor da soma de todos os boletos seja maior que o valor da fatura, então essa fatura deverá ser considerada como paga.

Uma fatura contém data, valor total e nome do cliente.
- [ ] Criar entidade ```Fatura``` com atributos: 
```
    data;
    valor total;
    nome do cliente.
```

Um boleto contém o código do boleto, data, e valor pago.
- [ ] Criar entidade ```Boleto``` com atributos
```
    código do boleto; 
    data;
    valor pago.
```

O processador de boletos, ao receber uma lista de boletos, deve então, 
para cada boleto, criar um ```pagamento``` associado a essa ```fatura```. 
Esse pagamento contém o valor pago, a data, e o tipo do pagamento efetuado (que nesse caso é "BOLETO").
- [ ] Criar método ```processador de boletos```, que tem como parâmetro uma lista de boletos.
- [ ] Iterar sobre a lista de boletos e criar um ```Pagamento``` para cada elemento.
- [ ] Criar entidade ```Pagamento``` com atributos
```
    valor pago;
    data;
    tipo de pagamento efetuado
```

Como dito anteriormente, caso a soma de todos os boletos seja igual ou ultrapasse o valor da fatura, a mesma deve ser marcada como "PAGA".
- [ ] Verificar se a soma dos ```boletos``` é igual ou maior que a ```fatura```
- [ ] Marcar fatura como ```Paga```

```
    Exemplo: Fatura de 1.500,00 com 3 boletos no valor de 500,00, 400,00 e 600,00: 
    Fatura marcada como PAGA, e três pagamentos do tipo BOLETO criados.
```

## Sistema de Reserva de Voo

Deve-se implementar um sistema que permita que os usuários pesquisem e reservem voos para destinos de sua escolha.

O sistema deve fornecer informações sobre voos disponíveis, datas, horários, preços e permitir que os usuários selecionem e reservem voos.
- [ ] Criar entidade ```Voo``` com atributos
```
    datas;
    horários;
    preços;
    origem;
    destino;
    lugares disponiveis;
```
- [ ] Criar método ```getVooDisponivel``` que retorna os detalhes de um voo disponível
```
    datas,
    horários,
    preços
```
- [ ] Criar iteração para usuário ```selecionar e reservar``` um voo disponível
- [ ] Criar método ```reservarVoo```

O sistema deve permitir que os usuários pesquisem voos com base em critérios como origem, destino, data e número de passageiros.
- [ ] Criar um método ```findVoos``` filtrando os voos com base nos parâmetros
```
    origem,
    destino,
    data,
    numero de passageiros
```

O sistema deve exibir uma lista de voos disponíveis com informações detalhadas, incluindo origem, destino, horário, preço e lugares disponíveis.
- [ ] Criar método ```getAllVoosDisponiveis``` que retorna info detalhada de todos os voos
```
    datas;
    horários;
    preços;
    origem;
    destino;
    lugares disponiveis;
```

Os usuários devem ser capazes de selecionar um voo e reservá-lo, inserindo detalhes como nome, número de passageiros e informações de contato.
- [ ] Criar entidade ```Reserva``` com atributos
```
    código de reserva,
    nome,
    numero de passageiros,
    informações de contato
```
- [ ] Criar método de ```criar reserva```

Os usuários devem poder cancelar uma reserva de voo, fornecendo o código de reserva ou identificação pessoal.
- [ ] Criar método de ```cancelamento de reserva``` feito pelo ```código``` ou ```identificação pessoal``` 

O sistema deve gerar uma confirmação de reserva contendo detalhes do voo, preço total e informações do passageiro.
- [ ] Criar método de ```confirmação de reserva``` com detalhes de
```
    voo,
    preço total,
    informações do passageiro
```