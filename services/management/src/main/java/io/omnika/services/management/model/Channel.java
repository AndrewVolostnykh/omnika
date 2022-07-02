package io.omnika.services.management.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.omnika.common.rest.services.management.model.ChannelType;
import io.omnika.services.management.core.model.BaseEntity;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "channels")
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Channel extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private ChannelType channelType;

    private String name;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode config;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @ManyToMany
    @JoinTable(
            name = "assigned_channels",
            joinColumns = @JoinColumn(name = "chanel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignedUsers;
}
