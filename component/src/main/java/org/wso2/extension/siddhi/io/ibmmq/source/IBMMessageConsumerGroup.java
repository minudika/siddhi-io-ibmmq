/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.extension.siddhi.io.ibmmq.source;

import com.ibm.mq.jms.MQQueueConnectionFactory;

import org.apache.log4j.Logger;
import org.wso2.siddhi.core.exception.ConnectionUnavailableException;
import org.wso2.siddhi.core.stream.input.source.Source;
import org.wso2.siddhi.core.stream.input.source.SourceEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import javax.jms.JMSException;


/**
 * This processes the IBM messages using a thread pool.
 */
public class IBMMessageConsumerGroup {
    private static final Logger logger = Logger.getLogger(IBMMessageConsumerGroup.class);
    private List<IBMMessageConsumerThread> ibmMessageConsumerThreads = new ArrayList<>();
    private ScheduledExecutorService executorService;
    private MQQueueConnectionFactory connectionFactory;
    private IBMMessageConsumerBean ibmMessageConsumerBean;
    private Source.ConnectionCallback connectionCallback;

    IBMMessageConsumerGroup(ScheduledExecutorService executorService, MQQueueConnectionFactory connectionFactory,
                            IBMMessageConsumerBean ibmMessageConsumerBean,
                            Source.ConnectionCallback connectionCallback) {
        this.executorService = executorService;
        this.connectionFactory = connectionFactory;
        this.ibmMessageConsumerBean = ibmMessageConsumerBean;
        this.connectionCallback = connectionCallback;
    }

    void pause() {
        ibmMessageConsumerThreads.forEach(IBMMessageConsumerThread::pause);
    }

    void resume() {
        ibmMessageConsumerThreads.forEach(IBMMessageConsumerThread::resume);
    }

    void shutdown() {
        ibmMessageConsumerThreads.forEach(IBMMessageConsumerThread::shutdownConsumer);
    }

    void run(SourceEventListener sourceEventListener) throws ConnectionUnavailableException {
        for (int i = 0; i < ibmMessageConsumerBean.getWorkerCount(); i++) {
            IBMMessageConsumerThread ibmMessageConsumer = null;
            try {
                ibmMessageConsumer = new IBMMessageConsumerThread(sourceEventListener,
                        ibmMessageConsumerBean, connectionFactory, connectionCallback);
            ibmMessageConsumerThreads.add(ibmMessageConsumer);
            logger.info("IBM MQ message consumer worker thread '" + i + "' starting to listen on queue '" +
                    ibmMessageConsumerBean.getQueueName() + "'");
            } catch (JMSException e) {
                throw new ConnectionUnavailableException(e.getMessage(), e);
            }
        }
        for (IBMMessageConsumerThread consumerThread : ibmMessageConsumerThreads) {
            executorService.submit(consumerThread);
        }
    }
}
