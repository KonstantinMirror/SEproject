package by.gsu.epamlab.factories;

import by.gsu.epamlab.bean.DecimalResult;
import by.gsu.epamlab.bean.AbstractResult;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class DecimalResultFactory extends AbstractFactory {

    @Override
    protected void loadToQueue(BlockingQueue<AbstractResult> queueResult, String nameFile) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser saxParser = spf.newSAXParser();
            TestSaxHandler handler = new TestSaxHandler(queueResult);
            saxParser.parse(new File(nameFile), handler);
        }catch (IOException | SAXException | ParserConfigurationException ex){
            ex.getLocalizedMessage();
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public AbstractResult getClassFromFactory(String login, String test, Date date, int mark) {
        return new DecimalResult(login,test,date,mark);
    }

    @Override
    public AbstractResult getClassFromFactory(String login, String test, String date, String mark) {
        return new DecimalResult(login,test,date,mark);
    }
}

class TestSaxHandler extends DefaultHandler {

    private enum ResultEnum {RESULTS, STUDENT, LOGIN, TESTS, TEST}

    private final static int NAME_INDEX = 0;
    private final static int DATE_INDEX = 1;
    private final static int MARK_INDEX = 2;

    private BlockingQueue<AbstractResult> queueResult;

    private String login;
    private ResultEnum currentEnum;

    public TestSaxHandler(BlockingQueue<AbstractResult> queueResult) {
        this.queueResult = queueResult;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        currentEnum = ResultEnum.valueOf(qName.toUpperCase());
        if (currentEnum == ResultEnum.TEST) {
            try {
                queueResult.put(new DecimalResult(login, attributes.getValue(NAME_INDEX), attributes.getValue(DATE_INDEX),
                        attributes.getValue(MARK_INDEX)));
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (currentEnum == ResultEnum.LOGIN) {
            String string = new String(ch, start, length).trim();
            if (!string.isEmpty()) {
                login = string;
            }
        }
    }
}
