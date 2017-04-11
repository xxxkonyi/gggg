package com.believe.wechat;

import com.believe.wechat.model.Ticket;
import com.believe.wechat.model.TicketType;
import com.google.common.base.Strings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultTicketLoader implements TicketLoader {

  private final Map<TicketType, Ticket> tickets = new ConcurrentHashMap<>();

  @Override
  public String get(TicketType type) {
    Ticket t = tickets.get(type);
    return (t == null
      || Strings.isNullOrEmpty(t.getTicket())
      || System.currentTimeMillis() > t.getExpireAt()) ? null : t.getTicket();
  }

  @Override
  public void refresh(Ticket ticket) {
    tickets.put(ticket.getType(), ticket);
  }
}
