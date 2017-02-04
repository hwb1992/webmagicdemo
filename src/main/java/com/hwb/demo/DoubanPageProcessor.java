package com.hwb.demo;

import com.hwb.demo.pipeline.ESPipeLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by hwb
 * On 2017/2/3 10:24
 */
public class DoubanPageProcessor implements PageProcessor {

    private Site site = Site.me().setTimeOut(10000).setRetryTimes(3).setSleepTime(SLEEP_TIME)
            .addCookie("bid", "WESGEdFAEe2")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/56.0.2924.76 Safari/537.36");

    public static final String FILM_LIST = "https://movie\\.douban\\.com/top\\d+\\?start=\\d+&filter=";
    public static final String FILM_CONTENT = "https://movie\\.douban\\.com/subject/\\d+/";

    private static final int SLEEP_TIME = 1000; // 1毫秒
    private static final Logger LOG = LoggerFactory.getLogger(DoubanPageProcessor.class);

    public void process(Page page) {
        // 文章页
        if (page.getUrl().regex(FILM_LIST).match()) {
            page.addTargetRequests(page.getHtml().xpath("//*[@class=\"item\"]/div/a")
                    .links().regex(FILM_CONTENT).all());
            page.addTargetRequests(page.getHtml().xpath("//*[@id=\"content\"]/div/div[1]/div[2]").links().regex
                    (FILM_LIST).all());
        } else { // 列表页
            page.putField("image", page.getHtml().xpath("//*[@id=\"mainpic\"]/a/img/@src"));
            page.putField("name", page.getHtml().xpath("//*[@id=\"content\"]/h1/span[1]/text()"));

        }
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        ESPipeLine esPipeLine = new ESPipeLine();
            Spider.create(new DoubanPageProcessor())
                    .addPipeline(esPipeLine).addUrl("https://movie.douban.com/top250?start=0&filter=")
                    .thread(5)
                    .run();
        esPipeLine.close();
    }
//    public static void main(String[] args) {
//        MysqlPipeLine mysqlPipeLine = new MysqlPipeLine();
//        try {
//            mysqlPipeLine.init();
//            Spider.create(new DoubanPageProcessor())
//                    .addPipeline(mysqlPipeLine).addUrl("https://movie.douban.com/top250?start=0&filter=")
//                    .thread(5)
//                    .run();
//            mysqlPipeLine.close();
//        } catch (SQLException e) {
//            LOG.error(e.getMessage(), e);
//        }
//    }

}
