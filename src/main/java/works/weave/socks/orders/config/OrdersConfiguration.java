package works.weave.socks.orders.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrdersConfiguration {

    @Value("${shipping.hostname:#{null}}")
    private String shippingHostname;

    @Value("${shipping.port:#{null}}")
    private String shippingPort;

    @Value("${payment.hostname:#{null}}")
    private String paymentHostname;

    @Value("${payment.port:#{null}}")
    private String paymentPort;

    @Bean
    @ConditionalOnMissingBean(OrdersConfigurationProperties.class)
    public OrdersConfigurationProperties frameworkMesosConfigProperties() {
        return new OrdersConfigurationProperties(shippingHostname, shippingPort, paymentHostname, paymentPort);
    }
}
