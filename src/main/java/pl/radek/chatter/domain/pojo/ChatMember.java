package pl.radek.chatter.domain.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class ChatMember {
    private UUID id;
    private String nickname;
    private Status status;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Long> excludedMembers;

    public void addExcludedMember(Long id) {
        excludedMembers.add(id);
    }

    public boolean isExcludedMember(Long id) {
        return excludedMembers.contains(id);
    }
}
