package net.dloud.platform.gateway.pack;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import net.dloud.platform.common.domain.BaseResult;
import net.dloud.platform.common.gateway.bean.InvokeKey;
import net.dloud.platform.gateway.bean.InvokeDetailCache;
import net.dloud.platform.parse.kafka.KafkaConsumer;
import net.dloud.platform.parse.kafka.annotation.Consumer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author QuDasheng
 * @create 2018-12-27 14:19
 **/
@Slf4j
@Consumer(value = "gatewayCacheClean", describe = "清理方法缓存")
public class GatewayCacheClean implements KafkaConsumer<List<InvokeKey>> {
    @Autowired
    private GatewayCache gatewayCache;


    @Override
    public BaseResult onMessage(List<InvokeKey> invokeKeys) {
        log.info("[GATEWAY] 要删除网关中的缓存是: {}", invokeKeys);
        final Cache<InvokeKey, InvokeDetailCache> genericCache = gatewayCache.getGenericCache();
        for (InvokeKey invokeKey : invokeKeys) {
            final InvokeDetailCache detailCache = genericCache.getIfPresent(invokeKey);
            if (null != detailCache) {
                gatewayCache.referenceClean(invokeKey.getGroup(), detailCache.getClassName());
                genericCache.invalidate(invokeKey);
            }
        }
        return new BaseResult();
    }
}
