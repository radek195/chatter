package pl.radek.chatter.unit

import pl.radek.chatter.domain.service.user.matching.matchers.MinAgeMatcher
import pl.radek.chatter.infrastructure.repository.user.UserEntity
import pl.radek.chatter.infrastructure.repository.user.preference.UserPreferenceEntity
import pl.radek.chatter.utils.DataProvider
import spock.lang.Specification
import spock.lang.Subject

class MinAgeMatcherUT extends Specification implements DataProvider {
    
    @Subject
    MinAgeMatcher minAgeMatcher = new MinAgeMatcher();
    
    def """Should return #expectedResult when
            user minimum age preference is #userMinAgePref - candidate age 38 AND
            candidate minimum age preference is #candidateMinAgePref - user age 42 """() {
        when:
            UserPreferenceEntity userPreference = getUserPreferenceEntity(userMinAgePref, null)
            UserEntity user = UserEntity.builder()
                .age(42)
                .userPreference(userPreference)
                .build()
            
            UserPreferenceEntity candidatePreference = getUserPreferenceEntity(candidateMinAgePref, null)
            UserEntity candidate = UserEntity.builder()
                .age(38)
                .userPreference(candidatePreference)
                .build()
        
        then:
            minAgeMatcher.isMatching(user, candidate) == expectedResult
        
        where:
            userMinAgePref | candidateMinAgePref || expectedResult
            null           | 41                  || true
            16             | 41                  || true
            16             | 42                  || true
            16             | 43                  || false
            null           | 43                  || false
            37             | null                || true
            37             | 16                  || true
            38             | 16                  || true
            39             | 16                  || false
            39             | null                || false
    }
    
}
