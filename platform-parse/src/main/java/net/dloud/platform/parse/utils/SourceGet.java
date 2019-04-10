package net.dloud.platform.parse.utils;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import net.dloud.platform.extend.constant.PlatformConstants;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author QuDasheng
 * @create 2018-09-01 23:40
 **/
@Slf4j
public class SourceGet {
    private static final String comma = ",";
    private static final String colon = ":";
    private static final String redisPre = "redis://";

    public static ThreadLocal<String> kafkaProof = new ThreadLocal<>();
    public static ThreadLocal<String> clientFrom = new ThreadLocal<>();


    public static LettuceConnectionFactory getLettuceFactory(String url, String pwd) {
        url = url.replaceFirst(redisPre, "");
        if (url.contains(comma)) {
            final List<RedisNode> list = new ArrayList<>();
            final RedisClusterConfiguration conf = new RedisClusterConfiguration();
            for (String one : url.split(comma)) {
                final String[] split = one.split(colon);
                list.add(new RedisNode(split[0], Integer.parseInt(split[1])));
            }
            conf.setClusterNodes(list);
            conf.setPassword(RedisPassword.of(pwd));
            return new LettuceConnectionFactory(conf);
        } else {
            final String[] split = url.split(colon);
            RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
            conf.setHostName(split[0]);
            conf.setPort(Integer.parseInt(split[1]));
            conf.setPassword(RedisPassword.of(pwd));
            return new LettuceConnectionFactory(conf);
        }
    }

    public static HikariDataSource getHikariSource(String url, String user, String pwd, String drive) {
        final HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(drive);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pwd);
        dataSource.setAutoCommit(true);
        dataSource.setMaximumPoolSize(PlatformConstants.PROCESSOR_NUMBER * 2);
        dataSource.setMaxLifetime(3600000);
        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource.addDataSourceProperty("useServerPrepStmts", "true");
        dataSource.addDataSourceProperty("useLocalSessionState", "true");
        dataSource.addDataSourceProperty("rewriteBatchedStatements", "true");
        dataSource.addDataSourceProperty("cacheResultSetMetadata", "true");
        dataSource.addDataSourceProperty("cacheServerConfiguration", "true");
        dataSource.addDataSourceProperty("elideSetAutoCommits", "true");
        dataSource.addDataSourceProperty("maintainTimeStats", "false");
        return dataSource;
    }
}
