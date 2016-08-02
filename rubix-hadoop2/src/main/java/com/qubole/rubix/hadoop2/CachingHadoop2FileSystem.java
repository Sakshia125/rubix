/**
 * Copyright (c) 2016. Qubole Inc
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. See accompanying LICENSE file.
 */
package com.qubole.rubix.hadoop2;
import com.qubole.rubix.core.CachingFileSystem;
import com.qubole.rubix.core.ClusterManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.s3native.NativeS3FileSystem;

import java.io.IOException;
import java.net.URI;

/**
 * Created by qubole on 28/7/16.
 */
public class CachingHadoop2FileSystem extends CachingFileSystem<NativeS3FileSystem>
{
    private static final Log LOG = LogFactory.getLog(CachingHadoop2FileSystem.class);

    private static ClusterManager clusterManager;

    public CachingHadoop2FileSystem()
    {
        super();
    }

    @Override
    public void initialize(URI uri, Configuration conf) throws IOException
    {
        LOG.info("Initializing CachingNativeS3FileSystem - Hadoop2");
        if (clusterManager == null) {
            initializeClusterManager(conf);
        }
        setClusterManager(clusterManager);
        super.initialize(uri, conf);
    }

    private synchronized void initializeClusterManager(Configuration conf)
    {
        if (clusterManager != null) {
            return;
        }
        clusterManager = new Hadoop2ClusterManager();
        clusterManager.initialize(conf);
    }
}
