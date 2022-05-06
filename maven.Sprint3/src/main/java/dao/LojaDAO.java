
package dao;

import model.Loja;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LojaDAO {

	private Connection conexao;
	
	public LojaDAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "Loja";
		int porta = 6789;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "ti@cc";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NãO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NãO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}
	
	public Loja getUm(String nome) {
		Loja loja = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM lojas WHERE nome ="+nome;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 loja = new Loja(rs.getString("nome"), rs.getString("nota"), rs.getString("reclamacoes"), rs.getString("respostas"), rs.getString("problemasSolv"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return loja;
	}
	
	public List<Loja> get(String orderBy) {
		List<Loja> loja = new ArrayList<Loja>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM loja" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Loja p = new Loja(rs.getString("nome"), rs.getString("nota"), rs.getString("reclamacoes"), rs.getString("respostas"), rs.getString("problemasSolv"));
	            loja.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return loja;
	}
	
	
	
	public List<Loja> get() {
		return get("");
	}

	
	public List<Loja> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Loja> getOrderByNota() {
		return get("nota");		
	}
	
	
	public List<Loja> getOrderByReclamacoes() {
		return get("Reclamacoes");		
	}

	
	

	public boolean inserirLoja(Loja loja) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO lojas (nome, nota, reclamacoes, resposta, problemaSolv)"
					       + "VALUES ("+loja.getNome()+ ", '" + loja.getNota() + "', '"  
					       + loja.getReclamacoes() + "', '" + loja.getRespostas() + "', ' " + loja.getProblemaSolv() + "');");
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean atualizarLoja(Loja loja) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE lojas SET problemaSolv = '" + loja.getProblemaSolv() + "', nota= '"  
				       + loja.getNota() + "', reclamacoes = '" +  loja.getReclamacoes() + "', respostas  = '" + loja.getReclamacoes() + "'"
					   + " WHERE nome = " + loja.getNome();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean excluirLoja(String nome) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM lojas WHERE nome= " + nome);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public String getLoja(String nome) {
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("SELECT FROM lojas WHERE nome= " + nome);
			st.close();
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		
		return nome;
	}
	
	public Loja[] getLoja() {
		Loja[] loja = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM lojas");		
	         if(rs.next()){
	             rs.last();
	             loja = new Loja[rs.getRow()];
	             rs.beforeFirst();
	             for(int i = 0; rs.next(); i++) {
	                loja[i] = new Loja(rs.getString("nome"), rs.getString("nota"),rs.getString("reclamacoes"), rs.getString("respostas"), rs.getString("problemaSolv"));
	             }
	          }
	          st.close();
		} catch (Exception e) {
			System.err.println("ERRO!");
		}
		return loja;
	}

}