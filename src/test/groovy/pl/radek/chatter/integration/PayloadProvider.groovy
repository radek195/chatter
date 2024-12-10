package pl.radek.chatter.integration

import pl.radek.chatter.interfaces.controller.MessagePayload

trait PayloadProvider {

    MessagePayload getMessagePayload(
            Long messageId = 17L,
            String content = "Hello there!",
            String senderNickname = "Marius",
            UUID senderId = UUID.fromString("1a4a84d1-ec7f-40b0-8af3-4957d45defb3")
    ) {
        MessagePayload.builder()
                .messageId(messageId)
                .content(content)
                .senderNickname(senderNickname)
                .senderId(senderId)
                .build()
    }
}