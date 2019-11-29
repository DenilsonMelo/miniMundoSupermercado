package br.edu.ifnmg.minimundo.Persistence;

import br.edu.ifnmg.minimundo.DomainModel.Cliente;
import br.edu.ifnmg.minimundo.DomainModel.ErroValidacaoException;
import br.edu.ifnmg.minimundo.DomainModel.Fornecedor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FornecedorRepositorio extends BancoDados{
  
    public FornecedorRepositorio(){
        super();
    }
    
    public boolean Salvar(Fornecedor fornecedor){
        try{
            if(fornecedor.getId() == 0){
                PreparedStatement sql = this.getConexao().
                        prepareStatement("insert into Fornecedores"
                                + "(razaoSocial, cnpj, status, endereco, email) values (?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);
             
                sql.setString(1, fornecedor.getRazaoSocial());
                sql.setString(2, fornecedor.getCNPJ().replace(".","").replace("/","").replace("-",""));
                sql.setString(3, fornecedor.getStatus());
                sql.setString(4, fornecedor.getEndereco());
                sql.setString(5, fornecedor.getEmail());

                if(sql.executeUpdate() > 0){
                    ResultSet key = sql.getGeneratedKeys();
                    key.next();
                    fornecedor.setId(key.getInt(1)); 

                    return true;
                }
                else
                    return false;
            }
            else{
                PreparedStatement sql = this.getConexao().
                        prepareStatement("update fornecedores set razaoSocial = ?, cnpj = ?, status = ?, endereco = ?, email = ?");
                            
                sql.setString(1, fornecedor.getRazaoSocial());
                sql.setString(2, fornecedor.getCNPJ().replace(".","").replace("/","").replace("-",""));
                sql.setString(3, fornecedor.getStatus());
                sql.setString(4, fornecedor.getEndereco());
                sql.setString(5, fornecedor.getEmail());
                
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
    
    public Fornecedor Abrir(int id){
        try{
            PreparedStatement sql = this.getConexao().prepareStatement("select * from fornecedores where id = ?");
            
            sql.setInt(1, id);
            
            ResultSet resultado = sql.executeQuery();
            
            Fornecedor fornecedor = new Fornecedor();
            
            try{
               fornecedor.setId(resultado.getInt("id"));
               fornecedor.setRazaoSocial(resultado.getString("razaoSocial"));
               fornecedor.setCNPJ(resultado.getString("cnpj"));
               fornecedor.setStatus(resultado.getString("status"));
               fornecedor.setEndereco(resultado.getString("endereco"));
               fornecedor.setEmail(resultado.getString("email"));
            }
            catch(Exception ex){
                fornecedor = null;
            }
            
            return fornecedor;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
    public boolean Apagar(Fornecedor fornecedor){
        try{
            PreparedStatement sql = this.getConexao().
                    prepareStatement("delete from Fornecedores where id = ?");
        
            sql.setInt(1, fornecedor.getId());
            
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
    
    public List<Fornecedor> Buscar(Fornecedor filtro) throws ErroValidacaoException{
        try{
            String where = "";
        
            if(filtro != null){
                if(filtro.getRazaoSocial() != null && !filtro.getRazaoSocial().isEmpty())
                    where += "razaosocial like '%"+filtro.getRazaoSocial()+ "%'";
            
                if(filtro.getCNPJ1() != null && !filtro.getCNPJ1().isEmpty() && 
                        !"00.000.000/0000-00".equals(filtro.getCNPJ1())){
                    if(where.length() > 0)
                        where += " and ";
                    where += "cnpj = '"+filtro.getCNPJ().replace(".","").replace("/","").replace("-","") + "'";
       
                }
            }
            
            String consulta = "select * from Fornecedores";
            
            if(where.length() > 0)
                consulta += " where " + where;
            
            PreparedStatement sql = this.getConexao().prepareStatement(consulta);
            
            ResultSet resultado = sql.executeQuery();
            
            List<Fornecedor> fornecedores = new ArrayList();
            
            while(resultado.next()){
                Fornecedor fornecedor = new Fornecedor();
            
                try{
                    fornecedor.setId(resultado.getInt("id"));
                    fornecedor.setStatus(resultado.getString("status"));
                    fornecedor.setRazaoSocial(resultado.getString("razaoSocial"));
                    fornecedor.setCNPJ(resultado.getString("CNPJ"));
                    fornecedor.setEndereco(resultado.getString("endereco"));                   
                    fornecedor.setEmail(resultado.getString("email"));
                }
                catch(Exception ex){
                    fornecedor = null;
                }
                fornecedores.add(fornecedor);
            }
            return fornecedores;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    
        return null;
    }
    
}

