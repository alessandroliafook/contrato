package alessandrofook.contrato.repository;

import alessandrofook.contrato.model.autenticacao.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredencialRepository extends JpaRepository<Credencial, Long> {


  void deleteByPessoaId(Long pessoaId);

  void deleteByLogin(String login);

  Credencial findByLogin(String login);
}