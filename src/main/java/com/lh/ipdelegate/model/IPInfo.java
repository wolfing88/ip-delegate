package com.lh.ipdelegate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString

public class IPInfo {

    private String ip;
    private Integer port;
    private String type;//http https
    private Date getTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IPInfo ipInfo = (IPInfo) o;
        return Objects.equals(ip, ipInfo.ip) &&
                Objects.equals(port, ipInfo.port) &&
                Objects.equals(type, ipInfo.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ip, port, type);
    }
}
