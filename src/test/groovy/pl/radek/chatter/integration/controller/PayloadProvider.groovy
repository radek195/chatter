package pl.radek.chatter.integration.controller

import pl.radek.chatter.domain.model.message.Message

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