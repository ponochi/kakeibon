package org.panda.systems.kakeibon.utils.common;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public interface Converter {
    public default ZonedDateTime convertUTCtoJST(ZonedDateTime dt) {
        try {
            return ZonedDateTime
                    .parse(dt.toLocalDateTime().toString(),
                            DateTimeFormatter
                                    .ISO_LOCAL_DATE_TIME
                                    .withZone(ZoneOffset.ofHours(0) /*ZoneId.of("UTC")*/)
                    )
                    .withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
        } catch (NullPointerException e) {
            return null;
        }
    }

    public default <T> T autoCast(Object obj) {
        T castObj = (T) obj;
        return castObj;
    }
}
