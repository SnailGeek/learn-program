package com.lagou.vo;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum ProcedureOperationEnum {

    @JsonProperty("tempsave")
    TEMPSAVE("tempsave"),
    @JsonProperty("complete")
    COMPLETE("complete"),
    @JsonProperty("goback")
    GOBACK("goback");
    private String code;

    ProcedureOperationEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
