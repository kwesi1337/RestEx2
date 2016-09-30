/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exceptions.QuoteNotFoundException;
import facade.Facade;
import java.util.Random;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
/**
 *
 * @author josephawwal
 */

@Path("/quote")
public class RestServiceQuote {
    
    
    Gson gson;
    JsonObject jsonObj;
    private Random rand;
    
    @Context
    private UriInfo context;
    
    
    public RestServiceQuote(){
        
         gson = new GsonBuilder().setPrettyPrinting().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
        rand = new Random();
        
    }
    
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getQuote(@PathParam("id") int index) throws QuoteNotFoundException{
        
        jsonObj = new JsonObject();
        jsonObj.addProperty("quote", Facade.getQuote(index));
        return Response.status(Response.Status.OK).entity(gson.toJson(jsonObj)).build();
        
    }
    
    @GET
    @Path("/random")
    @Produces("application/json")
    public Response getRandomQuote() throws QuoteNotFoundException{
        
        jsonObj = new JsonObject();
                      jsonObj.addProperty("quote", Facade.getRandomQuote());

                return Response.status(Response.Status.OK).entity(gson.toJson(jsonObj)).build();
                
    }
    
    @POST
    @Consumes("application/json")
    public Response createQuote(String quoteString){
        
        JsonObject jsonObject = new Gson().fromJson(quoteString, JsonObject.class);
        String quote = jsonObject.get("quote").getAsString();
        Facade.createQuote(quote);
        jsonObj = new JsonObject();
        jsonObj.addProperty("id", Facade.getLastIndex());
        jsonObj.addProperty("quote", quote);
        return Response.status(Response.Status.OK).entity(gson.toJson(jsonObj)).build();
        
    }
    
    @DELETE
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteQuote(@PathParam("id") int id) throws QuoteNotFoundException{
        
        String quote = Facade.getQuote(id);
        Facade.deleteQuote(id);
        jsonObj = new JsonObject();
        jsonObj.addProperty("quote", quote);
        return Response.status(Response.Status.OK).entity(gson.toJson(jsonObj)).build();
        
    }
    
}
