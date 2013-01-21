package test.org.superdeduper.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author evnjones
 *
 */
@RunWith(JUnit4.class)
public class DerbySetupTest {
	private final static String CLASS_NAME = DerbySetupTest.class.getName();
	private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);
	private final static String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	/**
	 * 
	 */
	@Test
	public void derbySetup() {
		final String methodName = "derbySetup()";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		LOGGER.entering(CLASS_NAME, methodName);

		try {
			// create the database and connect
			if(LOGGER.isLoggable(Level.INFO)) {
				LOGGER.logp(Level.INFO, CLASS_NAME, methodName, "Connecting and creating the database");
				LOGGER.logp(Level.INFO, CLASS_NAME, methodName, "user.home: " + System.getProperty("user.home"));
			}
			Class.forName(DB_DRIVER).newInstance();
			conn = DriverManager.getConnection("jdbc:derby:" + System.getProperty("user.home") + "/DerbySetupTestDB;create=true", new Properties());
			
			// create a test table
			if(LOGGER.isLoggable(Level.INFO)) {
				LOGGER.logp(Level.INFO, CLASS_NAME, methodName, "Creating test table");
			}
			ps = conn.prepareStatement("create table test (id bigint not null, path varchar(254), hash varchar(254) not null)");
			Assert.assertEquals("Create table", 0, ps.executeUpdate());
			cleanupPreparedStatement(ps);
			
			// add some data to the table
			if(LOGGER.isLoggable(Level.INFO)) {
				LOGGER.logp(Level.INFO, CLASS_NAME, methodName, "Creating test data");
			}
			ps = conn.prepareStatement("insert into test values (?, ?, ?)");
			
			ps.setInt(1, 1);
			ps.setString(2, "path1");
			ps.setString(3, "1095823FFAB234");
			ps.addBatch();
			
			ps.setInt(1, 2);
			ps.setString(2, "path2");
			ps.setString(3, "2095823FFAB234");
			ps.addBatch();
			
			ps.setInt(1, 3);
			ps.setString(2, "path3");
			ps.setString(3, "3095823FFAB234");
			ps.addBatch();
			
			Assert.assertArrayEquals("Insert data", new int[]{1, 1, 1}, ps.executeBatch());
			cleanupPreparedStatement(ps);
			
			// select data from the table
			if(LOGGER.isLoggable(Level.INFO)) {
				LOGGER.logp(Level.INFO, CLASS_NAME, methodName, "Querying the test table");
			}
			int rowCount = 0;
			ps = conn.prepareStatement("select id, path, hash from test order by id asc");
			rs = ps.executeQuery();
			while(rs.next()) {
				rowCount++;
				
				Assert.assertEquals("Id (row #" + rowCount + ")", rowCount, rs.getInt(1));
				Assert.assertEquals("Path (row #" + rowCount + ")", "path" + rowCount, rs.getString(2));
				Assert.assertEquals("Hash (row #" + rowCount + ")", rowCount + "095823FFAB234", rs.getString(3));
			}
			cleanupResultSet(rs);
			cleanupPreparedStatement(ps);
			
			// drop the table
			if(LOGGER.isLoggable(Level.INFO)) {
				LOGGER.logp(Level.INFO, CLASS_NAME, methodName, "Dropping the table");
			}
			ps = conn.prepareStatement("drop table test");
			Assert.assertEquals("Drop table", 0, ps.executeUpdate());
		} catch(Exception e) {
			if(LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Exception encountered", e);
			}
		} finally {
			cleanupResultSet(rs);
			cleanupPreparedStatement(ps);
			cleanupConnection(conn);
			shutdownDB();
		}

		LOGGER.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * 
	 * @param rs
	 */
	private void cleanupResultSet(ResultSet rs) {
		final String methodName = "cleanupResultSet()";

		LOGGER.entering(CLASS_NAME, methodName);

		if(rs != null) {
			try {
				rs.close();
			} catch(SQLException sqle) {
				if(LOGGER.isLoggable(Level.SEVERE)) {
					LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "SQLException occurred", sqle);
				}
			}
		}

		LOGGER.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * 
	 * @param ps
	 */
	private void cleanupPreparedStatement(PreparedStatement ps) {
		final String methodName = "cleanupPreparedStatement()";

		LOGGER.entering(CLASS_NAME, methodName);

		if(ps != null) {
			try {
				ps.close();
			} catch(SQLException sqle) {
				if(LOGGER.isLoggable(Level.SEVERE)) {
					LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "Exception occurred", sqle);
				}
			}
		}

		LOGGER.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * 
	 * @param conn
	 */
	private void cleanupConnection(Connection conn) {
		final String methodName = "cleanupConnection()";

		LOGGER.entering(CLASS_NAME, methodName);

		try {
			if(conn != null) {
				conn.close();
			}
		} catch(SQLException sqle) {
			if(LOGGER.isLoggable(Level.SEVERE)) {
				LOGGER.logp(Level.SEVERE, CLASS_NAME, methodName, "SQLException encountered", sqle);
			}
		}

		LOGGER.exiting(CLASS_NAME, methodName);
	}
	
	/**
	 * 
	 */
	private void shutdownDB() {
		final String methodName = "shutdownDB()";

		LOGGER.entering(CLASS_NAME, methodName);

		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch(SQLException sqle) {
			Assert.assertEquals("Shutdown SQL error code", 50000, sqle.getErrorCode());
			Assert.assertEquals("Shutdown SQL state", "XJ015", sqle.getSQLState());
		}

		LOGGER.exiting(CLASS_NAME, methodName);
	}
}
