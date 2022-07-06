package io.omnika.common.ipc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.zookeeper.discovery.ZookeeperDiscoveryProperties;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscoveryService {

    private final DiscoveryClient discoveryClient;
    private final ServiceInstanceRegistration currentInstanceInfo;

    private ServiceType serviceType;
    @Value("${spring.application.name}")
    private String serviceId;

    @PostConstruct
    private void init() {
        this.serviceType = ServiceType.getByName(serviceId);
    }

    public int getInstancesCount(ServiceType serviceType) {
        return discoveryClient.getInstances(getServiceId(serviceType)).size();
    }

    public int getInstanceIndex() {
        return discoveryClient.getInstances(getServiceId(getServiceType())).stream()
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

}
