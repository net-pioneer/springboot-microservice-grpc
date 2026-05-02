package com.pouya.library.DTO;

import com.pouya.common.grpc.CurrencyResponse;
import com.pouya.common.grpc.NotebookResponse;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

public class CurrencyDTO {
    private Long id;
    private String name;
    private String symbol;
    private BigDecimal rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public static CurrencyDTO fromProto(CurrencyResponse proto) {
        CurrencyDTO dto = new CurrencyDTO();
        BeanUtils.copyProperties(proto, dto);
        return dto;
    }
}
