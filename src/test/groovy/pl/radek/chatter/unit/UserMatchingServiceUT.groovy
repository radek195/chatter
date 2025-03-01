package pl.radek.chatter.unit

import pl.radek.chatter.domain.service.user.matching.UserMatchingService
import pl.radek.chatter.domain.service.user.matching.matchers.MaxAgeMatcher
import pl.radek.chatter.domain.service.user.matching.matchers.MinAgeMatcher
import pl.radek.chatter.infrastructure.repository.user.UserEntity
import pl.radek.chatter.infrastructure.repository.user.preference.UserPreferenceEntity
import pl.radek.chatter.utils.DataProvider
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class UserMatchingServiceUT extends Specification implements DataProvider {
    
    @Subject
    @Shared
    UserMatchingService userMatchingService
    
    def setupSpec() {
        userMatchingService = new UserMatchingService.Impl(
            List.of(new MinAgeMatcher(), new MaxAgeMatcher())
        )
    }
    
    def "Should return single matching user"() {
        given:
            UserPreferenceEntity userPreference = getUserPreferenceEntity(32, 38)
            UserEntity user = UserEntity.builder()
                .age(42)
                .userPreference(userPreference)
                .build()
            
            UserPreferenceEntity candidatePreference = getUserPreferenceEntity(40, 44)
            UserEntity expectedCandidate = UserEntity.builder()
                .age(36)
                .userPreference(candidatePreference)
                .build()
            UserEntity candidateOne = UserEntity.builder()
                .age(44)
                .userPreference(candidatePreference)
                .build()
            UserEntity candidateTwo = UserEntity.builder()
                .age(28)
                .userPreference(candidatePreference)
                .build()
        
        when:
            List<UserEntity> matchedCandidates = userMatchingService
                .findMatchingUsersByPreference(user, List.of(expectedCandidate, candidateOne, candidateTwo))
        
        then:
            matchedCandidates.size() == 1
            expectedCandidate == matchedCandidates.get(0)
    }
    
    def "Should return multiple matching users"() {
        given:
            UserPreferenceEntity userPreference = getUserPreferenceEntity(32, 38)
            UserEntity user = UserEntity.builder()
                .age(42)
                .userPreference(userPreference)
                .build()
            
            UserPreferenceEntity candidatePreference = getUserPreferenceEntity(40, 44)
            UserEntity expectedCandidateOne = UserEntity.builder()
                .age(36)
                .userPreference(candidatePreference)
                .build()
            UserEntity expectedCandidateTwo = UserEntity.builder()
                .age(38)
                .userPreference(candidatePreference)
                .build()
            UserEntity candidate = UserEntity.builder()
                .age(44)
                .userPreference(candidatePreference)
                .build()
        
        when:
            List<UserEntity> matchedCandidates = userMatchingService
                .findMatchingUsersByPreference(user, List.of(expectedCandidateOne, expectedCandidateTwo, candidate))
        
        then:
            matchedCandidates.size() == 2
    }
}
