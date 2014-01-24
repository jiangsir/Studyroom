package tw.jiangsir.Utils.DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import tw.jiangsir.Utils.Objects.Upfile;

public class UpfileDAO extends SuperDAO<Upfile> {

	@Override
	public int insert(Upfile upfile) throws SQLException {
		String sql = "INSERT INTO upfiles (filename, filetype, `bytes`, `timestamp`) VALUES (?, ?, ?, ?);";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql,
				Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, upfile.getFilename());
		pstmt.setString(2, upfile.getFiletype());
		pstmt.setBytes(3, upfile.getBytes());
		pstmt.setTimestamp(4, upfile.getTimestamp());
		return this.executeInsert(pstmt);
	}

	@Override
	public int update(Upfile upfile) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(long id) throws SQLException {
		String sql = "DELETE FROM upfiles WHERE id=?";
		PreparedStatement pstmt = this.getConnection().prepareStatement(sql);
		pstmt.setLong(1, id);
		return this.executeDelete(pstmt);
	}

	public List<Upfile> getUpfiles() {
		String sql = "SELECT * FROM upfiles";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			return this.executeQuery(pstmt, Upfile.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Upfile getUpfileById(int id) {
		String sql = "SELECT * FROM upfiles WHERE id=?";
		try {
			PreparedStatement pstmt = this.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, id);
			for (Upfile upfile : this.executeQuery(pstmt, Upfile.class)) {
				return upfile;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
