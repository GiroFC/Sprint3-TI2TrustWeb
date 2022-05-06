package service;

import java.util.Scanner;

import java.io.File;
import java.util.List;
import dao.LojaDAO;
import model.Loja;
import spark.Request;
import spark.Response;


public class LojaService {

	private LojaDAO LojaDAO = new LojaDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_NOME = 1;
	private final int FORM_ORDERBY_NOTA = 2;
	private final int FORM_ORDERBY_RECLAMACOES = 3;
	
	
	public LojaService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Loja(), FORM_ORDERBY_NOTA);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Loja(), orderBy);
	}

	
	public void makeForm(int tipo, Loja loja, int orderBy) {
		String nomeArquivo = "Back-EndLojas.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umLoja = "";
		if(tipo != FORM_INSERT) {
			umLoja += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/loja/list/1\">Nova Loja</a></b></font></td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t</table>";
			umLoja += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/loja/";
			String name, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Loja";
				descricao = "Loja desejada... ";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + loja.getNome();
				name = "Atualizar Loja (ID " + loja.getNome() + ")";
				descricao = loja.getNome();
				buttonLabel = "Atualizar";
			}
			umLoja += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umLoja += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ descricao +"\"></td>";
			umLoja += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"nota\" value=\""+ loja.getNota() +"\"></td>";
			umLoja += "\t\t\t<td>Quantidade: <input class=\"input--register\" type=\"text\" name=\"notaj\" value=\""+ loja.getNota() +"\"></td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t</table>";
			umLoja += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umLoja += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (Nome " + loja.getNome() + ")</b></font></td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td>&nbsp;Nome: "+ loja.getNome() +"</td>";
			umLoja += "\t\t\t<td>Nota: "+ loja.getNota() +"</td>";
			umLoja += "\t\t\t<td>Reclamações: "+ loja.getReclamacoes() +"</td>";
			umLoja += "\t\t\t<td>Respostas: "+ loja.getRespostas() +"</td>";
			umLoja += "\t\t\t<td>Numero de problemas resolvidos: "+ loja.getProblemaSolv() +"</td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t\t<tr>";
			umLoja += "\t\t\t<td>&nbsp;</td>";
			umLoja += "\t\t</tr>";
			umLoja += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umLoja);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Produtos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_NOME + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_NOTA + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_RECLAMACOES + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Loja> lojaList;
		if (orderBy == FORM_ORDERBY_NOME) {	
			lojaList = LojaDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_NOTA) {		
			lojaList = LojaDAO.getOrderByNota();
		} else if (orderBy == FORM_ORDERBY_RECLAMACOES) {			
			lojaList = LojaDAO.getOrderByReclamacoes();
		} else {											
			lojaList = LojaDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Loja p : lojaList) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getNota() + "</td>\n" +
            		  "\t<td>" + p.getReclamacoes() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/loja/" + p.getNome() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/loja/update/" + p.getNome() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteLoja('" + p.getNome() + "', '" + p.getNota() + "', '" + p.getReclamacoes() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-LOJA>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String nota = request.queryParams("nota");
		String reclamacoes = request.queryParams("reclamacoes");
		String respostas = request.queryParams("respostas");
		String problemaSolv = request.queryParams("problemaSolv");
		
		String resp = "";
		
		Loja loja = new Loja(nome, nota, reclamacoes, respostas, problemaSolv);
		
		if(LojaDAO.inserirLoja(loja) == true) {
            resp = "Loja (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Produto (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		String nome = request.params(":nome");		
		Loja loja = (Loja) LojaDAO.get(nome);
		
		if (loja != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, loja, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Loja " + nome + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		String nome = request.params(":nome");			
		Loja loja = (Loja) LojaDAO.get(nome);
		
		if (loja != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, loja, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Loja " + nome + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
		String nome = request.params(":nome");	
		Loja loja = LojaDAO.getUm(nome);
        String resp = "";       

        if (loja != null) {
        	loja.setNome(request.queryParams("nome"));
        	loja.setNota(request.queryParams("nota"));
        	loja.setReclamacoes(request.queryParams("reclamacoes"));
        	loja.setRespostas(request.queryParams("respostas"));
        	loja.setProblemaSolv(request.queryParams("problemaSolv"));
        	LojaDAO.atualizarLoja(loja);
        	response.status(200); // success
            resp = "Loja (Nome" + loja.getNome() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Loja (Nome \" + produto.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
		String nome = request.params(":nome");
        Loja loja = LojaDAO.getUm(nome);
        String resp = "";       

        if (loja != null) {
            LojaDAO.excluirLoja(nome);
            response.status(200); // success
            resp = "Loja (" + nome + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Loja (" + nome + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}