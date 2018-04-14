package com.yeguohao.music;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.yeguohao.music.common.player.impl.RecentlyPlaySongs;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "ExampleInstrumentedTest";

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.yeguohao.music", appContext.getPackageName());
    }

    @Test
    public void RecentlyPlaySongsTest() {
        RecentlyPlaySongs recentlyPlaySongs = new RecentlyPlaySongs();
        int random = recentlyPlaySongs.random(1);
        Log.d(TAG, "RecentlyPlaySongsTest: " + random);
    }

}
