package pl.radek.chatter.interfaces.controller;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder

public class RegisterBody {
    private String senderNickname;
    private UUID senderId;
}