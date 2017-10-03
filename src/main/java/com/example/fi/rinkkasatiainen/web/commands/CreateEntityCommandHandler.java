package com.example.fi.rinkkasatiainen.web.commands;

import java.util.UUID;

public interface CreateEntityCommandHandler<T extends Command> extends Handler<T, UUID>{
}
