package uk.ac.diamond.ispyb.test;

import uk.ac.diamond.ispyb.api.IspybSpyCatApi;
import uk.ac.diamond.ispyb.dao.IspybSpyCatDAO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import uk.ac.diamond.ispyb.dao.IspybSpyCatDaoFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SpyCatIntegrationTest {
    private final static IntegrationTestHelper<IspybSpyCatApi> helper = new IntegrationTestHelper<IspybSpyCatApi>(new IspybSpyCatDaoFactory());

    @BeforeClass
    public static void connect() throws Exception {
        helper.setUp();
    }

    @AfterClass
    public static void disconnect() throws Exception {
        helper.tearDown();
    }

    @Test
    public void testGetVisitsForUser() throws SQLException, IOException {
        List<String> visits = helper.execute(api -> api.getVisitsForUser("i03", "boaty", 147483646));
        List<String> expected = Arrays.asList("cm14451-1", "cm14451-2");
        assertThat(visits, is(equalTo(expected)));
    }

    @Test
    public void testGetLatestVisitWithPrefix() throws SQLException, IOException {
        String visit = helper.execute(api -> api.getLatestVisitWithPrefix("i03", "cm")).get();
        String expected = "cm14451-2";
        assertThat(visit, is(equalTo(expected)));
    }

    @Test
    public void testGetTitleForVisit() throws SQLException, IOException {
        String title = helper.execute(api -> api.getTitleForVisit("cm", 14451)).get();
        String expected = "I03 Commissioning Directory 2016";
        assertThat(title, is(equalTo(expected)));
    }
}
