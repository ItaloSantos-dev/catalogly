package com.italo.catalogy.dto.nfe_io.serviceInvoiceResponse;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.HashMap;
import java.util.Map;

public enum FlowStatus {
    CANCEL_FAILED("CANCEL_FAILED"),
    ISSUE_FAILED("ISSUE_FAILED"),
    ISSUED("ISSUED"),
    CANCELLED("CANCELLED"),
    PULL_FROM_CITY_HALL("PULL_FROM_CITY_HALL"),
    WAITING_CALCULATE_TAXES("WAITING_CALCULATE_TAXES"),
    WAITING_DEFINE_RPS_NUMBER("WAITING_DEFINE_RPS_NUMBER"),
    WAITING_SEND("WAITING_SEND"),
    WAITING_SEND_CANCEL("WAITING_SEND_CANCEL"),
    WAITING_RETURN("WAITING_RETURN"),
    WAITING_DOWNLOAD("WAITING_DOWNLOAD");

    private final String flowStatus;

    static final Map<String, FlowStatus> mappingFlowStatus = Map.ofEntries(
            Map.entry("CancelFailed", CANCEL_FAILED),
            Map.entry("IssueFailed", ISSUE_FAILED),
            Map.entry("Issued", ISSUED),
            Map.entry("Cancelled", CANCELLED),
            Map.entry("PullFromCityHall", PULL_FROM_CITY_HALL),
            Map.entry("WaitingCalculateTaxes", WAITING_CALCULATE_TAXES),
            Map.entry("WaitingDefineRpsNumber", WAITING_DEFINE_RPS_NUMBER),
            Map.entry("WaitingSend", WAITING_SEND),
            Map.entry("WaitingSendCancel", WAITING_SEND_CANCEL),
            Map.entry("WaitingReturn", WAITING_RETURN),
            Map.entry("WaitingDownload", WAITING_DOWNLOAD)
    );

    FlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getFlowStatus() {
        return this.flowStatus;
    }

    @JsonCreator
    public static FlowStatus fromString(String value){
        return mappingFlowStatus.get(value);
    }
}
