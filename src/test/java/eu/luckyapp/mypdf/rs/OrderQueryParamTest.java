package eu.luckyapp.mypdf.rs;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Private company LuckyApp
 * Created by LMochel on 2016-03-23.
 */
@RunWith(MockitoJUnitRunner.class)
public class OrderQueryParamTest {
    OrderQueryParam oop;

    @Before
    public void setUp() throws Exception {
        oop=new OrderQueryParam();
        oop.setMpk("gc2222");
        oop.setBudget("b75_cz*");
        oop.setItemDescription("*somethin new*");

    }

    @Test

    public void changeWildcardsInStringFieldsToSqlTest() throws Exception {
        OrderQueryParam expected=new OrderQueryParam();
        expected.setMpk("gc2222");
        expected.setBudget("b75_cz%");
        expected.setItemDescription("%somethin new%");

        oop.changeWildcardsInStringFieldsToSql();
        Assert.assertEquals(expected,oop);
    }
}