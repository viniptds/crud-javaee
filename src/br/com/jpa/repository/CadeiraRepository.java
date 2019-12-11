package br.com.jpa.repository;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import java.util.List;
import br.com.jpa.entity.Cadeira;

@Stateless
public class CadeiraRepository implements CadeiraRemoto {
	
	public void Inserir (Cadeira c, EntityManager em)
	{
		em.getTransaction().begin();
//		if(em.isOpen())
//			System.out.println("Conexao aberta");
//		if(em.createQuery("select f from funcionario") == null)
//			System.out.println("Sem dados");
//		else
//			System.out.println("Busca deu certo");
		try
		{
			List<Cadeira> listaGravados = listarCadeiras(em);
			int proximo = 0;
			if(listaGravados == null || listaGravados.size() == 0)
				proximo = 1;
			else
				proximo = listaGravados.get(listaGravados.size()-1).getCodigo()+1;
			c.setCodigo(proximo);
			em.persist(c);
			em.flush();
			
			em.getTransaction().commit();
		}
		catch(Exception e)
		{
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	public boolean Excluir (Cadeira c, EntityManager em)
	{
		em.getTransaction().begin();
		try
		{
			em.remove(em.getReference(Cadeira.class, c.getCodigo()));
			em.flush();
			
			em.getTransaction().commit();
			return true;
		}
		catch(Exception e)
		{
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean Alterar(Cadeira c, EntityManager em)
	{
		em.getTransaction().begin();
		try
		{
			em.merge(c);
			
			em.flush();
			em.getTransaction().commit();
			return true;
		}
		catch(Exception e)
		{
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Cadeira> listarCadeiras(EntityManager em)
	{
		List<Cadeira> lista = em.createQuery("select f from Cadeira f", Cadeira.class).getResultList();
		em.clear();
		return lista;
	}
	
	public List<Cadeira> listarCadeiraCod(EntityManager em, int cod)
	{
		List<Cadeira> lista = em.createQuery("from Cadeira cad where cad.codigo = :cod", Cadeira.class).setParameter("cod", cod).getResultList();
		em.clear();
		System.out.println("Lista retornando "+lista.size()+" elemento(s)");
		return lista;
	}
}
