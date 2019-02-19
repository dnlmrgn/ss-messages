# Message Files from a Spreadsheet

Maven Plugin That Allows Storing Messages in a Spreadsheet for Internationalization

[![Build Status](https://travis-ci.org/dnlmrgn/ss-messages.svg?branch=master)](https://travis-ci.org/dnlmrgn/ss-messages)

Sometimes it's very useful to keep all your language translations in a spreadsheet so that a translator
can easily edit a single file rather than editing manyproperty files. This plugin can be easily incorporated in
your project so property files can be automagically generated at build time from your spreadsheet file.

The spreadsheet will need to have a sheet per language and the sheet name will be the intended [lang]. The
generated property file will be named messages_{lang}.properties where lang is the sheet name.

## Usage:

First build this plugin locally and install it to your local maven repository:

```
git clone https://github.com/dnlmrgn/ss-messages.git
cd ss-messages
mvn clean install
```

To use in your project add the maven plugin:

```
    <build>
        <plugins>
            <plugin>
                <groupId>com.alef0</groupId>
                <artifactId>ss-messages</artifactId>
                <version>1.0</version>
                <configuration>
                    <spreadsheetFile>src/main/resources/messages.xlsx</spreadsheetFile>
                    <outputDirectory>src/main/resources/i18n</outputDirectory>
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
            <plugin>
                <artifactId>maven-clean-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                  <filesets>
                    <fileset>
                      <directory>src/main/resources/i18n</directory>
                      <includes>
                        <include>**</include>
                      </includes>
                      <followSymlinks>false</followSymlinks>
                    </fileset>
                  </filesets>
                </configuration>
              </plugin>
        </plugins>
    </build>

```
