package com.ftr.WorkitemService.external.client.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TerminalResponse {
    private String terminalId;
    private String terminalName;
    private String country;
    private String itemType;
    private String terminalDescription;
    private int capacity;
    private String status;
    private String harborLocation;
    private int availableCapacity;
}
