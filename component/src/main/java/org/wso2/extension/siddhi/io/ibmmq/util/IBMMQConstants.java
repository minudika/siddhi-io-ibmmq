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
    public static final String MAX_RETRIES = "max.retries";
    public static final String DEFAULT_MAX_RETRIES = "5";
    public static final String RETRY_INTERVAL = "retry.interval";
    public static final String DEFAULT_RETRY_INTERVAL = "2";
}
