package br.com.jpa.servlet;

import java.io.IOException;
import java.io.PrintWriter;

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

@WebServlet("/CadastroCadeira")
public class CadastroCadeira extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public CadastroCadeira()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().append("Server at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Tentando Cadastrar");
		EntityManager em;
		EntityManagerFactory managerfactory;
		managerfactory = Persistence.createEntityManagerFactory("ProjetoJPA");
		em = managerfactory.createEntityManager();
		
		try
		{
			CadeiraRepository funcRep = new CadeiraRepository();
			Cadeira c = new Cadeira();
			c.setMarca(request.getParameter("marca"));
			c.setModelo(request.getParameter("modelo"));
			c.setCor(request.getParameter("cor"));
			
			
			funcRep.Inserir(c, em);
			PrintWriter p = response.getWriter();
			p.print("<p>Cadeira <b>"+c.getModelo()+"</b>, <i>"+c.getMarca()+"</i> - <i>"+ c.getCor()+"</i> cadastrada com sucesso!</p>"
					+ "<p><a href='index.html'>Inicio</a></p>");
		}
		finally
		{
			if(em.isOpen())
				em.close();
			em = null;
			if(managerfactory != null)
				managerfactory.close();
			request.logout();
		}
	}
	
}
