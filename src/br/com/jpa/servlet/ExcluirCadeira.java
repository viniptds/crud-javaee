package br.com.jpa.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.jpa.entity.Cadeira;
import br.com.jpa.repository.CadeiraRepository;
@WebServlet("/ExcluirCadeira")
public class ExcluirCadeira extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ExcluirCadeira()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().append("Server at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Tentando excluir");
		EntityManager em;
		EntityManagerFactory managerfactory;
		managerfactory = Persistence.createEntityManagerFactory("ProjetoJPA");
		em = managerfactory.createEntityManager();
		
		try
		{
			CadeiraRepository funcRep = new CadeiraRepository();
			Cadeira c = new Cadeira();
			PrintWriter p = response.getWriter();
			c.setCodigo(Integer.parseInt(request.getParameter("codigo")));
			List<Cadeira> lista = funcRep.listarCadeiraCod(em, c.getCodigo());
			
			if(lista.size()==0 || lista.isEmpty())
				p.print("Cadeira "+c.getCodigo()+" não encontrada!");
			else
			{
				c.setCor(lista.get(0).getCor());
				c.setModelo(lista.get(0).getModelo());
				c.setMarca(lista.get(0).getMarca());
				
				
				if(funcRep.Excluir(c, em))
				{
					p.print("<p>Cadeira <b>"+c.getCodigo()+"</b> - <i>"+c.getModelo()+", "+c.getMarca()+"</i> - <i>"+ c.getCor()+"</i> EXCLUIDA COM SUCESSO</p>"
							+ "<p><a href='index.html'>Inicio</a></p>");
				}
				else
				{
					p.print("<p>FALHA NA EXCLUSÃO DA CADEIRA <b>"+c.getCodigo()+"</b> - <i>"+c.getModelo()+", "+c.getMarca()+"</i> - <i>"+ c.getCor()+"</i></p>"
							+ "<p><a href='index.html'>Inicio</a></p>");
				}					
				
			}
			
			
		}
		finally
		{
			if(em.isOpen())
				em.close();
			em = null;
			if(managerfactory != null)
				managerfactory.close();
		}
	}
	
}

