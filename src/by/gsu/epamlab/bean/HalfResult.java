package by.gsu.epamlab.bean;

import java.util.Date;
import java.util.Formatter;

public class HalfResult extends AbstractResult {
    private static final int SCALE = 10;
    private static final int MAX_MARK = 20*SCALE;
    private static final int STEP = (int)(0.5*SCALE);

    public HalfResult() {
    }

    public HalfResult(String login, String test, Date date, int mark) {
        super(login, test, date, mark);
    }

    public HalfResult(String login, String test, String date, String mark) {
        super(login, test, date, mark);
    }

    @Override
    public void setMark(int mark) {
        if (mark > MAX_MARK && mark < 0 && (mark % STEP !=0) ){
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
        Formatter formatter  =new Formatter();
        return formatter.format("%2.0s",getMark()).toString();
    }
}
