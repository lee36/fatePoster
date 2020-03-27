package com.lee.fateposter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/27 0027 14:23
 */
@ConfigurationProperties("fate.http")
public class OkHttpProperties {
     @DurationFormat(DurationStyle.SIMPLE)
     private Duration connectTimeout = Duration.
             of(10, ChronoUnit.SECONDS);
     @DurationFormat(DurationStyle.SIMPLE)
     private Duration readTimeout = Duration.
             of(10, ChronoUnit.SECONDS);
    @DurationFormat(DurationStyle.SIMPLE)
     private Duration  writeTimeout = Duration.
            of(10, ChronoUnit.SECONDS);
    private Duration callTimeout= Duration.
            of(10, ChronoUnit.SECONDS);;

    public void setCallTimeout(Duration callTimeout) {
        this.callTimeout = callTimeout;
    }

    public Duration getCallTimeout() {
        return callTimeout;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Duration getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(Duration writeTimeout) {
        this.writeTimeout = writeTimeout;
    }
}
