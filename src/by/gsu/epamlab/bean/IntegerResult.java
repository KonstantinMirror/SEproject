package by.gsu.epamlab.bean;

import java.util.Date;

public class IntegerResult extends AbstractResult {

    private static final int MAX_MARK = 1;

    public IntegerResult() {
    }

    public IntegerResult(String login, String test, Date date, int mark) {
        super(login, test, date, mark);
    }

    public IntegerResult(String login, String test, String date, String mark) {
        super(login, test, date, mark);
    }

    @Override
    public void setMark(int mark) {
        if (mark > MAX_MARK && mark < 0 ){
            throw new  IllegalArgumentException();
        }
        super.setMark(mark);
    }

    @Override
    public void setMark(String mark) {
        setMark(Integer.parseInt(mark));
    }

    @Override
    public String getStringMark(){
        return "" + getMark();
    }
}
