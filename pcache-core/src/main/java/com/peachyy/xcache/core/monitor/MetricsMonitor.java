package com.peachyy.xcache.core.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;

/**
 * @author Xs.Tao
 */
public class MetricsMonitor {
    private static AtomicLong COUNT_TOTAL = new AtomicLong();
    static {
        List<Tag> tags = new ArrayList<>();
        tags.add(new ImmutableTag("module", "totalCount"));
        tags.add(new ImmutableTag("name", "cacheHitTotalCount"));
        Metrics.gauge("cache_count", tags, COUNT_TOTAL);
    }

    public static AtomicLong getCountTotal() {
        return COUNT_TOTAL;
    }
}
