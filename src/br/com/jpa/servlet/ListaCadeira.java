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

@WebServlet("/ListaCadeira")
public class ListaCadeira extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ListaCadeira()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Listando todas cadeiras");
		EntityManager em;
		EntityManagerFactory managerfactory;
		managerfactory = Persistence.createEntityManagerFactory("ProjetoJPA");
		
		em = managerfactory.createEntityManager();
		try
		{
			//instanciando os objetos
			CadeiraRepository cadRep = new CadeiraRepository();
			Cadeira c = new Cadeira();
			List<Cadeira> lista = cadRep.listarCadeiras(em);
			PrintWriter p = response.getWriter(); //cria escritor para printar na pag
			
			p.print("<table><tr><td>Codigo</td><td>Modelo</td><td>Marca</td><td>Cor</td></tr>");			
			if(lista.isEmpty() || lista.size()==0)
				p.println("<tr><td colspan='4'>NÃO HÁ CADEIRAS!</td></tr>");
			else
				for(int i=0; i<lista.size(); i++)
				{
					c.setCodigo(lista.get(i).getCodigo());
					c.setCor(lista.get(i).getCor());
					c.setMarca(lista.get(i).getMarca());
					c.setModelo(lista.get(i).getModelo());
					p.print("<tr><td>"+c.getCodigo()+"</td><td>"+c.getModelo()+"</td><td>"+c.getMarca()+"</td><td>"+c.getCor()+"</td></tr>");
				}
			p.print("</table>");	
			p.print("<a href='index.html'>Inicio</a>");
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Passando pela listagem");
		EntityManager em;
		EntityManagerFactory managerfactory;
		managerfactory = Persistence.createEntityManagerFactory("ProjetoJPA");
		
		em = managerfactory.createEntityManager();
		try
		{
			CadeiraRepository funcRep = new CadeiraRepository();
			Cadeira c = new Cadeira();
			List<Cadeira> lista = null;
			PrintWriter p = response.getWriter(); //cria escritor para printar na pag
			
			if(request.getParameter("codigo")!=null && request.getParameter("op").equals("2"))
			{
				c.setCodigo(Integer.parseInt(request.getParameter("codigo")));
				lista = funcRep.listarCadeiraCod(em, c.getCodigo());
				if(lista.isEmpty())
				{
					p.print("Cadeira "+c.getCodigo()+" não encontrada!");
					p.print("<a href='index.html'>Inicio</a>");		
				}
				else
				{
					c.setModelo(lista.get(0).getModelo());
					c.setCor(lista.get(0).getCor());
					c.setMarca(lista.get(0).getMarca());
					p.print("<html><head></head><body>"
							+ "<form action = 'AlterarCadeira' method='POST'>"
							+ "<table><tr><td>Codigo</td><td>Modelo</td><td>Marca</td><td>Cor</td></tr>");
					
					p.print("<tr><td><input type='text' readonly='readonly' name='codigo' value='"+c.getCodigo()+"'/ ></td>"
							+ "<td><input type='text' name='modelo' value='"+c.getModelo()+"'/ ></td>"
							+ "<td><select name='marca'>"
							+ "<option value='CAVALETTI'>Cavaletti</option>"
							+ "<option value='PLAXMETAL'>Plaxmetal</option>"
							+ "<option value='MOBILAN'>Mobilan</option></select></td>"								
							+ "<td><select name='cor'>"
							+ "<option value='PRETO'>Preto</option>"
							+ "<option value='BRANCO'>Branco</option>"
							+ "<option value='AMARELO'>Amarelo</option>"
							+ "<option value='VERMELHO'>Vermelho</option>"
							+ "</select></td>"
							+ "</tr></table>"
							+ "<input type='submit' value='Alterar'><a href='index.html'>Inicio</a></form></body></html>");
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
