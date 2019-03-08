/*
 *  Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.extension.siddhi.io.ibmmq.util;

import com.ibm.mq.constants.MQConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * IBM MQ constants class
 */
public class IBMMQConstants {
    public static final String DESTINATION_NAME = "destination.name";
    public static final String HOST = "host";
    public static final String PORT = "port";
    public static final String CHANNEL = "channel";
    public static final String QUEUE_MANAGER_NAME = "queue.manager";
    public static final String USER_NAME = "username";
    public static final String PASSWORD = "password";
    public static final String WORKER_COUNT = "worker.count";
    public static final String PROPERTIES = "batch.properties";
    public static final String DEFAULT_CLIENT_RECONNECTION_TIMEOUT = "30";
    public static final String CLIENT_RECONNECT_TIMEOUT = "client.reconnecting.timeout";


    // Error codes
    public static final List<Integer> REASONS_FOR_CONNECTION_ISSUES =
            Collections.unmodifiableList(Arrays.asList(
                    MQConstants.MQRC_CONNECTION_BROKEN,
                    MQConstants.MQRC_Q_MGR_NOT_AVAILABLE,
                    MQConstants.MQRC_Q_MGR_QUIESCING,
                    MQConstants.MQRC_CONNECTION_QUIESCING,
                    MQConstants.MQRC_CONNECTION_STOPPING,
                    MQConstants.MQRC_Q_MGR_NOT_ACTIVE,
                    MQConstants.MQRC_CHANNEL_STOPPED_BY_USER,
                    MQConstants.MQRC_CHANNEL_NOT_AVAILABLE,
                    MQConstants.MQRC_HOST_NOT_AVAILABLE));

}
