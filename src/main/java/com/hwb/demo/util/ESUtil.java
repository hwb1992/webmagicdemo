package com.hwb.demo.util;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by hwb
 * ESclient的单例
 * On 2017/2/4 10:18
 */
public class ESUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ESUtil.class);

    private static TransportClient createClient() {
        Settings settings = Settings.settingsBuilder().build();
        try {
            return TransportClient.builder().settings(settings).build().addTransportAddress(new
                    InetSocketTransportAddress(InetAddress.getByName("localhost"),
                    9300));
        } catch (UnknownHostException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    private static class Holder {

        private static final TransportClient INSTANCE = createClient();
    }

    public static TransportClient getClient() {
        return Holder.INSTANCE;
    }

    public static void close() {
        TransportClient client = getClient();
        client.close();
    }

}
