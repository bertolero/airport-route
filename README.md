# airport-route

# Pre-requisitos para execução
- Java 11 instalado
- Maven 3.8.x instalado

# Como Compilar
Descompacte o código-fonte num diretório qualquer.
- Ex.: C:\meu-dir\install\

Abra um prompt de execução, e troque para o diretório em que o programa foi descompactado.
- Ex.: CD C:\meu-dir\install\

No diretório descompactado, digite:
- mvn clean install

Esse comando irá baixar as java dependências irá gerar o arquivo jar para a execução do programa.

# Como Executar
Ainda no diretório onde foi feita a descompactação, digite:
- java -jar target\airport-route-1.0-SNAPSHOT-jar-with-dependencies.jar <INPUT_FILE>

Onde:
- INPUT_FILE: arquivo de entrada com as conexões iniciais
- PORT_NUMBER: porta para execução do servidor http

# Estrutura de Pacotes
- br.com.bertol.io: contém as classes responsáveis por ler de arquivo e escrever em arquivo as conexões
- br.com.bertol.model: contém a classe que representa os nós (Airpot) e a classe que armazena a lista de nós
- br.com.bertol.ui: contém as classes com os dois tipos de interface
  - br.com.bertol.ui.rest: contém as classes referentes ao rest server
    - br.com.bertol.ui.rest.dto: contém os wrappers para a interface rest
  - br.com.bertol.ui.shell: contém a classe que referente a interface por linha de comando
- br.com.bertol.service: contém as classes com os serviços de negócio do programa
  - br.com.bertol.service.search: contém as classes que realizam a busca pelo melhor caminho
  - br.com.bertol.service.connections: contém serviços auxiliares para a atualização das rotas (add & update)

# Considerações sobre a solução
Uma vez identificado que o problema era de busca de melhor rota, foi implementado uma versão simples do algoritmo Dijkstra com foco em Java.

Dessa forma foi criada uma classe que representa o nó, as suas adjacências e o peso entre essas conexões (Airport), e também foi criada uma classe container para armazenar os nós em forma de lista.

A carga inicial do status dos nós é feita pela classe ReadRoutesFromFile. Como um dos requisitos é permitir atualizar as conexões que foram carregadas via arquivo, dois serviços auxiliares foram implementados:
- AirportInclusion
- RefreshConnections

Dessa forma, a lógica de adição de novas conexões e atualização de conexões existentes fica isolada e pôde ser reaproveitada.

Quando dado o comando para efetuar uma nova busca, a classe RouteSearcher é chamada para efetuar a tarefa. A sequência de ações segue como descrito abaixo:
- limpa os resultados de buscas anteriores para todos os nós
- verifica se a origem da busca existe. Se não existe retorna ORIGIN_NOT_FOUND
- verifica se o destino da busca existe. Se não existe retorna DESTINATION_NOT_FOUND
- realiza a busca pela rota. Se não existe uma rota retorna ROUTE_NOT_FOUND
- caso rota exista, retorna a rota no formato 'Nó' - 'Nó+' > 'valor da rota'

Independente da interface com o usuário, caso tenha uma rota válida, o formato a mesma será o mesmo

# API Rest
Dois enpoints foram implementados:
- searchBestRoute: faz a busca pela melhor rota
- addNewConnection: adiciona uma nova conexão entre aeroportos ou atualiza uma conexão existente

## searchBestRoute
Utiliza o método GET, recebe dois parâmetros:
- origin: nó de origem
- destination: nó de destino

Exemplo de request:
- http://localhost:10150/api/searchBestRoute?origin=GRU&destination=CDG

Retorno esperado:
```
{
  "route": "GRU - BRC - SCL - ORL - CDG > 40" 
}
```
# addNewConnection
Utiliza o método POST, recebe um payload em formato JSON como parâmetro. Em caso de sucesso, retorna o mesmo payload confirmando a operação. 

Exemplo de request:
- localhost:10150/api/addNewConnection/

Exemplo de payload:
```
{
    "origin": "BSB",
    "destination": "FTL",
    "distance": 10
}
```

Exemplo de retorno:
```
{
    "origin": "BSB",
    "destination": "FTL",
    "distance": 10
}
```