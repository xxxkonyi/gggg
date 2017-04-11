package com.believe.wechat;

import com.believe.wechat.model.Ticket;
import com.believe.wechat.model.TicketType;

public interface TicketLoader {

  String get(TicketType type);

  void refresh(Ticket ticket);
}
