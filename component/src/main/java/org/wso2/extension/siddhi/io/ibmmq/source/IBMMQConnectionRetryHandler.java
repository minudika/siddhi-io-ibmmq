/*
 *  Copyright (c) 2019 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  WSO2 Inc. licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.wso2.extension.siddhi.io.ibmmq.source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.siddhi.core.util.transport.BackoffRetryCounter;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class tries to connect to MQ provider until the maximum re-try count meets.
 */
class IBMMQConnectionRetryHandler {

    private AtomicBoolean isTryingToConnect = new AtomicBoolean(false);
    private BackoffRetryCounter backoffRetryCounter = new BackoffRetryCounter();

    public void setIsConnected(AtomicBoolean isConnected) {
        this.isConnected = isConnected;
    }

    private AtomicBoolean isConnected = new AtomicBoolean(false);

    /**
     * This {@link IBMMessageConsumerThread} instance represents the MQ receiver that asked for retry.
     */
    private IBMMessageConsumerThread messageConsumer;

    private static final Logger logger = LoggerFactory.getLogger(IBMMQConnectionRetryHandler.class);

    /**
     * Current retry interval in milliseconds.
     */
    private long currentRetryInterval;

    /**
     * Initial retry interval in milliseconds.
     */
    private long retryInterval;

    /**
     * Current retry count.
     */
    private int retryCount = 0;

    /**
     * States whether a isOnRetrying is in progress.
     */
    private volatile boolean isOnRetrying = false;

    private ScheduledExecutorService executorService;

    /**
     * Creates a IBMMQ connection retry handler.
     *
     * @param messageConsumer IBMMQ message consumer that needs to retry.
     * @param retryInterval   Retry interval between.
     */
    IBMMQConnectionRetryHandler(IBMMessageConsumerThread messageConsumer, long retryInterval,
                                ScheduledExecutorService executorService) {
        this.messageConsumer = messageConsumer;
        this.retryInterval = retryInterval;
        this.currentRetryInterval = retryInterval;
        this.executorService = executorService;
    }

    /**
     * To retry the isOnRetrying to connect to MQ provider.
     *
     * @return True if isOnRetrying was successful.
     * @throws JMSException MQ Connector Exception.
     */
    boolean retry() {
        if (isOnRetrying) {
            if (logger.isDebugEnabled()) {
                logger.debug("Retrying is in progress from a different thread, hence not retrying.");
            }
            return false;
        } else {
            isOnRetrying = true;
        }

        while (true) {
            retryCount++;
            messageConsumer.shutdownConsumer();
//                messageConsumer.connect();
            logger.info("Connected to the message broker to " + messageConsumer.getQueueName()
                    + "after isOnRetrying for " + retryCount + " time(s)");
            retryCount = 0;
            currentRetryInterval = retryInterval;
            isOnRetrying = false;
            return true;
        }
    }

    public int getRetryCount() {
        return retryCount;
    }
}
