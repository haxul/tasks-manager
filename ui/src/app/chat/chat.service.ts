import {Injectable} from '@angular/core'
import {environment} from "../../environments/environment"
import {HttpClient, HttpHeaders} from "@angular/common/http"
import {AuthService} from "../auth.service"

declare const SockJS
declare const Stomp

interface ChatMessage {
  from: string
  text: string
}

interface StompClient {
  connect: Function,
  subscribe: Function,
  send: Function
  disconnect: Function
}

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private httpClient: HttpClient, private authService: AuthService) {
  }

  stompClient: StompClient

  connect(username: string) {
    let socket = new SockJS(environment.serverUrl + '/gs-guide-websocket;')
    this.stompClient = Stomp.over(socket)
    this.stompClient.connect({},
      () => this.stompClient.subscribe("/topic/messages/" + username, (message) => console.log(JSON.parse(message.body))),
      () => console.log("DISCONNECT HAPPENED")
    )
  }

  sendMessage(from: string, text: string, to: string) {
    const message: ChatMessage = {from, text}
    this.stompClient.send("/app/user/" + to + "/chat", {}, JSON.stringify(message))
  }

  findAllMembers() {
    const headers = this.authService.createAuthHeaders()
    const httpOptions = {headers}
    return this.httpClient.get(`${environment.serverUrl}/users`, httpOptions)
  }
}
