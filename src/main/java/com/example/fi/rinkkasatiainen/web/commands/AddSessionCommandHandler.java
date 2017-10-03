package com.example.fi.rinkkasatiainen.web.commands;

import java.util.UUID;

public class AddSessionCommandHandler implements CreateEntityCommandHandler<AddSessionCommand> {
    @Override
    public UUID handles(AddSessionCommand any) {
        return null;
    }
}
