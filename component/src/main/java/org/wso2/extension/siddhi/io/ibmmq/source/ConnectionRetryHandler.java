/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import io.siddhi.core.exception.ConnectionUnavailableException;
import io.siddhi.core.stream.input.source.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class for handling retry connection operation
 */
public class ConnectionRetryHandler {
    private Source.ConnectionCallback connectionCallback;
    private static final Logger logger = LoggerFactory.getLogger(ConnectionRetryHandler.class);
    private AtomicBoolean isRetryPending = new AtomicBoolean(false);

    public ConnectionRetryHandler(Source.ConnectionCallback connectionCallback) {
        this.connectionCallback = connectionCallback;
    }

    /**
     * This method is used to start the retrying operation once a consumer is failed to connecto to the queue/topic.
     * @param t throwable object which is related to the failure at consumer level
     */
    public synchronized void onError(Throwable t) {
        if (!isRetryPending.get()) {
            isRetryPending.set(true);
            connectionCallback.onError(new ConnectionUnavailableException(t));
        }
    }
}
