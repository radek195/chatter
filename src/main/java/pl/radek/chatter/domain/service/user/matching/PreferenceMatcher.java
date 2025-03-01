package pl.radek.chatter.domain.service.user.matching;

import pl.radek.chatter.infrastructure.repository.user.UserEntity;

public interface PreferenceMatcher {

    boolean isMatching(UserEntity requester, UserEntity matched);

}
