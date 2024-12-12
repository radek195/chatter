package pl.radek.chatter.interfaces.controller.chatmember;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class RegisterResponse {
    private UUID uuid;
}
