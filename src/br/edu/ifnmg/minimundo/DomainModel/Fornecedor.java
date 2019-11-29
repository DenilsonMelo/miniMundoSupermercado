package br.edu.ifnmg.minimundo.DomainModel;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fornecedor {
   
    private int id;
    private String status;
    private String razaoSocial;
    private String CNPJ; /*XX.XXX.XXX/XXXX-XX*/
    private String endereco;
    private String email;   

    private Pattern regex_cnpj = 
            Pattern.compile("\\d{2}\\.?\\d{3}\\.?\\d{3}\\/?\\d{4}\\-?\\d{2}");
           
    public Fornecedor(){
        this.id = 0;
        this.razaoSocial = "";
        this.CNPJ = "00000000000000";
        this.status = "";
        this.endereco = "";
    }
    
    public Fornecedor(String razaoSocial, String CNPJ){
        this.id = 0;
        this.razaoSocial = razaoSocial;
        this.CNPJ = CNPJ;
        this.status = "";
        this.endereco = "";
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) throws ErroValidacaoException {
        if(razaoSocial.length() < 3)
            throw new ErroValidacaoException("O campo razão social deve ter ao menos 3 caracteres!");
        this.razaoSocial = razaoSocial;
    }

    public String getCNPJ() {  /*XX.XXX.XXX/XXXX-XX*/
        return CNPJ.substring(0, 2)+"."+    
        CNPJ.substring(2, 5)+"."+
        CNPJ.substring(5, 8)+"/"+
        CNPJ.substring(8, 12)+"-"+
        CNPJ.substring(12, 15);
    }
    
    public String getCNPJ1() {  /*XX.XXX.XXX/XXXX-XX*/
        return CNPJ.substring(0, 2)+"."+    
        CNPJ.substring(2, 5)+"."+
        CNPJ.substring(5, 8)+"/"+
        CNPJ.substring(8, 12)+"-"+
        CNPJ.substring(12, 14);
    }

    public void setCNPJ(String CNPJ) throws ErroValidacaoException {
        Matcher cnpj = regex_cnpj.matcher(CNPJ);
        if(cnpj.matches())
            this.CNPJ = CNPJ.replace(".", "").replace("/", "".replace("-",""));
        else
            throw new ErroValidacaoException("CNPJ Inválido!");
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        hash = 71 * hash + Objects.hashCode(this.status);
        hash = 71 * hash + Objects.hashCode(this.razaoSocial);
        hash = 71 * hash + Objects.hashCode(this.CNPJ);
        hash = 71 * hash + Objects.hashCode(this.endereco);
        hash = 71 * hash + Objects.hashCode(this.email);
        hash = 71 * hash + Objects.hashCode(this.regex_cnpj);
        return hash;
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
        final Fornecedor other = (Fornecedor) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.razaoSocial, other.razaoSocial)) {
            return false;
        }
        if (!Objects.equals(this.CNPJ, other.CNPJ)) {
            return false;
        }
        if (!Objects.equals(this.endereco, other.endereco)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.regex_cnpj, other.regex_cnpj)) {
            return false;
        }
        return true;
    }
    
    
}
