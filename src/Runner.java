
import by.gsu.epamlab.factories.IntegerResultFactory;
import by.gsu.epamlab.interfaces.IFacteriabel;
import by.gsu.epamlab.interfaces.IPrintable;
import by.gsu.epamlab.resource.Constants;

public class Runner {
    public static void main(String[] args)  {
        IFacteriabel factory = new IntegerResultFactory();
        IPrintable printerMark = mark -> Double.toString(mark);
        RunnerLogic.executeInit(factory, printerMark, Constants.FILE_CSV);
    }
}



