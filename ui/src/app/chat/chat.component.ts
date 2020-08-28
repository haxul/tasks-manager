import {Component, OnInit} from '@angular/core'
import {ChatService} from "./chat.service"
import {AuthService} from "../auth.service"
import {ResponseGetAllChatMessage, Member, ChatMessage, newIncomingMessage} from "./interfaces"
import {map} from "rxjs/operators"
import {environment} from "../../environments/environment"


declare const SockJS
declare const Stomp


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(private chatService: ChatService, private  authService: AuthService) {}

 
  chatMembers: Member[] = []
  pickedMember: Member
  newOutgoingMessage: string
  openChatMessages: ChatMessage[]
  unreadIncomingMessages: newIncomingMessage[] = []
  stompClient
  
  sendMessage() {
    if (!this.pickedMember) return
    const curUsername = localStorage.getItem("user")
    const receiver: string = this.pickedMember?.username
    if (!receiver || !curUsername || !this.newOutgoingMessage) return
    this.chatService.sendMessage(curUsername, this.newOutgoingMessage, receiver, this.stompClient)
    const formattedDate = this.chatService.formatDate(new Date())
    this.openChatMessages.push({
      text: this.newOutgoingMessage,
      date: formattedDate,
      isOutgoingMessage: true
    })
    this.newOutgoingMessage = null
  }
 
  pickMember(member: Member) {
    const currentUsername: string = localStorage.getItem("user")
    if (!currentUsername) return
    const modifyResponse: (n: ResponseGetAllChatMessage) => ChatMessage = (n: ResponseGetAllChatMessage) => {
      const formattedDate = this.chatService.formatDate(new Date(n.time))
      return {
        isOutgoingMessage: currentUsername === n.from,
        text: n.text,
        date: formattedDate
      }
    }
    this.pickedMember = member
    this.chatService.getChatMessagesWithFriend(member.username)
      .pipe(map((response: ResponseGetAllChatMessage[]) => response.map(modifyResponse)))
      .subscribe((messages: ChatMessage[]) => this.openChatMessages = messages)
  }


  handleIncomingMessage(newMessage) {
    if (!newMessage.body) return
    const incomingMessage: newIncomingMessage = JSON.parse(newMessage.body)
    if (this.pickedMember && this.pickedMember.username === incomingMessage.from) {
      const pushedData: ChatMessage = {
        isOutgoingMessage: false,
        text: incomingMessage.text,
        date: this.chatService.formatDate(new Date())
      }
      this.openChatMessages.push(pushedData)
    }
  }

  connect(username: string) {
    let socket = new SockJS(environment.serverUrl + '/gs-guide-websocket;')
    this.stompClient = Stomp.over(socket)
    this.stompClient.connect({username}, () => {
      this.stompClient.subscribe("/topic/messages/" + username, (newMessage) => this.handleIncomingMessage(newMessage))
      this.stompClient.send("/app/user/addHeader/" + username )
    })
  }

  ngOnInit(): void {
    this.authService.checkToken()
    const username = localStorage.getItem("user")
    this.connect(username)
    this.refreshMembers()
  }

  refreshMembers() {
    this.chatService
      .findAllMembers()
      .subscribe(result => this.chatMembers = (result as Member[]), error  => console.log(error))
  }
}
