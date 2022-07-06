package io.omnika.common.ipc.service;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@SuppressWarnings("UnstableApiUsage")
public class PartitionService {

    private final DiscoveryService discoveryService;

    private static final HashFunction HASH_FUNCTION = Hashing.murmur3_32();

    public int getPartitionIndex(ServiceType serviceType, Object partitionKey) {
        return hash(partitionKey) % discoveryService.getInstancesCount(serviceType);
    }

    public boolean isMyPartition(Object partitionKey) {
        int partitionIndex = getPartitionIndex(discoveryService.getServiceType(), partitionKey);
        return discoveryService.getInstanceIndex() == partitionIndex;
    }

    private int hash(Object value) {
        return HASH_FUNCTION.hashString(value.toString(), StandardCharsets.UTF_8).asInt();
    }

}
