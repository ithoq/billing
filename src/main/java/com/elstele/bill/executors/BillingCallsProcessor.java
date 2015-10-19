package com.elstele.bill.executors;

import com.elstele.bill.datasrv.CallDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Scope("singleton")
public class BillingCallsProcessor {

    @Autowired
    private CallDataService callDataService;
    @Autowired
    private WorkerFactory workerFactory;

    private final Integer poolCapacity = 20;
    private final Integer pageSize = 50;
    private Integer processedCallsCounter;

    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolCapacity);

    public void processCalls(){
        processedCallsCounter = 0;
        Integer callsCount =  callDataService.getUnbilledCallsCount();
        Integer pagesCount = callsCount/pageSize;
        System.out.println("-------------Initial call count:" + callsCount);


        for (int i=0; i<pagesCount+1; i++){
            System.out.println("-------------Active Count in Executor:" + executor.getActiveCount());
            waitForExecutorReady(executor);

            Integer tempCallsCount =  callDataService.getUnbilledCallsCount();
            List<Integer> curCallIds = callDataService.getUnbilledCallsIdList(pageSize, 0);
            System.out.println("-------------Call count at :" + i + " -- " + tempCallsCount + "(" + curCallIds.size() + ")");

            putCallsToExecutor(executor, curCallIds);

        }

        System.out.println("Processed calls:" + processedCallsCounter);
    }

    private void putCallsToExecutor(ThreadPoolExecutor executor, List<Integer> curCallIds) {
        for (Integer callId : curCallIds){
            BillingCallWorker worker = (BillingCallWorker)workerFactory.getWorker("billingCallWorker");
            worker.setCallId(callId);
            executor.execute(worker);
            processedCallsCounter = processedCallsCounter+1;
        }
    }

    private void waitForExecutorReady(ThreadPoolExecutor executor) {
        while(executor.getActiveCount() > 0){
            try {
                System.out.println("-----wait-----");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
