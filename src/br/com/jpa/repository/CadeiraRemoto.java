package br.com.jpa.repository;

import java.util.List;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import br.com.jpa.entity.Cadeira;

@Remote
public interface CadeiraRemoto 
{
	public void Inserir(Cadeira c, EntityManager em);
	public boolean Excluir (Cadeira c, EntityManager em);
	public boolean Alterar(Cadeira c, EntityManager em);
	
	public List<Cadeira> listarCadeiras(EntityManager em);
	public List<Cadeira> listarCadeiraCod(EntityManager em, int cod);
}