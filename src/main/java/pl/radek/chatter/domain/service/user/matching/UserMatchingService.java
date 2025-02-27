package pl.radek.chatter.domain.service.user.matching;


import pl.radek.chatter.infrastructure.repository.user.UserEntity;

import java.util.List;

public interface UserMatchingService {

    List<UserEntity> findMatchingUsersByPreference(UserEntity user, List<UserEntity> users);

}
