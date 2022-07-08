package org.acme.logging;

import java.util.StringJoiner;

import javax.ws.rs.core.MultivaluedMap;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class FilterHelper {

    static final String INBOUND = "inbound";

    static final String OUTBOUND = "outbound";

    static String getHeadersAsString(MultivaluedMap<String, ?> headers) {
        StringJoiner headerJoiner = new StringJoiner(", ");
        headers.forEach((key, value) -> headerJoiner.add(key + "=" + value));
        return headerJoiner.toString();
    }

    static StringJoiner createLogMessageJoiner(String bound) {
        StringJoiner logMessageJoiner = new StringJoiner("\n");

        if (INBOUND.equals(bound)) {
            logMessageJoiner.add("\nInbound request");
        } else if (OUTBOUND.equals(bound)) {
            logMessageJoiner.add("\nOutbound response");
        }

        logMessageJoiner.add("---------------");
        return logMessageJoiner;
    }
}
