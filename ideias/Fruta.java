package jogo.cacafrutas.frutas;

public class Fruta {
	
	protected String nome;
	protected boolean bichada; //Aqui deve entrar de alguma forma o calculo de porcentagem
	protected int posicaoi;
	protected int posicaoj;
	
	Fruta(String nome, boolean bichada, int posicaoi, int posicaoj){
		
		this.nome = nome;
		this.bichada = bichada;
		this.posicaoi = posicaoi;
		this.posicaoj = posicaoj;
		
	}
	
	
	void causarEfeito() {} //Coloquei assim, vazio pois cada filha pode sobrecarregar esse método de acordo com suas nescessidades

}
