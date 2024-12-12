package pl.radek.chatter.integration

import io.restassured.RestAssured
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.server.LocalServerPort
import pl.radek.chatter.config.SpecificationIT

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ChatMemberControllerIT extends SpecificationIT {

    @LocalServerPort
    int port

    final REGISTER_PATH = "/register"

    def "Should respond with registered member UUID"() {
        when:
            def response = RestAssured
                    .given()
                    .contentType("application/json")
                    .body("{\"nickname\": \"Tom95\"}")
                    .when()
                    .post("http://localhost:" + port + REGISTER_PATH)

        then:
            response.statusCode() == 201

        and:
            def body = response.body().jsonPath()
            body.get("uuid") != null
    }

}
