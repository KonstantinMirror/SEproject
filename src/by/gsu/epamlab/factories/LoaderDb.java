package by.gsu.epamlab.factories;

import by.gsu.epamlab.bean.AbstractResult;
import by.gsu.epamlab.db.DAO;
import by.gsu.epamlab.db.DButility;
import by.gsu.epamlab.interfaces.IResultDAO;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class LoaderDb implements Runnable {
    private IResultDAO dao;
    private final AbstractResult nullResult;
    private AtomicBoolean stateTransaction;
    private CountDownLatch latch;
    private BlockingQueue<AbstractResult> queue;

    public LoaderDb(BlockingQueue<AbstractResult> queue, AtomicBoolean stateTransaction,
                    AbstractResult nullResult,CountDownLatch latch ) {
        this.latch = latch;
        this.stateTransaction = stateTransaction;
        this.nullResult = nullResult;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            dao = new DAO();
            while (true) {
                System.out.println("put" + Thread.currentThread());
                AbstractResult result = queue.take();
                if (result != nullResult) {
                    dao.put(result);
                }else {
                    queue.put(nullResult);
                    break;
                }
            }
        }catch (SQLException e){
            DButility.printSQLException(e);
            stateTransaction.set(false);
        }
        catch (InterruptedException e) {
            System.out.println("Thread is aborted");
        }
        finally {
            try {
                if (dao!=null){
                    dao.closeResource();
                }
            }catch (SQLException e){
                DButility.printSQLException(e);
            }
            latch.countDown();
        }
    }
}
