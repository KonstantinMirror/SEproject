package by.gsu.epamlab.interfaces;

import by.gsu.epamlab.bean.AbstractResult;

import java.sql.SQLException;
import java.util.Date;


public interface IFacteriabel {
    void loadClassFromFactory(String fileName) throws SQLException;
    AbstractResult getClassFromFactory(String login, String test, Date date, int mark);
    AbstractResult getClassFromFactory(String login, String test, String date, String mark);
}
