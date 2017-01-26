# StarCode
Teste de Desenvolvedor Android - TradeForce

Starcode é um app desenvolvido baseado em requisitos de um desafio para concorrer a uma vaga de desenvolvedor Android na empresa TradeForce.

Os requisitos do aplicativo são:

##### Objetivo Geral
_Criação de um aplicativo Android nativo capaz de listar personagens dos filmes Star Wars com informações obtidas a partir da leitura de um QRCode, as informações lidas deverão ser armazenadas localmente, o usuário poderá listá-las e selecionar itens para ver detalhes._

###### Funcionalidades Obrigatórias

1. Captura de QR Code e armazenamento do código lido em base de dados local._
* a. O QR Code deverá conter uma URL de personagem do Star Wars disponibilizada pela Star Wars Api. Exemplo: http://swapi.co/api/people/1/
* b. Dica: QR Codes podem ser gerados em qualquer site para geração deste tipo de código.
2. Exibir ao usuário uma listagem com os personagens já lidos e armazenados localmente. Os itens da lista devem conter o nome do usuário e a URL lida no QRCode.

3. Possibilitar ao usuário tocar em um item da lista para exibir os detalhes do personagem.
* a. Os detalhes devem ser obtidos da Star Wars Api.
* b. Junto com os detalhes do personagem deve ser exibida uma lista com os nomes dos filmes nos quais ele apareceu, também disponíveis na Star Wars Api.

###### Funcionalidades Desejáveis

1. Ao capturar o QR Code capturar e armazenar em base local também as informações de geolocalização do usuário no momento da captura.
2. Ao exibir os detalhes do personagem do usuário exibir:

* a. data da captura e dados de geolocalização da captura (onde foi capturado).
* b. na listagem de filmes do personagem exibir uma imagem de pôster para cada um dos filmes. Possibilitar ao usuário tocar no filme e redirecioná-lo para o site do filme.
* i. O pôster e o website de cada filme podem ser obtidos utilizando a The Movie Db Api.

3. Cache das informações:

* a. Após cada requisição fazer cache local dos dados do personagem e informações dos filmes (imagem, URL do filme etc).
* b. Possibilitar ao usuário visualizar as informações mesmo offline.

______________________

Conforme os requisitos solicitam, o aplicativo funciona por meio de leitura de QRCodes que contenham uma URL da [StarWars Api](http://swapi.co) válida (O app verifica se a URL é válida antes de fazer qualquer tipo de requisição). Obtém os dados via requisição HTTP e mostrando em uma lista os personagens e algumas informações sobre eles conforme houver a leitura dos QRCodes relacionados aos mesmos. 

Para desenvolver o aplicativo optei por seguir uma linha com o menor número de libs externas possível. Fazendo uso apenas do framework e classes nativas do Android/Java. Fazendo o app o mais enxuto possível, além de seguir as guidelines do [Material Design](https://material.io/guidelines/).

Utilizei então, apenas a lib [Zbar](https://github.com/ZBar/ZBar) para facilitar e abstrair o uso da câmera e a leitura dos QRCodes.

Outras informações relevantes do projeto:

- Requisições feitas via AsyncTask
- Dados persistidos e armazenados via SQLite


