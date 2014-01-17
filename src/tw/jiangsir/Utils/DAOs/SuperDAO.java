package tw.jiangsir.Utils.DAOs;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import tw.jiangsir.Utils.Annotations.Persistent;

abstract public class SuperDAO<T> {
	private static Connection conn = null;
	private static DataSource source = null;

	abstract public int insert(T t) throws SQLException;

	abstract public int update(T t) throws SQLException;

	abstract public boolean delete(int i) throws SQLException;

	Logger logger = Logger.getLogger(this.getClass().getName());
	HashMap<String, Field> fields = new HashMap<String, Field>();

	public Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				if (source == null) {
					InitialContext icontext = new InitialContext();
					source = (DataSource) icontext
							.lookup("java:comp/env/mysql");
				}
				logger.info("資料庫連結不存在或關閉了。嘗試重新取得連線");
				conn = source.getConnection();
			} else {
				return conn;
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void setConnection(String driver, String jdbc, String dbaccount,
			String dbpasswd) {
		try {
			Class.forName(driver); // 連結驅動程式
			SuperDAO.conn = DriverManager.getConnection(jdbc, dbaccount,
					dbpasswd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getSetMethodName(String columnName) {
		String firstchar = columnName.substring(0, 1);
		firstchar = firstchar.toUpperCase();
		return "set" + firstchar + columnName.substring(1);
	}

	/**
	 * 取得某個 class 內某個 Persistent name 的 Field
	 * 
	 * @param theclass
	 * @return
	 */
	private Field getField(Class<T> theclass, String persistentname) {
		if (this.fields.size() == 0) {
			for (Field field : theclass.getDeclaredFields()) {
				Persistent persistent = field.getAnnotation(Persistent.class);
				if (persistent != null) {
					fields.put(persistent.name(), field);
				}
			}
		}
		return fields.get(persistentname);
	}

	public ArrayList<T> executeQueryByAnnotations(PreparedStatement pstmt,
			Class<T> clazz) {
		long starttime = System.currentTimeMillis();
		ResultSet rs = null;
		ArrayList<T> list = new ArrayList<T>(); // 準備用 ArrayList 來回傳查詢結果。
		try {
			rs = pstmt.executeQuery(); // 執行查詢
			ResultSetMetaData rsmd = rs.getMetaData(); // 取得資料表的 metadata
			int columnCount = rsmd.getColumnCount(); // 有幾個 column
			String columnName;
			Field field;
			Object value;
			while (rs.next()) {
				T t = (T) clazz.newInstance(); // 實體化一個 T
				for (int i = 1; i <= columnCount; i++) {
					columnName = rsmd.getColumnName(i);

					field = this.getField(clazz, columnName);
					if (field == null) {
						logger.info("資料庫欄位 columnName=" + columnName
								+ " 找不到相對應的 Field, Class name="
								+ clazz.getName());
						continue;
					}
					if (rsmd.getColumnType(i) == Types.BOOLEAN
							|| rsmd.getColumnType(i) == Types.TINYINT) {
						value = rs.getBoolean(columnName);
					} else if (rsmd.getColumnType(i) == Types.INTEGER) {
						value = rs.getInt(columnName);
					} else if (rsmd.getColumnType(i) == Types.BIGINT) {
						value = rs.getLong(columnName);
					} else if (rsmd.getColumnType(i) == Types.LONGVARBINARY) {
						continue;
					} else if (rsmd.getColumnType(i) == Types.BLOB) {
						continue;
					} else {
						value = rs.getObject(columnName);
					}

					try {
						String settername = "set"
								+ field.getName().toUpperCase().substring(0, 1)
								+ field.getName().substring(1);
						Method settermethod = clazz.getMethod(settername,
								new Class[] { value.getClass() });
						settermethod.invoke(t, new Object[] { value });
					} catch (SecurityException e) {
						e.printStackTrace();
						continue;
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
						continue;
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}

				}
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			logger.info("PSTMT_SQL=" + pstmt.toString() + " 共耗時 "
					+ (System.currentTimeMillis() - starttime) + " ms");
			try {
				if (rs != null) {
					rs.close();
				}
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList<T> executeQuery(PreparedStatement pstmt, Class<T> theclass) {
		long starttime = System.currentTimeMillis();
		ResultSet rs = null;
		ArrayList<T> list = new ArrayList<T>(); // 準備用 ArrayList 來回傳查詢結果。
		try {
			rs = pstmt.executeQuery(); // 執行查詢
			ResultSetMetaData rsmd = rs.getMetaData(); // 取得資料表的 metadata
			int columnCount = rsmd.getColumnCount(); // 有幾個 column
			while (rs.next()) {
				T t = (T) theclass.newInstance(); // 實體化一個 T
				for (int i = 1; i <= columnCount; i++) {
					String settername = this.getSetMethodName(rsmd
							.getColumnName(i));
					Object value;
					if (rsmd.getColumnType(i) == Types.BLOB
							|| rsmd.getColumnType(i) == Types.LONGVARBINARY) {
						value = rs.getBytes(rsmd.getColumnName(i));
						// value = rs.getBlob(rsmd.getColumnName(i));
						// value = rs.getBinaryStream(rsmd.getColumnName(i));
					} else {
						value = rs.getObject(rsmd.getColumnName(i));
					}
					try {
						Method m = t.getClass().getMethod(settername,
								new Class[] { value.getClass() });
						m.invoke(t, new Object[] { value });
					} catch (InvocationTargetException e1) {
						e1.printStackTrace();
						continue;
					} catch (NoSuchMethodException e1) {
						e1.printStackTrace();
						continue;
					}
				}
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} finally {
			logger.info("PSTMT_SQL=" + pstmt.toString() + " 共耗時 "
					+ (System.currentTimeMillis() - starttime) + " ms");
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 僅回傳該 table 的第一個欄位，並且回傳 long, 通常第一個欄位都是一個 table 的 id.
	 * 
	 * @param sql
	 * @return
	 */
	public ArrayList<Long> executeQueryId(String sql) {
		long starttime = System.currentTimeMillis();
		ResultSet rs = null;
		PreparedStatement pstmt;
		ArrayList<Long> list = new ArrayList<Long>();
		try {
			pstmt = this.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery(); // 執行查詢
			ResultSetMetaData rsmd = rs.getMetaData(); // 取得資料表的 metadata
			while (rs.next()) {
				String idname = rsmd.getColumnName(1);
				Long id = rs.getLong(idname);
				list.add(id);
			}
			logger.info("PSTMT_SQL=" + pstmt.toString() + " 共耗時 "
					+ (System.currentTimeMillis() - starttime) + " ms");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public synchronized int executeUpdate(PreparedStatement pstmt)
			throws SQLException {
		long starttime = System.currentTimeMillis();

		int result = pstmt.executeUpdate();
		logger.info("PSTMT_SQL=" + pstmt.toString() + " 共耗時 "
				+ (System.currentTimeMillis() - starttime) + " ms");
		pstmt.close();
		return result;
	}

	/**
	 * 
	 * @param pstmt
	 * @return
	 * @throws SQLException
	 */
	public synchronized int executeInsert(PreparedStatement pstmt)
			throws SQLException {
		long starttime = System.currentTimeMillis();
		int id = -1;
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		rs.next();
		id = rs.getInt(1);
		logger.info("PSTMT_SQL=" + pstmt.toString() + " 共耗時 "
				+ (System.currentTimeMillis() - starttime) + " ms");
		rs.close();
		pstmt.close();
		return id;
	}

	/**
	 * 執行 SQL 指令
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public synchronized boolean executeDelete(PreparedStatement pstmt)
			throws SQLException {
		int result = pstmt.executeUpdate();
		logger.info(pstmt.toString());
		pstmt.close();
		return result == 0 ? false : true;
	}

	/**
	 * 只用來獲取總筆數
	 * 
	 * @param sql
	 * @return
	 */
	public int executeCount(String sql) {
		int result = 0;
		if (sql.matches("^SELECT.+FROM.*")) {
			sql = sql.replaceFirst("^SELECT.+FROM",
					"SELECT COUNT(*) AS COUNT FROM");
		} else {
			return -1;
		}
		try {
			PreparedStatement pstmt;
			pstmt = this.getConnection().prepareStatement(sql);
			if (sql.contains("ORDER")) {
				sql = sql.substring(0, sql.indexOf("ORDER") - 1);
			}
			if (!sql.toUpperCase().startsWith("SELECT COUNT(*) AS COUNT FROM")) {
				return -1;
			}
			System.out.println(pstmt.toString());
			ResultSet rs = pstmt.executeQuery(sql);
			rs.next();
			result = rs.getInt("COUNT");
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return result;
	}

}
