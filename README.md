# YouTube Downloader
#### Baixe vídeos e áudios do YouTube
* Roda em Windows e Linux;
* Idiomas: Potuguês e Inglês;
* Roda em background via terminal;
* Pesquisa vídeos do YouTube pelo nome;
* Salva até 20 links relacionados a pesquisa (se disponível);
* Mostra cada captura detalhadamente para a escolha do usuário (título, descrição, autor e duração);
* Opção de escolha entre vídeo ou áudio;
* Opções para escolha de resolução ou qualidade de vídeo/áudio para baixar;
* Renomeia .mp4 para .mp3 (se possível) após baixar, se a escolha for áudio;
* Salva o arquivo no diretório de downloads padrão.

### Requisitos
* python3
  - $ sudo apt-get install python3
* python3-pip
  - $ sudo apt-get install python3-pip
  - $ sudo pip install -U pip
* git
  - $ sudo apt-get install git
* pytube
  - $ sudo pip install -U pytube
* selenium
  - $ sudo pip install -U selenium
#### Windows (testado no windows 10 64bit)
* Navegador Microsoft Edge (só);
* msedgedriver (extrair na mesma pasta do script youdownloader.py).
#### Linux
* Navegador Chromium ou Firefox;
* chromedriver (Chromium) ou geckodriver (Firefox) em /usr/bin/ ou /usr/local/bin.

### Instalação e Execução
* $ git clone https://github.com/fabioskb/youtube_downloader
* $ cd youtube_downloader
* $ python3 youdownloader.py
