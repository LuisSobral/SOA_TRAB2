
package com.soa.crudapp.dao;

import com.soa.crudapp.modelo.Publicacao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luisb
 */
public class PublicacaoDAO {
    private Connection con = null;
    
    private void abrirConexao() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/soa", "root", "root");
            System.out.println("Conexão aberta");
        }
        
        catch(Exception e) {
            System.out.println("Erro: " +e);
        }
    }
    
    public List<Publicacao> buscaPublicacoes() {
        
        try{
            abrirConexao();
            
            String sql = "SELECT * FROM publicacao";
            
            List<Publicacao> publicacoes = new ArrayList<>();
            
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(sql);
            
            while(rs.next())
            {
                Publicacao publicacao = new Publicacao();
                
                publicacao.setId(rs.getInt("id"));
                publicacao.setTitulo(rs.getString("titulo"));
                publicacao.setPaginaInicial(rs.getInt("paginaInicial"));
                publicacao.setPaginaFinal(rs.getInt("paginaFinal"));
                publicacao.setDataPublicao(rs.getTimestamp("dataPublicacao"));
                
                
                publicacoes.add(publicacao);
            }
            
            return publicacoes;
        }
        
        catch(Exception e) {
            System.out.println("Erro: " +e);
            
            return null;
        }
    }
    
     public Publicacao buscaPublicacao(int id) {
        
        try{
            abrirConexao();
            
            String sql = "SELECT * FROM publicacao WHERE id = ?";
            
            Publicacao publicacao = null;
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next())
            {
                publicacao = new Publicacao();
                
                publicacao.setId(rs.getInt("id"));
                publicacao.setTitulo(rs.getString("titulo"));
                publicacao.setPaginaInicial(rs.getInt("paginaInicial"));
                publicacao.setPaginaFinal(rs.getInt("paginaFinal"));
                publicacao.setDataPublicao(rs.getTimestamp("dataPublicacao"));
            }
            
            return publicacao;
        }
        
        catch(Exception e) {
            System.out.println("Erro: " +e);
            
            return null;
        }
    }
    
    public void atualiza(Publicacao publicacao) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        try{
            abrirConexao();
            
            String sql = "UPDATE publicacao SET titulo = ?, paginaInicial = ?, paginaFinal = ?, dataPublicacao = ? WHERE id = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, publicacao.getTitulo());
            ps.setInt(2, publicacao.getPaginaInicial());
            ps.setInt(3, publicacao.getPaginaFinal());
            ps.setTimestamp(4, new Timestamp (publicacao.getDataPublicao().getTime()));
            ps.setInt(5, publicacao.getId());
            ps.executeUpdate();
        }
        
        catch(Exception e) {
            System.out.println("Erro: " +e);
        }
    }
    
    public void insere(Publicacao publicacao) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        try{
            abrirConexao();
            
            String sql = "INSERT INTO publicacao (titulo,paginaInicial,paginaFinal,dataPublicacao) VALUES (?,?,?,?)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, publicacao.getTitulo());
            ps.setInt(2, publicacao.getPaginaInicial());
            ps.setInt(3, publicacao.getPaginaFinal());
            ps.setTimestamp(4, new Timestamp (publicacao.getDataPublicao().getTime()));
            ps.executeUpdate();
        }
        
        catch(Exception e) {
            System.out.println("Erro: " +e);
        }
    }
    
    public void deleta(Publicacao publicacao) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        try{
            abrirConexao();
            
            String sql = "DELETE FROM publicacao WHERE id = ?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, publicacao.getId());
            ps.executeUpdate();
        }
        
        catch(Exception e) {
            System.out.println("Erro: " +e);
        }
    }
}
