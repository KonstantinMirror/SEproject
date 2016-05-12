package by.gsu.epamlab.db;

import by.gsu.epamlab.bean.AbstractResult;
import by.gsu.epamlab.interfaces.IResultDAO;
import by.gsu.epamlab.interfaces.IFacteriabel;
import by.gsu.epamlab.interfaces.IPrintable;
import by.gsu.epamlab.resource.Constants;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class DAO implements IResultDAO {

    private static final String SEARCH_LOGIN = "SELECT idLogin " +
            "FROM logins " +
            "WHERE logins.name = ? ";

    private static final String SEARCH_TEST = "SELECT idTest " +
            " FROM tests " +
            " WHERE tests.name = ?";

    private static final String INSERT_LOGIN  = "INSERT INTO logins( name ) " +
            " VALUES ( ? )";

    private static final String INSERT_TEST = "INSERT INTO tests (name) " +
            " VALUES ( ? )";

    private static final String INSERT_RESULT = "INSERT INTO results (loginId,testId,dat,mark)" +
            "VALUES (?,?,?,?)";

    private static final String GET_LAST_INC_ID = "SELECT LAST_INSERT_ID()AS id";

    private static final String GET_MEAN_VALUE_MARK = "SELECT logins.name, ROUND(AVG(results.mark),2) as avg_value " +
            " FROM results,logins " +
            " WHERE results.loginId = logins.idLogin " +
            " GROUP BY logins.name " +
            " ORDER BY avg_value DESC ";

    private static final String GET_CURRENT_MONTH = "select logins.name,tests.name, results.dat,results.mark " +
            " FROM results,logins,tests " +
            " WHERE results.loginId = logins.idLogin  AND " +
            " results.testId = tests.idTest AND " +
            " MONTH(results.dat) = MONTH(NOW()) AND " +
            " YEAR(results.dat) = YEAR(NOW()) " +
            " ORDER BY results.dat ";

    private static final int LOGIN_ID_INDEX = 1;
    private static final int TEST_ID_INDEX = 2;

    private static final int LOGIN_INDEX = 1;
    private static final int DATE_INDEX = 3;
    private static final int MARK_INDEX = 4;


    private static final int MEAN_MARK_INDEX = 2;

    private Connection connection;
    private PreparedStatement stLogin;
    private PreparedStatement stTest;
    private PreparedStatement stInsertLogin;
    private PreparedStatement stInsertTest;
    private PreparedStatement stGetId;
    private PreparedStatement stInsertResult;
    private PreparedStatement stGetMeanValue;
    private PreparedStatement stGetMarkMonth;

    public DAO() throws SQLException {
        connection  = DriverManager.getConnection(Constants.URL_DB);
        stLogin = connection.prepareStatement(SEARCH_LOGIN);
        stTest = connection.prepareStatement(SEARCH_TEST);
        stInsertLogin = connection.prepareStatement(INSERT_LOGIN);
        stInsertTest = connection.prepareStatement(INSERT_TEST);
        stGetId = connection.prepareStatement(GET_LAST_INC_ID);
        stInsertResult = connection.prepareStatement(INSERT_RESULT);
        stGetMeanValue = connection.prepareStatement(GET_MEAN_VALUE_MARK);
        stGetMarkMonth = connection.prepareStatement(GET_CURRENT_MONTH);
    }

    @Override
    public void put(AbstractResult test) throws SQLException {
        int idLogin;
        int idTest;
        connection.setAutoCommit(false);

        try{
            idLogin = checkOrInsert(stLogin, stInsertLogin,test.getLogin());
            idTest = checkOrInsert(stTest, stInsertTest,test.getTest());

            stInsertResult.setInt(LOGIN_ID_INDEX,idLogin);
            stInsertResult.setInt(TEST_ID_INDEX, idTest);
            Date sqlDate = new Date(test.getDate().getTime());
            stInsertResult.setDate(DATE_INDEX,sqlDate);
            stInsertResult.setInt(MARK_INDEX,test.getMark());
            stInsertResult.execute();
            connection.commit();
            connection.setAutoCommit(true);
        }catch (SQLException e){
            connection.rollback();
            throw e;
        }
    }

    private int checkOrInsert (PreparedStatement check,PreparedStatement insert, String data) throws SQLException {
        final int INDEX = 1;
        int id;
        check.setString(INDEX,data);
        ResultSet resultSet = check.executeQuery();
        if (resultSet.next())
            id = resultSet.getInt(1);
        else {
            insert.setString(INDEX, data);
            insert.execute();
            resultSet  = stGetId.executeQuery();
            resultSet.next();
            id = resultSet.getInt(INDEX);
        }
        DButility.closeResultSet(resultSet);
        return id;
    }

    @Override
    public void closeResource() throws SQLException {
        DButility.closeStatement(stLogin, stTest, stInsertLogin, stInsertTest,
                stGetId, stInsertTest, stGetId, stInsertResult, stGetMeanValue, stGetMarkMonth);
        DButility.closeConnection(connection);
    }

    @Override
    public void clearTable() throws SQLException {
        String CLEAR = "DELETE FROM results";
        Statement st = connection.createStatement();
        st.execute(CLEAR);
    }

    @Override
    public String getMeanMarkStudent(IPrintable print) throws SQLException {
        ResultSet resultSet = stGetMeanValue.executeQuery();
        StringBuilder meanMark = new StringBuilder();
        while (resultSet.next()){
            meanMark.append(resultSet.getString(LOGIN_INDEX)).append(" = ")
                    .append(print.printMark(resultSet.getDouble(MEAN_MARK_INDEX))).append('\n');
        }
        DButility.closeResultSet(resultSet);
        return meanMark.toString();
    }

    @Override
    public List<AbstractResult> getTestsCurrentMonth(IFacteriabel factory) throws SQLException {
        final int LOGIN_INDEX = 1;
        final int TEST_INDEX = 2;
        final int DATE_INDEX = 3;
        final int MARK_INDEX = 4;
        List<AbstractResult> testList = new LinkedList<>();
        ResultSet resultSet = stGetMarkMonth.executeQuery();
        while (resultSet.next()){
            String login = resultSet.getString(LOGIN_INDEX);
            String test = resultSet.getString(TEST_INDEX);
            java.util.Date date = resultSet.getTimestamp(DATE_INDEX);
            int mark = resultSet.getInt(MARK_INDEX);
            testList.add(factory.getClassFromFactory(login, test, date, mark));
        }
        return testList;
    }
}
