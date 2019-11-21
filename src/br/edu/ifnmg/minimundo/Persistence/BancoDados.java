package br.edu.ifnmg.minimundo.Persistence;

/*Colocar o nome do DB no driver manager*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoDados {
    private Connection conexao;
    
    public BancoDados(){
        try {
            //Carrega o driver do MySQL na memória
            Class.forName("com.mysql.jdbc.Driver");
            
            //Conecta ao banco de dados
            conexao = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/MINIMUNDO","root","info159753");
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver do banco de dados não foi encontrado!");
        
        } catch (SQLException ex) {
            System.out.println("Dados da conexão com o banco de dados estão incorretos!");
        }    
    }
    public Connection getConexao(){
        return conexao;
    }
}
