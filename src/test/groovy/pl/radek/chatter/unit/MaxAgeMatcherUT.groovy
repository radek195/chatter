package pl.radek.chatter.unit

import pl.radek.chatter.domain.service.user.matching.matchers.MaxAgeMatcher
import pl.radek.chatter.infrastructure.repository.user.UserEntity
import pl.radek.chatter.infrastructure.repository.user.preference.UserPreferenceEntity
import pl.radek.chatter.utils.DataProvider
import spock.lang.Specification
import spock.lang.Subject

class MaxAgeMatcherUT extends Specification implements DataProvider {
    
    @Subject
    MaxAgeMatcher maxAgeMatcher = new MaxAgeMatcher();
    
    def """Should return #expectedResult when
            user minimum age preference is #userMaxAgePref - candidate age 38 AND
            candidate minimum age preference is #candidateMaxAgePref - user age 42 """() {
        when:
            UserPreferenceEntity userPreference = getUserPreferenceEntity(null, userMaxAgePref)
            UserEntity user = UserEntity.builder()
                .age(42)
                .userPreference(userPreference)
                .build()
            
            UserPreferenceEntity candidatePreference = getUserPreferenceEntity(null, candidateMaxAgePref)
            UserEntity candidate = UserEntity.builder()
                .age(38)
                .userPreference(candidatePreference)
                .build()
        
        then:
            maxAgeMatcher.isMatching(user, candidate) == expectedResult
        
        where:
            userMaxAgePref | candidateMaxAgePref || expectedResult
            null           | 41                  || false
            99             | 41                  || false
            99             | 42                  || true
            99             | 43                  || true
            null           | 43                  || true
            37             | null                || false
            37             | 99                  || false
            38             | 99                  || true
            39             | 99                  || true
            39             | null                || true
    }
    
}
