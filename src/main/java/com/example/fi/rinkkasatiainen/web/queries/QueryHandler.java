package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.web.commands.Handler;
import com.example.fi.rinkkasatiainen.web.commands.Query;

public interface QueryHandler<Q extends Query, R> extends Handler<Q, R> {
}
