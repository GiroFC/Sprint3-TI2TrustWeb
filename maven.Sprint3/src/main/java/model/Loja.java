package model;

public class Loja {
	private String nome;
	private	String nota;
	private String reclamacoes;
	private String respostas;
	private String problemaSolv;
	
	public Loja() {
		this.nome = "";
		this.nota = "";
		this.reclamacoes  = "";
		this.respostas = "";
		this.problemaSolv = "";
	}
	
	public Loja(String nome, String nota, String reclamacoes,String respostas,String problemaSolv) {
		this.nome = nome;
		this.nota = nota;
		this.reclamacoes  = reclamacoes;
		this.respostas = respostas;
		this.problemaSolv = problemaSolv;
		
	}
	
	public Loja(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
	
	public String getNota() {
		return this.nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}

    public String getReclamacoes() {
		return this.reclamacoes;
	}
	public void setReclamacoes(String reclamacoes) {
		this.reclamacoes = reclamacoes;
	}
	
	public String getRespostas() {
		return this.respostas;
	}
	public void setRespostas(String respostas) {
		this.respostas = respostas;
	}

    public String getProblemaSolv() {
		return this.problemaSolv;
	}
	public void setProblemaSolv(String problemaSolv) {
		this.problemaSolv = problemaSolv;
	}

	@Override
	public String toString() {
		return "Nome: " + nome + "   Nota: " + nota + "   Numero de Reclamações: " + reclamacoes + "    Respostas: " + respostas + "   Problemas Resolvidos: " + problemaSolv;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getNome() == ((Loja) obj).getNome());
	}

	
	
}