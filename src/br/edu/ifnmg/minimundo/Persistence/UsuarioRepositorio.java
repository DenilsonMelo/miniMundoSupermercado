package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.Cliente;
import br.edu.ifnmg.minimundo.DomainModel.ErroValidacaoException;
import br.edu.ifnmg.minimundo.DomainModel.Usuario;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio extends BancoDados{
    public UsuarioRepositorio(){
        super();
    }
    
    public boolean Salvar(Usuario usuario) throws SQLException{
        try{
            if(usuario.getId() == 0){
                PreparedStatement sql = 
                        this.getConexao().prepareStatement("insert into Usuario"
                                + "(status, nome, cpf, endereco, sexo, email, usuario, senha) values (?,?,?,?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);
                
                sql.setString(1, usuario.getStatus());
                sql.setString(2, usuario.getNome());
                sql.setString(3, usuario.getCPF().replace("-","").replace(".",""));
                sql.setString(4, usuario.getEndereco());
                sql.setString(5, usuario.getSexo());
                sql.setString(6, usuario.getEmail());
                sql.setString(7, usuario.getUsuario());
                sql.setString(8, usuario.getSenha());
                
                if(sql.executeUpdate() > 0){
                    ResultSet key = sql.getGeneratedKeys();
                    key.next();
                    usuario.setId(key.getInt(1));
                    
                    return true;
                }
                else 
                    return false;
            } else {
                    PreparedStatement sql = this.getConexao().prepareStatement(
                            "update Usuario set status = ?, nome = ?, cpf = ?, endereco = ?, sexo = ?, email = ?, usuario = ?, senha = ?");
                    
                    sql.setString(1, usuario.getStatus());
                    sql.setString(2, usuario.getNome());
                    sql.setString(3, usuario.getCPF().replace("-","").replace(".",""));
                    sql.setString(4, usuario.getEndereco());
                    sql.setString(5, usuario.getSexo());
                    sql.setString(6, usuario.getEmail());
                    sql.setString(7, usuario.getUsuario());
                    sql.setString(8, usuario.getSenha());

                    if(sql.executeUpdate() > 0)
                        return true;
                    else 
                        return false;
                }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    
        return false;
    }
    
    public void AtualizarTelefones(Usuario usuario){
        try{
            PreparedStatement sql = this.getConexao().
                    prepareStatement("delete from UsuariosTelefones where cliente_id = ?");
            
            sql.setInt(1, usuario.getId());
            
            String values = "";
            
            for(String telefone : usuario.getTelefones()){
                if(values.length() > 0)
                    values += ", ";
                
                values += "("+usuario.getId()+",'"+telefone+"')";
            }
            
            Statement sql2 = (Statement) this.getConexao().createStatement();
            
            sql2.executeUpdate("insert into UsuariosTelefones(cliente_id, telefone) values "+ values);
     
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
        
    public Usuario Abrir(int id){
        try{
            PreparedStatement sql = this.getConexao().prepareStatement("select * from Usuario where id = ?");
            
            sql.setInt(1, id);
            
            ResultSet resultado = sql.executeQuery();
            
            Usuario usuario = new Usuario();
            
            try{
                usuario.setId(resultado.getInt("id"));
                usuario.setStatus(resultado.getString("status"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setCPF(resultado.getString("CPF"));
                usuario.setEndereco(resultado.getString("endereco"));
                usuario.setSexo(resultado.getString("sexo"));
                usuario.setEmail(resultado.getString("email"));
                usuario.setUsuario(resultado.getString("usuario"));
                usuario.setSenha(resultado.getString("senha"));
                abrirTelefones(usuario);
                
            }
            catch(Exception ex){
                usuario = null;
            }
            
            return usuario;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    public void abrirTelefones(Usuario usuario) throws SQLException{
        try{
            PreparedStatement sql = this.getConexao().
                    prepareStatement("select telefone from UsuariosTelefones where cliente_id = ?");
        
            sql.setInt(1, usuario.getId());
            
            ResultSet resultado = sql.executeQuery();
            
            while(resultado.next()){
                usuario.addTelefone(resultado.getString("telefone"));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    
    }
    
    public boolean Apagar(Usuario usuario){
        try{
            PreparedStatement sql = this.getConexao().
                    prepareStatement("delete from Usuario where id = ?");
        
            sql.setInt(1, usuario.getId());
            
            if(sql.executeUpdate() > 0)
                return true;
            else
                return false;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    public List<Cliente> Buscar(Usuario filtro) throws ErroValidacaoException{
        try{
            String where = "";
        
            if(filtro != null){
                if(filtro.getNome() != null && !filtro.getNome().isEmpty())
                    where += "nome like '%"+filtro.getNome()+ "%'";
            
                if(filtro.getCPF() != null && !filtro.getCPF().isEmpty() && 
                        !"000.000.000-00".equals(filtro.getCPF())){
                    if(where.length() > 0)
                        where += " and ";
                    where += "cpf = '"+filtro.getCPF().replace("-","").replace(".","") + "'";
       
                }
            }
            
            String consulta = "select * from Cliente";
            
            if(where.length() > 0)
                consulta += " where " + where;
            
            PreparedStatement sql = this.getConexao().prepareStatement(consulta);
            
            ResultSet resultado = sql.executeQuery();
            
            List<Cliente> usuarios = new ArrayList();
            
            while(resultado.next()){
                Usuario usuario = new Usuario();
            
                try{
                    usuario.setId(resultado.getInt("id"));
                    usuario.setStatus(resultado.getString("status"));
                    usuario.setNome(resultado.getString("nome"));
                    usuario.setCPF(resultado.getString("CPF"));
                    usuario.setEndereco(resultado.getString("endereco"));
                    usuario.setSexo(resultado.getString("sexo"));
                    usuario.setEmail(resultado.getString("email"));
                    usuario.setUsuario(resultado.getString("usuario"));
                    usuario.setSenha(resultado.getString("senha"));
                    abrirTelefones(usuario);   
                }
                catch(SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }
        
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    
        return null;
    }
    
}