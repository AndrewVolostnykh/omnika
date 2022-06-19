package io.omnika.services.management.model;

import io.omnika.services.management.core.model.BaseEntity;
import io.omnika.services.management.model.channel.Channel;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tenants")
public class Tenant extends BaseEntity {

    public Tenant(UUID id) {
        this.setId(id);
    }

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenant")
    private List<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenant")
    private List<Channel> channels;
}
