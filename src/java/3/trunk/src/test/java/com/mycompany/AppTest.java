package com.mycompany;

import org.hamcrest.core.AnyOf;
import org.jooby.Request;
import org.jooby.Response;
import org.jooby.test.JoobyRule;
import org.jooby.test.MockRouter;
import org.junit.ClassRule;
import org.junit.Test;
import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
   * A service test that uses RestAssured to
   * check hair color functionality over HTTP
   */
  @Test
  public void serviceTest() {
    get("/color/hair.json")
        .then()
        .assertThat()
        .body(startsWith("{\"color\":\""))
        .body(endsWith("\"}"))
        .body(specifiesAnyOfTheAllowedColors())
        .statusCode(200)
        .contentType("application/json;charset=UTF-8");
  }

  private AnyOf<String> specifiesAnyOfTheAllowedColors() {
    return anyOf(containsString("Blonde"), containsString("Brown"),
            containsString("Black"), containsString("Red"));
  }

  /**
   * A unit test that checks hair color
   * functionality without HTTP or TCP/IP
   */
  @Test
  public void unitTest() throws Throwable {
    Response rsp = mock(Response.class);
    when(rsp.status(200)).thenReturn(rsp);
    when(rsp.type("application/json")).thenReturn(rsp);

    String result = new MockRouter(new App().withTogglesFor(Release3.class.getName()),
            mock(Request.class), rsp)
            .get("/color/hair.json");

    assertThat(result, startsWith("{\"color\":\""));
    assertThat(result, endsWith("\"}"));
    assertThat(result, specifiesAnyOfTheAllowedColors());
    verify(rsp).type("application/json");
    verify(rsp).status(200);
  }
}
