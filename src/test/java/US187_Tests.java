package test.java;

import java.main.memoranda.util.JsonLoader;
import org.junit.Before;
import org.junit.Test;

//import main.java.*;

public class US187_Tests {

    @Before
    public void setUp() throws Exception {
    }

    /**
     * Task the loadFromJson method in JsonLoader.java.
     */
    @Test
    public void testLoadJson() {
        JsonLoader jsonLoader = new JsonLoader();
        // Requires Data.json to be in the .memoranda folder
        jsonLoader.loadFromJson();
    }
}

