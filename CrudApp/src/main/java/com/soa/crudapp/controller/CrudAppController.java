package com.soa.crudapp.controller;

import com.soa.crudapp.dao.PublicacaoDAO;
import com.soa.crudapp.modelo.Publicacao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author luisb
 */
@Path("/crud")
public class CrudAppController {
    
    @GET
    @Path("/get")
    @Produces(MediaType.TEXT_HTML)
    public String getInformacao() {
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        
        List<Publicacao> publicacoes = publicacaoDAO.buscaPublicacoes();
        
        String info = "";
        
        if(publicacoes != null) {
            for(Publicacao publicacao : publicacoes) {
                info = info + "<h1>" + publicacao.getTitulo() + "</h1>" +
                       "<p>" + publicacao.getPaginaInicial() + "</p>" + "<p>" + publicacao.getPaginaFinal() + "</p>" +
                       "<p>" + publicacao.getDataPublicao() + "</p>";
            }
        }
        
        return info;
    }
    
    @POST
    @Path("/insere")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_HTML)
    public String insere(String publicacaoInfos) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        
        Publicacao publicacao = new Publicacao();
        
        String[] infos = publicacaoInfos.split(",");
        
        SimpleDateFormat formato = new SimpleDateFormat( "yyyy-MM-dd" );
        
        publicacao.setTitulo(infos[0]);
        publicacao.setPaginaInicial(Integer.parseInt(infos[1]));
        publicacao.setPaginaFinal(Integer.parseInt(infos[2]));
        publicacao.setDataPublicao(formato.parse(infos[3]));
        
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        publicacaoDAO.insere(publicacao);
        
        return "<h1>" + publicacao.getTitulo() + "</h1>" +
               "<p>" + publicacao.getPaginaInicial() + "</p>" + "<p>" + publicacao.getPaginaFinal() + "</p>" +
               "<p>" + publicacao.getDataPublicao() + "</p>";
    }
    
    @PUT
    @Path("/atualiza")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_HTML)
    public String atualiza(String publicacaoInfos) throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        
        String[] infos = publicacaoInfos.split(",");
        String info = "";
        
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        Publicacao publicacao = publicacaoDAO.buscaPublicacao(Integer.parseInt(infos[0]));
        
        if(publicacao == null)
            info = "<p>Não há publicacao com esse id</p>";
        
        else
        {
            info = "<h1>Publicacao antes da atualizacao</h1>" + 
                   "<p>" + publicacao.getTitulo() + "</p>" + 
                   "<p>" + publicacao.getPaginaInicial() + "</p>" + 
                   "<p>" + publicacao.getPaginaFinal() + "</p>" +
                   "<p>" + publicacao.getDataPublicao() + "</p>";
            
            SimpleDateFormat formato = new SimpleDateFormat( "yyyy-MM-dd" );
        
            publicacao.setId(Integer.parseInt(infos[0]));
            publicacao.setTitulo(infos[1]);
            publicacao.setPaginaInicial(Integer.parseInt(infos[2]));
            publicacao.setPaginaFinal(Integer.parseInt(infos[3]));
            publicacao.setDataPublicao(formato.parse(infos[4]));

            PublicacaoDAO publicacoDAO = new PublicacaoDAO();
            publicacoDAO.atualiza(publicacao);
            
            info = info + "<br><br><h1>Publicacao após atualizacao</h1><p>" + publicacao.getTitulo() + "</p>" +
                   "<p>" + publicacao.getPaginaInicial() + "</p>" +
                   "<p>" + publicacao.getPaginaFinal() + "</p>" +
                   "<p>" + publicacao.getDataPublicao() + "</p>";
            
        }
        
        return info;
    }
    
    @DELETE
    @Path("/deleta/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String deleta(@PathParam("id") String id) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
                
        PublicacaoDAO publicacaoDAO = new PublicacaoDAO();
        Publicacao publicacao = publicacaoDAO.buscaPublicacao(Integer.parseInt(id));
        String info = "";
        
        if(publicacao == null)
            info = "<p>Não há publicacao com esse id</p>";
        
        else
        {
            info = "<h1>Publicacao a ser deletada</h1>" + 
                   "<p>" + publicacao.getTitulo() + "</p>" + 
                   "<p>" + publicacao.getPaginaInicial() + "</p>" + 
                   "<p>" + publicacao.getPaginaFinal() + "</p>" +
                   "<p>" + publicacao.getDataPublicao() + "</p>";
            
            
            publicacaoDAO.deleta(publicacao);
        }
        
        return info;
    }
}
