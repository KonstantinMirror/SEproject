package by.gsu.epamlab.interfaces;

import by.gsu.epamlab.bean.AbstractResult;

import java.sql.SQLException;
import java.util.List;

public interface IResultDAO {
    void put(AbstractResult test) throws SQLException;
    void clearTable() throws SQLException;
    String getMeanMarkStudent(IPrintable print) throws SQLException;
    List<AbstractResult> getTestsCurrentMonth(IFacteriabel factory) throws SQLException;
    void closeResource() throws SQLException;
}
