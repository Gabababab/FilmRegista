package it.prova.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import it.prova.connection.MyConnection;
import it.prova.model.*;

public class FilmDao {

	// Select *
	public List<Film> list() {

		Connection c = null;
		ResultSet rs = null;
		Statement s = null;
		List<Film> result = new ArrayList<Film>();

		try {

			c = MyConnection.getConnection();
			s = c.createStatement();

			rs = s.executeQuery("select * from film f inner join regista r on r.id=f.regista_id;");

			while (rs.next()) {
				Film filmTmp = new Film();
				filmTmp.setTitolo(rs.getString("titolo"));
				filmTmp.setGenere(rs.getString("genere"));
				filmTmp.setId(rs.getLong("id"));
				filmTmp.setDurata(rs.getInt("durata"));

				Regista registaTmp = new Regista();
				registaTmp.setId(rs.getLong("id"));
				registaTmp.setNome(rs.getString("nome"));
				registaTmp.setCognome(rs.getString("cognome"));
				registaTmp.setNumeroOscarVinti(rs.getInt("numerooscarvinti"));

				filmTmp.setRegista(registaTmp);
				result.add(filmTmp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				s.close();
				c.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// -------------------------------------
	public Film selectJoinById(Long idFilmInput) {

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Film result = null;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement(
					"SELECT * FROM film f left outer join regista r on f.regista_id=r.id where f.id=?; ");
			ps.setLong(1, idFilmInput);

			rs = ps.executeQuery();

			if (rs.next()) {
				result = new Film();
				result.setTitolo(rs.getString("titolo"));
				result.setGenere(rs.getString("genere"));
				result.setId(rs.getLong("id"));
				result.setDurata(rs.getInt("durata"));

				Regista registaTmp = new Regista();
				registaTmp.setId(rs.getLong("id"));
				registaTmp.setNome(rs.getString("nome"));
				registaTmp.setCognome(rs.getString("cognome"));
				registaTmp.setNumeroOscarVinti(rs.getInt("numerooscarvinti"));

				result.setRegista(registaTmp);
			} else {
				result = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//--------------------------INSERT
	public int insert(Film film) {

		Connection c = null;
		PreparedStatement ps = null;
		int result = 0;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement("INSERT INTO film (titolo, genere,durata,regista_id) VALUES (?, ?,?, ?);");
			ps.setString(1, film.getTitolo());
			ps.setString(2, film.getGenere());
			ps.setInt(3, film.getDurata());
			ps.setLong(4, film.getRegista().getId());
			
			result = ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	//----------------------------UPDATE
	
	public int update(Film input) {

		if (input == null || input.getId() < 1) {
			return 0;
		}

		Connection c = null;
		PreparedStatement ps = null;
		int result = 0;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement("UPDATE film SET titolo=?, genere=?, durata=? where id=?;");
			ps.setString(1, input.getTitolo());
			ps.setString(2, input.getGenere());
			ps.setInt(3, input.getDurata());
			ps.setLong(4, input.getId());

			result = ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				ps.close();
				c.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	//----------------------------------DELETE
	public int delete(Film input) {

		if (input == null || input.getId() < 1) {
			return 0;
		}

		Connection c = null;
		PreparedStatement ps = null;
		int result = 0;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement("DELETE from film where id=?;");
			ps.setLong(1, input.getId());

			result = ps.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {
			try {
				ps.close();
				c.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
