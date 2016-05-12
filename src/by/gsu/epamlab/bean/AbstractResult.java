package by.gsu.epamlab.bean;

import by.gsu.epamlab.resource.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

public  abstract class AbstractResult {
    private final static   String SEPARATOR = "-";

    private final static SimpleDateFormat OUTPUT_DATE_FORMAT =
            new SimpleDateFormat("dd.MM.yyyy");
    private final static SimpleDateFormat INPUT_DATE_PARSE =
            new SimpleDateFormat("yy"+SEPARATOR + "MM" + SEPARATOR + "dd");

    private String login;
    private String test;
    private Date date;
    private int mark;

    public AbstractResult() {
    }

    public AbstractResult(String login, String test, Date date, int mark) {
        this.login = login;
        this.test = test;
        this.date = date;
        this.mark = mark;
    }
    public AbstractResult(String login, String test, String date, String mark){
        this.login = login;
        this.test = test;
        setDate(date);
        setMark(mark);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(String date){
        try {
            this.date = INPUT_DATE_PARSE.parse(date);
        } catch (ParseException e) {
            throw  new IllegalArgumentException(Constants.ERROR_PARSE_DATE);
        }
    }

    public String getStringDate(){
        return OUTPUT_DATE_FORMAT.format(date);
    }

    public int getMark() {
        return mark;
    }

    public  void setMark(int mark){
        this.mark = mark;
    }

    public abstract void setMark(String mark);

    public abstract String getStringMark();


    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        return formatter.format("%s;%s;%s;%s",login,test, getStringDate(),getStringMark()).toString();
    }
}
