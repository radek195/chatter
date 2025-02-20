package pl.radek.chatter.unit

import pl.radek.chatter.utils.DataProvider
import pl.radek.chatter.domain.service.user.UserService
import pl.radek.chatter.infrastructure.exceptions.UserNotFoundException
import pl.radek.chatter.infrastructure.repository.user.UserEntity
import pl.radek.chatter.infrastructure.repository.user.UserRepository
import spock.lang.Specification
import spock.lang.Subject

class UserServiceUT extends Specification implements DataProvider {
  
  UserRepository userRepository = Mock()
  
  @Subject
  UserService userService = new UserService.Impl(userRepository)
  
  def "Should throw UserNotFoundException when adding preference to user that does not exist."() {
    given:
      userRepository.findById(_) >> Optional.empty()
    
    when:
      userService.addUserPreferenceForUser(getUserPreference(), 2)
    
    then:
      def exception = thrown(UserNotFoundException)
      exception.message == "Could not find user with id: 2"
  }
  
  def "Should add preference for user that exists."() {
    given:
      def userEntity = Mock(UserEntity)
      userRepository.findById(2) >> Optional.of(userEntity)
    
    when:
      userService.addUserPreferenceForUser(getUserPreference(), 2)
    
    then:
      1 * userEntity.setUserPreference(_)
      1 * userRepository.save(userEntity)
  }
}
