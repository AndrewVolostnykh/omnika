package io.omnika.common.ipc.service;

import io.omnika.common.ipc.events.InstanceCountChangedEvent;
import io.omnika.common.model.channel.ServiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

    private final DiscoveryClient discoveryClient;
    private final ServiceInstanceRegistration currentInstanceInfo;
    private final ApplicationEventPublisher eventPublisher;
    private final ScheduledExecutorService scheduler;

    private ServiceType serviceType;
    @Value("${spring.application.name}")
    private String serviceId;

    private final Map<ServiceType, List<ServiceInstance>> services = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        this.serviceType = ServiceType.getByName(serviceId);
        scheduler.scheduleAtFixedRate(() -> {
            updateServiceList();
        }, 0, 1, TimeUnit.SECONDS);
    }

    public int getInstancesCount(ServiceType serviceType) {
        return getInstances(serviceType).size();
    }

    public int getInstanceIndex() {
        return getInstances(getServiceType()).stream()
                .map(ServiceInstance::getInstanceId)
                .sorted().collect(Collectors.toList())
                .indexOf(getCurrentInstanceId());
    }

    private String getCurrentInstanceId() {
        return currentInstanceInfo.getServiceInstance().getId();
    }

    private String getServiceId(ServiceType serviceType) {
        return serviceType.getName();
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    private List<ServiceInstance> getInstances(ServiceType serviceType) {
        return Optional.ofNullable(services.get(serviceType)).orElse(Collections.emptyList());
    }

    private void updateServiceList() {
        for (ServiceType serviceType : ServiceType.values()) {
            List<ServiceInstance> instances = getInstances(serviceType);
            List<ServiceInstance> currentInstances = discoveryClient.getInstances(getServiceId(serviceType));

            Set<String> instancesIds = instances.stream().map(ServiceInstance::getInstanceId).collect(Collectors.toSet());
            Set<String> currentInstancesIds = currentInstances.stream().map(ServiceInstance::getInstanceId).collect(Collectors.toSet());
            services.put(serviceType, currentInstances);
            if (instancesIds.size() != currentInstancesIds.size() || !instancesIds.containsAll(currentInstancesIds)) {
                eventPublisher.publishEvent(new InstanceCountChangedEvent(serviceType, currentInstancesIds.size()));
            }
        }
    }

}
