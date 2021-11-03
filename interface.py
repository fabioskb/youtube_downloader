import os, locale

### CLASS ArqInterface
class Interface(object):
	"""docstring for Interface"""

	largura = os.get_terminal_size().columns
	lang = locale.getdefaultlocale()[0][0:2]

	def __init__(self):
		#super(Interface, self).__init__()
		pass

# Cria banner
	def criar_banner(self, titulo='YouTube Downloader', mens='', caracter='*', alt=15, larg=largura, banner=True, desc=False):
		"""Cria e imprime um banner no terminal (opcional, padrão=True) retangular feito com caracteres, e, com uma
		mensagem abaixo.
		
		Args:
		    titulo (str, optional): Adiciona um título no centro do banner do banner, se o banner estiver habilitado (padrão='YOUTUBE DOWNLOADER').
		    mens (str): Adiciona uma mensagem abaixo do banner, se habilitado, se não imprime apenas a mensagem (padrão='').
		    caracter (str, optional): Caracter que compõe o banner (padrão='*').
		    alt (int, optional): Número (ÍMPAR) de caracteres na vertical do banner (altura) (padrão=7).
		    larg (int, optional): Número de caracteres na horizontal do banner (largura) (padrão=40).
		    banner (bool, optional): Habilita/desabilita o banner, padrão Habilitado (padrão=True).
		    desc (bool, optional): Habilita/desabilita uma descrição centralizada em vez de só uma mensagem, abaixo do banner (padrão=False).
		"""
		if banner:
			self.limpar_tela()
			for linha in range(alt):
				if (alt - 1) == linha or linha == 0:
					print(f'{caracter*larg}')
				elif alt // 2 == linha:
					print(f'\033[7;31m{titulo.upper().center(larg)}\033[m')
				else:
					print(f'\033[7;31m{" ".center(larg)}\033[m')
			print(f'\n{mens}' if not desc else (f'\033[4m{"DESCRIÇÃO".center(larg)}\033[m\n{mens.center(larg)}\n{caracter*larg}' if self.lang == 'pt' else f'\033[4m{"DESCRIPTION".center(larg)}\033[m\n{mens.center(larg)}\n{caracter*larg}'))
		else:
			print(f'\n{mens}')

# Menus
	def menu(self, titulo=' ', caracter='\033[34m-\033[m', linha=False):
		"""Cria um menu com uma linha de 40 caracteres encima e abaixo, com um título centralizado ou apenas imprime uma linha com 40 caracteres no terminal.
		
		Args:
		    titulo (str, optional): Título do menu (padrão=' ').
		    caracter (str, optional): Caracter de composição da(s) linha(s) (padrão='-').
		    linha (bool, optional): Habilita/desabilita a impressão de uma linha única (padrão=False).
		"""
		if linha:
			print(caracter * self.largura)
		else:
			print(f'{caracter * self.largura}\n{titulo.center(self.largura)}\n{caracter * self.largura}')

# Limpar tela
	def limpar_tela(self):
		"""Limpa o terminal
		
		Returns:
		    TYPE: Usa o os.system(...) para limpar a tela.
		"""
		return os.system('clear' if os.name == 'posix' else 'cls')

# entrada de texto
	def entrada_txt(self, mensagem='Nome do vídeo: ' if lang == 'pt' else 'Video name: '):
		"""Um input para texto.
		
		Args:
		    mensagem (str, optional): Texto do input (padrão='Nome do vídeo: ').
		
		Returns:
		    TYPE: Retorna a entrada de texto.
		"""
		opcao = input(mensagem).strip()
		return opcao

# entrada de numeros
	def entrada_num(self, num_text='Informe uma das opções acima: ' if lang == 'pt' else 'Enter one of the options above: '):
		"""Um input para números
		
		Args:
		    num_text (str, optional): Texto para a entrada de numeros (padrão='Informe uma das opções acima: ').
		
		Returns:
		    TYPE: Retorna o número digitado pelo usuário.
		"""
		while True:
			try:
				opcao = int(input(num_text))
			except ValueError:
				print('Digite apenas números inteiros.' if self.lang == 'pt' else 'Enter whole numbers only.')
			else:
				break
		return opcao
	

# Remover arquivo links
	def remover(self, arq, printar=True):
		"""Remove um arquivo ou pasta passado pelo parâmetro arq.
		
		Args:
		    arq (str): Nome ou caminho do arquivo/pasta.
		    printar (str, optional): habilita/desabilita o print na tela (padrão=True).

		Usage:

		"""
		if os.path.isfile(arq):
			os.remove(arq)
			if printar:
				print(f'{arq} removido!' if self.lang == 'pt' else f'{arq} removed!')
		else:
			if printar:
				print(f'{arq} inexistente!' if self.lang == 'pt' else f'{arq} nonexistent!')
			else:
				pass

# abrir arquivo
	def abrir_arquivo(self, arquivo):
		"""Abre um arquivo (existente) passado por parâmetro e retorna o
		arquivo aberto 'arq_aberto = arquivo.read()'.
		
		Args:
		    arquivo (str): Nome ou caminho do arquivo.
		
		Returns:
		    TYPE: Retorna o arquivo aberto (arq.read()).

		Usage:

		"""
		if os.path.isfile(arquivo):
			with open(arquivo) as a:
				arq_aberto = a.read()
			return arq_aberto
		else:
			return f'{arquivo} inesxistente!' if self.lang == 'pt' else f'{arquivo} nonexistent!'

# Editar arquivo
	def editar_arquivo(self, info, arquivo):
		"""Adiciona uma linha a um arquivo existente ou
		cria um arquivo com uma linha.
		
		Args:
		    info (str): Informação a ser passada para o arquivo em
		    uma linha.
		    arquivo (str): Nome ou caminho completo do arquivo.
		"""
		if os.path.isfile(arquivo):	
			with open(arquivo, 'a') as a:
				arq_editado = a.write(f'{info}\n')
		else:
			with open(arquivo, 'w') as a:
				arq_editado = a.write(f'{info}\n')
