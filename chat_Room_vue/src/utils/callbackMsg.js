import SockJS from "sockjs-client";
import * as Stomp from "stompjs";

let stompClient = null;

class StompService {
  constructor() {
    if (!stompClient) {
      const socket = new SockJS("http://localhost:8066/room");
      stompClient = Stomp.over(socket);
      stompClient.connect({}, () => {
        console.log("Connected to WebSocket server");
      });
    }
  }

  sendMessage({ openId, roomId, contentType, message, messageType }) {
    const data = {
      openId,
      friendId: roomId,
      contentType,
      contentStatus: "unread",
      message,
      timestamp: new Date().getTime(),
      messageType: messageType
    };
    stompClient.send("/app/sendMessage", {}, JSON.stringify(data));
  }

  subscribe(topic, callback) {
    stompClient.subscribe(topic, message => {
      const data = JSON.parse(message.body);
      callback(data);
    });
  }

  disconnect() {
    if (stompClient) {
      stompClient.disconnect(() => {
        console.log("Disconnected from WebSocket server");
      });
      stompClient = null;
    }
  }
}

const stompServiceInstance = new StompService();

export default stompServiceInstance;
