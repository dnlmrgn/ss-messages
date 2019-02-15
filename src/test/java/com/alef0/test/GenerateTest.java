package com.alef0.test;

import com.alef0.Generate;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.File;

public class GenerateTest extends AbstractMojoTestCase {

    public void testGenerateFiles() throws Exception {
        File pom = getTestFile("src/test/resources/pom.xml");
        assertNotNull(pom);

        Generate generate = (Generate) lookupMojo("generate", pom);
        assertNotNull(generate);

        generate.execute();

        assertTrue(
                new File(getBasedir() + "/target/test-classes/i18n/messages_en.properties").exists()
        );

        assertTrue(
                new File(getBasedir() + "/target/test-classes/i18n/messages_enca.properties").exists()
        );

        assertTrue(
                new File(getBasedir() + "/target/test-classes/i18n/messages_frfr.properties").exists()

        );

        assertTrue(
                new File(getBasedir() + "/target/test-classes/i18n/messages_frca.properties").exists()

        );

        assertTrue(
                new File(getBasedir() + "/target/test-classes/i18n/messages_jpjp.properties").exists()

        );
    }

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
