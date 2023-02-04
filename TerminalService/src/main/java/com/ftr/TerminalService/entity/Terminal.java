package com.ftr.TerminalService.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Terminal {

    @Id
    @Column(name = "TERMINAL_ID")
    @GenericGenerator(name = "terminal_id_generator", strategy = "com.ftr.TerminalService.entity.TerminalIdGenerator")
    @GeneratedValue(generator = "terminal_id_generator")
    private String terminalId;

    @Column(name = "TERMINAL_NAME")
    private String terminalName;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "ITEM_TYPE")
    private String itemType;

    @Column(name = "TERMINAL_DESCRIPTION")
    private String terminalDescription;

    @Column(name = "CAPACITY")
    private int capacity;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "HARBOR_LOCATION")
    private String harborLocation;

    @Column(name = "AVAILABLE_CAPACITY")
    private int availableCapacity;

    @Column(name = "DELETED_STATUS")
    private String deleted_status;

    @PrePersist
    public void prePersist() {
        this.deleted_status = "Active";
    }
}
