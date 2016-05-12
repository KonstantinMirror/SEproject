package by.gsu.epamlab.bean;

import java.util.Date;

public class DecimalResult extends AbstractResult {

    private static final int SCALE = 10;
    private static final int MAX_MARK = 10*SCALE;
    private static final int STEP = (int)(0.1*SCALE);


    public DecimalResult() {
    }

    public DecimalResult(String login, String test, Date date, int mark) {
        super(login, test, date, mark);
    }

    public DecimalResult(String login, String test, String date, String mark) {
        super(login, test, date, mark);
    }


    @Override
    public void setMark(int mark) {
        if (mark > MAX_MARK && mark < 0 && mark%SCALE != 0){
            throw new IllegalArgumentException();
        }
        super.setMark(mark);
    }

    @Override
    public void setMark(String mark) {
        setMark((int)(Double.parseDouble(mark)*SCALE));
    }



    @Override
    public String getStringMark() {
        return (getMark()/10) + "." + (getMark()%10);
    }
}
