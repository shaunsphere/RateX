package xevate.com.ratex;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import xevate.com.ratex.MainActivity.GetRateSync;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void GetRate() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        GetRateSync getratesync;
        getratesync = new MainActivity().new GetRateSync();
        getratesync.execute();

//        assertEquals(expected, actual);
        //  assertEquals(4, 2 + 2);
    }
}