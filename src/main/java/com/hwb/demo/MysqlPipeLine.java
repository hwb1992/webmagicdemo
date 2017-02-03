package com.hwb.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by hwb
 * 批量写入Mysql
 * On 2017/2/3 11:20
 */
public class MysqlPipeLine implements Pipeline {

    private int times = 0;
    private Connection connection;
    private PreparedStatement preparedStatement;

    private static final String INSERT_FILM_DESC = "insert into film_desc(image_src,file_name) values(?,?)";
    private static final Integer BATCH_SIZE = 100;
    private static final Logger LOG = LoggerFactory.getLogger(MysqlPipeLine.class);

    /**
     * 初始化
     *
     * @throws SQLException
     */
    public void init() throws SQLException {
        connection = MysqlJdbcUtil.getConnection();
        preparedStatement = connection.prepareStatement(INSERT_FILM_DESC);
    }

    /**
     * 处理数据
     *
     * @param resultItems
     * @param task
     */
    public void process(ResultItems resultItems, Task task) {
        if (!resultItems.getAll().isEmpty()) {
            try {
                String image = (String) resultItems.getAll().get("image").toString();
                String name = (String) resultItems.getAll().get("name").toString();
                preparedStatement.setString(1, image);
                preparedStatement.setString(2, name);
                preparedStatement.addBatch();
                times++;
                if (times % BATCH_SIZE == 0) {
                    preparedStatement.executeBatch();
                }
            } catch (SQLException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        try {
            if (preparedStatement != null) {
                preparedStatement.executeBatch();
            }
            MysqlJdbcUtil.release(preparedStatement);
            MysqlJdbcUtil.close(connection);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

}
