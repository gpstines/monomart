package mart.mono.gateway.refresh;

import org.springframework.stereotype.Component;

@Component
public class ConfigVersionContext {
    private String version;
    public String getVersion() {
        return version;
    }

    public void storeVersion(String propertyResponseVersion) {
        this.version = propertyResponseVersion;
    }
}
