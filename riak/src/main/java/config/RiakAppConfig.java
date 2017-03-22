package config;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Created by shavene on 3/17/2017.
 */
@ConfigurationProperties(prefix = "riak")
public class RiakAppConfig {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
