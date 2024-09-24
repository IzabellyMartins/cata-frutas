package jogo.cacafrutas.tabuleiro;

final public class Tabuleiro {
	
	private int i = 0;
	protected TILES dimensao[][] = new TILES[3+i][3+i]; //No caso, cada unidade estatica dentro do tabuleiro pertense a essa classe
											  // aqui imagino o tabuleiro apenas como uma matriz de posições, na qual cada elemento
											  // é representado apenas pela posição, não pelo elemento em si, já que isso envolveria
											  // que ele tivesse a capacidade de guardar elementos de diferentes tipos
	protected int numeroPedras;
	protected int probabilidadeBichada;
	protected int quantidadeMaracuja;
	protected int quantidadeCoco;
	protected int quantidadeAbacate;
	protected int quantidadeLaranja;
	protected int quantidadeAcerola;
	protected int quantidadeAmora;
	protected int quantidadeGoiaba;
	
	
	Tabuleiro( int posi, int posj, int numeroPedras, int probabilidadeBichada, int quantidadeMaracuja, int quantidadeCoco, int quantidadeAbacate,
			int quantidadeLaranja, int quantidadeAcerola, int quantidadeAmora, int quantidadeGoiaba){
		
		this.numeroPedras = numeroPedras;
		
		this.quantidadeMaracuja = quantidadeMaracuja;
		this.quantidadeCoco = quantidadeCoco;
		this.quantidadeAbacate = quantidadeAbacate;
		this.quantidadeLaranja = quantidadeLaranja;
		this.quantidadeAcerola = quantidadeAcerola;
		this.quantidadeAmora = quantidadeAmora;
		this.quantidadeGoiaba = quantidadeGoiaba;
		
		this.probabilidadeBichada = probabilidadeBichada;
		
	     //calculo para detrinar a quantidade máxima de frutas bichadas. Nessa ideia o maracujá entra na conta, mesmo que ele não possa estar bichado
		
		//int aux = quantidadeMaracuja + quantidadeCoco + quantidadeAbacate + quantidadeLaranja + quantidadeAcerola + quantidadeAmora + quantidadeGoiaba
		//float aux2 = (probabilidadeBichada * 100) / aux
		
		//Fruta(aux2, posi, posj); //Um dos atributs é o nome, porém ele não deve entrar no construtor, afinal aqui isso não é relevante
		//Competidor(posi, posj, 0, 0, 2, false); 
		
		
	}
	
	void inciarTabuleiro() {
		
		//Arvore(aux2); //Aqui para lembrar que esse dado de quantas frutas estarão bichadas é relevante aqui também
		
	}
	
}
