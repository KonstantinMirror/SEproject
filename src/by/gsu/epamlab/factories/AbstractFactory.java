package by.gsu.epamlab.factories;

import by.gsu.epamlab.bean.AbstractResult;
import by.gsu.epamlab.bean.IntegerResult;
import by.gsu.epamlab.interfaces.IFacteriabel;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;


public  abstract class AbstractFactory implements IFacteriabel{
    private static final int COUNT_THREAD = 10;
    private static final String ILLEGAL_TRANSACTION = "FAILURE TRANSACTION";

    public void loadClassFromFactory(String nameFile) throws SQLException {
        CountDownLatch latch = new CountDownLatch(COUNT_THREAD);
        AbstractResult nullResult = new IntegerResult();
        BlockingQueue<AbstractResult> queueResult = new LinkedBlockingQueue<>();
        AtomicBoolean stateTransaction = new AtomicBoolean(true);
        long start, end;
        start = System.currentTimeMillis();
        ExecutorService pool = Executors.newFixedThreadPool(COUNT_THREAD);
        for (int i = 0; i < COUNT_THREAD ; i++){
            pool.submit(new LoaderDb(queueResult,stateTransaction,nullResult,latch));
        }
        Scanner sc = null;
        try {
            loadToQueue(queueResult,nameFile);
            queueResult.put(nullResult);
            System.out.println("Read file is complete");
            latch.await();
            pool.shutdown();
            end =System.currentTimeMillis();
            System.out.println("Full time is " + (end-start));
            if (!stateTransaction.get()){
                throw new  IllegalStateException(ILLEGAL_TRANSACTION);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        finally {
            if (sc != null){
                sc.close();
            }
        }
    }

    protected abstract void loadToQueue(BlockingQueue<AbstractResult> queueResult ,String nameFile);
}
