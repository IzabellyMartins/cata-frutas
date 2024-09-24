package jogo.cacafrutas.arvores;

final public class Maracujazeiro extends Arvore {

	protected String nome = "Maracujazeiro";
	//A porcenetagem de frutass bichadas tambpem seria uma atributo, ou apenas uma variável que a Arvore recebe (talvez por em um try/cat para acelerar isso)
	protected int posicaoi;
	protected int posicaoj;
	protected int tempoGestacaoA;
	protected int tempoGestacaoB;
	
	Maracujazeiro(String nome, int posicaoi, int posicaoj, int tempoGestacaoA, int tempoGestacaoB){
		super(nome, posicaoi, posicaoj, tempoGestacaoA, tempoGestacaoB);
		
		this.nome = nome;
		this.posicaoi = posicaoi;
		this.posicaoj = posicaoj;
		this.tempoGestacaoA = tempoGestacaoA;
		this.tempoGestacaoB = tempoGestacaoB;	
		
	}
	
	Fruta produzir() { //Coloquei para chamar diretamente a fruta em específico
		
	   //Maracuja("Fruta ouro", aux2, posi, posj);
		
	}
	
}
