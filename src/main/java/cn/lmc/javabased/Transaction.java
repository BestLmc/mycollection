package main.java.cn.lmc.javabased;

/**
 * Transaction
 *
 * @author limingcheng
 * @Date 2020/6/29
 */
public class Transaction {
//    publicfinalvoidcommit(TransactionStatusstatus)throwsTransactionException{
//        if(status.isCompleted()){
//            thrownewIllegalTransactionStateException("Transactionisalreadycompleted-donotcallcommitorrollbackmorethanoncepertransaction");
//        }
//
//        DefaultTransactionStatusdefStatus=(DefaultTransactionStatus)status;
//        if(defStatus.isLocalRollbackOnly()){
//            if(defStatus.isDebug()){
//                this.logger.debug("Transactionalcodehasrequestedrollback");
//            }
//            processRollback(defStatus);
//            return;
//        }
//        if((!(shouldCommitOnGlobalRollbackOnly()))&&(defStatus.isGlobalRollbackOnly())){
//            if(defStatus.isDebug()){
//                this.logger.debug("Globaltransactionismarkedasrollback-onlybuttransactionalcoderequestedcommit");
//            }
//            processRollback(defStatus);
//
//            if((status.isNewTransaction())||(isFailEarlyOnGlobalRollbackOnly())){
//                thrownewUnexpectedRollbackException("Transactionrolledbackbecauseithasbeenmarkedasrollback-only");
//            }
//
//            return;
//        }
//
//        processCommit(defStatus);
//    }
}
