package com.esprit.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

  private final SimpMessagingTemplate messagingTemplate;

  @Autowired
  public NotificationService(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  public void notifyUser(String username, String message) {
    messagingTemplate.convertAndSendToUser(username, "/queue/notifications", message);
  }
}
