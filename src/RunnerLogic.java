import by.gsu.epamlab.bean.AbstractResult;
import by.gsu.epamlab.db.DAO;
import by.gsu.epamlab.db.DButility;
import by.gsu.epamlab.db.PoolConnection;
import by.gsu.epamlab.interfaces.IFacteriabel;
import by.gsu.epamlab.interfaces.IPrintable;
import by.gsu.epamlab.interfaces.IResultDAO;
import by.gsu.epamlab.resource.Constants;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;


public class RunnerLogic {
    public static void executeInit(IFacteriabel factory,IPrintable printerMark, String fileName) {
        IResultDAO dao = null;
        try {
            System.out.println("Loading underlying JDBC driver.");
            PoolConnection.setupDataSource();
            System.out.println("Done.");
            dao = new DAO();
            dao.clearTable();
            factory.loadClassFromFactory(fileName);
            System.out.println(Constants.SPACE_LINE);
            System.out.println(dao.getMeanMarkStudent(printerMark));
            System.out.println(Constants.SPACE_LINE);
            System.out.println(getCurrentMonth(factory));

        }catch (SQLException ex){
            DButility.printSQLException(ex);
        }
        catch (Exception ex){
            ex.getLocalizedMessage();
            ex.printStackTrace();
        }
        finally {
            try {
                if (dao!=null) {
                    dao.closeResource();
                }
                try {
                    PoolConnection.shutdownDriver();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }catch (SQLException ex){
                DButility.printSQLException(ex);
            }
        }
    }

    public static String getCurrentMonth(IFacteriabel factory) throws SQLException {
        final String NOT_DATA = "No data";
        IResultDAO dao = new DAO();
        List<AbstractResult> tests = dao.getTestsCurrentMonth(factory);
        StringBuilder resultMonth = new StringBuilder();
        if (!tests.isEmpty()) {
            Date date = tests.get(tests.size() - 1).getDate();
            for (ListIterator<AbstractResult> iterator = tests.listIterator(tests.size()); iterator.hasPrevious(); ) {
                AbstractResult test = iterator.previous();
                if (test.getDate().compareTo(date) == 0) {
                    resultMonth.append(test).append('\n');
                    continue;
                }
                break;
            }
        }else {
            resultMonth.append(NOT_DATA);
        }
        return resultMonth.toString();
    }
}
