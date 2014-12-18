package com.twobros.hatebyte.ystrdy.date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class YstrDateTest {


    @Before
    public void setup() {}

    @Test
    public void test_isWithinTwoHoursOfNow_ReturnFalseWhenOlderThan2Hours() {
        Date oldDate = new Date();
        long twoHours = (2 * 60 * 60 + 1) * 1000;
        oldDate.setTime(oldDate.getTime() - twoHours);

        assertFalse(YstrDate.isWithinTwoHoursOfNow(oldDate));
    }

    @Test
    public void test_isWithinTwoHoursOfNow_ReturnTrueWhenYoungerThan2Hours() {
        Date oldDate = new Date();
        long twoHours = (2 * 60 * 60 + 1) * 1000;
        oldDate.setTime(oldDate.getTime() - (twoHours - 1));

        assertTrue(YstrDate.isWithinTwoHoursOfNow(oldDate));
    }

    @Test
    public void test_isWithinTwoHoursOfNow_ReturnFalseWhenDateIs3HoursFuture() {
        Date futureDate = new Date();
        long threeHours = (3 * 60 * 60 + 1) * 1000;
        futureDate.setTime(futureDate.getTime() + threeHours);

        assertFalse(YstrDate.isWithinTwoHoursOfNow(futureDate));
    }

    @Test
    public void test_isOlderThanEighteenHours_ReturnTrueWhenOlder() {
        Date oldDate = new Date();
        long twentyFourHours = (24 * 60 * 60 + 1) * 1000;
        oldDate.setTime(oldDate.getTime() - twentyFourHours);

        assertTrue(YstrDate.isOlderThanEighteenHours(oldDate));
    }

    @Test
    public void test_isOlderThanEigteenHours_ReturnFalseWhenYounger() {
        Date oldDate = new Date();
        long eighteenHours = (18 * 60 * 60 + 1) * 1000;
        oldDate.setTime(oldDate.getTime() - (eighteenHours - 1));

        assertFalse(YstrDate.isOlderThanEighteenHours(oldDate));
    }

    @Test
    public void test_isYoungerThanThirtyHours_ReturnTrueWhenYounger() {
        Date oldDate = new Date();
        long twentyFourHours = (24 * 60 * 60 + 1) * 1000;
        oldDate.setTime(oldDate.getTime() - twentyFourHours);

        assertTrue(YstrDate.isYoungerThanThirtyHours(oldDate));
    }

    @Test
    public void test_isYoungerThanThirtyHours_ReturnFalseWhenOlder() {
        Date oldDate = new Date();
        long thirtyHours = (30 * 60 * 60 + 1) * 1000;
        oldDate.setTime(oldDate.getTime() - (thirtyHours + 1));

        assertFalse(YstrDate.isYoungerThanThirtyHours(oldDate));
    }























//    @Test
//    public void test_dateIsOlderThanYesterday_returnsFalse() {
//        Date date = new Date();
//        date.setTime(date.getTime() - (YstrDate.firstDayTime() - 1));
//
//        assertFalse(YstrDate.hasFirstDayPassed(date));
//    }
//
//    @Test
//    public void test_dateIsOlderThanYesterday_returnsTrue() {
//        Date date = new Date();
//        date.setTime(date.getTime() - YstrDate.twentyFourHours());
//
//        assertTrue(YstrDate.hasFirstDayPassed(date));
//    }
//
//    @Test
//    public void test_isDifferenceYoungEnoughToRepeat_returnsFalse() {
//        Date date = new Date();
//        date.setTime(date.getTime() - (YstrDate.twoHours()));
//
//        assertFalse(YstrDate.isYoungerThan2Hours(date));
//    }

//    @Test
//    public void test_isDifferenceYoungEnoughToRepeat_returnsTrue() {
//        Date date = new Date();
//        date.setTime(date.getTime() - (YstrDate.twoHours() - 1));
//
//        assertTrue(YstrDate.isYoungerThan2Hours(date));
//    }
//


}
