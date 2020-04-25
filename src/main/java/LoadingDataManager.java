import service.DatabaseManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author i.isaev on 22.01.2020
 * @project unloading
 */
public class LoadingDataManager {


    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleWithFixedDelay(DatabaseManager.getInstance(), 0, 5, TimeUnit.SECONDS);

    }



}
