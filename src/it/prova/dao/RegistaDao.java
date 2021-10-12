package it.prova.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.prova.connection.MyConnection;
import it.prova.model.Film;
import it.prova.model.Regista;

public class RegistaDao {

	public List<Regista> list() {

		Connection c = null;
		ResultSet rs = null;
		Statement s = null;
		List<Regista> result = new ArrayList<Regista>();

		try {

			c = MyConnection.getConnection();
			s = c.createStatement();
			rs = s.executeQuery("select * from regista;");

			while (rs.next()) {
				Regista registaTmp = new Regista();
				registaTmp.setId(rs.getLong("id"));
				registaTmp.setNome(rs.getString("nome"));
				registaTmp.setCognome(rs.getString("cognome"));
				registaTmp.setNumeroOscarVinti(rs.getInt("numerooscarvinti"));

				result.add(registaTmp);
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

	// =============================================== FINDBYID
	public Regista findById(long input) {

		if (input < 1) {
			return null;
		}

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Regista result = null;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement("select * from regista u where id=?;");
			ps.setLong(1, input);

			rs = ps.executeQuery();

			if (rs.next()) {
				result = new Regista();
				result.setNome(rs.getString("NOME"));
				result.setCognome(rs.getString("COGNOME"));
				result.setId(rs.getLong("id"));
				result.setNumeroOscarVinti(rs.getInt("numerooscarvinti"));

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

	// --------------------------INSERT
	public int insert(Regista regista) {

		Connection c = null;
		PreparedStatement ps = null;
		int result = 0;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement("INSERT INTO regista (nome, cognome,numerooscarvinti) VALUES (?, ?,?);");
			ps.setString(1, regista.getNome());
			ps.setString(2, regista.getCognome());
			ps.setInt(3, regista.getNumeroOscarVinti());

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

	// ----------------------------UPDATE

	public int update(Regista input) {

		if (input == null || input.getId() < 1) {
			return 0;
		}

		Connection c = null;
		PreparedStatement ps = null;
		int result = 0;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement("UPDATE regista SET nome=?, cognome=?, numerooscarvinti=? where id=?;");
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setInt(3, input.getNumeroOscarVinti());
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

	// ----------------------------------DELETE
	public int delete(Regista input) {

		if (input == null || input.getId() < 1) {
			return 0;
		}

		Connection c = null;
		PreparedStatement ps = null;
		int result = 0;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement("DELETE from regista where id=?;");
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

	// -----------------------------------
	public Regista findChiConPiuOscar() {

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Regista result = null;

		try {

			c = MyConnection.getConnection();
			ps = c.prepareStatement(
					"SELECT *, MAX(numerooscarvinti) as L FROM filmregista.regista GROUP BY nome, cognome ORDER BY L DESC LIMIT 1;");
			rs = ps.executeQuery();

			if (rs.next()) {
				result = new Regista();
				result.setNome(rs.getString("NOME"));
				result.setCognome(rs.getString("COGNOME"));
				result.setId(rs.getLong("id"));
				result.setNumeroOscarVinti(rs.getInt("numerooscarvinti"));

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
}
