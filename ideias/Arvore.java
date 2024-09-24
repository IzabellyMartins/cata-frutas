package jogo.cacafrutas.arvores;
import jogo.cacafrutas.frutas.*;

public abstract class Arvore {
	
	protected String nome;
	//A porcenetagem de frutass bichadas tambpem seria uma atributo, ou apenas uma variável que a Arvore recebe (talvez por em um try/cat para acelerar isso)
	protected int posicaoi;
	protected int posicaoj;
	protected int tempoGestacaoA;
	protected int tempoGestacaoB;
	
	Arvore(String nome, int posicaoi, int posicaoj, int tempoGestacaoA, int tempoGestacaoB){
		
		this.nome = nome;
		this.posicaoi = posicaoi;
		this.posicaoj = posicaoj;
		this.tempoGestacaoA = tempoGestacaoA;
		this.tempoGestacaoB = tempoGestacaoB;	
		
	}
	
	public void produzir() {} //Coloquei assim, vazio pois cada filha pode sobrecarregar esse método de acordo com suas nescessidades
	
	}
