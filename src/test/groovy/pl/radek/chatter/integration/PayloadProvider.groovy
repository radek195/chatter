package pl.radek.chatter.integration

import pl.radek.chatter.interfaces.controller.chatmessaging.MessagePayload

trait PayloadProvider {

    MessagePayload getMessagePayload(
            String content = "Hello there!",
            String senderNickname = "Marius"
    ) {
        MessagePayload.builder()
                .content(content)
                .senderNickname(senderNickname)
                .build()
    }
}