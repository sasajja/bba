package com.mycompany;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.AnyOf;
import org.jooby.test.JoobyRule;
import org.jooby.test.MockRouter;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Paul Hammant DevOps, (c) 2018
 */
public class AppTest {

  /**
   * One app/server for all the tests in this class. If you want to start/stop a new server per test,
   * remove the static modifier and replace the {@link ClassRule} annotation with {@link Rule}.
   */
  @ClassRule
  public static JoobyRule app = new JoobyRule(new App());

  /**
   * An integration test that uses RestAssured to
   * check hair color functionality over HTTP (whichever is
   * configured in conf/application.conf (unless
   * overridden at launch)
   */
  @Test
  public void integrationTest() {
    int x = 0;
    do {
      // 20 iterations as hair color impl is random
      x++;
      get("/")
              .then()
              .assertThat()
              .body(startsWith("Hello "))
              .body(endsWith(" World!"))
              .body(specifiesAnyOfTheAllowedColors())
              .statusCode(200)
              .contentType("text/html;charset=UTF-8");
    } while (x < 20);
  }

  private AnyOf<String> specifiesAnyOfTheAllowedColors() {
    return anyOf(containsString("Blonde"), containsString("Brown"),
            containsString("Black"), containsString("Red"));
  }

  /**
   * A unit test that checks 'new' integer-based
   * implementation directly (without HTTP or TCP/IP)
   */
  @Test
  public void newHairColorTest() throws Throwable {
    App app = new App();
    app.bbaf = new Release4();

    int x = 0;
    do {
      // 40 iterations as hair color impl is random
      x++;
      String result = new MockRouter(app).get("/");
      assertThat(result, startsWith("Hello "));
      assertThat(result, endsWith(" World!"));
      assertThat(result, specifiesAnyOfTheAllowedColors());
    } while (x < 40);
  }

}
