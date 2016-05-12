package by.gsu.epamlab.factories;

import by.gsu.epamlab.bean.IntegerResult;
import by.gsu.epamlab.bean.AbstractResult;

import java.util.Date;

public class IntegerResultFactory extends AbstractCSVFactory {

    @Override
    public AbstractResult getClassFromFactory(String login, String test, String date, String mark){
        return new IntegerResult(login,test,date,mark);
    }

    @Override
    public AbstractResult getClassFromFactory(String login, String test, Date date, int mark){
        return new IntegerResult(login,test,date,mark);
    }


}
