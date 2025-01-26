package pl.radek.chatter.integration

import pl.radek.chatter.domain.message.Message

trait PayloadProvider {

    Message getMessagePayload(
            String content = "Hello there!",
            String senderNickname = "Marius"
    ) {
        Message.builder()
                .content(content)
                .senderNickname(senderNickname)
                .build()
    }
}