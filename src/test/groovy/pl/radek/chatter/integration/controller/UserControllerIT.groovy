package pl.radek.chatter.integration.controller

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.radek.chatter.domain.model.user_preference.UserPreference
import pl.radek.chatter.domain.service.user.UserService
import pl.radek.chatter.interfaces.controller.user.UserController
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

@WebMvcTest(UserController)
class UserControllerIT extends Specification {
    
    @Autowired
    MockMvc mockMvc
    
    @SpringBean
    UserService userService = Mock()
    
    def "Should validate nickname and age when POST /user is sent."() {
        given:
            def requestBody = """
        {
          "nickname": "${nickname}",
          "gender": "MALE",
          "age": ${age}
        }
      """
        
        when:
            def response = mockMvc.perform(
                post("/api/user")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().response
        
        then:
            response.status == status
            response.contentAsString.contains(message)
        
        where:
            nickname | age | status | message
            "T"      | 11  | 400    | "Nickname must be between 3 and 18 characters."
            "To"     | 17  | 400    | "Nickname must be between 3 and 18 characters."
            "Tom"    | 17  | 200    | ""
            "Lenny"  | 14  | 400    | "You must be 16 or older."
            "Lenny"  | 15  | 400    | "You must be 16 or older."
            "Lenny"  | 16  | 200    | ""
    }
    
    def "Should validate gender when POST /user is sent."() {
        given:
            def requestBody = """
        {
          "nickname": "Lenny",
          "age": 27
        }
      """
        
        when:
            def response = mockMvc.perform(
                post("/api/user")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().response
        
        then:
            response.status == 400
            response.contentAsString.contains("You must provide your gender.")
        
    }
    
    def "Should add user preference when POST /user/{id}/preference is sent."() {
        given:
            def requestBody = """
        {
          "minAge": "21",
          "maxAge": "28"
        }
      """
            
            def expectedDto = UserPreference.builder()
                .minAge(21)
                .maxAge(28)
                .build()
            def expectedId = 8L
        
        when:
            def response = mockMvc.perform(
                put("/api/user/$expectedId/preference")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().response
        
        then:
            response.status == 200
        
        and:
            1 * userService.addUserPreferenceForUser(expectedDto, expectedId)
        
    }
    
    def "Should validate minAge when POST /user/{id}/preference is sent."() {
        given:
            def requestBody = """
        {
          "minAge": "$age",
          "maxAge": "28"
        }
      """
        
        when:
            def response = mockMvc.perform(
                put("/api/user/1/preference")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().response
        
        then:
            response.status == status
            response.contentAsString.contains(message)
        
        where:
            age | status | message
            15  | 400    | "Minimum age cannot be lower than 16."
            16  | 200    | ""
            17  | 200    | ""
    }
}
