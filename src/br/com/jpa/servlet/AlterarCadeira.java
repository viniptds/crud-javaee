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

	
@WebServlet("/AlterarCadeira")
public class AlterarCadeira extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public AlterarCadeira()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getWriter().append("Server at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Tentando alterar");
		EntityManager em;
		EntityManagerFactory managerfactory;
		managerfactory = Persistence.createEntityManagerFactory("ProjetoJPA");
		em = managerfactory.createEntityManager();
		
		try 
		{			
			CadeiraRepository cadRep = new CadeiraRepository();
			Cadeira cad = new Cadeira();
			PrintWriter p = response.getWriter();
			cad.setCodigo(Integer.parseInt(request.getParameter("codigo")));					
			cad.setModelo(request.getParameter("modelo"));
			cad.setMarca(request.getParameter("marca"));			
			cad.setCor(request.getParameter("cor"));
			// vai no repositorio e realiza alteração
			if(cadRep.Alterar(cad, em))
				p.print("<p>Cadeira <b>"+cad.getCodigo()+"</b> alterada com sucesso!<p>");
			
			p.print("<a href='index.html'>Início</a>");
			
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
	}
		
}
