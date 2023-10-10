# Encurtador de URLs

Este projeto é uma API Rest em Kotlin que utiliza Spring Boot para criar um encurtador de URLs. O objetivo é tornar o endereço mais fácil de lembrar e possibilitar a verificação da quantidade de acessos por cada URL encurtada.

## Preparando o Ambiente

Primeiramente, você pode usar o site [Spring Initializr](https://start.spring.io/) para gerar um projeto Spring Boot personalizado. Escolha sua linguagem de programação de preferência, a versão do projeto e os metadados necessários.

Em seguida, foram escolhidas as dependências para este projeto. A configuração ficou como mostrado abaixo:

![Spring](https://github.com/Elardenberg/urlshortener/raw/main/assets/spring%20initializr.png)

Após selecionar as dependências, clique em "Generate" para baixar um arquivo Spring Boot com todas elas configuradas.

## Configuração da IDE

Para este projeto, a IDE utilizada foi o [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=linux). Instalada a IDE, abrir o projeto nela dará o seguinte resultado:

![Intellij](https://github.com/Elardenberg/urlshortener/raw/main/assets/intellij.png)


## Configurando o Banco de Dados

Certifique-se de ter um banco de dados instalado (por exemplo, PostgreSQL) e configure a conexão no seu projeto. Você pode usar o IntelliJ IDEA para isso:

1. Abra o IntelliJ IDEA e importe o projeto gerado.
2. No ícone do banco de dados à direita, clique em "+" -> Data Source e escolha o seu banco de dados.
3. Dê um nome para o Banco de Dados e selecione um banco de dados existente no seu servidor.

Se preferir, você pode usar ferramentas como o pgAdmin4 para criar o banco de dados com uma interface gráfica.

![IDE1](https://github.com/Elardenberg/urlshortener/raw/main/assets/db%20config%20path.png)

Dê um nome para o Banco de Dados e escolha um que já tenha sido feito no seu servidor.

![IDE2](https://github.com/Elardenberg/urlshortener/raw/main/assets/db%20configuration.png)

No pgAdmin4, é possível fazer essa criação com uma interface gráfica:

![PGADMIN](https://github.com/Elardenberg/urlshortener/raw/main/assets/pg%20admin%204.png)

## Executando o Projeto

Após configurar a conexão com o banco de dados, siga estas etapas para executar o projeto:

1. Abra a aplicação no IntelliJ IDEA (arquivo em destaque no projeto).
2. Clique com o botão direito do mouse e escolha "Run UrlshortenerApplication" ou pressione Shift + F10 para executar o projeto.
3. Se tudo estiver configurado corretamente, a aplicação estará rodando localmente na porta 8080.

Digite `http://localhost:8080` em seu navegador para acessar a aplicação.

# Estrutura do projeto

## Criação de Models

O projeto possui duas entidades:

### shortenedURL

- `id` (integer)
- `full_URL` (string)
- `shortened_URL` (string)
- `statistics` (statistics[])

### statistics

- `id` (integer)
- `shortenedURL_id` (integer)
- `click_datetime` (timestamp)

## Criação de Controllers

### shortURLController

#### [GET] ("/")

- Método: `listarURL()`
- Descrição: Retorna todas as URLs cadastradas, juntamente com os respectivos registros de cada vez que foram acessadas por meio do link encurtado.

#### [GET] ("/buscar/{texto}")

- Método: `procurarURLs(s: String)`
- Descrição: Procura por todas as URLs curtas que contenham a substring `s` e lista-as como no método anterior.

#### [GET] ("/{shortURL}")

- Método: `redirecionarShortURL(shortURL: String)`
- Descrição: Recebe a chave `shortURL` de uma URL cadastrada e redireciona para o respectivo endereço.

#### [POST] ("/")

- Método: `cadastrarURL(url: String)`
- Descrição: Cria um novo objeto da classe `shortenedURL` com base na URL passada como parâmetro.

#### [DELETE] ("/{id}")

- Método: `deletarURL(id: Int)`
- Descrição: Remove o registro da URL com o ID especificado do banco de dados.

#### [GET] ("/clicks")

- Método: `listarStatistics()`
- Descrição: Retorna todos os registros de acessos através do URL Shortener.

#### [GET] ("/clicks/id/{id}")

- Método: `listarStatisticsById(id: Int)`
- Descrição: Retorna todos os registros de acesso associados a uma URL específica com o ID especificado.

#### [GET] ("/clicks/dia/{day}")

- Método: `listarStatisticsByDay(day: String)`
- Descrição: Retorna todos os registros de acesso em um dia específico no formato 'dd-mm-yyyy'.

## Testando a API com o Postman

Para testar o método POST, foi utilizada a API Postman. Siga os passos abaixo para realizar testes com a API:

1. Escolha o método POST para o endpoint desejado.

2. Configure o cabeçalho "Content-Type":
   - Na aba "Key," insira "Content-Type."
   - Na aba "Value," defina o valor como "application/json."

3. No corpo da requisição, insira os dados do objeto em formato JSON para criar uma nova URL encurtada. Neste caso, a única informação necessária é o endereço completo da aplicação.

4. Envie a requisição.

Para testar o método `deletarURL`, siga os passos abaixo:

1. Escolha o método DELETE para o endpoint desejado.

2. Insira o ID da URL que você deseja excluir como parâmetro.

3. Envie a requisição.

Isso permitirá que você teste a API usando o Postman e realize operações como criar novas URLs encurtadas e excluir URLs existentes.

![POSTMAN](https://github.com/Elardenberg/urlshortener/raw/main/assets/postman%20post.png)

As requisições do tipo GET podem ser feitas pelo postman ou por um navegador web que possua uma ferramenta de visualização de arquivos _json_. Neste exemplo, foi utilizado o Firefox. Ao se realizar uma busca de urls, pode-se clicar diretamente na url encurtada gerada, à qual o navegador tentará se conectar, e a API o redirecionará para o endereço original.

![link1](https://github.com/Elardenberg/urlshortener/raw/main/assets/short%20link.png)

![link2](https://github.com/Elardenberg/urlshortener/raw/main/assets/redirected%20link.png)
