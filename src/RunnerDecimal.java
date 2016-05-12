import by.gsu.epamlab.factories.DecimalResultFactory;
import by.gsu.epamlab.interfaces.IFacteriabel;
import by.gsu.epamlab.interfaces.IPrintable;
import by.gsu.epamlab.resource.Constants;
import java.util.Formatter;


public class RunnerDecimal {
    public static void main(String[] args)  {
        IFacteriabel factory = new DecimalResultFactory();
        IPrintable printerMark = mark ->{Formatter formatter = new Formatter();
            formatter.format("%2.2f", (mark / 10));
            return formatter.toString();};
        RunnerLogic.executeInit(factory, printerMark, Constants.FILE_XML);
    }
}
