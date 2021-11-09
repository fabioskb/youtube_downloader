#!/usr/bin/python3
from interface import Interface
from time import sleep
from pytube import YouTube
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
import os
import locale
os.environ['TERM'] = 'xterm'


# CLASS YoutubeDownloader
class YoutubeDownloader(Interface):
    """docstrinsecsg for YoutubeDownloader"""
    lang = locale.getdefaultlocale()[0][0:2]

    def __init__(self):
        super(YoutubeDownloader, self).__init__()
        self.arq = 'video_links.txt'
        self.video = False
        self.audio = False
        self.video_existe = False
        if os.name == 'nt':
            self.pasta_download = fr'C:\Users\{os.getlogin()}\Downloads/'
        else:
            self.pasta_download = fr'/home/{os.getlogin()}/Downloads/'

# Criar Título com descrição e pesquisar pelo nome no Google.
    def pesquisar_capturar(self):
        if self.lang == 'pt':
            self.criar_banner(desc=True, mens="""\033[32m
* Idiomas: Português e Inglês;
* Compatível com Windows e Linux;
* Roda em background via terminal;            
* Pesquisa o possível nome do vídeo;
* Salva os links de até 20 vídeos relacionados a pesquisa;
* Mostra opções de todos os vídeos capturados, com título, descrição, autor e duração;
* Opções de escolha entre baixar em vídeo (mp4) ou áudio (mp3);
* Opções de escolha de resolução ou qualidade do vídeo/áudio para baixar.\033[m""")
        else:
            self.criar_banner(desc=True, mens="""\033[32m
* Languages: Portuguese and English;
* Compatible with Windows and Linux;
* Runs in background via terminal;
* Search the possible video name;
* Saves the links of up to 20 videos related to search;
* Shows options of all captured videos, with title, description, author and duration;
* Options to choose between downloading video (mp4) or audio (mp3);
* Options to choose video/audio resolution or quality to download.\033[m""")
        nome_video = self.entrada_txt(
            'Pesquise por: ' if self.lang == 'pt' else 'Search for: ')
        self.criar_banner(
            banner=False, mens='Pesquisando...' if self.lang == 'pt' else 'Searching...')
        if os.name == 'nt':
            options = webdriver.EdgeOptions()
            options.add_argument('headless')
            options.add_argument('disable-gpu')
            browser = webdriver.Edge('msedgedriver', options=options)
        else:
            try:
                options = webdriver.ChromeOptions()
                options.add_argument('headless')
                options.add_argument('disable-gpu')
                browser = webdriver.Chrome('chromedriver', options=options)
            except Exception as e:
                if os.name == 'posix':
                    print(f'\033[31m{e}\nNão foi possível utilizar o chromedriver.\033[m\n\033[32mAguarde!\nTentanto com Firefox...\033[m' if self.lang ==
                      'pt' else '\033[31mCould not use chromedriver.\033[m\n\033[32mWait!\nTrying with Firefox...\033[m')
                    options = webdriver.FirefoxOptions()
                    options.add_argument('--headless')
                    options.add_argument('disable-gpu')
                    browser = webdriver.Firefox(options=None)
                else:
                    pass
        browser.get("https://www.google.com/")
        pesq = browser.find_element(By.CLASS_NAME, 'gLFyf')
        pesq.send_keys('youtube ' + nome_video)
        pesq.send_keys(Keys.RETURN)

        sleep(2)

# Capturar e salvar os links dos vídeos do YouTube.
        elementos = browser.find_elements(By.TAG_NAME, 'a')
        self.remover(self.arq, printar=False)
        self.criar_banner(banner=False, mens='Capturando e salvando links...' if self.lang ==
                          'pt' else 'Capturing and saving links...')
        for link in elementos:
            href = link.get_attribute('href')
            if href is not None and 'https://www.youtube.com/watch' in href.split('?v='):
                self.editar_arquivo(href, self.arq)
        browser.quit()

# Remover links duplicados e obter informações de até 20 links
# salvos.
    def obter_baixar(self):
        self.criar_banner(mens='Removendo links duplicados e obtendo\ninformações...' if self.lang == 'pt' else 'Removing duplicate links and getting\ninformation...')
        links = self.abrir_arquivo(self.arq).split()

        for link in links:  # Remove duplicados.
            if links.count(link) > 1:
                links.remove(link)
        quant_links = str(len(links)) if len(links) <= 20 else '20'
        self.menu(titulo=quant_links + ' OPÇÕES' if self.lang ==
                  'pt' else ' OPTIONS')

        for c, link in enumerate(links):
            # Mostra os videos disponíveis detalhadamente.
            if len(links) > 20 and c == 20:
                break
            else:
                youtube = YouTube(link)
                title = youtube.title
                if self.lang == 'pt':
                    print(f'\033[1;36m[{c+1}] - {title}\033[m\n    '
                          f'\033[1;31mDescrição:\033[m\n        {youtube.description[0:801] if len(youtube.description) > 800 else youtube.description}...\n    '
                          f'\033[1;31mAutor:\033[m\n        {youtube.author}\n    '
                          f'\033[1;31mDuração:\033[m\n        {youtube.length / 60:.1f} minutos\n')
                else:
                    print(f'\033[1;36m[{c+1}] - {title}\033[m\n    '
                          f'\033[1;31mDescription:\033[m\n        {youtube.description[0:801] if len(youtube.description) > 800 else youtube.description}...\n    '
                          f'\033[1;31mAuthor:\033[m\n        {youtube.author}\n    '
                          f'\033[1;31mDuration:\033[m\n        {youtube.length / 60:.1f} minutes\n')
                sleep(0.5)

        self.menu(linha=True)

        opcao_video = self.entrada_num()

# Tratar o Download e baixar
        while True:
            audio_video = self.entrada_txt(
                '\n\033[33mEscolha uma das opções abaixo\n[1] - Áudio\n[2] - Vídeo\nOpção:\033[m ' if self.lang == 'pt' else '\n\033[33mChoose one of the options below\n[1] - Audio\n[2] - Video\nOption:\033[m ')
            if audio_video in ('1', '2'):
                break
            else:
                print('Opção inválida, tente novamente.' if self.lang ==
                      'pt' else 'Invalid option, try again.')

        self.criar_banner(mens='Aguardando...' if self.lang == 'pt' else 'Waitting...')
        for c, link in enumerate(links):  # Prepara e trata o download.
            if c + 1 == opcao_video:
                youtube = YouTube(link)
                title = youtube.title.strip()
                arq = f'{self.pasta_download + title}.mp4' if audio_video == '2' else f'{self.pasta_download + title}.mp3'

                if os.path.isfile(arq):
                    dl_condicao = input(
                        'O arquivo já existe, deseja fazer novo download? [s/n] ' if self.lang == 'pt' else 'The file already exists, do you want to download it again? [y/n] ').strip().upper()
                    if dl_condicao in 'SY':
                        pass
                    elif dl_condicao == 'N':
                        print('Download cancelado!' if self.lang ==
                              'pt' else 'Download cancelled')
                        self.video_existe = True
                        break
                    else:
                        print('Opção inválida!\nDownload cancelado!' if self.lang ==
                              'pt' else 'Invalid option!\nDownload cancelled!')
                        self.video_existe = True
                        break
                print(f'LINK: {link}\nTÍTULO: {title}' if self.lang == 'pt' else f'LINK: {link}\nTITLE: {title}')

                if audio_video == '2':
                    videos = youtube.streams.filter(progressive=True)
                else:
                    videos = youtube.streams.filter(only_audio=True)

                if len(videos) >= 1:
                    if audio_video == '2':
                        self.video = True
                    else:
                        self.audio = True
                break

        if self.video or self.audio:  # Baixar (se disponível).
            self.menu(titulo='OPÇÕES PARA BAIXAR' if self.lang ==
                      'pt' else 'DOWNLOAD OPTIONS')

            for c, video in enumerate(videos):
                video = str(video).split()

                if self.video:
                    print(f"[{c+1}] - {video[3].center(5)} {video[5].center(5)}")
                elif self.audio:
                    print(f'[{c+1}] - {video}')

            self.menu(linha=True)
            resposta = self.entrada_num()
            if os.name == 'nt':
                self.criar_banner(mens=f'\n\033[32m{self.pasta_download + title}{".mp4" if self.video else ".mp3"}\033[m\nBaixando...' if self.lang == 'pt' else f'\n\033[32m{self.pasta_download + title}{".mp4" if self.video else ".mp3"}\033[m\nDownloading...')
            else:
                self.criar_banner(mens=f'\n\033[32m~{self.pasta_download[-11:] + title}{".mp4" if self.video else ".mp3"}\033[m\nBaixando...' if self.lang == 'pt' else f'\n\033[32m~{self.pasta_download[-11:] + title}{".mp4" if self.video else ".mp3"}\033[m\nDownloading...')
            arq_baixado = videos[resposta-1].download(self.pasta_download)
            
            try:
                nome, ext = os.path.splitext(arq_baixado)
                mp4 = nome + ext
                if self.audio:
                    mp3 = self.pasta_download + title + '.mp3'
                    os.rename(mp4, mp3)
                else:
                    mp4_mod = self.pasta_download + title + '.mp4'
                    os.rename(mp4, mp4_mod)
            except FileNotFoundError:
                if self.audio:
                    print(f'\033[31m\nERRO AO RENOMEAR: Não foi possivel renomear o "{mp4}" para "{mp3}"\nFaça isso manualmente!\033[m' if self.lang == 'pt' else f'\033[31m\nERROR '
                        f'RENAMING: Unable to rename "{mp4}" to "{mp3}"\nDo this manually!\033[m')
                else:
                    print(f'\033[31mErro ao tentar renomear de acordo com "{title}"\033[m')

            print('Download concluído!' if self.lang == 'pt' else 'Done!')
        else:
            print('' if self.video_existe else ('\033[31mFALHA\033[m' if self.lang == 'pt' else '\033[31mERROR\033[m'))


# Executando
if __name__ == '__main__':
    sair = False
    while True:
        ydl = YoutubeDownloader()
        ydl.pesquisar_capturar()
        ydl.obter_baixar()
        novo = ' '
        while novo not in ('S', 'N', 'Y'):
            novo = input('\nDeseja fazer uma nova pesquisa? [S/N] ' if ydl.lang == 'pt' else 'Do you want to do a new search? [Y/N]').strip().upper()
            if novo in ('S', 'Y'):
                pass
            elif novo == 'N':
                sair = True
            else:
                print('\033[31mOpção inválida\nTente novamente.\033[m\n' if ydl.lang == 'pt' else '\033[31mInvalid option\nTry again.\033[m\n')
        if sair:
            print('\nSaindo...' if ydl.lang == 'pt' else '\nExitting...')
            sleep(2)
            ydl.limpar_tela()
            break
        else:
            print('\nContinuando...' if ydl.lang == 'pt' else '\nContinuing...')
            sleep(2)
