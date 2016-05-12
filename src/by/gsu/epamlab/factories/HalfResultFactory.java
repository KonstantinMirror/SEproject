package by.gsu.epamlab.factories;
import by.gsu.epamlab.bean.HalfResult;
import by.gsu.epamlab.bean.AbstractResult;
import java.util.Date;



public class HalfResultFactory extends AbstractCSVFactory {

    @Override
    public AbstractResult getClassFromFactory(String login, String test, String date, String mark){
        return new HalfResult(login,test,date,mark);
    }

    @Override
    public AbstractResult getClassFromFactory(String login, String test, Date date, int mark){
        return new HalfResult(login,test,date,mark);
    }
}
