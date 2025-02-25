package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO department (Name) VALUES(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			
			int arrowAffected = st.executeUpdate();
			
			if(arrowAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				
				if(rs.next()) {
					int id = rs.getInt(1);	
					obj.setId(id);	
					
					DB.closeResultSet(rs);
				}else {
					throw new DbException("Erro, nenhuma linha foi afetada.");
				}
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department obj) {

		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("UPDATE department SET name = ? WHERE id = ?");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
			
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected > 0) {
				System.out.println("Linhas Afetadas: " +rowsAffected);
			}else {
				throw new DbException("Erro!! Nenhuma linha afetada.");
			}
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("DELETE from department WHERE Id = ?", Statement.RETURN_GENERATED_KEYS);			
			st.setInt(1, id);
			
			int rownsAffectd = st.executeUpdate();
			
			if(rownsAffectd == 0) {
				
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		catch (DbIntegrityException e1) {
			throw new DbIntegrityException(e1.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
