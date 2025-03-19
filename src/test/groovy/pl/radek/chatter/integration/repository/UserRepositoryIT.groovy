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
    
    def 'Should save new user'() {
        given:
            def user = getUser()
        
        when:
            def id = userRepository.save(user.toEntity()).getId()
        
        then:
            def foundUser= userRepository.findById(id).orElseThrow()
        
            foundUser.getNickname() == user.getNickname()
            foundUser.getAge() == user.getAge()
            foundUser.getGender() == user.getGender()
            foundUser.getChatroomId() == user.getChatroomId()
        
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
    
    @Sql('/sql/InsertMultipleUsers.sql')
    def "Should find two users with unique chatroom."() {
        when:
            List<UserEntity> users = userRepository.findAllWithUniqueChatroomIdExcluding(8)
        
        then:
            users.size() == 2
            users.get(0).getId() != 8
            users.get(1).getId() != 8
    }
    
    @Sql('/sql/InsertMultipleUsers.sql')
    def "Should return all users with the same chatroomId"() {
        when:
            List<UserEntity> actual = userRepository.findAllByChatroomId(UUID.fromString("7734c459-41b9-43f7-8cfc-a93279a01059"))
            
        then:
            actual.size() == 2
            
    }
    
}
