package tw.jiangsir.Utils.DAOs;

import java.sql.SQLException;
import java.util.TreeMap;

import tw.jiangsir.Utils.Config.AppConfig;
import tw.jiangsir.Utils.Exceptions.DataException;

public class AppConfigService {

	public int insert(AppConfig appConfig) throws DataException {
		try {
			return new AppConfigDAO().insert(appConfig);
		} catch (SQLException e) {
			throw new DataException(e);
		}
	}

	public void update(AppConfig appConfig) throws DataException {
		try {
			new AppConfigDAO().update(appConfig);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public void delete(long id) throws DataException {
		try {
			new AppConfigDAO().delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}
	}

	public AppConfig getAppConfig() {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		for (AppConfig appConfig : new AppConfigDAO().getAppConfigByFields(
				fields, "id DESC", 0)) {
			return appConfig;
		}
		return new AppConfig();
	}

}