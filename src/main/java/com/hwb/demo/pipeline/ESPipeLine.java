package com.hwb.demo.pipeline;

import com.google.common.collect.Maps;
import com.hwb.demo.util.ESUtil;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * Created by hwb
 * 写入数据到ES
 * On 2017/2/4 9:41
 */
public class ESPipeLine implements Pipeline {

    private static final String INDEX = "douban";
    private static final String TYPE = "film";
    private static final Logger LOG = LoggerFactory.getLogger(ESPipeLine.class);

    public void process(ResultItems resultItems, Task task) {
        if (!resultItems.getAll().isEmpty()) {
            String image = (String) resultItems.getAll().get("image").toString();
            String name = (String) resultItems.getAll().get("name").toString();
            TransportClient transportClient = ESUtil.getClient();
            Map map = Maps.newHashMap();
            map.put("image", image);
            map.put("name", name);
            IndexResponse indexResponse = transportClient.prepareIndex(INDEX, TYPE).setSource(map)
                    .get();
            LOG.info("IndexResponse {}", indexResponse);
        }
    }

    /**
     * 关闭资源
     */
    public void close() {
        ESUtil.close();
    }

}
