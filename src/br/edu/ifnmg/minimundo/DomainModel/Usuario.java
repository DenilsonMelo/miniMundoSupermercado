package br.edu.ifnmg.minimundo.DomainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
      
    private int id;
    private String status;
    private String nome;
    private String CPF;
    private String endereco;
    private List<String> telefones;
    private String email;
    private String sexo;
    private String usuario;
    private String senha;
    
    private Pattern regex_cpf = 
            Pattern.compile("\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}");
    
    
    public Usuario(){
        this.id = 0;
        this.nome = "";
        this.CPF = "00000000000";
        this.sexo = "M";
        this.status = "";
        this.telefones = new ArrayList<>(); 
        this.usuario = "";
        this.senha = "";
    
    }
    
    public Usuario(String nome, String CPF){
        this.id = 0;
        this.nome = nome;
        this.CPF = CPF;
        this.sexo = "M";
        this.status = "";
        this.telefones = new ArrayList<>();
        this.usuario = "";
        this.senha = "";
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ErroValidacaoException {
        this.nome = nome;
    }

    public String getCPF() {
         return CPF.substring(0, 3)+"."+
                CPF.substring(3, 6)+"."+
                CPF.substring(6, 9)+"-"+
                CPF.substring(9, 11);
    }

    public void setCPF(String CPF) throws ErroValidacaoException {
        Matcher m = regex_cpf.matcher(CPF);
        if(m.matches())
            this.CPF = CPF.replace(".", "").replace("-", "");
        else
            throw new ErroValidacaoException("CPF Inválido!");
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }
    
    public void addTelefone(String telefone){
        if(! this.telefones.contains(telefone))
            this.telefones.add(telefone);
    }
    
    public void removeTelefone(String telefone){
        if(this.telefones.contains(telefone))
            this.telefones.remove(telefone);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
     @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.CPF, other.CPF)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
    
}
