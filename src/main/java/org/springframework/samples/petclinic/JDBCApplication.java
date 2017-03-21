package org.springframework.samples.petclinic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetType;

public class JDBCApplication {

	public static void main(String[] args) {
		System.out.println("-------- Test de conexión con MySQL ------------");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("No encuentro el driver en el Classpath");
			e.printStackTrace();
			return;
		}

		System.out.println("Driver instalado y funcionando");
		Connection connection = null;
		PreparedStatement ps= null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/petclinic","root", "Everis2017");
			if (connection != null)
				System.out.println("Conexión establecida");
			
			//Crear objeto clase Owner
				Owner yo = new Owner();
				yo.setFirstName("Jose2o");
				yo.setLastName("Raez2");
				yo.setAddress("C/Antonio de Nebrija n19");
				yo.setCity("Sevilla");
				yo.setTelephone("628420203");
			//Rellenar datos personales (parametrizado)
			//Insertarte a ti mismo
				String sql = "INSERT INTO owners VALUES (NULL, ?, ?, ?, ?, ?)";
			    ps = connection.prepareStatement(sql);
			    ps.setString(1, yo.getFirstName());
			    ps.setString(2, yo.getLastName());
			    ps.setString(3, yo.getAddress());
			    ps.setString(4, yo.getCity());
			    ps.setString(5, yo.getTelephone());
			    System.out.println("Se ha introducido "+ps.executeUpdate()+" fila. ");
			
			//Crear Pet
			    Pet mascota = new Pet();
			//Insertar Pet (parametrizado)
			    mascota.setName("Chispa");
			    /*GregorianCalendar fecha_nac = new GregorianCalendar();
			    fecha_nac.set(GregorianCalendar.DAY_OF_MONTH, 10);
			    fecha_nac.set(GregorianCalendar.MONTH,0);
			    fecha_nac.set(GregorianCalendar.YEAR, 2008);*/
			    mascota.setBirthDate(new Date());
			    
			    PetType tipo = new PetType();
			    tipo.setId(2);
			    tipo.setName("Dog");
			    mascota.setType(tipo);
			    
			    //Sacamos el owner.			    
			    sql = "SELECT * FROM owners WHERE first_name=? AND last_name=?;";
			    ps = connection.prepareStatement(sql);
			    ps.setString(1, yo.getFirstName());
			    ps.setString(2, yo.getLastName());
				ResultSet sr = ps.executeQuery();
				sr.next();
			    int idOwn = sr.getInt(1);
			    

			   
			    sql = "INSERT INTO pets VALUES (NULL, ?, ?, ?, ?, NULL)";
			    ps = connection.prepareStatement(sql);
			    ps.setString(1, mascota.getName());
			    ps.setDate(2, new java.sql.Date(mascota.getBirthDate().getTime()));
			    ps.setInt(3, mascota.getType().getId());
			    ps.setInt(4, idOwn);
			    
			    System.out.println("Se ha introducido "+ps.executeUpdate());
				
			    //Hacemos consulta
			    
			    sql = "SELECT * FROM owners WHERE first_name=? AND last_name=?;";
			    ps = connection.prepareStatement(sql);
			    ps.setString(1, yo.getFirstName());
			    ps.setString(2, yo.getLastName());
				sr = ps.executeQuery();

				while (sr.next()){
					
					System.out.print(sr.getInt(1) + "\t");
					System.out.print(sr.getString(2) + "\t");
					System.out.print(sr.getString(3) + "\t");
					System.out.print(sr.getString(4) + "\t");
					System.out.print(sr.getString(5) + "\t");
					System.out.print(sr.getString(6) + "\n");
				}
				sql = "SELECT * FROM pets WHERE name=?;";
			    ps = connection.prepareStatement(sql);
			    ps.setString(1, mascota.getName());
				sr = ps.executeQuery();

				while (sr.next()){
					
					System.out.print(sr.getInt(1) + "\t");
					System.out.println(sr.getString(2) + "\t");
				}
				
			//Borrar pet
				
				sql = "DELETE FROM pets WHERE name=?;";
				ps = connection.prepareStatement(sql);
			    ps.setString(1, mascota.getName());
			    System.out.println("Se ha eliminado "+ps.executeUpdate());
			//Borrar owner
			    sql = "DELETE FROM owners WHERE first_name=? AND last_name=?;";
			    ps = connection.prepareStatement(sql);
			    ps.setString(1, yo.getFirstName());
			    ps.setString(2, yo.getLastName());
			    System.out.println("Se ha eliminado "+ps.executeUpdate());
		
			/*
			sql = "INSERT INTO owners VALUES (NULL, ?, ?, ?, ?, ?)";
		    ps = connection.prepareStatement(sql);
		    ps.setString(1, "Pepe");
		    ps.setString(2, "Heidi");
		    ps.setString(3, "Gran Plaza sn");
		    ps.setString(4, "Mayorca");
		    ps.setString(5, "999333000");
		    System.out.println("Se ha actualizado "+ps.executeUpdate());
			
			*/
			    
		    sql = "SELECT * FROM owners;";
		    ps = connection.prepareStatement(sql);

			sr = ps.executeQuery();

			while (sr.next()){
				
				System.out.print(sr.getInt(1) + "\t");
				System.out.print(sr.getString(2) + "\t");
				System.out.print(sr.getString(3) + "\t");
				System.out.print(sr.getString(4) + "\t");
				System.out.print(sr.getString(5) + "\t");
				System.out.print(sr.getString(6) + "\n");
			}
			
			sr.close();
			ps.close();
			
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		} finally {
			try {
				if(ps != null)
					connection.close();
			} catch (SQLException se) {
		    	  
		    }
		    try {
		        if(connection != null)
		            connection.close();
		    } catch (SQLException se) {
		         	se.printStackTrace();
		    }
		}
	}

}
