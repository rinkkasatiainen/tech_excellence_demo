package com.example.fi.rinkkasatiainen.atdd;

import javax.validation.constraints.NotNull;
import java.util.UUID;

class SessionUUID {
    public final UUID uuid;

    public SessionUUID(@NotNull UUID uuid) {
        this.uuid = uuid;
    }
}
