package org.springframework.samples.petclinic;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.User;


//V1: Add hardcoded data
public class AnadirDatos {
	@Autowired
	SessionFactory sf;
	
	public static void main(String[] args){

		Session hib = (new AnadirDatos()).getSession();
		Transaction ts = hib.beginTransaction();
		
		User usuario = new User();
		usuario.setId(null);
		usuario.setUser("userHib");
		usuario.setPass("passHib");
		usuario.setFechaCreacion(new Date());
		
		//Sacamos un usuario
		
		Query query = hib.createQuery("from owners");
		List list = query.list();
		
		
		usuario.setOwner((Owner)list.get(2)); //mismo
	
		hib.save(usuario);
		
		ts.commit();
		
		query = hib.createQuery("from users");
		list = query.list();
		
		for(User leido : (List<User>)list){
			System.out.println("Usuario: "+leido.getUser()+" \tPass: "+leido.getPass());
		}
		
		hib.close();
	}
	
	Session getSession(){
		return sf.openSession();
	}
			
}