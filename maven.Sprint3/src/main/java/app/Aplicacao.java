package app;

import static spark.Spark.*;
import service.LojaService;


public class Aplicacao {
	
	private static LojaService lojaService = new LojaService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/loja/insert", (request, response) -> lojaService.insert(request, response));

        get("/loja/:nome", (request, response) -> lojaService.get(request, response));
        
        get("/loja/list/:orderby", (request, response) -> lojaService.getAll(request, response));

        get("/loja/update/:nome", (request, response) -> lojaService.getToUpdate(request, response));
        
        post("/loja/update/:nome", (request, response) -> lojaService.update(request, response));
           
        get("/loja/delete/:nome", (request, response) -> lojaService.delete(request, response));

             
    }
}
