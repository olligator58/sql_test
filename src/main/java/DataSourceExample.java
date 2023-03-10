import oracle.jdbc.pool.OracleDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;


public class DataSourceExample {

    // этот пример без Oracle работать не будет
    public static void main(String[] args) {
        try {
            OracleDataSource ods = new OracleDataSource();

            ods.setUser("root");
            ods.setPassword("secret");
            ods.setDriverType("thin");
            ods.setDatabaseName("test");
            ods.setServerName("localhost");
            ods.setPortNumber(1521);

            Connection connection = ods.getConnection();
            System.out.println("Connection successful !!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // пример работы с JNDI
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, "file:JNDI");
        try {
            Context ctx = new InitialContext(env);
            DataSource ods = (DataSource) ctx.lookup("myDatabase");
            Connection connection = ods.getConnection();
            System.out.println("Connection successful !!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
