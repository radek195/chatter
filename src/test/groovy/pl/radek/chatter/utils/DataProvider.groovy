package pl.radek.chatter.utils

import pl.radek.chatter.domain.model.user_preference.UserPreference

trait DataProvider {
  
  UserPreference getUserPreference(
    def minAge = 25,
    def maxAge = 38
  ) {
    UserPreference.builder()
      .minAge(minAge)
      .maxAge(maxAge)
      .build()
  }
  
}