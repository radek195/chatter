package pl.radek.chatter.utils

import pl.radek.chatter.domain.model.user.Gender
import pl.radek.chatter.domain.model.user.User
import pl.radek.chatter.domain.model.user.preference.UserPreference

trait DataProvider {
  
  User getUser(
      def gender = Gender.MALE,
      def age = 26,
      def nickname = "Marco",
      def chatroomId = UUID.randomUUID()
  ) {
    User.builder()
      .gender(gender)
      .age(age)
      .nickname(nickname)
      .chatroomId(chatroomId)
      .build()
  }
  
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