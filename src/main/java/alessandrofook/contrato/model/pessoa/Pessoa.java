package alessandrofook.contrato.model.pessoa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "O nome da pessoa não pode ser null!")
    @NotEmpty(message = "O nome da pessoa não pode ser vazio!")
    private String nome;

    @NotEmpty(message = "A função da pessoa não pode ser vazia!")
    @NotNull(message = "A função da pessoa não pode ser null!")
    private FuncaoPessoa funcao;

    @NotNull(message = "O senha da pessoa não pode ser null!")
    @NotEmpty(message = "O senha da pessoa não pode ser vazio!")
    private String senha;

    public Pessoa() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public FuncaoPessoa getFuncao() {
        return funcao;
    }

    public void setFuncao(FuncaoPessoa funcao) {
        this.funcao = funcao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(getNome(), pessoa.getNome()) &&
                getFuncao() == pessoa.getFuncao() &&
                Objects.equals(getSenha(), pessoa.getSenha());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNome(), getFuncao(), getSenha());
    }
}
