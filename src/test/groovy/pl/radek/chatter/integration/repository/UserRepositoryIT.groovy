package pl.radek.chatter.integration.repository


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import pl.radek.chatter.domain.service.user.UserService
import pl.radek.chatter.infrastructure.repository.user.UserEntity
import pl.radek.chatter.infrastructure.repository.user.UserRepository
import pl.radek.chatter.utils.DataProvider
import spock.lang.Specification
import spock.lang.Subject

@DataJpaTest
@ActiveProfiles('test')
class UserRepositoryIT extends Specification implements DataProvider {
  
  @Autowired
  UserRepository userRepository
  
  @Subject
  UserService userService
  
  
  def setup() {
    userService = new UserService.Impl(userRepository)
  }
  
  @Sql('/sql/InsertUser.sql')
  def 'Should save user preferences to existing user.'() {
    given:
      def expectedMinAge = 25
      def expectedMaxAge = 38
      def userPreferenceDto = getUserPreference(expectedMinAge, expectedMaxAge)
    
    when:
      userService.addUserPreferenceForUser(userPreferenceDto, 1)
    
    then:
      UserEntity actualUser = userRepository.findById(1).orElseThrow()
      
      actualUser.userPreference.minAge == expectedMinAge
      actualUser.userPreference.maxAge == expectedMaxAge
  }
  
  @Sql('/sql/InsertUserWithPreferences.sql')
  def 'Should replace user preferences to existing user.'() {
    given:
      UserEntity existingUser = userRepository.findById(1).orElseThrow()
      existingUser.userPreference.minAge == 36
      existingUser.userPreference.maxAge == 45
    
    and:
      def userPreferenceDto = getUserPreference(expectedMinAge, expectedMaxAge)
    
    when:
      userService.addUserPreferenceForUser(userPreferenceDto, 1)
    
    then:
      UserEntity actualUser = userRepository.findById(1).orElseThrow()
      
      actualUser.userPreference.minAge == expectedMinAge
      actualUser.userPreference.maxAge == expectedMaxAge
    
    where:
      expectedMinAge | expectedMaxAge
      31             | 48
      null           | 47
      19             | null
  }
  
}
