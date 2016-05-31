package db;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

import client.Blogeintrag;
import client.Person;
import client.Textbeitrag;

public class BlogMapper {
	

	private static BlogMapper blogMapper = null;
	private Blogeintrag blogeintrag;
	
	 public BlogMapper() {
	    }

	 
	  public static BlogMapper blogMapper() {
	        if (blogMapper == null) {
	            blogMapper = new BlogMapper();
	        }
	        return blogMapper;
	    }
	 
	 
	
		
		
		
		 public Vector<Blogeintrag> findAll() throws SQLException {
		        Connection con = DBConnection.connection();
		        Vector<Blogeintrag> result = new Vector<Blogeintrag>();
		   
		            Statement stmt = con.createStatement();
		            ResultSet rs = stmt.executeQuery(
		            		"select * from blogeintrag join textbeitrag on textbeitrag.id = blogeintrag.fk_textbeitrag join person on person.id = textbeitrag.fk_person ORDER BY blogeintrag.id");
		           
		            
		            
		            // Fuer jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt erstellt.
		            while (rs.next()) {
		            	
		            	//Textbeitrag erstellen
		            	Date datum = rs.getDate("datum");
		            	String inhalt = rs.getString("inhalt");
		            	
		            	Textbeitrag textbeitrag = new Textbeitrag( inhalt);
		            	textbeitrag.setDatum(datum);
		            	
		            	//Person erstellen
		            	String name = rs.getString("name");
		            	String vorname = rs.getString("vorname");
		            	String email = rs.getString("email");
		            	
		            	Person person = new Person(name, vorname, email);
		            	
		            	//Blogeintrag erstellen
		            	int id = rs.getInt("id");
		            	String titel = rs.getString("titel");
		            	String untertitel = rs.getString("untertitel");
		            	Blogeintrag blogeintrag = new Blogeintrag(textbeitrag, person, titel, untertitel);
		            
		           
		              result.addElement(blogeintrag);
		            }
		       
		        // Ergebnisvektor zurueckgeben
		        return result;
		    }	
		
		
		 
		 
	public  void add (Blogeintrag blogeintrag) throws SQLException{
		
		this.blogeintrag = blogeintrag;
		
		Connection con = DBConnection.connection();
		
		//Person erstellen
		
		String insertTableSQL = "INSERT INTO person "
				+ "(name, vorname) VALUES "
				+ "(?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, blogeintrag.person.getName());
		preparedStatement.setString(2, "asdad222");
	
		// execute insert SQL stetement
		preparedStatement.executeUpdate();
		
		ResultSet key = preparedStatement.getGeneratedKeys();
		key.next();
		int id = key.getInt(1);
		
		
		//Textbeitrag erstellen
		
		insertTableSQL = "INSERT INTO textbeitrag "
				+ "(datum, inhalt, fk_person) VALUES "
				+ "(?,?,?)";
		preparedStatement = con.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setDate(1, blogeintrag.textbeitrag.getDatum());
		preparedStatement.setString(2, blogeintrag.textbeitrag.getInhalt());
		preparedStatement.setInt(3, id);
	
		// execute insert SQL stetement
		preparedStatement.executeUpdate();
		
		key = preparedStatement.getGeneratedKeys();
		key.next();
		id = key.getInt(1);
		
		//Blogeintrag erstellen
		
		insertTableSQL = "INSERT INTO blogeintrag "
				+ "(titel, untertitel, fk_textbeitrag) VALUES "
				+ "(?,?,?)";
		preparedStatement = con.prepareStatement(insertTableSQL, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, blogeintrag.getTitel());
		preparedStatement.setString(2, blogeintrag.getUntertitel());
		preparedStatement.setInt(3, id);
	
		// execute insert SQL stetement
		preparedStatement.executeUpdate();
		
		key = preparedStatement.getGeneratedKeys();
		key.next();
		id = key.getInt(1);
		
		
		
		
		
	
	
		 
	}
	
public void delete (int id) throws SQLException	{
	
	
	Connection con = DBConnection.connection();
		
	Statement stmt = con.createStatement();
	
	
     String sql = "DELETE FROM blogeintrag " +
                  "WHERE id = "+id;
     
     stmt.executeUpdate(sql);
		
		
	}
	
	
	
	
	
	
	
//public addKommentar (Kommentar kommentar){}









}


		 
		 
		 
		
		
		
	 
	 
	 




