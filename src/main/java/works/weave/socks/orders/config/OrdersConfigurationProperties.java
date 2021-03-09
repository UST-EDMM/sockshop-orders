package works.weave.socks.orders.config;

import java.net.URI;
import java.util.Objects;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class OrdersConfigurationProperties {

    private final String shippingHostname;
    private final String shippingPort;
    private final String paymentHostname;
    private final String paymentPort;

    private String domain = "";

    public OrdersConfigurationProperties(String shippingHostname, String shippingPort, String paymentHostname, String paymentPort) {
        this.shippingHostname = shippingHostname;
        this.shippingPort = shippingPort;
        this.paymentHostname = paymentHostname;
        this.paymentPort = paymentPort;
    }

    public URI getPaymentUri() {
        if (Objects.isNull(paymentHostname) || Objects.isNull(paymentPort)) {
            return new ServiceUri(new Hostname("payment"), new Domain(domain), "/paymentAuth").toUri();
        }
        return new ServiceUri(new Hostname(paymentHostname + ":" + paymentPort), new Domain(""), "/paymentAuth").toUri();
    }

    public URI getShippingUri() {
        if (Objects.isNull(shippingHostname) || Objects.isNull(shippingPort)) {
            return new ServiceUri(new Hostname("shipping"), new Domain(domain), "/shipping").toUri();
        }
        return new ServiceUri(new Hostname(shippingHostname + ":" + shippingPort), new Domain(""), "/shipping").toUri();
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    private class Hostname {
        private final String hostname;

        private Hostname(String hostname) {
            this.hostname = hostname;
        }

        @Override
        public String toString() {
            if (hostname != null && !hostname.equals("")) {
                return hostname;
            } else {
                return "";
            }
        }
    }

    private class Domain {
        private final String domain;

        private Domain(String domain) {
            this.domain = domain;
        }

        @Override
        public String toString() {
            if (domain != null && !domain.equals("")) {
                return "." + domain;
            } else {
                return "";
            }
        }
    }

    private class ServiceUri {
        private final Hostname hostname;
        private final Domain domain;
        private final String endpoint;

        private ServiceUri(Hostname hostname, Domain domain, String endpoint) {
            this.hostname = hostname;
            this.domain = domain;
            this.endpoint = endpoint;
        }

        public URI toUri() {
            return URI.create(wrapHTTP(hostname.toString() + domain.toString()) + endpoint);
        }

        private String wrapHTTP(String host) {
            return "http://" + host;
        }

        @Override
        public String toString() {
            return "ServiceUri{" +
                    "hostname=" + hostname +
                    ", domain=" + domain +
                    '}';
        }
    }
}
