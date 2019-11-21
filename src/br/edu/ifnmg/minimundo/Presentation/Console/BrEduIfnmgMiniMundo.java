/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifnmg.minimundo.Presentation.Console;

import br.edu.ifnmg.minimundo.DomainModel.Cliente;
import br.edu.ifnmg.minimundo.DomainModel.ErroValidacaoException;
import br.edu.ifnmg.minimundo.Persistence.ClienteRepositorio;
import java.sql.SQLException;

/**
 *
 * @author denil
 */
public class BrEduIfnmgMiniMundo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ErroValidacaoException, SQLException {
        // TODO code application logic here
        
        ClienteRepositorio cliente_repo = new ClienteRepositorio();
        
        Cliente cliente = new Cliente();
        
        cliente.setNome("Petrônio");
        cliente.setCPF("333.888.333-77");
        cliente_repo.Salvar(cliente);
    }
    
}
