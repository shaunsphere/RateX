package xevate.com.ratex;

import android.graphics.Point;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class MainActivityTests {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);
    private MainActivity mainActivity;

    @Before
    public void init(){
        UiDevice uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Point[] coordinates = new Point[4];
        coordinates[0] = new Point(248, 1520);
        coordinates[1] = new Point(248, 929);
        coordinates[2] = new Point(796, 1520);
        coordinates[3] = new Point(796, 929);
        try {
            if (!uiDevice.isScreenOn()) {
                uiDevice.wakeUp();
                uiDevice.swipe(coordinates, 10);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Before
    public void setActivity() {
        mainActivity = mActivityRule.getActivity();
    }

    @Test
    public void testRateX() {
        onView(withId(R.id.priceinputTxtEdit)).perform(typeText("123.jhjkghkj456"));
        onView(withId(R.id.okbtn)).perform(click());

        onView(withId(R.id.spinner1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("CAD"))).perform(click());
        onView(withId(R.id.spinner1)).check(matches(withSpinnerText(containsString("CAD"))));




    }
}