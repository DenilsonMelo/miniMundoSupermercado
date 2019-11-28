package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.Cliente;
import br.edu.ifnmg.minimundo.DomainModel.ErroValidacaoException;
import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositorio extends BancoDados{

    public ClienteRepositorio(){
        super();
    } 
        
    public boolean Salvar(Cliente cliente) throws SQLException{
        try{
            if(cliente.getId() == 0){
                PreparedStatement sql = 
                        this.getConexao().prepareStatement("insert into Clientes"
                                + "(status, nome, cpf, endereco, sexo, email) values (?,?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);
                
                sql.setString(1, cliente.getStatus());
                sql.setString(2, cliente.getNome());
                sql.setString(3, cliente.getCPF().replace("-","").replace(".",""));
                sql.setString(4, cliente.getEndereco());
                sql.setString(5, cliente.getSexo());
                sql.setString(6, cliente.getEmail());
                
                if(sql.executeUpdate() > 0){
                    ResultSet key = sql.getGeneratedKeys();
                    key.next();
                    cliente.setId(key.getInt(1));
                    
                    return true;
                }
                else 
                    return false;
            } else {
                    PreparedStatement sql = this.getConexao().prepareStatement(
                            "update Clientes set status = ?, nome = ?, cpf = ?, endereco = ?, sexo = ?, email = ?");
                    
                    sql.setString(1, cliente.getStatus());
                    sql.setString(2, cliente.getNome());
                    sql.setString(3, cliente.getCPF().replace("-","").replace(".",""));
                    sql.setString(4, cliente.getEndereco());
                    sql.setString(5, cliente.getSexo());
                    sql.setString(6, cliente.getEmail());

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
    
    public void AtualizarTelefones(Cliente cliente){
        try{
            PreparedStatement sql = this.getConexao().
                    prepareStatement("delete from ClientesTelefones where cliente_id = ?");
            
            sql.setInt(1, cliente.getId());
            
            String values = "";
            
            for(String telefone : cliente.getTelefones()){
                if(values.length() > 0)
                    values += ", ";
                
                values += "("+cliente.getId()+",'"+telefone+"')";
            }
            
            Statement sql2 = (Statement) this.getConexao().createStatement();
            
            sql2.executeUpdate("insert into ClientesTelefones(cliente_id, telefone) values "+ values);
     
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public Cliente Abrir(int id){
        try{
            PreparedStatement sql = this.getConexao().prepareStatement("select * from Clientes where id = ?");
            
            sql.setInt(1, id);
            
            ResultSet resultado = sql.executeQuery();
            
            Cliente cliente = new Cliente();
            
            try{
                cliente.setId(resultado.getInt("id"));
                cliente.setStatus(resultado.getString("status"));
                cliente.setNome(resultado.getString("nome"));
                cliente.setCPF(resultado.getString("CPF"));
                cliente.setEndereco(resultado.getString("endereco"));
                cliente.setSexo(resultado.getString("sexo"));
                cliente.setEmail(resultado.getString("email"));
                abrirTelefones(cliente);
                
            }
            catch(Exception ex){
                cliente = null;
            }
            
            return cliente;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    public void abrirTelefones(Cliente cliente) throws SQLException{
        try{
            PreparedStatement sql = this.getConexao().
                    prepareStatement("select telefone from ClientesTelefones where cliente_id = ?");
        
            sql.setInt(1, cliente.getId());
            
            ResultSet resultado = sql.executeQuery();
            
            while(resultado.next()){
                cliente.addTelefone(resultado.getString("telefone"));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    
    }
    
    public boolean Apagar(Cliente cliente){
        try{
            PreparedStatement sql = this.getConexao().
                    prepareStatement("delete from Clientes where id = ?");
        
            sql.setInt(1, cliente.getId());
            
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
    
    public List<Cliente> Buscar(Cliente filtro) throws ErroValidacaoException{
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
                if(filtro.getSexo() != null){ 
                    if(where.length() > 0)
                        where += " and ";
                    where += "sexo = '"+filtro.getSexo().toString()+"'";
                }
            }
            
            String consulta = "select * from Clientes";
            
            if(where.length() > 0)
                consulta += " where " + where;
            
            PreparedStatement sql = this.getConexao().prepareStatement(consulta);
            
            ResultSet resultado = sql.executeQuery();
            
            List<Cliente> clientes = new ArrayList();
            
            while(resultado.next()){
                Cliente cliente = new Cliente();
            
                try{
                    cliente.setId(resultado.getInt("id"));
                    cliente.setStatus(resultado.getString("status"));
                    cliente.setNome(resultado.getString("nome"));
                    cliente.setCPF(resultado.getString("CPF"));
                    cliente.setEndereco(resultado.getString("endereco"));
                    cliente.setSexo(resultado.getString("sexo"));
                    cliente.setEmail(resultado.getString("email"));
                    abrirTelefones(cliente);   

                }
                catch(Exception ex){
                    cliente = null;
                }
                clientes.add(cliente);
            }
            return clientes;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    
        return null;
    }
    
}

 
