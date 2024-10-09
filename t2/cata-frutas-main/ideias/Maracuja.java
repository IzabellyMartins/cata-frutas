package jogo.cacafrutas.frutas;

public class Maracuja extends Fruta{
	
	protected String nome;
	protected boolean bichada; //Aqui deve entrar de alguma forma o calculo de porcentagem
	protected int posicaoi;
	protected int posicaoj;
	
	Maracuja(String nome, boolean bichada, int posicaoi, int posicaoj){
		super(nome, bichada, posicaoi, posicaoj);
		
		this.nome = nome;
		this.bichada = bichada;
		this.posicaoi = posicaoi;
		this.posicaoj = posicaoj;
		
	}
	
	
	void causarEfeito() {} //Coloquei assim, vazio pois cada filha pode sobrecarregar esse método de acordo com suas nescessidades

}

