package by.gsu.epamlab.factories;

import by.gsu.epamlab.bean.AbstractResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import static by.gsu.epamlab.resource.Constants.*;



public abstract class AbstractCSVFactory extends AbstractFactory {

    @Override
    protected  void loadToQueue(BlockingQueue<AbstractResult> queueResult ,String nameFile){
        Scanner sc = null;
        try {
            sc = new Scanner(new File(nameFile));
            while (sc.hasNext()){
                String[] values = sc.next().split(DELIMETER);
                queueResult.put(getClassFromFactory(values[STUDENT_INDEX], values[TEST_INDEX],
                        values[DATE_INDEX], values[MARK_INDEX]));
            }
        }
        catch (FileNotFoundException | InterruptedException e){
            throw new  IllegalStateException(e);
        }
        finally {
            if (sc != null){
                sc.close();
            }
        }
    }
}
