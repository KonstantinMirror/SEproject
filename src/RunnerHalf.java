import by.gsu.epamlab.factories.HalfResultFactory;
import by.gsu.epamlab.interfaces.IFacteriabel;
import by.gsu.epamlab.interfaces.IPrintable;
import by.gsu.epamlab.resource.Constants;
import java.util.Formatter;

public class RunnerHalf {
    public static void main(String[] args)  {
        IFacteriabel factory = new HalfResultFactory();
        IPrintable printerMark = mark ->{Formatter formatter = new Formatter();
                            formatter.format("%2.2f", (mark / 10));
                            return formatter.toString();};
        RunnerLogic.executeInit(factory, printerMark, Constants.FILE_CSV_V2);
    }
}
