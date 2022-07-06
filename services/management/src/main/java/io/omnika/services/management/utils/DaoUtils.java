package io.omnika.services.management.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.function.Consumer;
import java.util.function.Function;

public class DaoUtils {

    public static <T> void processInBatches(Function<PageRequest, Page<T>> finderFunction,
                                            Consumer<T> processor, int batchSize) {
        PageRequest batchRequest = PageRequest.of(0, batchSize);
        boolean hasNextBatch;
        do {
            Page<T> batch = finderFunction.apply(batchRequest);
            batch.get().forEach(processor);

            hasNextBatch = batch.hasNext();
            batchRequest = batchRequest.next();
        } while (hasNextBatch);
    }

}
