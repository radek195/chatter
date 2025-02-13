package pl.radek.chatter.integration.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import pl.radek.chatter.domain.service.user.UserService
import pl.radek.chatter.interfaces.controller.user.UserController
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest(UserController)
class UserControllerIT extends Specification {
  
  @Autowired
  MockMvc mockMvc
  
  @MockBean
  UserService userService
  
  def "Should return error message when nickname is too short"() {
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
        post("/api/guest")
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
  
}
