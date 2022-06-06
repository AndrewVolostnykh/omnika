package io.omnika.common.utils.hibernate;

import java.util.concurrent.Callable;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class HibernateUtils {

    private final EntityManager em;

    @SuppressWarnings("unchecked")
    public <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new
                    NullPointerException("Entity passed for initialization is null");
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

    @SneakyThrows
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T doInNewTransaction(Callable<T> callable) {
        return callable.call();
    }
}
