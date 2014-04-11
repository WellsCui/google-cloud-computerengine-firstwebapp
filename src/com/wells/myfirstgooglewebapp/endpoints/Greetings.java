package com.wells.myfirstgooglewebapp.endpoints;

import com.google.api.server.spi.config.Api;
import javax.inject.Named;
import java.util.ArrayList;
/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 */
@Api(name = "helloworld", version = "v1")
public class Greetings {
  public static ArrayList<HelloGreeting> greetings = new ArrayList<HelloGreeting>();

  static {
    greetings.add(new HelloGreeting("hello world!"));
    greetings.add(new HelloGreeting("goodbye world!"));
  }

  public HelloGreeting getGreeting(@Named("id") Integer id) {
    return greetings.get(id);
  }
}
