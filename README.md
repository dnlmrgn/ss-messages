# Message Files Originating in a Spreadsheet file Maven MOJO

[![Build Status](https://travis-ci.org/dnlmrgn/ss-messages.svg?branch=master)](https://travis-ci.org/dnlmrgn/ss-messages)

Sometimes it's very useful to keep all your language translations in a spreadsheet so that a translator
can easily edit it without editing property file. This plugin can be incorporated in your project so
property files can be automagically generated at build time from your spreadsheet.

## Usage:

To use in your project add the maven plugin:

```
    <build>
        <plugins>
            <plugin>
                <groupId>com.alef0</groupId>
                <artifactId>ss-messages</artifactId>
                <version>0.0.1-SNAPSHOT</version>
                <configuration>
                    <spreadsheetFile>${basedir}/src/main/resources/messages.xlsx</spreadsheetFile>
                    <outputDirectory>${basedir}/src/main/resources/i18n</outputDirectory>
                </configuration>
                <executions>
                    <execution>
                        <phase>generate-resources</phase>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

```
