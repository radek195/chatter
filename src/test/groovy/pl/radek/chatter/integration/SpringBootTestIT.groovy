package pl.radek.chatter.integration

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles('test')
class SpringBootTestIT extends Specification {
    def "Should start"() {
        expect:
            true
    }
}
