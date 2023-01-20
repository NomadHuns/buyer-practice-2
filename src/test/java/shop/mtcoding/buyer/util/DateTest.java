package shop.mtcoding.buyer.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void parse_test() {
        // given
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        // when
        Timestamp stamp = Timestamp.valueOf(now);
        System.out.println(stamp);
        // "yyyy-MM-dd hh:mm:ss"
        LocalDateTime nowTime = stamp.toLocalDateTime();
        String nowStr = nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // verify
        System.out.println(nowStr);
    }
}
