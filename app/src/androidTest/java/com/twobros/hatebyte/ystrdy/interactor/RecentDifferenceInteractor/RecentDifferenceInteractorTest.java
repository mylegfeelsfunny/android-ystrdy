package com.twobros.hatebyte.ystrdy.interactor.RecentDifferenceInteractor;

import com.twobros.hatebyte.ystrdy.date.YstrDate;
import com.twobros.hatebyte.ystrdy.egi.mock.TestEGI;
import com.twobros.hatebyte.ystrdy.interactor.RecentDifferenceInteractor.mock.FakeRecentDifferenceInteractor;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeDifferenceInteratorGateway;
import com.twobros.hatebyte.ystrdy.interactor.mockgateways.FakeRecordInteratorGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.entity.DifferenceEntity;
import com.twobros.hatebyte.ystrdy.weatherreport.interactor.sql.entitygateway.DifferenceGateway;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherRequest;
import com.twobros.hatebyte.ystrdy.weatherreport.request.WeatherResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Date;
import java.util.InvalidPropertiesFormatException;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by scott on 12/21/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class RecentDifferenceInteractorTest {

    FakeDifferenceInteratorGateway differenceInteratorGateway;
    FakeRecordInteratorGateway recordInteratorGateway;

    WeatherRequest weatherRequest;
    TestEGI testEGI;

    FakeRecentDifferenceInteractor recentDifferenceInteractor;

    @Before
    public void setup() {
        differenceInteratorGateway              = new FakeDifferenceInteratorGateway();
        testEGI                                 = new TestEGI();
        differenceInteratorGateway.setEntityGatewayImplementation(testEGI);

        recentDifferenceInteractor              = new FakeRecentDifferenceInteractor();
        recentDifferenceInteractor.setDifferenceGateway(differenceInteratorGateway);
        recentDifferenceInteractor.setRecordGateway(recordInteratorGateway);

        weatherRequest                          = new WeatherRequest();
    }

    @After
    public void teardown() {
        recentDifferenceInteractor              = null;
        differenceInteratorGateway              = null;
        weatherRequest                          = null;
    }

    @Test
    public void test_returnsNullIfNoRecordYoungerThan2Hours() {
        differenceInteratorGateway.shouldSendNullYoungRecordEntity = true;
        WeatherResponse response = recentDifferenceInteractor.getReport(weatherRequest);

        assertThat(response).isNull();
    }

    @Test
    public void test_returnsIfRecordYoungerThanAn2Hours() {
        insertDifferenceRecord(youngerThan2Days());
        WeatherResponse response = recentDifferenceInteractor.getReport(weatherRequest);

        assertThat(response.difference).isEqualTo(30);
    }

    private Date youngerThan2Days() {
        Date d = new Date();
        d.setTime(d.getTime() - (YstrDate.twoHours() - 1000));
        return d;
    }

    public long insertDifferenceRecord(Date date) {
        DifferenceEntity differenceEntity = new DifferenceEntity();
        differenceEntity.date = date;
        differenceEntity.difference = 30;
        differenceEntity.todayRecordId = 20;
        differenceEntity.ystrdyRecordId = 23;

        DifferenceGateway differenceEG = new DifferenceGateway();
        differenceEG.setEntity(differenceEntity);

        long rId = 0;
        try {
            rId = testEGI.insert(differenceEG);
        } catch (InvalidPropertiesFormatException expected) {

        }
        return rId;
    }


}
