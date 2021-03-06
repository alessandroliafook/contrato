package alessandrofook.contrato.service;

import alessandrofook.contrato.excecoes.CadastroDePessoaException;
import alessandrofook.contrato.excecoes.EdicaoDePessoaException;
import alessandrofook.contrato.excecoes.PessoaInexistenteException;
import alessandrofook.contrato.model.contrato.Contrato;
import alessandrofook.contrato.model.pessoa.Pessoa;
import alessandrofook.contrato.repository.PessoaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PessoaService {

  @Autowired
  private PessoaRepository repository;

  /**
   * Realiza o cadastro de uma nova pessoa no banco de dados do sistema.
   *
   * @param pessoa - Objeto do tipo pessoa a ser cadastrado no sistema.
   * @return Objeto do tipo pessoa conforme se encontra no banco de dados.
   */
  public Pessoa cadastrarPessoa(Pessoa pessoa) {

    if (pessoa.getId() != null) {
      throw new CadastroDePessoaException("O valor do id deve ser selecionado pelo servidor!");

    } else if (repository.existsByNome(pessoa.getNome())) {
      throw new CadastroDePessoaException("Pessoa já existe no sistema");

    } else {
      return repository.save(pessoa);
    }
  }

  /**
   * Modifica o valor do status de pagamento para o valor lógico oposto.
   *
   * @param id - chave de registro do usuário a ser objeto da alteração.
   * @return Objeto do tipo Pessoa com a alteração do status de pagamento.
   */
  public Pessoa mudarStatusDePagamento(Long id) {

    if (!repository.existsById(id)) {
      throw new PessoaInexistenteException();
    }

    Pessoa pessoa = repository.getOne(id);
    pessoa.setStatusDePagamento(!pessoa.isStatusDePagamento());

    return repository.save(pessoa);
  }

  /**
   * Remove um determinado usuário do sistema.
   *
   * @param id - chave de registro do usuário a ser objeto da alteração.
   */
  public void removerPessoa(Long id) {
    if (!repository.existsById(id)) {
      throw new PessoaInexistenteException();
    } else {
      repository.deleteById(id);
    }
  }

  public List<Pessoa> listarPessoas() {
    return repository.findAll();
  }

  /**
   * Método que modifica o nome da pessoa cadastrada no sistema.
   * @param id - Id da pessoa a ser modificada.
   * @param nome - Novo nome a ser atribuído a pessoa.
   * @return - Objeto do tipo Pessoa atualizado no banco de dados do sistema.
   */
  public Pessoa editarNomeDaPessoa(Long id, String nome) {

    Pessoa pessoa = repository.getOne(id);
    pessoa.setNome(nome);
    return repository.save(pessoa);
  }


  /**
   * Método que remove a referência de um contrato do registro de uma pessoa.
   *
   * @param contrato - contrato cuja refência deve ser removida.
   */
  public void removerContrato(Contrato contrato) {

    Pessoa pessoa = repository.findByContratosContaining(contrato);
    pessoa.getContratos().remove(contrato);
    editarPessoa(pessoa);
  }

  /**
   * Método que cadastra a referência de um contrato em uma pessoa.
   *
   * @param contratoCadastrado - Objeto do tipo contrato a ser referenciado.
   * @param id - id da pessoa a conter a referência do contrato.
   */
  public void cadastrarContrato(Contrato contratoCadastrado, Long id) {
    Pessoa pessoa = repository.getOne(id);

    pessoa.getContratos().add(contratoCadastrado);
    editarPessoa(pessoa);
  }

  /**
   * Método que edita as informações de uma pessoa cadastrada no sistema.
   *
   * @param pessoa - Objeto contendo as informações de uma pessoa do sistema.
   * @return Objeto do tipo Pessoa com as informações atualizadas na base de dados.
   */
  private Pessoa editarPessoa(Pessoa pessoa) {

    if (!repository.existsById(pessoa.getId())) {
      throw new PessoaInexistenteException();

    } else {
      Pessoa pessoaCadastrada = repository.getOne(pessoa.getId());

      if (pessoaCadastrada.isStatusDePagamento() != pessoa.isStatusDePagamento()) {
        throw new EdicaoDePessoaException("Status de Pagamento");

      }

      return repository.save(pessoa);
    }
  }
}
