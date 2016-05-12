package by.gsu.epamlab.db;

import java.sql.*;

public final class DButility {

    public static void closeResultSet(ResultSet... resultSets){
        for (ResultSet rs :resultSets) {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
    }

    public static void closeStatement(Statement... statement){
        for (Statement st : statement){
            if (st!=null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    printSQLException(ex);
                }
            }
        }
    }

    public static void closeConnection(Connection connection){
          if (connection!=null){
            try {
                connection.close();
            }catch (SQLException ex){
                printSQLException(ex);
            }
          }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " +
                        ((SQLException)e).getSQLState());
                System.err.println("Error Code: " +
                        ((SQLException)e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while(t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
