package com.ftr.WorkitemService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkitemTerminal {

    @Id
    @Column(name = "WORKITEM_ID")
    private String workitemId;

    @Column(name = "TERMINAL_ID")
    private String terminalId;

}
